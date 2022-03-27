package com.example.secondkill.controller;

import com.example.secondkill.utils.annotation.RequestLimitAnno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/23 16:36
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    @RequestLimitAnno(limitCount = 5,limitedTime = 30000,reqUrl = "hello")
    public String hello(){
        return "Hello,Wolrd!";
    }
}
