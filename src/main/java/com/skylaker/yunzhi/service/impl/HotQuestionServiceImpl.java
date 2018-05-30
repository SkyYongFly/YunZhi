package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.AnswerMapper;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.AnswerDetail;
import com.skylaker.yunzhi.pojo.HotQuestionsList;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  问题热门处理逻辑类 <br/>
 *
 *  热门指数计算公式：问题回答数 * 10.0 + 问题回答点赞数 * 1.0
 * </p>
 *
 * User: zhuyong
 * Date: 2018/5/28 20:59
 */
@Service("hotQuestionServiceImpl")
public class HotQuestionServiceImpl implements IHotQuestionService {
    //问题初始化热门指数
    private static final double QUESTION_HOT_INDEX = 0.0;
    //回答一个问题热门指数增加的值
    private static final double ANSWER_HOT_INDEX = 10.0;
    //点赞一次对应回答的问题热门指数增加的值
    private static final double STAR_HOT_INDEX = 1.0;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;



    @Override
    public void initQuestionHotIndex(int qid) {
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, qid, this.QUESTION_HOT_INDEX);
    }

    @Override
    public synchronized void updateQuestionHotIndexOfAnswer(int qid) {
        redisUtil.increaseZsetScore(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, qid , this.ANSWER_HOT_INDEX);
    }

    /**
     * 获取最热门的问题集合
     *
     * @param page  页码
     * @param token 缓存标识
     * @return
     */
    @Override
    public HotQuestionsList getHotQuestionsDetailsByPage(int page, String token) {
        if(page < 1){
            return null;
        }

        //从redis缓存中获取最热门的10条问题
        Set<ZSetOperations.TypedTuple<Object>> questions = getHotQuestionsFromRedis(page, token);
        if(null == questions || questions.size() < 1){
            return null;
        }

        //获取热门问题详情
        List<QuestionDetail> questionDetailList = getQuestionsDetail(questions);

        //设置热门问题点赞数最高的回答信息
        setQuestionHotAnswer(questionDetailList);

        //获取当前时刻热门问题的总数量
        Long num = getHotQuestionsCount();

        return new HotQuestionsList(questionDetailList, num, "HASHOTZSETCACHE");
    }

    /**
     * 从redis缓存中获取最热门的10条问题
     *
     * @param   page   分页查询页码
     * @param   token  缓存标识
     * @return
     */
    private Set<ZSetOperations.TypedTuple<Object>> getHotQuestionsFromRedis(int page, String token) {
        //当前用户查询最热门问题有序集合key
        String cacheHotQuestionsKey = BaseUtil.getCacheHotQuestionsKey();

        //没有缓存标识，说明之前未请求过；或者没有目标zset
        if(BaseUtil.isNullOrEmpty(token) || !redisUtil.existZset(cacheHotQuestionsKey)){
            //如果刷新页面重新请求，需要先把旧的缓存清除
            redisUtil.deleteKey(cacheHotQuestionsKey);
            //获取最热门问题集合，并缓存
            redisUtil.cacheSortedZset(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, cacheHotQuestionsKey);
        }

        //计算索引开始位置
        int index = (page - 1) * GlobalConstant.QUESTIONS_NUM;

        //查询问题列表，每次查询10条
        return redisUtil.getZsetMaxKeysOfScores(cacheHotQuestionsKey, index, index + GlobalConstant.QUESTIONS_NUM - 1);
    }

    /**
     * 获取热门问题详情
     *
     * @param   questions   热门问题
     * @return
     */
    private List<QuestionDetail> getQuestionsDetail(Set<ZSetOperations.TypedTuple<Object>> questions) {
        StringBuilder builder = new StringBuilder();

        //获取当前页问题ID
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = questions.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next().getValue()).append(",");
        }
        //去掉最后的逗号，获取查询详细信息的问题ID字符串
        String qids = builder.toString().substring(0, builder.length() - 1);

        //获取指定问题列表的详细信息
        List<QuestionDetail> questionDetailList = questionMapper.getQuestionDetailList(qids);

        //设置问题的热门指数
        Iterator<ZSetOperations.TypedTuple<Object>> iterator2 = questions.iterator();
        while (iterator2.hasNext()){
            ZSetOperations.TypedTuple<Object> zsetKV = iterator2.next();

            //先根据问题ID获取问题，再设置热门指数
            QuestionDetail questionDetail =  getQuestionFromListByQid(questionDetailList, (Integer) zsetKV.getValue());
            if(null != questionDetail){
                questionDetail.setHotIndex(zsetKV.getScore());
            }else {
                questionDetail.setHotIndex(Double.valueOf(0));
            }
        }

        //按照热门指数重新排序
        questionDetailList.sort(new Comparator<QuestionDetail>() {
            @Override
            public int compare(QuestionDetail qus1, QuestionDetail qus2) {
                int res = Double.valueOf(qus2.getHotIndex() - qus1.getHotIndex()).intValue();
                if(0 == res){
                    return Long.valueOf(qus2.getCreatetime().getTime() - qus1.getCreatetime().getTime()).intValue();
                }

                return  res;
            }
        });

        return questionDetailList;
    }

    /**
     * 设置热门问题点赞数最高的回答信息
     *
     * @param questionDetailList    热门问题
     */
    private void setQuestionHotAnswer(List<QuestionDetail> questionDetailList) {
        if(null == questionDetailList || 0 == questionDetailList.size()){
            return;
        }

        for(int i = 0, len = questionDetailList.size(); i < len; i++){
            //获取问题点赞数最多的回答
            Set<ZSetOperations.TypedTuple<Object>> hotAnswer =
                    redisUtil.getZsetMaxKeysInScoresOfScoreInfoWithPage(
                            BaseUtil.getRedisQuestionAnswersKey(questionDetailList.get(i).getQid()),
                            Double.valueOf(0.0),
                            Double.POSITIVE_INFINITY,
                            Long.valueOf(0),
                            Long.valueOf(1));

            if(null == hotAnswer || 0 == hotAnswer.size()){
                continue;
            }

            //点赞数最多的回答
            ZSetOperations.TypedTuple<Object> answer = hotAnswer.iterator().next();
            //获取点赞数最多回答详情
            AnswerDetail answerDetail = answerMapper.getAnswerDetail((Integer) answer.getValue());

            //设置问题点赞最多回答信息
            questionDetailList.get(i).setAnswerInfo(answerDetail);
            questionDetailList.get(i).setHotstar(answer.getScore().longValue());
        }
    }

    /**
     * 获取当前时刻热门问题的总数量
     *
     * @return
     */
    private Long getHotQuestionsCount() {
        //当前用户查询最热门问题有序集合key
        String cacheHotQuestionsKey = BaseUtil.getCacheHotQuestionsKey();

        if(!redisUtil.existZset(cacheHotQuestionsKey)){
            return Long.valueOf(0);
        }

        return redisUtil.getZsetCount(cacheHotQuestionsKey, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * 从问题集合中获取指定问题ID的问题
     *
     * @param   questionDetailList    问题集合
     * @param   qid                   问题ID
     * @return
     */
    private QuestionDetail getQuestionFromListByQid(List<QuestionDetail> questionDetailList, Integer qid) {
        if(null == questionDetailList || 0 == questionDetailList.size() || null == qid || 0 == qid.intValue()){
            return null;
        }

        for (int i = 0, len = questionDetailList.size(); i < len; i++) {
            if(questionDetailList.get(i).getQid().intValue() == qid.intValue()){
                return questionDetailList.get(i);
            }
        }

        return null;
    }

}