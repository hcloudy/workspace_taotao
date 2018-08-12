package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {

    List<TbContent> getContentList(Long cid);

    TaotaoResult syncContentList(Long cid);
}
