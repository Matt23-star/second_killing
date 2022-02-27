package com.example.secondkill.service.impl;

import com.example.secondkill.entity.pojo.Sponsor;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.service.ISponsorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SponsorServiceImpl extends ServiceImpl<SponsorMapper, Sponsor> implements ISponsorService {

    @Autowired
    SponsorMapper sponsorMapper;

    @Override
    public void addSponsor(Sponsor sponsor) {
        sponsorMapper.insert(sponsor);
    }
}
