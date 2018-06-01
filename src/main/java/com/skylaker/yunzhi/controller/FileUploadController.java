package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.BaseResult;
import com.skylaker.yunzhi.pojo.FileUploadItem;
import com.skylaker.yunzhi.pojo.IResult;
import com.skylaker.yunzhi.service.IFileUploadService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传处理控制器
 *
 * User: zhuyong
 * Date: 2018/6/1 10:10
 */
@Controller
@RequestMapping("/files")
public class FileUploadController {

    @Resource(name = "fileUploadServiceImpl")
    private IFileUploadService fileUploadService;


    /**
     * 文件上传处理
     *
     * @param   file
     * @return
     */
    @RequestMapping("/upload")
    public @ResponseBody JSONObject fileUpload(@RequestParam("file")MultipartFile file, @RequestParam("type")String type){
        //文件上传具体处理
        IResult result = fileUploadService.resolveFileUpload(new FileUploadItem(file, type));

        return BaseUtil.getResult(result);
    }
}