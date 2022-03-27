package com.example.secondkill.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author: Matt
 * @date: 2022/3/27/20:44
 * @description:
 */

@Data
public class UserDTO {

    private String name;

    private String idCardNumber;

    private Integer age;

    private String nickName;

    private String gender;

    private String phoneNumber;

    private String email;

    private String address;

    private BigDecimal deposit;

    private String password;

    private String checkCode;
}
