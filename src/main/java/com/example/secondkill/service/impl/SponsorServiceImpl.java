package com.example.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.KillImformationDTO;
import com.example.secondkill.entity.dto.KillImformationDetailsDTO;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.ProductInformation;
import com.example.secondkill.entity.pojo.Sponsor;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.mapper.Product_informationMapper;
import com.example.secondkill.mapper.SponsorMapper;
import com.example.secondkill.mapper.UserMapper;
import com.example.secondkill.service.ISponsorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private SponsorMapper sponsorMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Kill_informationMapper kill_informationMapper;

    @Autowired
    private Product_informationMapper productInformationMapper;

    @Override
    public void addSponsor(Sponsor sponsor) {
        sponsorMapper.insert(sponsor);
    }


    //全局模糊查询
    public List<KillInformation> universalKillSelect(String colName, String value, String orderBy, String aOrD, int from, int limit){
        if (value == null || value.equals("*")) value = "";
        final List<KillInformation> killInformations
                = kill_informationMapper.universalKillSelect(colName, value, orderBy, aOrD, from, limit);
        int size = killInformations.size();
        for (int i = 0; i < size; i++) {
            KillInformation killInformation = killInformations.get(i);
        }
        return killInformations;
    }

    @Override
    public Result getKillDetails(String killId) {
        QueryWrapper<KillInformation> queryWrapper = new QueryWrapper<KillInformation>().eq("id",killId);
        KillInformation killInformation = kill_informationMapper.selectOne(queryWrapper);
        KillImformationDetailsDTO killImformationDetailsDTO = new KillImformationDetailsDTO();
        System.out.println(killInformation);
        killImformationDetailsDTO.setId(killInformation.getId());
        killImformationDetailsDTO.setSurplusNum(killInformation.getProductNum());
        killImformationDetailsDTO.setProductNum(killInformation.getProductNum());
        killImformationDetailsDTO.setBuyMaximum(killInformation.getBuyMaximum());
        killImformationDetailsDTO.setBeginTime(killInformation.getBeginTime());
        killImformationDetailsDTO.setEndTime(killInformation.getEndTime());
        killImformationDetailsDTO.setDescription(killInformation.getDescription());
        killImformationDetailsDTO.setSponsor(sponsorMapper.selectById(killInformation.getSponsorId()));
        killImformationDetailsDTO.setProductInformation(productInformationMapper.selectById(killInformation.getProductId()));
        System.out.println(killImformationDetailsDTO);
        return ResultUtils.success(killImformationDetailsDTO);
    }

    @Override
    public Result updateKillInfo(KillInformation killInformation) {
        int i = kill_informationMapper.updateById(killInformation);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (411, "Update Failed"));
        else return ResultUtils.success("Updated successfully.");
    }

    @Override
    public Result deleteKillInfo(String killId) {
        int i = kill_informationMapper.deleteById(killId);
        if (i <= 0) return ResultUtils.error
                (new ResultMessage
                        (412, "Delete Failed"));
        else return ResultUtils.success("Deleted successfully.");
    }

    @Override
    public List<KillInformation> getAllKillWithLimit(Integer from, Integer num) {
        List<KillInformation> kills = kill_informationMapper.getAllKillWithLimit(from, num);
        int size = kills.size();
        for (int i = 0; i < size; i++) {
            KillInformation killInformation = kills.get(i);
        }
        return kills;
    }

    private static final long EXPIRE_TIME=1*24*60*1000;

    @Override
    public Result login(String name, String password, HttpServletResponse response) {
        if(StringUtils.isBlank(name)||StringUtils.isBlank(password))
            return ResultUtils.error(new ResultMessage(13001,"账号或密码为空"));
        Sponsor sponsor=new Sponsor();
        sponsor.setName(name);
        sponsor.setPassword(password);
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("name", name);
        columnMap.put("password", password);
        List<Sponsor> sponsors = sponsorMapper.selectByMap(columnMap);
        if(sponsors.size()==0)
            return ResultUtils.error(new ResultMessage(13002,"账号或密码错误"));
        //查到有相关用户
        Sponsor s = sponsors.get(0);
        redisTemplate.opsForValue().set(s.getId()+"adminToken",s .getId(),EXPIRE_TIME, TimeUnit.MILLISECONDS);
        Cookie cookie=new Cookie("adminToken",s.getId());
        response.addCookie(cookie);
        return ResultUtils.success(s);
    }
}
