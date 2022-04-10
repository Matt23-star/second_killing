package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.dto.UserDTO;
import com.example.secondkill.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "idCardNumber", value = "用户idcard", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "age", value = "用户年龄", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "nickName", value = "用户昵称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "gender", value = "用户性别", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "phoneNumber", value = "用户手机号码", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "email", value = "用户邮箱", paramType = "query", required = true),
            @ApiImplicitParam(name = "address", value = "用户地址", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "deposit", value = "用户存款", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "Date", paramType = "query", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", dataType = "Date", paramType = "query", required = true)
    })
    public Result register(@RequestBody  UserDTO userDTO){
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public Result userLogin(@RequestParam("email")String email,
                            @RequestParam("password")String password,
                            HttpServletResponse response) {
        return userService.userLogin(email,password,response);
    }
}

