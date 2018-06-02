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
     void saveUserHeadImgInfo(FileUploadItem fileupload);

    void savePhoneHasSendSmsInfo(String phone);

    boolean hasSendSmsInOneMin(String phone);

    boolean validateVercode(User user);

    void saveHasRegisterPhone(User user);

    boolean validatePhoneHasRegister(String phone);


    Set<ZSetOperations.TypedTuple<Object>> getHotQuestionsFromRedis(int page, String token);

    ZSetOperations.TypedTuple<Object> getQuestionMostStarAnswer(Question question);

    Long getHotQuestionsCount();

    void saveInfoIntoRedis(Answer answer);

    String getUserHeadImg(Integer userId);

    Long getQuestionAnswersCount(Integer qid);

    boolean hasStaredAnswer(Integer aid);

    void staredAnswer(Integer qid, Integer aid);

    Long getUserQuestionsCount(Integer userId);

    Long getAnswerStars(Integer qid, Integer aid);

    void saveQuestionToRedis(Question question);

    Set<Object> getNewestQuestions();

    Long getNewestQuestionsCountByTime(long time);

    Set<Object> getNewestQuestionsByIndex(int page, long time);
}