package com.example.secondkill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.RuleInformation;
import com.example.secondkill.entity.pojo.ScreenResult;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.mapper.Kill_informationMapper;
import com.example.secondkill.mapper.Screen_resultMapper;
import com.example.secondkill.mapper.UserMapper;
import com.example.secondkill.service.IKill_informationService;
import com.example.secondkill.service.IScreen_resultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.service.ISponsorService;
import com.example.secondkill.utils.MailUtils;
import com.example.secondkill.utils.ResultUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class Screen_resultServiceImpl extends ServiceImpl<Screen_resultMapper, ScreenResult> implements IScreen_resultService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Screen_resultMapper screenResultMapper;

    @Autowired
    private ISponsorService sponsorService;

    @Autowired
    private Kill_informationMapper killInformationMapper;

    @Autowired
    private Rule_informationServiceImpl ruleInformationService;

    @Autowired
    private MailServiceImpl mailService;

    @Override
    public Result screenUsers(String killId) {
        KillInformation killInformation = killInformationMapper.selectById(killId);
        RuleInformation ruleInformation = ruleInformationService.getRule(killInformation.getRuleId()).getData();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        for (User user : userList) {
            ScreenResult screenResult = new ScreenResult();
            screenResult.setUserId(user.getId());
            screenResult.setKillInfoId(killId);
            screenResult.setResult(screenUser(user,ruleInformation).toString());
            if(screenUser(user,ruleInformation)){
                if(user.getGender().equals("男")){
                    mailService.sendSecondKill(user.getName()+"先生",user.getEmail(),killInformation.getId());
                }else
                    mailService.sendSecondKill(user.getName()+"女士",user.getEmail(),killInformation.getId());
            }
            screenResultMapper.insert(screenResult);
        }
        return ResultUtils.success(userList);
    }

    private Boolean screenUser(User user, RuleInformation ruleInformation) {
        if (ruleInformation.getDepositMax() != null && ruleInformation.getDepositMin() != null) {
            if (user.getDeposit().doubleValue() < Double.parseDouble(ruleInformation.getDepositMin())
                    || user.getDeposit().doubleValue() > Double.parseDouble(ruleInformation.getDepositMax())) {
                return false;
            }
        }
        if (ruleInformation.getDepositMin() != null && ruleInformation.getDepositMax() == null) {
            if (user.getDeposit().doubleValue() < Double.parseDouble(ruleInformation.getDepositMin())) {
                return false;
            }
        }
        if (ruleInformation.getDepositMin() == null && ruleInformation.getDepositMax() != null) {
            if (user.getDeposit().doubleValue() > Double.parseDouble(ruleInformation.getDepositMax())) {
                return false;
            }
        }

        if (ruleInformation.getAgeMax() != null && ruleInformation.getAgeMin() != null) {
            if (user.getAge() < Integer.parseInt(ruleInformation.getAgeMin())
                    || user.getAge() > Integer.parseInt(ruleInformation.getAgeMax())) {
                return false;
            }
        }
        if (ruleInformation.getAgeMin() != null && ruleInformation.getAgeMax() == null) {
            if (user.getAge() < Integer.parseInt(ruleInformation.getAgeMin())) {
                return false;
            }
        }
        if (ruleInformation.getAgeMin() == null && ruleInformation.getAgeMax() != null) {
            if (user.getAge() > Integer.parseInt(ruleInformation.getAgeMax())) {
                return false;
            }
        }
        if (ruleInformation.getProfessions() != null) {
            Boolean contain = false;
            String[] professions = ruleInformation.getProfessions().split(";");
            for (String profession : professions) {
                if (profession.equals(user.getProfession())) {
                    contain = true;
                }
            }
            if (!contain) {
                return false;
            }
        }
        if (ruleInformation.getAllowUncredit().toUpperCase() == "NO") {
            if (user.getUncredit().toUpperCase() == "YES") {
                return false;
            }
        }
        if (ruleInformation.getRiskDegree() != null) {
            if (user.getRiskDegree() <= ruleInformation.getRiskDegree()) {
                return false;
            }
        }
        return true;
    }
}
