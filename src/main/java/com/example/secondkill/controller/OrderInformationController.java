package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.service.IOrder_informationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/order_information")
public class OrderInformationController {

    @Autowired
    private IOrder_informationService orderService;

    @PostMapping("pay/{userId}/{killId}")
    public Result pay(@PathVariable("userId") String userId, @PathVariable("killId") String killId) {
        return orderService.pay(killId, userId);
    }

}

