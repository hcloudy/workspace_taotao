package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加购物车
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Override
    public TaotaoResult addCartService(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) {
        // 1.从cookie中取商品列表
        List<CartItem> list = getCartFromCookie(request);
        // 2.判断是否已经添加过购物车
        boolean haveflag = false;
        for (CartItem c : list) {
            //3.如果添加过,把对应的商品取出来，数量加Num
            if(c.getId().longValue() == itemId) {
                c.setNum(c.getNum() + num);
                haveflag = true;
                break;
            }
        }
        // 4.如果没添加过,则把该商品添加进去。
        if (!haveflag) {
            TbItem item = itemService.getItemById(itemId);
            CartItem cartItem = new CartItem();
            cartItem.setNum(num);
            cartItem.setId(itemId);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if (item.getImage() != null) {
                String image = item.getImage();
                String[] strings = image.split(",");
                cartItem.setImage(strings[0]);
            }
            list.add(cartItem);
        }
        // 5.再把list返回到cookie中
        CookieUtils.setCookie(request, response, "TT_CART",JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
        return TaotaoResult.ok();
    }

    private List<CartItem> getCartFromCookie(HttpServletRequest request) {
        try {
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            if (list == null) {
                return new ArrayList<>();
            }else {
                return list;
            }
        }catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<CartItem> getCartLists(HttpServletRequest request) {
        List<CartItem> cartItemList = getCartFromCookie(request);
        return cartItemList;
    }

    @Override
    public TaotaoResult updateCartNum(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) {
        List<CartItem> cartLists = getCartLists(request);
        for(CartItem c : cartLists) {
            if(c.getId().longValue() == itemId) {
                c.setNum(num);
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART",JsonUtils.objectToJson(cartLists), COOKIE_EXPIRE, true);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> lists = getCartLists(request);
        for (CartItem c : lists) {
            if(c.getId().longValue() == itemId) {
                lists.remove(c);
                break;
            }
        }
        CookieUtils.setCookie(request, response,"TT_CART",JsonUtils.objectToJson(lists), COOKIE_EXPIRE, true);
        return TaotaoResult.ok();
    }
}
