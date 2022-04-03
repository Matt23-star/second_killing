package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.service.impl.Kill_informationServiceImpl;
import com.example.secondkill.service.impl.RedisServiceImpl;
import com.example.secondkill.utils.annotation.RequestLimitAnno;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private RedisServiceImpl redisService;

    @RequestLimitAnno(reqUrl = "getRandomUrl")
    @GetMapping("/getRandomUrl/{userId}/{killInformationId}")
    public Result<String> getRandomUrl(@PathVariable String userId, @PathVariable String killInformationId){
        return kill_informationService.getRandomUrl(userId,killInformationId);
    }

    @GetMapping("/getRandomUrlWithoutAOP/{userId}/{killInformationId}")
    public Result<String> getRandomUrlWithoutAOP(@PathVariable String userId, @PathVariable String killInformationId){
        return kill_informationService.getRandomUrl(userId,killInformationId);
    }


    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}/{randomUrl}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "killId", value = "秒杀活动id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "buyAmount", value = "购买产品数量", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "randomUrl", value = "动态url", dataType = "String", paramType = "query", required = true)
    })
    public Result secondKill(@PathVariable("uId") String uId,
                             @PathVariable("killId") String killId,
                             @PathVariable("buyAmount") Integer buyAmount,
                             @PathVariable("randomUrl") String randomUrl) {
        return redisService.secondKill(uId, killId, buyAmount,randomUrl);
    }

    @GetMapping("/getAvailableKill/{userId}/{from}/{num}")
    public Result<List<KillInformation>> getAvailableKill(@PathVariable("userId") String userId,
                                                          @PathVariable("from") String from,
                                                          @PathVariable("num") String num){
        return kill_informationService.getAvailableKill(userId, from, num);
    }

//    @PostMapping("/insert")
//
//    public Result addSecondKill(@RequestBody KillImformationDTO killImformationDTO){
//        return kill_informationService.addSecondKill(killImformationDTO);
//    }

}

