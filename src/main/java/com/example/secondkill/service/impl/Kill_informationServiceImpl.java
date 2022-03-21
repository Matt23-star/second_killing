package com.example.secondkill.service.impl;

import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.service.IKill_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
    public Kill_informationServiceImpl(){
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
                        stringStringRedisTemplate.opsForSet().remove("secondKills",killId);
                        stringStringRedisTemplate.delete(killId + "url");
                        continue;
                    }
                    Boolean noCreateUrl = stringStringRedisTemplate.hasKey(killId+"noCreateUrlFlag");
                    if(noCreateUrl){
                        continue;
                    }
                    String s = UUID.randomUUID().toString();
                    stringStringRedisTemplate.opsForValue()
                            .set(killId + "url", s);
                }
            }
        }).run();
    }

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringStringRedisTemplate;

    private static final Long createUrl = 5000L;

    @Override
    public Result addSecondKill(KillInformation killInformation) {
        if(killInformation==null){
            return ResultUtils.error(ResultMsg.ERROR);
        }
        killInformation.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        if(killInformationMapper.insert(killInformation)<=0)
            return ResultUtils.error(ResultMsg.ERROR);
        Date begin = killInformation.getBeginTime();
        Date end = killInformation.getEndTime();
        Long createUrlTimes = begin.getTime()-System.currentTimeMillis()-createUrl;
        Long endTimes = end.getTime() - begin.getTime();
        redisTemplate.opsForSet().add("sencondKills",killInformation.getId());
        redisTemplate.opsForValue().set(killInformation.getId()+"noCreateUrlFlag",killInformation.getId(),createUrlTimes, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(killInformation.getId()+"noKillFlag",killInformation.getId(),createUrlTimes+createUrl,TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(killInformation.getId()+"noEndKillFlag",killInformation.getId(),endTimes,TimeUnit.MILLISECONDS);
        return ResultUtils.success(ResultMsg.SUCCESS);
    }
}