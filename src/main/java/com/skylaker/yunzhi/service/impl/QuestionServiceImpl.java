package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.AnswerMapper;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.com.PageInfo;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.db.QuestionsList;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.pojo.res.QuestionResult;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  问题相关逻辑处理   <br/>
 *
 *  热门指数计算公式：问题回答数 * 10.0 + 问题回答点赞数 * 1.0
 * </p>
 * User: zhuyong
 * Date: 2018/5/26 15:48
 */
@Service("questionServiceImpl")
public class QuestionServiceImpl extends BaseService<Question> implements IQuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Resource(name = "userServiceImpl")
    private IUserService userService;


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
        redisService.saveQuestionInfo(question);

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

    /**
     * 分页查询最热门的问题集合，注意这里分页是从redis中查询
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
            return new QuestionsList(null, Long.valueOf(0), null);
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

            //设置点赞数最多回答人员头像
            questionList.get(i).setHotuserheadimg(
                    userService.getUserHeadImg(questionList.get(i).getHotuserid()));
        }
    }

    /**
     * 从问题集合中获取指定问题ID的问题
     *
     * @param   questionList    问题集合
     * @param   qid             问题ID
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