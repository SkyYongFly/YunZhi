package com.skylaker.yunzhi.pojo.db;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 文件操作
 *
 * User: zhuyong
 * Date: 2018/6/1 14:36
 */
public class FileUploadItem extends  Fileupload{
    private static final String pathPropertiesName = "filepath.properties";

    //上传的文件对象
    private MultipartFile multipartFile;
    //业务类型
    private String type;

    private String newFileName;


    public FileUploadItem(MultipartFile multipartFile, String type){
        this.multipartFile = multipartFile;
        this.type = type;

        super.setFtype(multipartFile.getContentType());
        super.setSize(Double.valueOf(multipartFile.getSize() / 1000.0));
        super.setFname(getFileName());
        super.setFpath(getFileDiskPath());
        super.setFspath(getFileRelativePath());
        super.setTime(new Date());
        super.setUserid(BaseUtil.getSessionUser().getId());
        super.setFcode(getFcodeByType());
    }

    /**
     * 获取文件上传输入流
     *
     * @return
     */
    public InputStream getInputStream() {
        try {
            return  this.multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件原有名称
     *
     * @return  {strng}
     */
    public String getFileName() {
        return multipartFile.getOriginalFilename();
    }

    /**
     * 获取文件系统中保存的名称
     *
     * @return  {string} 文件新名称,如果原有文件为空则返回空
     */
    public String getNewFileName() {
        if(BaseUtil.isNullOrEmpty(this.newFileName)){
            String fileName = getFileName();
            if(BaseUtil.isNullOrEmpty(fileName)){
                return null;
            }

            this.newFileName = BaseUtil.getUUID() + fileName.substring(fileName.lastIndexOf("."));
        }

        return this.newFileName;
    }

    /**
     * 保存文件保存的相对路径
     *
     * @return
     */
    public String getFileRelativePath() {
        String fileRelativeRootPath = getFileRelativeRootPath();

        if(BaseUtil.isNullOrEmpty(fileRelativeRootPath)){
            return null;
        }

        return fileRelativeRootPath + getNewFileName();
    }

    /**
     * 获取文件磁盘保存绝对路径
     *
     * @return
     */
    public String getFileDiskPath() {
        String fileDiskRootPath = getFileDiskRootPath();

        if(BaseUtil.isNullOrEmpty(fileDiskRootPath)){
            return null;
        }

        return fileDiskRootPath + getNewFileName();
    }

    /**
     * 根据配置文件获取文件保存磁盘根路径
     *
     * @return
     */
    private String getFileDiskRootPath(){
        if(BaseUtil.isNullOrEmpty(this.type)){
            return null;
        }

        String propertyName = null;
        if(GlobalConstant.FILE_TYPE_USER_HEAD_IMG.equals(this.type)){
            propertyName = "file.disk.path.userhead";

        }else if(GlobalConstant.FILE_TYPE_ANSWERS_IMG.equals(this.type)){
            propertyName = "file.disk.path.answerimg";
        }

        return BaseUtil.getPropertyValue(pathPropertiesName, propertyName);
    }

    /**
     * 根据配置文件获取文件保存磁盘根路径
     *
     * @return
     */
    private String getFileRelativeRootPath(){
        if(BaseUtil.isNullOrEmpty(this.type)){
            return null;
        }

        String propertyName = null;
        if(GlobalConstant.FILE_TYPE_USER_HEAD_IMG.equals(this.type)){
            propertyName = "file.relative.path.userhead";

        }else if(GlobalConstant.FILE_TYPE_ANSWERS_IMG.equals(this.type)){
            propertyName = "file.relative.path.answerimg";
        }

        return BaseUtil.getPropertyValue(pathPropertiesName, propertyName);
    }

    /**
     * 根据业务类型获取附件记录保存的标识
     *
     * @return
     */
    private String getFcodeByType() {
        if(GlobalConstant.FILE_TYPE_USER_HEAD_IMG.equals(this.type)){
            return "USERHEADIMG_" + BaseUtil.getSessionUser().getId();
        }else if(GlobalConstant.FILE_TYPE_ANSWERS_IMG.equals(this.type)){
            return "ANSWERSIMG_" + BaseUtil.getSessionUser().getId();
        }

        return null;
    }

    public String getType() {
        return type;
    }
}