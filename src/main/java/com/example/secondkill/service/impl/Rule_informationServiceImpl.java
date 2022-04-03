package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.pojo.RuleInformation;
import com.example.secondkill.mapper.Rule_informationMapper;
import com.example.secondkill.service.IRule_informationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.ResultUtils;
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
public class Rule_informationServiceImpl extends ServiceImpl<Rule_informationMapper, RuleInformation> implements IRule_informationService {

    @Autowired
    private Rule_informationMapper ruleInformationMapper;

    public Result<RuleInformation> getRule(String ruleId){
        return ResultUtils.success(ruleInformationMapper.selectById(ruleId));
    }
}
