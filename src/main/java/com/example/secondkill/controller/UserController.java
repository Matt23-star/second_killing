package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public Result<User> userLogin(@RequestParam("userName")String userName,
                                  @RequestParam("password")String password,
                                  HttpServletResponse response) {
        return userService.userLogin(userName,password,response);
    }
}

