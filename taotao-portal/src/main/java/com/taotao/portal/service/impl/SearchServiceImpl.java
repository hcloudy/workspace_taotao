package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String keywords, int page, int rows) {
        Map<String,String> map = new HashMap<>();
        map.put("keyWords",keywords);
        map.put("page",page+"");
        map.put("rows",rows+"");
        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, map);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
        SearchResult searchResult = (SearchResult) taotaoResult.getData();
        return searchResult;
    }
}

