package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_INFO}")
    private String SSO_USER_INFO;

    @Override
    public TbUser getCookieByToken(HttpServletRequest request, HttpServletResponse response) {
        try{
            String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
            if (StringUtils.isBlank(token)) {
                return null;
            }
            //cookie中如果取到值,调用sso服务从cookie取到用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_INFO + token);
            TaotaoResult result = TaotaoResult.format(json);
            if (result.getStatus() != 200) {
                return null;
            }
            result = TaotaoResult.formatToPojo(json, TbUser.class);
            TbUser user = (TbUser) result.getData();
            return user;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
