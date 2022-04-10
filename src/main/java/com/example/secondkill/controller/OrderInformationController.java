package com.example.secondkill.controller;


import com.example.secondkill.entity.Result;
import com.example.secondkill.service.IOrder_informationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "订单接口")
public class OrderInformationController {

    @Autowired
    private IOrder_informationService orderService;

    @PostMapping("pay/{userId}/{killId}")
    public Result pay(@PathVariable("userId") String userId, @PathVariable("killId") String killId) {
        return orderService.pay(killId, userId);
    }

    @GetMapping("orders/{userId}")
    public Result getOrders(@PathVariable("userId") String userId){
        return orderService.getOrdersByUserId(userId);
    }
}

