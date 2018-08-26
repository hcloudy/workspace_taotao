package com.taotao.portal.Interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor{

    @Autowired
    private UserService userService;
    @Value("${SSO_LOGIN_URL}")
    private String SSO_LOGIN_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TbUser user = userService.getCookieByToken(request, response);
        if (user == null) {
            response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" + request.getRequestURL());
            return false;
        }
        request.setAttribute("user",user);
        return true;
        //返回false说明拦截, true放行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //拦截后，返回modelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //返回modelAndView之后
    }
}
