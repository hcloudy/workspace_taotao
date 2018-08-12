package com.taotao.search.controller;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 发布搜索服务
 */
@Controller
public class SearchController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/query")
    @ResponseBody
    public TaotaoResult search(@RequestParam(defaultValue = "") String keyWords, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows) {
        try {
            //转换字符集
            keyWords = new String(keyWords.getBytes("iso8859-1"), "utf-8");
            SearchResult search = searchItemService.search(keyWords, page, rows);
            return TaotaoResult.ok(search);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
