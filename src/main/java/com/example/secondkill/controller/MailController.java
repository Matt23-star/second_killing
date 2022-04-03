package com.example.secondkill.controller;

import com.example.secondkill.entity.Result;
import com.example.secondkill.service.MailService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Matt
 * @date: 2022-03-28 10:43
 * @description:
 */


@RestController
@RequestMapping("/mails")
@Api(tags = "邮箱接口")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/poll-codes")
    @ApiOperation(value = "通过nickname和email发送验证码，返回验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "用户名nickname", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "email", value = "用户邮箱email", dataType = "String", paramType = "query", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = String.class),
            @ApiResponse(code = 503, message = "邮件发送失败")
    })
    public Result<String> getPollCode(@RequestParam("nickname") String nickname,
                                      @RequestParam("email") String password) {
        return mailService.sendVerifyEmail(nickname, password);
    }

}
