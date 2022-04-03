package com.example.secondkill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/27 22:14
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello(){
        return "Hello";
    }
}
