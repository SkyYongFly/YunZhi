package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.Answer;
import com.skylaker.yunzhi.pojo.AnswerDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

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
    AnswerDetail getAnswerDetail(@Param("aid") Integer aid);
}
