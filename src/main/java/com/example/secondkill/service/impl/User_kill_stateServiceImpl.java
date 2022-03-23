package com.example.secondkill.service.impl;

import com.example.secondkill.entity.pojo.UserKillState;
import com.example.secondkill.mapper.User_kill_stateMapper;
import com.example.secondkill.service.IUser_kill_stateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class User_kill_stateServiceImpl extends ServiceImpl<User_kill_stateMapper, UserKillState> implements IUser_kill_stateService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    private User_kill_stateMapper userKillStateMapper;

    @PostConstruct
    public void init() {
        // 此线程将秒杀状态从redis中存入数据库中
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
                        else userKillStateMapper.insert(userKillState);
                    }
                }
            }
        }).start();
    }

}
