package com.example.secondkill.utils.aspect;

import com.example.secondkill.utils.annotation.RequestLimitAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/23 14:54
 */
@Component
@Aspect
public class RequestLimitAspect {

    private Logger logger= LoggerFactory.getLogger(RequestLimitAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(requestLimit)")
    public void limit(RequestLimitAnno requestLimit) {

    }

    @Around("limit(requestLimit)")
    public Object requestLimitLog(ProceedingJoinPoint joinPoint, RequestLimitAnno requestLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof HttpServletRequest)
                request = (HttpServletRequest) arg;
            if(arg instanceof HttpServletResponse)
                response = (HttpServletResponse) arg;
        }
        //方法中缺失HttpServletRequest参数
        if (request == null || response == null)
            return null;
        String ip = request.getRemoteAddr();
        String reqUrl= requestLimit.reqUrl();
        String key = "req_IpLimit".concat(ip).concat(reqUrl);
        String keyFlag="req_IpLimitFlag".concat(ip).concat(reqUrl);
        long limitTime = requestLimit.limitedTime();

        logger.info("ip:"+ip);
        if(redisTemplate.opsForValue().get(keyFlag)!=null){
            throw new Exception("恶意请求");
        }
        //从redis获取值
        Long count = redisTemplate.opsForValue().increment(key);
        //请求次数超过限制
        if(count>requestLimit.limitCount()){
            redisTemplate.opsForValue().set(keyFlag,keyFlag+"exists",limitTime,TimeUnit.MILLISECONDS);
            redisTemplate.opsForValue().set(key,requestLimit.limitCount()/2,limitTime,TimeUnit.MILLISECONDS);
            throw new Exception("恶意请求");
        }
        logger.info(String.valueOf(count));
        return joinPoint.proceed(args);
    }
}

