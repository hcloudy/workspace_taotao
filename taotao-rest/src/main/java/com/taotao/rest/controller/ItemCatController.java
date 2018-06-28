package com.taotao.rest.controller;

import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/list"/*,produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8"*/)
    @ResponseBody
    public Object getItemCatList(String callback) {
        ItemCatResult itemCatList = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            return itemCatList;
        }
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(itemCatList);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
