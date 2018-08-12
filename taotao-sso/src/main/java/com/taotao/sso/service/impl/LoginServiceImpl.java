package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.component.JedisClient;
import com.taotao.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${TOKEN_EXPIRE}")
    private Integer TOKEN_EXPIRE;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;

    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return TaotaoResult.build(400, "用户名和密码不能为空");
        }
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return TaotaoResult.build(400, "用户名和密码错误");
        }
        TbUser user = list.get(0);
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return TaotaoResult.build(400, "用户名和密码错误");
        }
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        jedisClient.set(REDIS_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_SESSION_KEY+token, TOKEN_EXPIRE);
        //写入cookie
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        return TaotaoResult.ok(token);
    }
}
