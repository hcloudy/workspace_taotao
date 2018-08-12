package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Autowired
    TbContentMapper tbContentMapper;

    public List<TbContent> getContentList(Long cid) {
        //先查询redis缓存中是否有数据
        try {
            String s = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
            if(!StringUtils.isBlank(s)) {
                List<TbContent> list = JsonUtils.jsonToList(s, TbContent.class);
                return list;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);

        //添加缓存到redis,切记不要影响正常的业务逻辑
        try {
            jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除content的redis
     * @param cid
     * @return
     */
    public TaotaoResult syncContentList(Long cid) {
        jedisClient.hdel(REDIS_CONTENT_KEY, cid + "");
        return TaotaoResult.ok();
    }
}
