package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.FileUploadItem;
import com.skylaker.yunzhi.pojo.res.IResult;

/**
 * 文件上传逻辑处理定义接口
 *
 * User: zhuyong
 * Date: 2018/6/1 11:13
 */
public interface IFileUploadService {
    /**
     * 处理文件上传请求
     *
     * @param file 上传的文件
     * @return
     */
    IResult resolveFileUpload(FileUploadItem file);
}
