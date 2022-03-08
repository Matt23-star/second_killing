package com.example.secondkill.service;

import com.example.secondkill.entity.Result;

/**
 * @author: Matt
 * @date: 2022/3/6/21:26
 * @description:
 */
public interface IRedisService {

    Result secondKill(String userId,String killId, Integer bugAmount);
}
