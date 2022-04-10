package com.example.secondkill.service.impl;

import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.OrderInformation;
import com.example.secondkill.entity.pojo.ProductInformation;
import com.example.secondkill.entity.pojo.UserKillState;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.mapper.Order_informationMapper;
import com.example.secondkill.mapper.Product_informationMapper;
import com.example.secondkill.mapper.User_kill_stateMapper;
import com.example.secondkill.service.IUser_kill_stateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class User_kill_stateServiceImpl extends ServiceImpl<User_kill_stateMapper, UserKillState> implements IUser_kill_stateService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    private User_kill_stateMapper userKillStateMapper;

    @Autowired
    private Order_informationMapper orderMapper;

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private Product_informationMapper productInformationMapper;

    public static Object payLock = new Object();

    @PostConstruct
    public void init() {
        // 此线程将秒杀状态和订单信息从redis中存入数据库中
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String killId = stringStringRedisTemplate.opsForSet().randomMember("secondKills");
                if (killId != null) {
                    for (int i = 0; i < 120; i++) {
                        UserKillState userKillState =
                                (UserKillState) redisTemplate.opsForList().rightPop(killId + ".userStateList");
                        if (userKillState == null) break;
                        else {
                            synchronized (payLock) {
                                Object payState = redisTemplate.opsForValue().get(killId + "." + userKillState.getUserId() + ".waiting for pay");
                                // 此时已经付款
                                if (payState == null) userKillState.setState("已支付");
                                else redisTemplate.delete(killId + "." + userKillState.getUserId() + ".waiting for pay");
                                userKillStateMapper.insert(userKillState);

                                Integer buyNum = (Integer) redisTemplate.opsForValue().get(killId + "." + userKillState.getUserId() + ".buyNum");
                                redisTemplate.delete(killId + "." + userKillState.getUserId() + ".buyNum");
                                KillInformation killInformation = killInformationMapper.selectById(killId);
                                ProductInformation productInformation = productInformationMapper.selectById(killInformation.getProductId());

                                OrderInformation orderInformation = new OrderInformation();
                                orderInformation.setId
                                        (UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
                                orderInformation.setKillInformationId(killId);
                                orderInformation.setUserId(userKillState.getUserId());
                                orderInformation.setBuyNumber(buyNum);
                                orderInformation.setTotalPrice(new BigDecimal(productInformation.getPrice().doubleValue() * buyNum));
                                orderInformation.setTime(new Date());
                                orderInformation.setState(userKillState.getState());

                                orderMapper.insert(orderInformation);
                            }
                        }
                    }
                }
            }
        }).start();
    }

}
