package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.Answer;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 问题回答SQL mapper
 *
 * User: zhuyong
 * Date: 2018/5/28 21:46
 */
public interface AnswerMapper extends Mapper<Answer>, MySqlMapper<Answer> {
}
