package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.service.impl.Kill_informationServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/getRandomUrl/{userId}/{killInformationId}")
    public Result<String> getRandomUrl(@PathVariable String userId, @PathVariable String killInformationId){
        return kill_informationService.getRandomUrl(userId,killInformationId);
    }

}

