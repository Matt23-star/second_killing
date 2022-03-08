package com.example.secondkill.controller;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.enums.ResultMsg;
import com.example.secondkill.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, String> redisTemplate;

    private Object lockA = new Object();
    private Object lockB = new Object();
    private long successNum = 0;
    private long failNum = 0;
    private long totalNum = 0;

    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}")
    @ApiImplicitParam(name = "")
    public Result<String> secondKill(@PathVariable("uId") String uId,
                                     @PathVariable("killId") String killId,
                                     @PathVariable("buyAmount")Integer buyAmount) {


        return ResultUtils.success(ResultMsg.SUCCESS,"success");
    }

    private long num = 0;

    @RequestMapping("/test")
    public synchronized String test() {
        if (num % 10000 == 0) System.out.println(System.currentTimeMillis());
        num++;
        return "hi";
    }

    @RequestMapping("/kill/{userId}/{killId}/{num}")
    public String kill(@PathVariable("userId") String userId,
                       @PathVariable("killId") String killId,
                       @PathVariable("num") Integer num) {
        synchronized (lockA) {
            totalNum++;
            if (totalNum % 5000 == 0) System.out.println(totalNum);
            // 键为 “用户id.秒杀id” 代表用户是否参加过该次秒杀
            String s = redisTemplate.opsForValue().get(userId + "." + killId);
            if (s != null) return "fail";
        }
        synchronized (lockB) {
            String leftNumStr = redisTemplate.opsForValue().get(killId + ".leftNum");
            if (leftNumStr == null) return "fail";
            int leftNum = Integer.parseInt(leftNumStr);
            if (leftNum < num) return "fail";
            redisTemplate.opsForValue().set(userId + " " + killId, "1");
            redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
            return "success";
        }
    }
}
