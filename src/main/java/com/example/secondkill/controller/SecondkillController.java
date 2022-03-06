package com.example.secondkill.controller;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.enums.ResultMsg;
import com.example.secondkill.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Matt
 * @date: 2022/3/6/19:11
 * @description:
 */

@RestController
@RequestMapping("/second-kill")
public class SecondkillController {

    @GetMapping("/users/{uId}/kills/{killId}/products/{buyAmount}")
    @ApiImplicitParam(name = "")
    public Result<String> secondKill(@PathVariable("uId") String uId, @PathVariable("killId") String killId, @PathVariable("buyAmount")Integer buyAmount) {
        return ResultUtils.success(ResultMsg.SUCCESS,"success");
    }
}
