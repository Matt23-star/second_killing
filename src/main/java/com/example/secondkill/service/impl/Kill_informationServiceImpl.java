package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.service.IKill_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class Kill_informationServiceImpl extends ServiceImpl<Kill_informationMapper, KillInformation> implements IKill_informationService {

    @Autowired
    private RedisTemplate redisTemplate;

    public Result<String> getRandomUrl(String userId, String killInformationId) {
        String noCreateUrl = (String) redisTemplate.opsForValue().get(killInformationId + "noCreateUrl");
        if (noCreateUrl != null)
            return ResultUtils.error(new ResultMessage(1234,"秒杀活动尚未开始",new Date(),System.currentTimeMillis()));
        String noEndKillFlag = (String) redisTemplate.opsForValue().get(killInformationId + "noEndKillFlag");
        if (noEndKillFlag == null)
            return ResultUtils.error(new ResultMessage(2345,"秒杀活动已经结束",new Date(),System.currentTimeMillis()));
        String randomUrl = (String) redisTemplate.opsForValue().get(killInformationId + "randomUrl");
        return ResultUtils.success(new ResultMessage(200,randomUrl,new Date(),System.currentTimeMillis()));
    }

}
