package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.com.PageInfo;
import com.skylaker.yunzhi.pojo.db.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 问题回答SQL mapper
 *
 * User: zhuyong
 * Date: 2018/5/28 21:46
 */
@Repository
public interface AnswerMapper extends Mapper<Answer>, MySqlMapper<Answer> {
    /**
     * 获取回答详情
     *
     * @param   aid  回答ID
     * @return
     */
    Answer getAnswer(@Param("aid") Integer aid);

    /**
     * 页查询问题回答
     *
     * @param pageInfo
     * @return
     */
    List<Answer> getQuestionAllAnswers(@Param("pageInfo")  PageInfo pageInfo);

    /**
     * 增加回答的点赞数
     *
     * @param aid 回答ID
     */
    void increaseStarsNum(Integer aid);

    /**
     * 减少回答的点赞数
     *
     * @param aid 回答ID
     */
    void decreaseStarsNum(Integer aid);
}
