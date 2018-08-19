package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {

    TaotaoResult addCartService(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num);

    List<CartItem> getCartLists(HttpServletRequest request);

    TaotaoResult updateCartNum(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num);

    TaotaoResult deleteCartItem(Long itemId,HttpServletRequest request, HttpServletResponse response);
}
