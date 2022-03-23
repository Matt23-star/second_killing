package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.enums.ResultMsg;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.RuleInformation;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.service.IKill_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Set<String> killIds = stringStringRedisTemplate.opsForSet().members("secondKills");
                for (String killId : killIds) {
                    Boolean noEnd = stringStringRedisTemplate.hasKey(killId + "noEndKillFlag");
                    if (!noEnd) {
                        stringStringRedisTemplate.opsForSet().remove("secondKills", killId);
                        stringStringRedisTemplate.delete(killId + "url");
                        continue;
                    }
                    Boolean noCreateUrl = stringStringRedisTemplate.hasKey(killId + "noCreateUrlFlag");
                    if (noCreateUrl) {
                        continue;
                    }
                    String s = UUID.randomUUID().toString();
                    stringStringRedisTemplate.opsForValue()
                            .set(killId + "url", s);
                    RedisService.initBloomFilters();
                    //stringStringRedisTemplate.opsForValue().set(killId + ".leftNum",);

                }
            }
        }).start();
    }

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringStringRedisTemplate;

    private static final Long createUrl = 5000L;

    public Result<String> getRandomUrl(String userId, String killInformationId) {
        String noCreateUrl = (String) redisTemplate.opsForValue().get(killInformationId + "noKillFlag");
        if (noCreateUrl != null)
            return ResultUtils.error(new ResultMessage(1234, "秒杀活动尚未开始", new Date(), System.currentTimeMillis()));
        String noEndKillFlag = (String) redisTemplate.opsForValue().get(killInformationId + "noEndKillFlag");
        if (noEndKillFlag == null)
            return ResultUtils.error(new ResultMessage(2345, "秒杀活动已经结束", new Date(), System.currentTimeMillis()));
        String randomUrl = (String) redisTemplate.opsForValue().get(killInformationId + "randomUrl");
        return ResultUtils.success(new ResultMessage(200, "动态url返回成功", DateUtils.dateConvert(new Date()), System.currentTimeMillis()), randomUrl);
    }


    @Override
    public Result addSecondKill(KillImformationDTO killImformationDTO) {
        if (killImformationDTO == null) {
            return ResultUtils.error(new ResultMessage(500, "数据为空", DateUtils.dateConvert(new Date()), System.currentTimeMillis()));
        }
        RuleInformation ruleInformation = new RuleInformation();
        KillInformation killInformation = new KillInformation();

        killInformation.setProductId(killImformationDTO.getProductId());
        killInformation.setProductNum(killImformationDTO.getProductNum());
        killInformation.setSurplusNum(killImformationDTO.getProductNum());
        killInformation.setBuyMaximum(killImformationDTO.getBuyMaximum());
        killInformation.setBeginTime(killImformationDTO.getBeginTime());
        killInformation.setEndTime(killImformationDTO.getEndTime());
        killInformation.setState("未开始");
        killInformation.setRuleId(ruleInformation.getId());
        killInformation.setSponsorId(killImformationDTO.getSponsorId());
        killInformation.setDescription(killImformationDTO.getDescription());

        if (killInformationMapper.insert(killInformation) <= 0)
            return ResultUtils.error(new ResultMessage(500, "KillInformation insert error", DateUtils.dateConvert(new Date()), System.currentTimeMillis()));
        Date begin = killInformation.getBeginTime();
        Date end = killInformation.getEndTime();
        Long createUrlTimes = begin.getTime() - System.currentTimeMillis() - createUrl;
        Long endTimes = end.getTime() - System.currentTimeMillis();
        redisTemplate.opsForSet().add("sencondKills", killInformation.getId());
        redisTemplate.opsForValue()
                .set(killInformation.getId() + "noCreateUrlFlag", killInformation.getId(), createUrlTimes, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue()
                .set(killInformation.getId() + "noKillFlag", killInformation.getId(), createUrlTimes + createUrl, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue()
                .set(killInformation.getId() + "noEndKillFlag", killInformation.getId(), endTimes, TimeUnit.MILLISECONDS);
        return ResultUtils.success(new ResultMessage(200, "Add Second Kill Successfully", DateUtils.dateConvert(new Date()), System.currentTimeMillis()));
    }
}