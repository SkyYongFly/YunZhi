package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.Question;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 15:52
 * Description:
 */
public interface QuestionMapper extends Mapper<Question>,MySqlMapper<Question> {
}
