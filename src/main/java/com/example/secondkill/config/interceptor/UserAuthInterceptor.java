package com.example.secondkill.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/27 20:28
 */
@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    private Logger logger= LoggerFactory.getLogger(UserAuthInterceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统一拦截
        Cookie[] cookies = request.getCookies();
        logger.info(String.valueOf(cookies==null));

        Cookie token=null;
        for (Cookie cookie : cookies) {
            if ("userToken".equals(cookie.getName())){
                token=cookie;
                break;
            }
        }
        if(token==null)return false;
        String uid = (String)redisTemplate.opsForValue().get(token.getValue() + "userToken");
        if(uid==null)return false;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
