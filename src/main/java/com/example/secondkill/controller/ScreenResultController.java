package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.service.impl.Screen_resultServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/screen_result")
@Api(tags = "筛选接口")
public class ScreenResultController {

    @Autowired
    private Screen_resultServiceImpl screenResultService;

    @PostMapping("/invite")
    public Result userLogin(@RequestParam("killId")String killId) {
        return screenResultService.screenUsers(killId);
    }

}

