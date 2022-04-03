package com.example.secondkill.config;

import com.example.secondkill.config.interceptor.AdminAuthInterceptor;
import com.example.secondkill.config.interceptor.UserAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/27 20:30
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        //registry.addInterceptor(new JwtInterceptor()).excludePathPatterns("user/login","user/register");
        //注册userAuthInterceptor拦截器
        InterceptorRegistration registration1 = registry.addInterceptor(new UserAuthInterceptor());
        //所有路径都被拦截
        registration1.addPathPatterns("/**");
        //添加不拦截路径
        registration1.excludePathPatterns("/user/login","/user/register","/sponsor");

        //注册adminAuthInterceptor拦截器
        InterceptorRegistration registration2 = registry.addInterceptor(new AdminAuthInterceptor());
        //添加拦截路径
        registration2.addPathPatterns("/sponsor");
        //添加不拦截路径
        registration2.excludePathPatterns("/sponsor/login");

    }
}
