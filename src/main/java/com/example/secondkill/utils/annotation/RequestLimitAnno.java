package com.example.secondkill.utils.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 自定义注解（限制ip访问次数）
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/23 14:44
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimitAnno {

    /**
     * 允许访问的最大次数,阈值
     */
    int limitCount() default 200;

    /**
     * 用户访问最小安全时间，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
     * 时间 毫秒
     */
    //long minSafeTime() default 5000;

    /**
     * 某个ip限制访问时间，单位为毫秒，默认值一分钟
     */
    long limitedTime() default 60000;

    /**
     * 请求url
     */
    String reqUrl();

}
