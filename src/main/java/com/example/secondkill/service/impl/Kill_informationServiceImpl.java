package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.RuleInformation;
import com.example.secondkill.entity.pojo.ScreenResult;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.mapper.Screen_resultMapper;
import com.example.secondkill.service.IKill_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
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
        // 此线程进行秒杀的基本管理
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Set<String> killIds = stringStringRedisTemplate.opsForSet().members("secondKills");
                for (String killId : killIds) {
                    if (!stringStringRedisTemplate.hasKey(killId + "noEndKillFlag")) {
                        // 此时线程发现该秒杀活动已经结束
                        stringStringRedisTemplate.opsForSet().remove("secondKills", killId);
                        stringStringRedisTemplate.delete(killId + "url");
                        stringStringRedisTemplate.delete(killId + "inited");
                        stringStringRedisTemplate.delete(killId + ".buyMaximum");
                        KillInformation killInformation = killInformationMapper.selectById(killId);
                        killInformation
                                .setSurplusNum(Integer.parseInt(
                                        stringStringRedisTemplate.opsForValue().get(killId + ".leftNum")));
                        stringStringRedisTemplate.delete(killId + ".leftNum");
                        killInformation.setState("已结束");
                        killInformationMapper.updateById(killInformation);
                        continue;
                    }
                    // 进行布隆过滤器的初始化、随机秒杀连接的初始化以及缓存的初始化写入
                    if (!stringStringRedisTemplate.hasKey(killId + "noCreateUrlFlag")) {
                        if (!stringStringRedisTemplate.hasKey(killId + "inited")) {
                            String s = UUID.randomUUID().toString();
                            stringStringRedisTemplate.opsForValue().set(killId + "randomUrl", s);
                            RedisServiceImpl.initBloomFilters();
                            KillInformation killInformation = killInformationMapper.selectById(killId);
                            stringStringRedisTemplate.opsForValue()
                                    .set(killId + ".leftNum", killInformation.getProductNum() + "");
                            stringStringRedisTemplate.opsForValue()
                                    .set(killId + ".buyMaximum", killInformation.getBuyMaximum() + "");
                            stringStringRedisTemplate.opsForValue().set(killId + "inited", " ");
                        }
                    }
                }
            }
        }).start();
    }

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private Screen_resultMapper srMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringStringRedisTemplate;

    private static final Long createUrl = 5000L;

    public Result getRandomUrl(String userId, String killInformationId) {
        String noCreateUrl = (String) redisTemplate.opsForValue().get(killInformationId + "noKillFlag");
        if (noCreateUrl != null)
            return ResultUtils.error(new ResultMessage(1234, "秒杀活动尚未开始"));
        String noEndKillFlag = (String) redisTemplate.opsForValue().get(killInformationId + "noEndKillFlag");
        if (noEndKillFlag == null)
            return ResultUtils.error(new ResultMessage(2345, "秒杀活动已经结束"));
        String randomUrl = (String) redisTemplate.opsForValue().get(killInformationId + "randomUrl");
        return ResultUtils.success(new ResultMessage(200, "动态url返回成功"), randomUrl);
    }

    @Override
    public Result<List<KillInformation>> getAvailableKill(String userId, String from, String num) {
        LinkedList<KillInformation> results = new LinkedList<>();
        List<ScreenResult> srs = srMapper.getByUserId(userId);
        for (ScreenResult sr : srs) {
            results.add(killInformationMapper.selectById(sr.getKillInfoId()));
        }
        return ResultUtils.success(new ResultMessage(199, "可用秒杀列表返回成功"), results);
    }


    @Override
    public Result addSecondKill(KillImformationDTO killImformationDTO) {
        if (killImformationDTO == null) {
            return ResultUtils.error(new ResultMessage(500, "数据为空"));
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
            return ResultUtils.error(new ResultMessage(500, "KillInformation insert error"));
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
        return ResultUtils.success(new ResultMessage(200, "Add Second Kill Successfully"),null);
    }
}
