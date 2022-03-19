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
    private RedisTemplate<Object, Object> redisTemplate;


    private Object lockA = new Object();
    private Object lockB = new Object();
    private Object lockC = new Object();
    private Object lockD = new Object();
    private long successNum = 0;
    private long failNum = 0;
    private long totalNum = 0;
    @Autowired
    private RedisService redisService;

    private BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000000,0.001);

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
            String s = (String) redisTemplate.opsForValue().get(userId + "." + killId);
            if (s != null) return "fail";
        }
        synchronized (lockB) {
            String leftNumStr = (String) redisTemplate.opsForValue().get(killId + ".leftNum");
            if (leftNumStr == null) return "fail";
            int leftNum = Integer.parseInt(leftNumStr);
            if (leftNum < num) return "fail";
            redisTemplate.opsForValue().set(userId + " " + killId, "1");
            redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
            return "success";
        }
    }

    @RequestMapping("/kill5/{userId}/{killId}/{num}")
    public String kill5(@PathVariable("userId") String userId,
                       @PathVariable("killId") String killId,
                       @PathVariable("num") Integer num) {
        synchronized (lockC) {
            if(bloomFilter.mightContain(userId))
                return "false";
            bloomFilter.put(userId);
        }
        Long decrement = 0L;
        synchronized (lockD) {
            decrement = redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
        }
        if (decrement < 0) {
            redisTemplate.opsForValue().increment(killId + ".leftNum", num);
            return "fail";
        }

        return "success";
    }

    // 双布隆
    private static final int availableProcessorsNum = Runtime.getRuntime().availableProcessors();
    private static final BloomFilter<String>[] bloomFilters = new BloomFilter[availableProcessorsNum * 2];
    private static final Object[] locks = new Object[availableProcessorsNum];
    static {
        for (int i = 0; i < bloomFilters.length; i++) {
            bloomFilters[i] = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000000,0.0003);
        }

        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
    }
    @RequestMapping("/kill6/{userId}/{killId}/{num}")
    public String kill6(@PathVariable("userId") String userId,
                        @PathVariable("killId") String killId,
                        @PathVariable("num") Integer num) {
        int index = userId.hashCode() % availableProcessorsNum;
        synchronized (locks[index]) {
            BloomFilter<String> outBloomFilter = bloomFilters[index * 2];
            BloomFilter<String> inBloomFilter = bloomFilters[index * 2 + 1];
            if(outBloomFilter.mightContain(userId)) {
                if (inBloomFilter.mightContain(userId)) return "failed";
                else inBloomFilter.put(userId);
            } else {
                outBloomFilter.put(userId);
                inBloomFilter.put(userId);
            }
        }
        Long decrement = 0L;
        synchronized (locks[index]) {
            decrement = redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
        }
        if (decrement < 0) {
            redisTemplate.opsForValue().increment(killId + ".leftNum", num);
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/kill7/{userId}/{killId}/{num}")
    public String kill7(@PathVariable("userId") String userId,
                        @PathVariable("killId") String killId,
                        @PathVariable("num") Integer num) {
        synchronized (locks) {
            if (redisTemplate.opsForValue().decrement(killId + ".leftNum", num) < 0 || !redisTemplate.opsForZSet().add(killId, userId, num.doubleValue())) {
                redisTemplate.opsForValue().increment(killId + ".leftNum", num);
                redisTemplate.opsForZSet().remove(killId,userId);
                return "false";
            }
            return "success";
        }
    }

    // 双布隆3 32锁
    private static final BloomFilter<String>[] bloomFilters3 = new BloomFilter[(availableProcessorsNum) * 4];
    private static final Object[] locks3 = new Object[availableProcessorsNum * 2];
    static {
        for (int i = 0; i < bloomFilters3.length - 1; i+=2) {
            bloomFilters3[i + 1] = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000000,0.000001);
            bloomFilters3[i] = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000000,0.01);
        }

        for (int i = 0; i < locks3.length; i++) {
            locks3[i] = new Object();
        }
    }

    //kill 9 500k 1m45s
    @RequestMapping("/kill9/{userId}/{killId}/{num}")
    public String kill9(@PathVariable("userId") String userId,
                        @PathVariable("killId") String killId,
                        @PathVariable("num") Integer num) {
        int index = userId.hashCode() % (availableProcessorsNum * 2);
        synchronized (locks3[index]) {
            BloomFilter<String> outBloomFilter = bloomFilters3[index * 2];
            BloomFilter<String> inBloomFilter = bloomFilters3[index * 2 + 1];
            if(outBloomFilter.mightContain(userId)) {
                if (inBloomFilter.mightContain(userId)) return "failed";
                else inBloomFilter.put(userId);
            } else {
                outBloomFilter.put(userId);
                inBloomFilter.put(userId);
            }
        }
        Long decrement = 0L;
        UserKillState userKillState = new UserKillState();
        userKillState.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        userKillState.setUserId(userId);
        userKillState.setKillActivityId(killId);
        decrement = redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
        if (decrement < 0) {
            redisTemplate.opsForValue().increment(killId + ".leftNum", num);
            userKillState.setState("余量不足，抢购失败");
            userKillState.setTime(new Date(System.currentTimeMillis()));
            redisTemplate.opsForList().leftPush(killId+".userStateList",userKillState);
            return "fail";
        }
        userKillState.setState("购买成功");
        userKillState.setTime(new Date(System.currentTimeMillis()));
        redisTemplate.opsForList().leftPush(killId+".userStateList",userKillState);
        return "success";
    }

    private static Object[] locks10 = new Object[availableProcessorsNum];
    static {
        for (int i = 0; i < locks10.length; i++) {
            locks10[i] = new Object();
        }
    }

    //kill 10 500k 1m43s
    @RequestMapping("/kill10/{userId}/{killId}/{num}")
    public String kill10(@PathVariable("userId") String userId,
                         @PathVariable("killId") String killId,
                         @PathVariable("num") Integer num) {
        int index = userId.hashCode() % availableProcessorsNum;
        synchronized (locks10[index]) {
            if (redisTemplate.opsForValue().decrement(killId + ".leftNum", num) < 0 || redisTemplate.opsForSet().add(killId, userId)==0) {
                redisTemplate.opsForValue().increment(killId + ".leftNum", num);
                redisTemplate.opsForSet().remove(killId,userId);
                return "false";
            }
            redisTemplate.opsForValue().decrement(killId + ".leftNum", num);
            return "success";
        }
    }

}
