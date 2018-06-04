package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.service.ILuceneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内容搜索相关请求处理控制器
 *
 * User: zhuyong
 * Date: 2018/6/4 15:34
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Resource(name = "luceneServiceImpl")
    private ILuceneService luceneService;


    /**
     * 内容检索请求
     *
     * @param   words  检索关键词
     * @return
     */
    @RequestMapping(value = "/text", method = RequestMethod.GET)
    public @ResponseBody List<Object> getSearchResult(@RequestParam("words")String words){
        return luceneService.getSearchResult(words);
    }
}