package com.example.secondkill.service;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.KillInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
public interface IKill_informationService extends IService<KillInformation> {
    public Result addSecondKill(KillInformation killInformation);

    public Result<String> getRandomUrl(String userId, String killInformationId);
}
