package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.service.impl.Kill_informationServiceImpl;
import com.example.secondkill.service.impl.RedisService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/kill_information")
@Api(tags="秒杀活动接口")
public class KillInformationController {

    @Autowired
    private Kill_informationServiceImpl kill_informationService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getRandomUrl/{userId}/{killInformationId}")
    public Result<String> getRandomUrl(@PathVariable String userId, @PathVariable String killInformationId){
        return kill_informationService.getRandomUrl(userId,killInformationId);
    }

    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}/{randomUrl}")
    @ApiImplicitParam(name = "")
    public Result secondKill(@PathVariable("uId") String uId,
                             @PathVariable("killId") String killId,
                             @PathVariable("buyAmount") Integer buyAmount,
                             @PathVariable("randomUrl") String randomUrl) {
        return redisService.secondKill(uId, killId, buyAmount,randomUrl);
    }



}

