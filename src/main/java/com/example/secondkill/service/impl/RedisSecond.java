package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.service.IRedisService;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: Matt
 * @date: 2022/3/6/21:26
 * @description:
 */
public class RedisSecond implements IRedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Result secondKill(String userId, String killId, Integer bugAmount) {

        redisTemplate.opsForValue().decrement(111,bugAmount);
        return ResultUtils.success();
    }
}
