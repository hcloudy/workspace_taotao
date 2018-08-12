package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Value("${REST_CID}")
    private String REST_CID;

    @Override
    public String getAd1List() {
        String url = REST_BASE_URL+REST_CONTENT_URL+REST_CID;
        String s = HttpClientUtil.doGet(url);
        //把json装换成java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToList(s, TbContent.class);
        List<TbContent> list = (List<TbContent>) taotaoResult.getData();
        List<AdNode> adNodes = new ArrayList<>();
        for (TbContent tb : list) {
            AdNode adNode = new AdNode();
            adNode.setHeight(240);
            adNode.setWidth(670);
            adNode.setSrc(tb.getPic());

            adNode.setHeightB(240);
            adNode.setWidthB(550);
            adNode.setSrcB(tb.getPic2());

            adNode.setAlt(tb.getTitle());
            adNode.setHerf(tb.getUrl());
            adNodes.add(adNode);
        }
        String result = JsonUtils.objectToJson(adNodes);
        return result;
    }
}
