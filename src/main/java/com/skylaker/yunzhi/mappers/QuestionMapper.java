package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.com.PageInfo;
import com.skylaker.yunzhi.pojo.db.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 问题SQL Mapper
 *
 * User: zhuyong
 * Date: 2018/5/26 15:52
 */
@Repository
public interface QuestionMapper extends Mapper<Question>,MySqlMapper<Question> {

    /**
     * 查询用户所有的问题
     *
     * @param   pageInfo
     * @return
     */
    List<Question> getUserQuestions(@Param("pageInfo")PageInfo pageInfo);

    /**
     * 查询指定问题列表详细信息
     *
     * @param   qids  问题ID字符串
     * @return
     */
    List<Question> getQuestionsList(@Param("qids")String qids);
}