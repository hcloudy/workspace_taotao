package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

/**
 * 商品基本信息服务
 */
public interface ItemService {

    TbItem  getItemById(Long itemId);

    TbItemDesc getItemDescById(Long itemId);

    TbItemParamItem getItemParamItemById(Long itemId);
}
