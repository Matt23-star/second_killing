package com.example.secondkill.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/4/3 12:04
 */
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统一拦截
        Cookie[] cookies = request.getCookies();

        Cookie token=null;
        for (Cookie cookie : cookies) {
            if ("sponsorToken".equals(cookie.getName())){
                token=cookie;
                break;
            }
        }
        if(token==null)return false;
        String aid = (String)redisTemplate.opsForValue().get(token.getValue() + "sponsorToken");
        if(aid==null)return false;
        return true;
    }
}
