package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.AnswerMapper;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.db.QuestionsList;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class HotQuestionServiceImpl extends BaseService<Question> implements IHotQuestionService {
    //问题初始化热门指数
    private static final double QUESTION_HOT_INDEX = 0.0;
    //回答一个问题热门指数增加的值
    private static final double ANSWER_HOT_INDEX = 10.0;
    //点赞一次对应回答的问题热门指数增加的值
    private static final double STAR_HOT_INDEX = 1.0;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Resource(name = "userServiceImpl")
    private IUserService userService;

    /**
     * 获取最热门的问题集合
     *
     * @param page  页码
     * @param token 缓存标识
     * @return
     */
    @Override
    public QuestionsList getHotQuestionsByPage(int page, String token) {
        if(page < 1){
            return null;
        }

        //从redis缓存中获取最热门的10条问题
        Set<ZSetOperations.TypedTuple<Object>> questions = redisService.getHotQuestionsFromRedis(page, token);
        if(null == questions || questions.size() < 1){
            return null;
        }

        //获取热门问题详情
        List<Question> questionsList = getQuestions(questions);

        //设置热门问题点赞数最高的回答信息
        setQuestionHotAnswer(questionsList);

        //获取当前时刻热门问题的总数量
        Long num = redisService.getHotQuestionsCount();

        return new QuestionsList(questionsList, num, "HASHOTZSETCACHE");
    }


    /**
     * 获取热门问题详情
     *
     * @param   questions   热门问题
     * @return
     */
    private List<Question> getQuestions(Set<ZSetOperations.TypedTuple<Object>> questions) {
        StringBuilder builder = new StringBuilder();

        //获取当前页问题ID
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = questions.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next().getValue()).append(",");
        }
        //去掉最后的逗号，获取查询详细信息的问题ID字符串
        String qids = builder.toString().substring(0, builder.length() - 1);

        //获取指定问题列表的详细信息
        List<Question> questionList = questionMapper.getQuestionsList(qids);

        //设置问题的热门指数
        Iterator<ZSetOperations.TypedTuple<Object>> iterator2 = questions.iterator();
        while (iterator2.hasNext()){
            ZSetOperations.TypedTuple<Object> zsetKV = iterator2.next();

            //先根据问题ID获取问题，再设置热门指数
            Question question =  getQuestionFromListByQid(questionList, (Integer) zsetKV.getValue());
            if(null != question){
                question.setHotIndex(zsetKV.getScore());
            }else {
                question.setHotIndex(Double.valueOf(0));
            }
        }

        //按照热门指数重新排序
        questionList.sort(new Comparator<Question>() {
            @Override
            public int compare(Question qus1, Question qus2) {
                int res = Double.valueOf(qus2.getHotIndex() - qus1.getHotIndex()).intValue();
                if(0 == res){
                    return Long.valueOf(qus2.getCreatetime().getTime() - qus1.getCreatetime().getTime()).intValue();
                }

                return  res;
            }
        });

        return questionList;
    }

    /**
     * 设置热门问题点赞数最高的回答信息
     *
     * @param questionList    热门问题
     */
    private void setQuestionHotAnswer(List<Question> questionList) {
        if(null == questionList || 0 == questionList.size()){
            return;
        }

        for(int i = 0, len = questionList.size(); i < len; i++){
            //点赞数最多的回答
            ZSetOperations.TypedTuple<Object> hotAnswer = redisService.getQuestionMostStarAnswer(questionList.get(i));
            if(null == hotAnswer){
                continue;
            }

            //获取点赞数最多回答详情
            Answer answer = answerMapper.getAnswer((Integer) hotAnswer.getValue());

            //设置问题点赞最多回答信息
            questionList.get(i).setAnswerInfo(answer);
            questionList.get(i).setHotstar(hotAnswer.getScore().longValue());

            //设置点赞数最多回答人员头像相对路径
            questionList.get(i).setHotuserheadimg(
                    userService.getUserHeadImg(questionList.get(i).getHotuserid()));
        }
    }

    /**
     * 从问题集合中获取指定问题ID的问题
     *
     * @param   questionList    问题集合
     * @param   qid                   问题ID
     * @return
     */
    private Question getQuestionFromListByQid(List<Question> questionList, Integer qid) {
        if(null == questionList || 0 == questionList.size() || null == qid || 0 == qid.intValue()){
            return null;
        }

        for (int i = 0, len = questionList.size(); i < len; i++) {
            if(questionList.get(i).getQid().intValue() == qid.intValue()){
                return questionList.get(i);
            }
        }

        return null;
    }

}