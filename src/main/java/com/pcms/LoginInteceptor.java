package com.pcms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInteceptor  implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LoginInteceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session
        HttpSession session = request.getSession(true);

        logger.info(request.getRequestURL().toString());
        //判断用户ID是否存在，不存在就跳转到登录界面
        if(session.getAttribute("user") == null){
            logger.info("------:跳转到login页面！");
            response.sendRedirect("/login/login");
            return false;
        }else{
            session.setAttribute("user", session.getAttribute("user"));
            return true;
        }
    }



}
