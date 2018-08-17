package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CartService {

    TaotaoResult addCartService(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num);
}
