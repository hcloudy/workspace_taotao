package com.taotao.portal.controller;


import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String keywords,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "30") Integer rows, Model model) {
        try {
            keywords = new String(keywords.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            keywords = "";
            e.printStackTrace();
        }
        SearchResult search = searchService.search(keywords, page, rows);
        model.addAttribute("query", keywords);
        model.addAttribute("totalPages", search.getRecordCount());
        model.addAttribute("itemList",search.getSearchItemList());
        return "search";
    }
}
