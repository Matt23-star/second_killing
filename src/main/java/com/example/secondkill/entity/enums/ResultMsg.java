package com.example.secondkill.entity.enums;

import com.example.secondkill.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author: Matt
 * @date: 2022-02-27 15:42
 * @description: 
 */
public enum ResultMsg {

    SUCCESS(200,"成功", DateUtils.dateFormat(new Date()),System.currentTimeMillis()),
    ERROR(500,"失败",DateUtils.dateFormat(new Date()),System.currentTimeMillis());


    private Integer code;
    private String message;
    private String time;
    private Long timestamp;

    ResultMsg(Integer code, String message, String time, Long timestamp) {
        this.code = code;
        this.message = message;
        this.time = time;
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public Long getTimestamp() {
        return timestamp;
    }


    @Override
    public String toString() {
        return "ResultMsg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", timestamp=" + timestamp +
                '}';
    }
}
