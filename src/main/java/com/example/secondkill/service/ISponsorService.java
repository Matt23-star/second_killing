package com.example.secondkill.service;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.Sponsor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondkill.entity.pojo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    List<KillInformation> universalKillSelect(String colName, String value, String orderBy, String aOrD, int from, int limit);
    KillInformation getKillDetails(String killId);
    Result updateKillInfo (KillInformation killInformation);
    Result deleteKillInfo(String killId);
    List<KillInformation> getAllKillWithLimit(Integer from, Integer num);
    Result login(String name, String password, HttpServletResponse response);
}
