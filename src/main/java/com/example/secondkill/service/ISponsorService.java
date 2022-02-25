package com.example.secondkill.service;

import com.example.secondkill.entity.Sponsor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
public interface ISponsorService extends IService<Sponsor> {
    void addSponsor(Sponsor sponsor);
}
