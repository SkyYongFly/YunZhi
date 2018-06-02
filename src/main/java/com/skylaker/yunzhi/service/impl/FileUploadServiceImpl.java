package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.db.FileUploadItem;
import com.skylaker.yunzhi.pojo.db.Fileupload;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.service.IFileUploadService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传处理逻辑类
 *
 * User: zhuyong
 * Date: 2018/6/1 11:16
 */
@Service("fileUploadServiceImpl")
public class FileUploadServiceImpl extends BaseService<Fileupload> implements IFileUploadService {
    /**
     * 处理文件上传
     *
     * @param   fileUploadItem
     * @return  {Enum}
     */
    @Override
    public IResult resolveFileUpload(FileUploadItem fileUploadItem) {
        if(null == fileUploadItem || BaseUtil.isNullOrEmpty(fileUploadItem.getFileName())){
            return BaseResult.FAILTURE;
        }

        try {
            //保存文件到磁盘
            File desPath = new File(fileUploadItem.getFileDiskPath());
            FileUtils.copyInputStreamToFile(fileUploadItem.getInputStream(), desPath);

            //保存文件记录到数据库
            saveToDb(fileUploadItem);

            //保存用户头像缓存信息
            redisService.saveUserHeadImg(fileUploadItem);

            return BaseResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResult.FAILTURE;
        } catch (Exception e){
            e.printStackTrace();
            return BaseResult.FAILTURE;
        }
    }

    /**
     * 保存文件记录到数据库
     *
     * @param fileUploadItem
     */
    @Transactional
    public void saveToDb(FileUploadItem fileUploadItem) {
        Fileupload fileupload = fileUploadItem;
        super.save(fileupload);
    }
}