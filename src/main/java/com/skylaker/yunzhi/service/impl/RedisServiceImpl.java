package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.db.*;
import com.skylaker.yunzhi.service.IRedisService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.skylaker.yunzhi.config.GlobalConstant.REDIS_ZSET_QUESTIONS_TIME;

/**
 * Redis具体逻辑处理类
 *
 * User: zhuyong
 * Date: 2018/6/2 11:50
 */
@Service("redisServiceImpl")
public class RedisServiceImpl implements IRedisService {
    //问题初始化热门指数
    private static final double QUESTION_HOT_INDEX = 0.0;
    //回答一个问题热门指数增加的值
    private static final double ANSWER_HOT_INDEX = 10.0;
    //点赞一次对应回答的问题热门指数增加的值
    private static final double STAR_HOT_INDEX = 1.0;

    @Autowired
    private  RedisUtil redisUtil;


    /**
     * 保存用户头像缓存信息
     *
     * @param fileUploadItem
     */
    @Override
    public void saveUserHeadImgInfo(FileUploadItem fileUploadItem) {
        if(GlobalConstant.FILE_TYPE_USER_HEAD_IMG.equals(fileUploadItem.getType())){
            redisUtil.setHashValue(GlobalConstant.REDIS_HASH_USER_HEAD_IMG,
                    String.valueOf(BaseUtil.getSessionUser().getId()), fileUploadItem.getFileRelativePath());
        }
    }


    /**
     * 将请求验证码手机号及请求时间缓存到redis
     *
     * @param  phone 手机号
     */
    @Override
    public void savePhoneHasSendSmsInfo(String phone) {
        redisUtil.setHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone, System.currentTimeMillis());
    }

    /**
     * 从redis中获取缓存的手机号及请求时间以判断手机号是否在一分钟之内已发送过验证码
     *
     * @param   phone       手机号
     * @return  {boolean}   一分钟内发送过：true ；未发送过或者超过一分钟 ：false
     */
    @Override
    public boolean hasSendSmsInOneMin(String phone) {
        long lastTime = 0;
        if(redisUtil.hasHashKey(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone)){
            lastTime = (long) redisUtil.getHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone);
        }

        long nowTime = System.currentTimeMillis();
        if(nowTime - lastTime < GlobalConstant.ONE_MINUTE_MICRO_SECONDS){
            return true;
        }

        return false;
    }


    /**
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  user         注册用户
     * @return {boolean}    验证通过：true；不通过：false
     */
    @Override
    public boolean validateVercode(User user) {
        if(BaseUtil.isNullOrEmpty(user.getVercode())){
            return false;
        }

        //判断是否存在指定键
        if(!redisUtil.hasHashKey(GlobalConstant.REDIS_HASH_PHONEVERCODES, user.getPhone())){
            return false;
        }

        //获取并判断验证码
        String vercode = (String) redisUtil.getHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODES, user.getPhone());
        if(!user.getVercode().equals(vercode)){
            return false;
        }

        return true;
    }

    /**
     * 缓存已注册用户手机号信息
     *
     * @param   user   注册用户
     */
    @Override
    public void saveHasRegisterPhone(User user){
        redisUtil.addSetValue(GlobalConstant.REDIS_SET_HASREGISTERPHONE, user.getPhone());
    }

    /**
     * 判断指定手机号用户是否已注册
     *
     * @param   phone   手机号
     * @return
     */
    @Override
    public boolean validatePhoneHasRegister(String phone) {
        if(BaseUtil.isNullOrEmpty(phone)){
            return false;
        }

        return  redisUtil.existInSet(GlobalConstant.REDIS_SET_HASREGISTERPHONE, phone);
    }

    /**
     * 从redis缓存中获取最热门的10条问题
     *
     * @param   page   分页查询页码
     * @param   token  缓存标识
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getHotQuestionsFromRedis(int page, String token) {
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
     * 获取问题点赞数最多的回答
     *
     * @param question
     * @return
     */
    @Override
    public ZSetOperations.TypedTuple<Object> getQuestionMostStarAnswer(Question question){
        if(null == question){
            return null;
        }

        //获取问题点赞数最多的回答
        Set<ZSetOperations.TypedTuple<Object>> hotAnswers = redisUtil.getZsetMaxKeysInScoresOfScoreInfoWithPage(
                BaseUtil.getRedisQuestionAnswersKey(question.getQid()),
                Double.valueOf(0.0),
                Double.POSITIVE_INFINITY,
                Long.valueOf(0),
                Long.valueOf(1));

        if(null == hotAnswers || 0 == hotAnswers.size()){
            return null;
        }

        //点赞数最多的回答
        return hotAnswers.iterator().next();
    }

    /**
     * 获取当前时刻热门问题的总数量
     *
     * @return
     */
    @Override
    public Long getHotQuestionsCount() {
        //当前用户查询最热门问题有序集合key
        String cacheHotQuestionsKey = BaseUtil.getCacheHotQuestionsKey();

        if(!redisUtil.existZset(cacheHotQuestionsKey)){
            return Long.valueOf(0);
        }

        return redisUtil.getZsetCount(cacheHotQuestionsKey, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * <p>
     *  保存相关信息到redis <br/>
     *
     *  1、新增回答记录到问题zset中 ，需要初始化点赞数为 0 <br/>
     *  2、修改回答对应的问题热门指数 <br/>
     * </p>
     *
     * @param answer 新增的问题回答
     */
    @Override
    public void saveInfoIntoRedis(Answer answer) {
        //新增回答记录到问题zset中
        redisUtil.addZsetValue(BaseUtil.getRedisQuestionAnswersKey(answer.getQid()), answer.getAid(), 0.0);

        //增加问题热门指数
        synchronized(this){
            redisUtil.increaseZsetScore(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, answer.getQid() , this.ANSWER_HOT_INDEX);
        }
    }

    /**
     * 获取用户头像
     *
     * @param userId
     * @return
     */
    @Override
    public String getUserHeadImg(Integer userId){
        if(null == userId){
            return null;
        }

        //先从redis中获取用户头像相对路径
        return  (String) redisUtil.getHashValue(
                GlobalConstant.REDIS_HASH_USER_HEAD_IMG, String.valueOf(userId));
    }

    /**
     * 获取问题回答总数
     *
     * @param   qid
     * @return
     */
    @Override
    public Long getQuestionAnswersCount(Integer qid){
        if(null == qid){
            return null;
        }

        return redisUtil.getZsetCount(BaseUtil.getRedisQuestionAnswersKey(qid), Double.valueOf(0), Double.POSITIVE_INFINITY);
    }

    /**
     * 对回答点赞
     *
     * @param aid
     * @return
     */
    @Override
    public void staredAnswer(Integer qid, Integer aid){
        //获取用户点赞的回答SET键名
        Object  userStarAnswers = BaseUtil.getSessionUser().getId() + GlobalConstant.REDIS_SET_STAR_ANSWERS;
        //判断用户是否哦对当前回答点赞过
        boolean hasStared =  redisUtil.existInSet(userStarAnswers, aid);

        //问题回答redis键名
        Object questionAnswersRedisKey = BaseUtil.getRedisQuestionAnswersKey(qid);
        //获取问题点赞数
        Long stars = redisUtil.getZsetKeyValue(questionAnswersRedisKey, aid).longValue();

        //已经点赞过
        if(hasStared){
            //redis 中回答点赞数减 1
            redisUtil.increaseZsetScore(questionAnswersRedisKey, aid, Double.valueOf(-1));
            //去除用户点赞的回答
            redisUtil.removeSetValue(userStarAnswers, aid);
        }
        //没有点赞过
        else{
            //redis 中回答点赞数加 1
            redisUtil.increaseZsetScore(questionAnswersRedisKey, aid, Double.valueOf(1));
            //缓存用户点赞的回答
            redisUtil.addSetValue(userStarAnswers, aid);
        }
    }

    @Override
    public boolean hasStaredAnswer(Integer aid) {
        //获取用户点赞的回答SET键名
        Object  userStarAnswers = BaseUtil.getSessionUser().getId() + GlobalConstant.REDIS_SET_STAR_ANSWERS;
        //判断用户是否哦对当前回答点赞过
        return redisUtil.existInSet(userStarAnswers, aid);
    }

    /**
     * 获取用户提问的所有问题的数量
     *
     * @param userId
     * @return
     */
    @Override
    public Long getUserQuestionsCount(Integer userId){
        //获取用户提问的所有问题数量
        return redisUtil.getSetCount(userId + GlobalConstant.REDIS_SET_USER_QUESTIONS);
    }

    /**
     * 获取回答点赞数
     *
     * @param qid
     * @param aid
     * @return
     */
    @Override
    public Long getAnswerStars(Integer qid, Integer aid){
        //问题回答redis键名
        Object questionAnswersRedisKey = BaseUtil.getRedisQuestionAnswersKey(qid);
        //获取问题点赞数
        return redisUtil.getZsetKeyValue(questionAnswersRedisKey, aid).longValue();
    }

    /**
     * 新增问题，注册到Redis
     *
     * @param question
     */
    @Override
    public void saveQuestionToRedis(Question question){
        JSONObject questionInfo = new JSONObject();
        questionInfo.put(String.valueOf(question.getQid()), question.getTitle());

        //保存问题时间戳
        redisUtil.addZsetValue(REDIS_ZSET_QUESTIONS_TIME,
                questionInfo.toJSONString() , Double.valueOf(System.currentTimeMillis()));

        //保存用户问题信息
        redisUtil.addSetValue(BaseUtil.getSessionUser().getId() + GlobalConstant.REDIS_SET_USER_QUESTIONS, question.getQid());

        //初始化问题热门指数
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, question.getQid(), this.QUESTION_HOT_INDEX);
    }


    /**
     * 获取最新的10个问题
     *
     * @return
     */
    @Override
    public Set<Object> getNewestQuestions(){
        //从Redis中获取最新的10个问题
        return redisUtil.getZsetMaxKeys(
                REDIS_ZSET_QUESTIONS_TIME, 0 , Long.valueOf(GlobalConstant.QUESTIONS_NUM -1));
    }

    @Override
    public Long getNewestQuestionsCountByTime(long time) {
        if(time < 0){
            return Long.valueOf(0);
        }

        return redisUtil.getZsetCount(REDIS_ZSET_QUESTIONS_TIME, 0.0, time);
    }

    /**
     * 查询指定时间内按照索引顺序最新的问题
     *
     * @param page
     * @param time
     * @return
     */
    @Override
    public Set<Object> getNewestQuestionsByIndex(int page, long time){
        if(page < 1){
            return null;
        }

        //计算索引开始位置
        int index = (page - 1) * GlobalConstant.QUESTIONS_NUM;

        //查询问题列表，每次查询10条
        return  redisUtil.getZsetMaxKeysInScoresWithPage(REDIS_ZSET_QUESTIONS_TIME,
                0.0, Double.valueOf(time), Long.valueOf(index), GlobalConstant.QUESTIONS_NUM);
    }

}