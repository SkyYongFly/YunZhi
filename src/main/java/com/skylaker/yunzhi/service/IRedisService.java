package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.*;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * Redis处理逻辑定义接口
 *
 * User: zhuyong
 * Date: 2018/6/2 11:50
 */
public interface IRedisService{
    /**
     * 保存用户头像缓存信息
     *
     * @param fileupload
     */
    void saveUserHeadImg(FileUploadItem fileupload);

    /**
     * 获取用户头像
     *
     * @param   userId      用户ID
     * @return  {string}    头像相对路径
     */
    String getUserHeadImg(Integer userId);

    /**
     * 将请求验证码手机号及请求时间缓存到redis
     *
     * @param  phone 手机号
     */
    void savePhoneHasSendSms(String phone);

    /**
     * 缓存已注册用户手机号信息
     *
     * @param   user   注册用户
     */
    void saveHasRegisterPhone(User user);

    /**
     * 从redis中获取缓存的手机号及请求时间以判断手机号是否在一分钟之内已发送过验证码
     *
     * @param   phone       手机号
     * @return  {boolean}   一分钟内发送过：true ；未发送过或者超过一分钟 ：false
     */
    boolean hasSendSmsInOneMin(String phone);

    /**
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  user         注册用户
     * @return {boolean}    验证通过：true；不通过：false
     */
    boolean validateVercode(User user);

    /**
     * 判断指定手机号用户是否已注册
     *
     * @param   phone   手机号
     * @return
     */
    boolean validatePhoneHasRegister(String phone);

    /**
     * 新增问题，注册到Redis
     *
     * @param question
     */
    void saveQuestionInfo(Question question);

    /**
     * 获取当前时刻热门问题的总数量
     *
     * @return
     */
    Long getHotQuestionsCount();

    /**
     * 从redis缓存中获取最热门的10条问题
     *
     * @param   page   分页查询页码
     * @param   token  缓存标识
     * @return
     */
    Set<ZSetOperations.TypedTuple<Object>> getHotQuestionsFromRedis(int page, String token);

    /**
     * 获取问题点赞数最多的回答
     *
     * @param question
     * @return
     */
    ZSetOperations.TypedTuple<Object> getQuestionMostStarAnswer(Question question);

    /**
     * 获取最新的10个问题
     *
     * @return
     */
    Set<Object> getNewestQuestions();

    /**
     * 获取指定时刻之前最新问题数量
     *
     * @param time
     * @return
     */
    Long getNewestQuestionsCountByTime(long time);

    /**
     * 查询指定时间内按照索引顺序最新的问题
     *
     * @param   page    分页查询页码
     * @param   time    查询时刻
     * @return
     */
    Set<Object> getNewestQuestionsByIndex(int page, long time);

    /**
     * 获取用户提问的所有问题的数量
     *
     * @param userId
     * @return
     */
    Long getUserQuestionsCount(Integer userId);

    /**
     * 新增回答后保存相关信息
     *
     * @param answer 新增的问题回答
     */
    void saveAnswerInfo(Answer answer);

    /**
     * 获取问题回答总数
     *
     * @param   qid  问题ID
     * @return
     */
    Long getQuestionAnswersCount(Integer qid);

    /**
     * 对回答点赞操作处理
     *
     * @param   aid  回答ID
     * @return
     */
    void staredAnswer(Integer qid, Integer aid);

    /**
     * 获取已经点赞的回答
     *
     * @param   aid  回答ID
     * @return
     */
    boolean hasStaredAnswer(Integer aid);

    /**
     * 获取回答点赞数
     *
     * @param qid   问题ID
     * @param aid   回答ID
     * @return
     */
    Long getAnswerStars(Integer qid, Integer aid);

    /**
     * 内容合法性验证
     *
     * @param text
     * @return {boolean} 合法：true ; 不合法 ：false
     */
    boolean legalValidate(String text);
}