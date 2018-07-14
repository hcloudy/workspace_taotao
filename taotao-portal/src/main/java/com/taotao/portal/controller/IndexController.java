package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 淘淘商城首页Controller
 */
@Controller
public class IndexController {

    @Autowired
    ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        String list = contentService.getAd1List();
        model.addAttribute("ad1",list);
        return "index";
    }

    @RequestMapping(value = "/testPost",method = RequestMethod.POST)
    @ResponseBody
    public String testPost(String name,String pass) {
        System.out.println(name);
        System.out.println(pass);
        return "OK";
    }
}
