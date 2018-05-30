package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.*;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.skylaker.yunzhi.config.GlobalConstant.REDIS_ZSET_QUESTIONS_TIME;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 15:48
 * Description:
 *      问题相关逻辑处理
 */
@Service("questionServiceImpl")
public class QuestionServiceImpl extends BaseService<Question> implements IQuestionService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Qualifier("notQuestionServiceImpl")
    private IHotQuestionService hotQuestionService;

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public QuestionResult addQuestion(Question question) {
        if(BaseUtil.isNullOrEmpty(question.getTitle())){
            return QuestionResult.NULL_TITLE;
        }

        //TODO 后期对于话题的判断
        User user = BaseUtil.getSessionUser();
        if(null == user){
            throw new UnknownAccountException();
        }

        question.setUserid(user.getId());
        question.setCreatetime(new Date());
        question.setUpdatetime(new Date());

        //TODO 如何做到同步
        //保存问题到DB
        super.save(question);
        //缓存问题信息
        saveQuestionToRedis(question);

        return QuestionResult.SUCCESS;
    }

    /**
     * 新增问题，注册到Redis
     *
     * @param question
     */
    private void saveQuestionToRedis(Question question){
        JSONObject questionInfo = new JSONObject();
        questionInfo.put(String.valueOf(question.getQid()), question.getTitle());

        //保存问题时间戳
        redisUtil.addZsetValue(REDIS_ZSET_QUESTIONS_TIME,
                   questionInfo.toJSONString() , Double.valueOf(System.currentTimeMillis()));

        //初始化问题热门指数
        hotQuestionService.initQuestionHotIndex(question.getQid());
    }


    @Override
    public List<Question> getNewestQuestions() {
       //从Redis中获取最新的10个问题
        Set<Object> questions = redisUtil.getZsetMaxKeys(
                REDIS_ZSET_QUESTIONS_TIME, 0 , Long.valueOf(GlobalConstant.QUESTIONS_NUM -1));

        List<Question> result = new ArrayList<>();

        //解析问题标题信息
        Iterator<Object> iterator = questions.iterator();
        while (iterator.hasNext()){
            JSONObject json = JSONObject.parseObject(iterator.next().toString());

            Question question = new Question();
            String key = json.keySet().iterator().next();
            question.setQid(Integer.valueOf(key));
            question.setTitle((String) json.get(key));

            result.add(question);
        }

        return result;
    }

    /**
     * 查询问题相信信息
     *
     * @param   qid 问题ID
     * @return
     */
    @Override
    @Cacheable(value = "questionCache")
    public Question getQuestionDetail(String qid) {
        return selectByKey(qid);
    }

    /**
     * 获取指定时间戳之前最新的问题列表，需要根据信息过滤
     *
     * @param   page    分页信息，第几页
     * @param   time    用户请求时间戳
     * @return
     */
    @Override
    public List<QuestionDetail> getNewestQuestionsDetails(int page, long time) {
        if(page < 1){
            return null;
        }

        //计算索引开始位置
        int index = (page - 1) * GlobalConstant.QUESTIONS_NUM;

        //查询问题列表，每次查询10条
        Set<Object> questions =  redisUtil.getZsetMaxKeysInScoresWithPage(
                REDIS_ZSET_QUESTIONS_TIME, 0.0, Double.valueOf(time), Long.valueOf(index), GlobalConstant.QUESTIONS_NUM);

        if(null == questions || questions.size() < 1){
            return null;
        }

        StringBuilder builder = new StringBuilder();

        //解析问题标题信息
        Iterator<Object> iterator = questions.iterator();
        while (iterator.hasNext()){
            int key = Integer.valueOf(JSONObject.parseObject(iterator.next().toString()).keySet().iterator().next());
            builder.append(key).append(",");
        }

        //去掉最后的逗号，获取查询详细信息的问题ID字符串
        String qids = builder.toString().substring(0, builder.length() - 1);

        //获取指定问题列表的详细信息
        List<QuestionDetail> questionDetailList = questionMapper.getQuestionDetailList(qids);
        setQuestionAnswersInfo(questionDetailList);

        return questionDetailList;
    }

    /**
     * 获取问题的回答数
     *
     * @param questionDetailList
     */
    private void setQuestionAnswersInfo(List<QuestionDetail> questionDetailList) {
        for(int i = 0, len = questionDetailList.size(); i < len; i++){
            QuestionDetail questionDetail = questionDetailList.get(i);
            questionDetail.setAnswersnum(getQuestionAnswers(questionDetail.getQid()));
        }
    }

    @Override
    public Long getNewestQuestionsCount(long time) {
        if(time < 0){
            return Long.valueOf(0);
        }

        return redisUtil.getZsetCount(REDIS_ZSET_QUESTIONS_TIME, 0.0, time);
    }

    @Override
    public Long getQuestionAnswers(int qid) {
        if(null == Integer.valueOf(qid)){
            return Long.valueOf(0);
        }

        return redisUtil.getZsetCount(qid + GlobalConstant.REDIS_ZSET_QUESTION_ANSWERS,
                Double.valueOf(0), Double.POSITIVE_INFINITY);
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

        //当前用户查询最热门问题有序集合key
        Integer userId = BaseUtil.getSessionUser().getId();
        String cacheHotQuestionsKey = userId + "_" +  GlobalConstant.REDIS_ZSET_QUESTIONS_HOT;

        //没有缓存标识，说明之前未请求过；或者没有目标zset
        if(BaseUtil.isNullOrEmpty(token) || !redisUtil.existZset(cacheHotQuestionsKey)){
            //获取最热门问题集合，并缓存
            redisUtil.cacheSortedZset(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, cacheHotQuestionsKey);
        }

        //计算索引开始位置
        int index = (page - 1) * GlobalConstant.QUESTIONS_NUM;

        //查询问题列表，每次查询10条
        Set<Object> questions =  redisUtil.getZsetMaxKeys(cacheHotQuestionsKey, index, index + GlobalConstant.QUESTIONS_NUM - 1);

        if(null == questions || questions.size() < 1){
            return null;
        }

        StringBuilder builder = new StringBuilder();

        //获取当前页问题ID
        Iterator<Object> iterator = questions.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next()).append(",");
        }
        //去掉最后的逗号，获取查询详细信息的问题ID字符串
        String qids = builder.toString().substring(0, builder.length() - 1);

        //获取指定问题列表的详细信息
        List<QuestionDetail> questionDetailList = questionMapper.getQuestionDetailList(qids);




        return null;
    }
}