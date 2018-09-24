package com.pcms;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends  WebMvcAutoConfiguration   implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInteceptor loginInterceptor = new LoginInteceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/login/login");
        loginRegistry.excludePathPatterns("/login/dologin");
        loginRegistry.excludePathPatterns("/login/loginout");
        loginRegistry.excludePathPatterns("/wx");
        loginRegistry.excludePathPatterns("/moive/search");
        loginRegistry.excludePathPatterns("/moive/qmoive");
        loginRegistry.excludePathPatterns("/moive/limoive");//link invalid
        // 排除资源请求
        loginRegistry.excludePathPatterns("/static/**");
        loginRegistry.excludePathPatterns("/templates/**");

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }
}