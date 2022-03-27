package com.example.secondkill.service.impl;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.UserDTO;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.mapper.UserMapper;
import com.example.secondkill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result register(UserDTO userDTO) {
        if(userDTO==null)
            return ResultUtils.error(new ResultMessage(500, "表单数据为空"));
        if(redisTemplate.opsForValue().get(userDTO)==null){
            return ResultUtils.error(new ResultMessage(500, "验证码已过期"));
        }
        if(!redisTemplate.opsForValue().get(userDTO).equals(userDTO.getCheckCode())){
            return ResultUtils.error(new ResultMessage(500, "验证码错误"));
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setGender(userDTO.getGender());
        user.setNickName(userDTO.getNickName());
        user.setIdCardNumber(userDTO.getIdCardNumber());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDeposit(userDTO.getDeposit());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        if(userMapper.insert(user)<=0)
            return ResultUtils.error(new ResultMessage(500, "User insert error"));
        return ResultUtils.success(new ResultMessage(200,"注册成功"));
    }
}
