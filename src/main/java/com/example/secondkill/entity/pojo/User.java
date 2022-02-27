package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Data
public class User implements Serializable {


    private String id;

    private String name;

    private String idCardNumber;

    private Integer age;

    private String nickName;

    private String gender;

    private String phoneNumber;

    private String email;

    private String address;

    private BigDecimal deposit;

}
