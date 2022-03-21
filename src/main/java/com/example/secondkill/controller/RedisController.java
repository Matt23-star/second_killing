package com.example.secondkill.controller;

import com.example.secondkill.entity.Result;
import com.example.secondkill.service.impl.RedisService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Matt
 * @date: 2022/3/6/19:11
 * @description:
 */

@RestController
@RequestMapping("/second-kill")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}/{randomUrl}")
    @ApiImplicitParam(name = "")
    public Result secondKill(@PathVariable("uId") String uId,
                             @PathVariable("killId") String killId,
                             @PathVariable("buyAmount") Integer buyAmount,
                             @PathVariable("randomUrl") String randomUrl) {
        return redisService.secondKill(uId, killId, buyAmount);
    }

}
