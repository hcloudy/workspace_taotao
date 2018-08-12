package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

import java.io.IOException;

/**
 * 生成静态文件service
 */
public interface StaticPageService {

    TaotaoResult genStaticPage(Long itemId) throws Exception;
}
