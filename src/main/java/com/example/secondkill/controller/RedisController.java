package com.example.secondkill.controller;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.enums.ResultMsg;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.entity.pojo.UserKillState;
import com.example.secondkill.service.impl.RedisService;
import com.example.secondkill.utils.ResultUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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

    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}")
    @ApiImplicitParam(name = "")
    public Result secondKill(@PathVariable("uId") String uId,
                             @PathVariable("killId") String killId,
                             @PathVariable("buyAmount") Integer buyAmount) {

        return redisService.secondKill(uId, killId, buyAmount);
    }

}
