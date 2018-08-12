package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchItemService {

    SearchResult search(String queryString, int page, int rows) throws SolrServerException;
}
