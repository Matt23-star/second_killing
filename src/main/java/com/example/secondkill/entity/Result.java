package com.example.secondkill.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Matt
 * @date: 2021/7/9/14:46
 * @description:
 */

@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private String time;
    private Long timestamp;
    private T data;

}
