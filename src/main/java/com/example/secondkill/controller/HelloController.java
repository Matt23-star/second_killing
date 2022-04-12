package com.example.secondkill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.dto.AmountDTO;
import com.example.secondkill.mapper.*;
import com.example.secondkill.utils.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjl(1552217634 @ qq.com)
 * @date 2022/3/27 22:14
 */
@RestController
@Api(tags = "通用接口")
public class HelloController {

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private Order_informationMapper order_informationMapper;
    @Autowired
    private Product_informationMapper productInformationMapper;
    @Autowired
    private Rule_informationMapper ruleInformationMapper;
    @Autowired
    private SponsorMapper sponsorMapper;
    @Autowired
    private Screen_resultMapper screenResultMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User_kill_stateMapper userKillStateMapper;

    @GetMapping("/hello")
    public String getHello(){
        return "Hello";
    }

    @GetMapping("/amount")
    public Result getAmount(){
        AmountDTO amountDTO = new AmountDTO();
        amountDTO.setKillAmount(killInformationMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setOrderAmount(order_informationMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setRuleAmount(ruleInformationMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setProductAmount(productInformationMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setUserAmount(userMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setSponsorAmount(sponsorMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setUserKillStateA(userKillStateMapper.selectCount(new QueryWrapper<>()));
        amountDTO.setScreenResultA(screenResultMapper.selectCount(new QueryWrapper<>()));
        return ResultUtils.success(amountDTO);
    }
}
