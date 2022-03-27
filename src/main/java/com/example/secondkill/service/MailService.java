package com.example.secondkill.service;


import com.example.secondkill.entity.Result;

/**
 * @author: Matt
 * @date: 2022-03-27 21:00
 * @description:
 */


public interface MailService {

    Result<String> sendVerifyEmail(String account, String email);
//
//    Result<Boolean> sendEmailToTalent(Integer toId, String title, String content);


}
