package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.service.IRedisService;
import com.example.secondkill.utils.ResultUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: Matt
 * @date: 2022/3/6/21:26
 * @description:
 */
@Service
public class RedisService implements IRedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Result secondKill(String userId, String killId, Integer bugAmount) {

        redisTemplate.opsForValue().decrement(111,bugAmount);
        return ResultUtils.success();
    }

//    /**
//     * 根据给定的布隆过滤器添加值
//     */
//    public <T> void addByBloomFilter(BloomFilterUtils<T> bloomFilterHelper, String key, T value) {
//        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
//        int[] offset = bloomFilterHelper.murmurHashOffset(value);
//        for (int i : offset) {
////            System.out.println("key : " + key + " " + "value : " + i);
//            redisTemplate.opsForValue().setBit(key, i, true);
//        }
//    }
//
//    /**
//     * 根据给定的布隆过滤器判断值是否存在
//     */
//    public <T> boolean includeByBloomFilter(BloomFilterUtils<T> bloomFilterHelper, String key, T value) {
//        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
//        int[] offset = bloomFilterHelper.murmurHashOffset(value);
//        for (int i : offset) {
////            System.out.println("key : " + key + " " + "value : " + i);
//            if (!redisTemplate.opsForValue().getBit(key, i)) {
//                return false;
//            }
//        }
//        return true;
//    }

}
