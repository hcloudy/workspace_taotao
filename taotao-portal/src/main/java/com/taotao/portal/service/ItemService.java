package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

/**
 * 展示商品基本信息
 */
public interface ItemService {

    TbItem getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParamById(Long itemId);
}
