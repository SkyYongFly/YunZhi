package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import com.skylaker.yunzhi.pojo.QuestionResult;
import com.skylaker.yunzhi.pojo.User;
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
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_TIME,
                   questionInfo.toJSONString() , Double.valueOf(System.currentTimeMillis()));

        //初始化问题热门指数
        hotQuestionService.initQuestionHotIndex(question.getQid());
    }


    @Override
    public List<Question> getNewestQuestions() {
       //从Redis中获取最新的10个问题
        Set<Object> questions = redisUtil.getZsetMaxKeys(
                GlobalConstant.REDIS_ZSET_QUESTIONS_TIME, 0 , Long.valueOf(GlobalConstant.QUESTIONS_NUM -1));

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
                GlobalConstant.REDIS_ZSET_QUESTIONS_TIME, Double.valueOf(time), 0.0, Long.valueOf(index), GlobalConstant.QUESTIONS_NUM);

        if(null == questions || questions.size() < 1){
            return null;
        }

        StringBuilder builder = new StringBuilder();

        //解析问题标题信息
        Iterator<Object> iterator = questions.iterator();
        while (iterator.hasNext()){
            int key = Integer.valueOf(JSONObject.parseObject(iterator.next().toString()).keySet().iterator().next());

            if(iterator.hasNext()){
                builder.append(key).append(",");
            }
        }

        //获取指定问题列表的详细信息
        return questionMapper.getQuestionDetailList(builder.toString());
    }
}