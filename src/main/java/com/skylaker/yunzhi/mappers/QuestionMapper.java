package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 15:52
 * Description:
 */
public interface QuestionMapper extends Mapper<Question>,MySqlMapper<Question> {
    /**
     * 查询指定问题列表详细信息
     *
     * @param   qids  问题ID字符串
     * @return
     */
    List<QuestionDetail> getQuestionDetailList(@Param("ids") String qids);
}
