package com.example.secondkill.service.impl;


import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDetailsDTO;
import com.example.secondkill.entity.enums.ResultMsg;
import com.example.secondkill.service.MailService;
import com.example.secondkill.utils.CheckHtmlUtils;
import com.example.secondkill.utils.MailUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @author: Matt
 * @date: 2022-03-27 21:01
 * @description:
 */


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SponsorServiceImpl sponsorService;

//    @Autowired
//    private IUserService userService;

    @Override
    public Result<String> sendVerifyEmail(String account, String email) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        redisTemplate.opsForValue().set(email+"checkCode",checkCode,300000, TimeUnit.MILLISECONDS);
        CheckHtmlUtils.initCodeEmailTemplate();
        String content = CheckHtmlUtils.setCodeEmailHtml("秒杀系统验证码", account, "注册验证", checkCode);
        if (mailUtils.sendHtmlEmail(email, "秒杀系统验证码", content))
            return ResultUtils.success(new ResultMessage(200,"验证码邮箱发送成功"), checkCode);
        return ResultUtils.error(ResultMsg.ERROR);
    }

    @Override
    public Result<String> sendSecondKill(String name, String email, String killId) {
        System.out.println(killId);
        CheckHtmlUtils.initSecondKillTemplate();
        String content = new CheckHtmlUtils().setSecondKillHtml((KillImformationDetailsDTO) sponsorService.getKillDetails(killId).getData(),name);
        if (mailUtils.sendHtmlEmail(email, "秒杀活动邀请", content))
            return ResultUtils.success(new ResultMessage(200,"邀请邮箱发送成功"));
        return ResultUtils.error(ResultMsg.ERROR);
    }


//    @Override
//    public Result<Boolean> sendEmailToTalent(Integer toId, String title, String content) {
//        User talent = userService.getTalentById(toId).getData();
//        return ResultUtils.success(ResultMsg.SUCCESS, mailUtils.sendHtmlEmail(talent.getEmail(), title, content));
//    }

}