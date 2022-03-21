package com.example.secondkill.entity.enums;

/**
 * @author: Matt
 * @date: 2022-02-27 15:42
 * @description: 
 */

public enum ResultMsg {

    SUCCESS(200,"成功"),
    ERROR(500,"失败");


    private Integer code;
    private String message;


    ResultMsg(Integer code, String message){
        this.code = code;
        this.message = message;
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

    @Override
    public String toString() {
        return "ResultMsg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
