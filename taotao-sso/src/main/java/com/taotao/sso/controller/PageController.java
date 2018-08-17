package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录和注册页面
 */
@Controller
public class PageController {

    @RequestMapping("/page/login")
    public String showLogin(String redirectUrl, Model model) {
        model.addAttribute("redirect", redirectUrl);
        return "login";
    }

    @RequestMapping("/page/register")
    public String showRegis() {
        return "register";
    }
}
