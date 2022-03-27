package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.Sponsor;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.service.ISponsorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class SponsorServiceImpl extends ServiceImpl<SponsorMapper, Sponsor> implements ISponsorService {

    @Autowired
    SponsorMapper sponsorMapper;

    @Autowired
    private Kill_informationMapper kill_informationMapper;

    @Override
    public void addSponsor(Sponsor sponsor) {
        sponsorMapper.insert(sponsor);
    }

    //全局模糊查询
    public List<KillInformation> universalKillSelect(String colName, String value, String orderBy, String aOrD, int from, int limit){
        final List<KillInformation> killInformations
                = kill_informationMapper.universalKillSelect(colName, value, orderBy, aOrD, from, limit);
        return killInformations;
    }

    @Override
    public KillInformation getKillDetails(String killId) {
        return kill_informationMapper.selectById(killId);
    }

    @Override
    public Result updateKillInfo(KillInformation killInformation) {
        int i = kill_informationMapper.updateById(killInformation);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (411, "Update Failed", DateUtils.dateFormat(new Date()), System.currentTimeMillis()));
        else return ResultUtils.success("Updated successfully.");
    }

    @Override
    public Result deleteKillInfo(String killId) {
        int i = kill_informationMapper.deleteById(killId);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (412, "Delete Failed", DateUtils.dateFormat(new Date()), System.currentTimeMillis()));
        else return ResultUtils.success("Deleted successfully.");
    }

}
