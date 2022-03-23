package com.example.secondkill.utils;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.enums.ResultMsg;

import java.util.Date;

/**
 * @author: Matt
 * @date: 2021/7/9/14:50
 * @description:
 */
public class ResultUtils {

    //带code和参数的成功
    public static <T> Result success(ResultMsg resultMessage, T data){
        Result<T> result = new Result<>();
        result.setCode(resultMessage.getCode());
        result.setMessage(resultMessage.getMessage());
        result.setTime(resultMessage.getTime());
        result.setTimestamp(resultMessage.getTimestamp());
        result.setData(data);
        return result;
    }

    public static <T> Result success(ResultMessage resultMessage, T data){
        Result<T> result = new Result<>();
        result.setCode(resultMessage.getCode());
        result.setMessage(resultMessage.getMessage());
        result.setTime(resultMessage.getTime());
        result.setTimestamp(resultMessage.getTimestamp());
        result.setData(data);
        return result;
    }

    //带参数成功
    public static <T> Result success(T data){
        Result<T> result = new Result();
        result.setCode(0);
        result.setMessage("成功！！！");
        result.setTime(DateUtils.dateFormat(new Date()));
        result.setTimestamp(System.currentTimeMillis());
        result.setData(data);
        return result;
    }
    //不带参数成功
    public static <T> Result success(){
        return success(null);
    }

    public static <T> Result error(ResultMessage resultMessage){
        Result<T> result = new Result();
        result.setCode(resultMessage.getCode());
        result.setMessage(resultMessage.getMessage());
        result.setTime(resultMessage.getTime());
        result.setTimestamp(resultMessage.getTimestamp());
        return result;
    }

    public static <T> Result error(ResultMsg errorMsg){
        Result<T> result = new Result();
        result.setCode(errorMsg.getCode());
        result.setMessage(errorMsg.getMessage());
        result.setTime(errorMsg.getTime());
        result.setTimestamp(errorMsg.getTimestamp());
        return result;
    }
}
