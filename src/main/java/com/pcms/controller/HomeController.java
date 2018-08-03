package com.pcms.controller;


import com.pcms.domain.Userinfo;
import com.pcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public String LoginPage(Model model){
        return "login";
    }


    @RequestMapping("/dologin")
    public String doLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        //登陆成功，跳转到首页

        Userinfo user = new Userinfo();
        user.setUsername(username);
        user.setPassword(password);
        boolean result = userService.dologin(user);

        if(result){
            return "index";
        }
        return  "";
    }

    @RequestMapping("/loginout")
    public String loginout(){
        return "login";
    }


}
