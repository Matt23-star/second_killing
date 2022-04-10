package com.example.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.OrderDTO;
import com.example.secondkill.entity.pojo.*;
import com.example.secondkill.mapper.*;
import com.example.secondkill.service.IOrder_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private Order_informationMapper orderMapper;

    @Autowired
    private User_kill_stateMapper uksMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Kill_informationMapper killMapper;

    @Autowired
    private Product_informationMapper productMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /*@PostConstruct
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
    }*/

    @Override
    public Result pay(String killId, String userId) {
        synchronized (User_kill_stateServiceImpl.payLock) {
            Object o = redisTemplate.opsForValue().get(killId + "." + userId + ".waiting for pay");
            if (o == null) {
                UserKillState userKillState = uksMapper.selectByUidAndKid(userId, killId);
                if (userKillState == null)
                    return ResultUtils.error(new ResultMessage(6750, "指定订单不存在"));
                else {
                    User user = userMapper.selectById(userId);
                    OrderInformation orderInformation = orderMapper.getOrderByUidAndKid(userId, killId);
                    if (orderInformation.getTotalPrice().doubleValue() > user.getDeposit().doubleValue()) {
                        return ResultUtils.error(new ResultMessage(6900, "余额不足"));
                    } else {
                        user.setDeposit(user.getDeposit().subtract(orderInformation.getTotalPrice()));
                        userKillState.setState("已支付");
                        orderInformation.setState("已支付");
                        userMapper.updateById(user);
                        uksMapper.updateById(userKillState);
                        orderMapper.updateById(orderInformation);
                        return ResultUtils.success(new ResultMessage(6910, "支付成功"));
                    }
                }
            } else {
                User user = userMapper.selectById(userId);
                KillInformation killInformation = killMapper.selectById(killId);
                ProductInformation productInformation = productMapper.selectById(killInformation.getProductId());
                String bnStr = (String) redisTemplate.opsForValue().get(killId + "." + userId + ".buyNum");
                Integer buyNum = Integer.parseInt(bnStr);
                BigDecimal totalPrice = productInformation.getPrice().multiply(new BigDecimal(buyNum));
                if (totalPrice.doubleValue() > user.getDeposit().doubleValue()) {
                    return ResultUtils.error(new ResultMessage(6900, "余额不足"));
                } else {
                    user.setDeposit(user.getDeposit().subtract(totalPrice));
                    redisTemplate.delete(killId + "." + userId + ".waiting for pay");
                    userMapper.updateById(user);
                    return ResultUtils.success(new ResultMessage(6910, "支付成功"));
                }
            }
        }
    }

    @Override
    public Result getOrdersByUserId(String userId) {
        QueryWrapper<OrderInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<OrderInformation> orderInformationList = orderMapper.selectList(queryWrapper);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderInformation orderInformation : orderInformationList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setTotalPrice(orderInformation.getTotalPrice());
            orderDTO.setUserId(orderInformation.getUserId());
            orderDTO.setBeginTime(killMapper.selectById(orderInformation.getKillInformationId()).getBeginTime());
            orderDTO.setKillInformationId(orderInformation.getKillInformationId());
            orderDTO.setBuyNumber(orderInformation.getBuyNumber());
            orderDTO.setEndTime(killMapper.selectById(orderInformation.getKillInformationId()).getEndTime());
            orderDTO.setTime(orderInformation.getTime());
            orderDTO.setState(orderInformation.getState());
            orderDTOList.add(orderDTO);
        }
        return ResultUtils.success(orderDTOList);
    }
}
