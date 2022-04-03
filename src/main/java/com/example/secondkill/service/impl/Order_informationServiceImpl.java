package com.example.secondkill.service.impl;

import com.example.secondkill.entity.pojo.OrderInformation;
import com.example.secondkill.mapper.Order_informationMapper;
import com.example.secondkill.service.IOrder_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class Order_informationServiceImpl extends ServiceImpl<Order_informationMapper, OrderInformation> implements IOrder_informationService {
    @PostConstruct
    public void init() {
        // 此线程将订单状态从redis中存入数据库中
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
