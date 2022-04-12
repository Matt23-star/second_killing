package com.example.secondkill.utils;

import com.example.secondkill.entity.dto.KillImformationDetailsDTO;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.service.impl.Kill_informationServiceImpl;
import com.example.secondkill.service.impl.SponsorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: Matt
 * @date: 2021-07-15 11:23
 * @description: 邮件html格式规范
 */

public class CheckHtmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(CheckHtmlUtils.class);

    @Autowired
    private SponsorServiceImpl sponsorService;

    public static String setCodeEmailHtml(String title, String userName, String type, String captcha) {
        String emailTemplet = System.getProperty("email_template");
        emailTemplet = emailTemplet.replace("$(title)", title);
        emailTemplet = emailTemplet.replace("$(userName)", userName);
        emailTemplet = emailTemplet.replace("$(type)", type);
        emailTemplet = emailTemplet.replace("$(captcha)", captcha);
        System.out.println("设置完成");
        return emailTemplet;
    }

    public static String setSecondKillHtml(KillImformationDetailsDTO killImformationDetailsDTO, String name){
        String secondKillTemplet = System.getProperty("postman");
        secondKillTemplet= secondKillTemplet.replace("$(user)",name);
        secondKillTemplet= secondKillTemplet.replace("$(killName)","银行存款产品秒杀活动");
        secondKillTemplet = secondKillTemplet.replace("$(product)",killImformationDetailsDTO.getProductInformation().getName());
        secondKillTemplet = secondKillTemplet.replace("$(saleNumber)",killImformationDetailsDTO.getProductNum().toString());
        secondKillTemplet = secondKillTemplet.replace("$(killBeginTime)",DateUtils.dateFormat(killImformationDetailsDTO.getBeginTime()));
        secondKillTemplet = secondKillTemplet.replace("$(killEndTime)",DateUtils.dateFormat(killImformationDetailsDTO.getEndTime()));
        secondKillTemplet = secondKillTemplet.replace("$(killDescription)",killImformationDetailsDTO.getDescription());
        secondKillTemplet = secondKillTemplet.replace("$(Sponsor)", killImformationDetailsDTO.getSponsor().getName());
        secondKillTemplet = secondKillTemplet.replace("$(SponsorDescription)",killImformationDetailsDTO.getSponsor().getDescription());
        secondKillTemplet = secondKillTemplet.replace("$(url)","ss" );
        System.out.println("设置完成");
        return secondKillTemplet;
    }

    /**
     * @author: Matt
     * @date: 2022-04-12 11:28
     * @descriptin: a
     */


    public static void initCodeEmailTemplate() {
        StringBuilder sb = new StringBuilder();
        try {
            String encoding = "UTF-8";

            ClassPathResource classPathResource = new ClassPathResource("templates/email_template.html");
            InputStream resourceAsStream = classPathResource.getInputStream();
//          考虑到编码格式
            InputStreamReader read = new InputStreamReader(
                    resourceAsStream, encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            System.setProperty("email_template", sb.toString());
            logger.info("注入文件:"+sb.toString());
            resourceAsStream.close();
            read.close();
            logger.info("注入完成");
        } catch (IOException e) {
            logger.error("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public static void initSecondKillTemplate() {
        StringBuilder sb = new StringBuilder();
        try {
            String encoding = "UTF-8";

            ClassPathResource classPathResource = new ClassPathResource("templates/postman.html");
            InputStream resourceAsStream = classPathResource.getInputStream();
//          考虑到编码格式
            InputStreamReader read = new InputStreamReader(
                    resourceAsStream, encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            System.setProperty("postman", sb.toString());
            logger.info("注入文件:"+sb.toString());
            resourceAsStream.close();
            read.close();
            logger.info("注入完成");
        } catch (IOException e) {
            logger.error("读取文件内容出错");
            e.printStackTrace();
        }
    }
}