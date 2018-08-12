package com.taotao.rest.service.impl;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_KEY}")
    private String ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;


    @Override
    public TbItem getItemById(Long itemId) {
        //先查redis,如果有直接返回
        try{
            String json = jedisClient.get(ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId);
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        //查询并插入redis缓存中，操作redis切记不要影响正常的业务逻辑
        try {
            jedisClient.set(ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, JsonUtils.objectToJson(tbItem));
            jedisClient.expire(ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, ITEM_EXPIRE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        try{
            String json = jedisClient.get(ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId);
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        try{
            jedisClient.set(ITEM_KEY+":"+ITEM_DESC_KEY+":"+itemId, JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(ITEM_KEY+":"+ITEM_DESC_KEY+":"+itemId, ITEM_EXPIRE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    @Override
    public TbItemParamItem getItemParamItemById(Long itemId) {
        //先从redis中查
        try{
            String json = jedisClient.get(ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId);
            if (StringUtils.isNotBlank(json)) {
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return tbItemParamItem;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem tbItemParamItem = list.get(0);
            //返回之前往redis中插入数据
            try{
                jedisClient.set(ITEM_KEY +":"+ ITEM_PARAM_KEY +":"+itemId, JsonUtils.objectToJson(tbItemParamItem));
                jedisClient.expire(ITEM_KEY +":"+ ITEM_PARAM_KEY +":"+itemId, ITEM_EXPIRE);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return tbItemParamItem;
        }
        return null;
    }
}
