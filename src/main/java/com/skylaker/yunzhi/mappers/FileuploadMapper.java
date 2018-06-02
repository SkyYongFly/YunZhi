package com.skylaker.yunzhi.mappers;

import com.skylaker.yunzhi.pojo.db.Fileupload;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * User: zhuyong
 * Date: 2018/6/1 11:33
 */
@Repository
public interface FileuploadMapper extends Mapper<Fileupload>, MySqlMapper<Fileupload> {
}
