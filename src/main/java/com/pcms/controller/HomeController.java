package com.pcms.controller;


import com.pcms.domain.Userinfo;
import com.pcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public String LoginPage(Model model) {
        return "login";
    }


    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        //登陆成功，跳转到首页
        String s = (String) session.getAttribute("user");
        if (!StringUtils.isEmpty(s) && s == username) {
            return "/moive/add";
        }
        Userinfo user = new Userinfo();
        user.setUsername(username);
        user.setPassword(password);
        boolean result = userService.dologin(user);
        if (result) {
            session.setAttribute("user", username);

            return "/moive/add";
        }
        //TODO 设置错误码
        return "redirect:/login";
    }

    @RequestMapping("/loginout")
    public String loginout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }


}
