package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchItemDao {

    SearchResult searchItemList(SolrQuery solrQuery) throws SolrServerException;
}
