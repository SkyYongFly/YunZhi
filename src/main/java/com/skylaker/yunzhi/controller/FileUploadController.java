package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.db.FileUploadItem;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.service.IFileUploadService;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
    @LogAnnotation(type = "文件上传", action = "新增上传文件")
    @RequestMapping("/upload")
    public @ResponseBody JSONObject fileUpload(@RequestParam("file")MultipartFile file, @RequestParam("type")String type){
        //文件上传具体处理
        IResult result = fileUploadService.resolveFileUpload(new FileUploadItem(file, type));

        return BaseUtil.getResult(result);
    }
}