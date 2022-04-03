package com.example.secondkill.service;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.OrderInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
public interface IOrder_informationService extends IService<OrderInformation> {
    Result pay(String killId, String userId);
}
