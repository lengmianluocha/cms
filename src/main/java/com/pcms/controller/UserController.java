package com.pcms.controller;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Pageable;
import com.pcms.domain.Userinfo;
import com.pcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/getUser")
    public String getUserInfo(Model model) throws Exception {
        Map param = new HashMap();

        param.put("userName", "hf");

        Userinfo user = userService.getUserByParam(param);

        model.addAttribute("user", user);


        //构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");//模板文件后缀
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //构造上下文(Model)
        Context context = new Context();
        context.setVariable("name", "蔬菜列表");
        context.setVariable("array", new String[]{"土豆", "番茄", "白菜", "芹菜"});

        //渲染模板
        FileWriter write = new FileWriter("result.html");
        templateEngine.process("example", context, write);

        return "login";
    }


    @RequestMapping("/user/listView")
    public ModelAndView requestListView(Model model, HttpSession session, ModelAndView mav) {
        String s = (String) session.getAttribute("user");
        mav.addObject("username", s);
        mav.setViewName("user/list");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/user/list")
    public JSONObject searchRequestListByParam(HttpServletRequest request, HttpServletResponse response) {

        Integer draw = Integer.parseInt(request.getParameter("draw"));
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));

        int pageSize = length;
        Map param = new HashMap();
        param.put("currentPage", start);
        param.put("pageSize", pageSize);

        int count = userService.getUserCountByParam(param);

        List<Userinfo> userinfos = userService.searchUserByParam(param);

        Pageable<Userinfo> pageable = new Pageable<>();
        pageable.setData(userinfos);
        pageable.setDraw(draw);
        pageable.setTotal(Long.parseLong(count + ""));
        return JSONObject.parseObject(JSONObject.toJSONString(pageable));
    }
}
