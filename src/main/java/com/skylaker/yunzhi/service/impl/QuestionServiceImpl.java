package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.*;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
    private QuestionMapper questionMapper;

    @Autowired
    @Qualifier("hotQuestionServiceImpl")
    private IHotQuestionService hotQuestionService;

    @Resource(name = "userServiceImpl")
    private IUserService userService;

    //不能定义成成员变量，因为框架初始化时加载类，且是单例，这个时候尚未登录，无法获取登录信息
    //private Integer userId = BaseUtil.getSessionUser().getId();

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

        //保存用户问题信息
        redisUtil.addSetValue(BaseUtil.getSessionUser().getId() + GlobalConstant.USER_QUESTIONS, question.getQid());

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
    public QuestionDetail getQuestionDetail(int qid) {
        //查询问题详情
        QuestionDetail question =  new QuestionDetail(selectByKey(qid));

        //查询问题的回答数
        question.setAnswersnum(
                redisUtil.getZsetCount(
                        BaseUtil.getRedisQuestionAnswersKey(qid),
                        Double.valueOf(0),
                        Double.POSITIVE_INFINITY));

        return question;
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
        setQuestionInfo(questionDetailList);

        return questionDetailList;
    }

    /**
     * 设置问题相关信息
     *
     * @param questionDetailList
     */
    private void setQuestionInfo(List<QuestionDetail> questionDetailList) {
        for(int i = 0, len = questionDetailList.size(); i < len; i++){
            QuestionDetail questionDetail = questionDetailList.get(i);

            //设置问题回答数量
            questionDetail.setAnswersnum(getQuestionAnswers(questionDetail.getQid()));
            //设置用户头像
            questionDetail.setUserheadimg(userService.getUserHeadImg(questionDetail.getUserid()));
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


    @Override
    public QuestionsList getUserQuestions(int page) {
        Integer userId = BaseUtil.getSessionUser().getId();

        //分页查询用户问题
        PageInfo pageInfo = new PageInfo(page, GlobalConstant.QUESTIONS_NUM);
        pageInfo.setUserid(userId);
        List<QuestionDetail> questionsList = questionMapper.getUserQuestions(pageInfo);

        //获取用户提问的所有问题数量
        Long questionsCount = redisUtil.getSetCount(userId + GlobalConstant.USER_QUESTIONS);

        if(questionsCount < questionsList.size()){  // 缓存失效
            Example example = new Example(Question.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userid", userId);

            questionsCount = Long.valueOf(super.getCount(example));
        }

        return new QuestionsList(questionsList, questionsCount);
    }
}