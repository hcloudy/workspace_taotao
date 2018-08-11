package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.ItemCatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {
        List list = getItemCatList(0l);
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setData(list);
        return itemCatResult;
    }

    public List getItemCatList(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List result = new ArrayList();
        int index = 0;
        //把list拼成category.json格式
        for (TbItemCat tbItemCat : list) {
            if (index <= 13) {
                if (tbItemCat.getIsParent()) {
                    ItemCatNode itemCatNode = new ItemCatNode();
                    itemCatNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                    if (tbItemCat.getParentId() == 0) {
                        itemCatNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a");
                        index ++;
                    } else {
                        itemCatNode.setName(tbItemCat.getName());
                    }
                    itemCatNode.setItems(getItemCatList(tbItemCat.getId()));
                    result.add(itemCatNode);
                } else {
                    String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                    result.add(item);
                }
            }
        }
        return result;
    }
}
