package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据token取用户信息，如果取不到返回null，反之tbUser
 */
public interface UserService {

    TbUser getCookieByToken(HttpServletRequest request, HttpServletResponse response);
}
