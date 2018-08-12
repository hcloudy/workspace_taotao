package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class StaticPageServiceImpl implements StaticPageService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${FREEMARKER_STATIC_PATH}")
    private String FREEMARKER_STATIC_PATH;

    @Override
    public TaotaoResult genStaticPage(Long itemId) throws Exception {
        TbItem item = itemService.getItemById(itemId);
        String desc = itemService.getItemDescById(itemId);
        String param = itemService.getItemParamById(itemId);
        //生成静态文件
        Configuration config = freeMarkerConfigurer.getConfiguration();
        Template template = config.getTemplate("item.ftl");
        //创建数据类型
        Map root = new HashMap();
        root.put("item", item);
        root.put("itemDesc", desc);
        root.put("itemParam", param);
        //创建writer
        Writer out = new FileWriter(new File(FREEMARKER_STATIC_PATH+itemId+".html"));
        template.process(root, out);
        out.flush();
        out.close();
        return TaotaoResult.ok();
    }
}
