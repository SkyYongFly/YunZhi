package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.com.PageInfo;
import com.skylaker.yunzhi.pojo.db.*;
import com.skylaker.yunzhi.pojo.res.QuestionResult;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 问题相关逻辑处理
 *
 * User: zhuyong
 * Date: 2018/5/26 15:48
 */
@Service("questionServiceImpl")
public class QuestionServiceImpl extends BaseService<Question> implements IQuestionService {
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
        redisService.saveQuestionToRedis(question);

        return QuestionResult.SUCCESS;
    }


    @Override
    public List<Question> getNewestQuestions() {
       //从Redis中获取最新的10个问题
        Set<Object> questions = redisService.getNewestQuestions();

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
     * 查询问题相关信息
     *
     * @param   qid 问题ID
     * @return
     */
    @Override
    @Cacheable(value = "questionCache")
    public Question getQuestion(int qid) {
        //查询问题详情
        Question question =  new Question(selectByKey(qid));
        //设置问题的回答数
        question.setAnswersnum(redisService.getQuestionAnswersCount(qid));

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
    public QuestionsList getNewestQuestionsDetails(int page, long time) {
        //查询问题列表，每次查询10条
        Set<Object> questions =  redisService.getNewestQuestionsByIndex(page, time);

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
        List<Question> questionsList = questionMapper.getQuestionsList(qids);
        setQuestionInfo(questionsList);

        //获取要展示的问题总数量
        Long sum = redisService.getNewestQuestionsCountByTime(time);

        return new QuestionsList(questionsList, sum);
    }

    /**
     * 设置问题相关信息
     *
     * @param questionDetailList
     */
    private void setQuestionInfo(List<Question> questionDetailList) {
        for(int i = 0, len = questionDetailList.size(); i < len; i++){
            Question questionDetail = questionDetailList.get(i);

            //设置问题回答数量
            questionDetail.setAnswersnum(redisService.getQuestionAnswersCount(questionDetail.getQid()));
            //设置用户头像
            questionDetail.setUserheadimg(userService.getUserHeadImg(questionDetail.getUserid()));
        }
    }

    @Override
    public QuestionsList getUserQuestions(int page) {
        Integer userId = BaseUtil.getSessionUser().getId();

        //分页查询用户问题
        PageInfo pageInfo = new PageInfo(page, GlobalConstant.QUESTIONS_NUM);
        pageInfo.setUserid(userId);
        List<Question> questionsList = questionMapper.getUserQuestions(pageInfo);

        //获取用户提问的所有问题数量
        Long questionsCount = redisService.getUserQuestionsCount(userId);

        if(questionsCount < questionsList.size()){  // 缓存失效
            Example example = new Example(Question.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userid", userId);

            questionsCount = Long.valueOf(super.getCount(example));
        }

        return new QuestionsList(questionsList, questionsCount);
    }
}