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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login/login")
    public String LoginPage(Model model) {
        return "login";
    }

    @RequestMapping("/blank")
    public ModelAndView blank(Model model, HttpSession session,ModelAndView mav ) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("blank");
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/login/dologin", method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session,ModelAndView mav ) {
        //登陆成功，跳转到首页
        String s = (String) session.getAttribute("user");
        if (!StringUtils.isEmpty(s) && s == username) {
            mav.addObject("username", username);
            mav.setViewName("redirect:/blank");
        }
        Userinfo user = new Userinfo();
        user.setUsername(username);
        user.setPassword(password);
        boolean result = userService.dologin(user);
        if (result) {
            session.setAttribute("user", username);
             mav.addObject("username", username);
             mav.setViewName("redirect:/blank");
        }else {
            //TODO 设置错误码
            mav.setViewName("redirect:login/login");
        }
        return mav;
    }

    @RequestMapping("/login/loginout")
    public ModelAndView loginout(HttpSession session,ModelAndView mav ) {
        session.removeAttribute("user");
        mav.setViewName("redirect:login/login");
        return mav;
    }
}
