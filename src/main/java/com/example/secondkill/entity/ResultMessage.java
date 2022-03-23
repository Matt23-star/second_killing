package com.example.secondkill.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: Matt
 * @date: 2021/7/9/15:00
 * @description:
 */

@Data
public class ResultMessage {

    private Integer code;
    private String message;
    private String time;
    private Long timestamp;

    public ResultMessage(Integer code, String message, String time, Long timestamp) {
        this.code = code;
        this.message = message;
        this.time = time;
        this.timestamp = timestamp;
    }
}
