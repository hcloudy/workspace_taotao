package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String keywords,int page,int rows);
}
