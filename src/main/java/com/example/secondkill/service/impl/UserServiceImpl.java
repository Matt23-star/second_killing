package com.example.secondkill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.dto.UserDTO;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.mapper.UserMapper;
import com.example.secondkill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import io.lettuce.core.pubsub.PubSubEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Wrapper;
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


    //一天的有效登录
    private static final long EXPIRE_TIME=1*24*60*1000;

    public Result userLogin(String email, String password, HttpServletResponse response){
        if(StringUtils.isBlank(email)||StringUtils.isBlank(password))
            return ResultUtils.error(new ResultMessage(12001,"邮箱或密码为空"));
        User user=new User();
        user.setName(email);
        user.setPassword(password);
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("email", email);
        columnMap.put("password", password);
        List<User> users = userMapper.selectByMap(columnMap);
        if(users.size()==0)
            return ResultUtils.error(new ResultMessage(12002,"邮箱或密码错误"));
        //查到有相关用户
        User u = users.get(0);
        redisTemplate.opsForValue().set(u.getId()+"userToken",u.getId(),EXPIRE_TIME, TimeUnit.MILLISECONDS);
        Cookie cookie=new Cookie("userToken",u.getId());
        response.addCookie(cookie);
        return ResultUtils.success(u);
    }

    @Override
    public Result addUser(User user) {
        if(user==null){
            return ResultUtils.error(new ResultMessage(14001,"用户信息为空"));
        }
        if(userMapper.insert(user)<=0){
            return ResultUtils.error(new ResultMessage(14002,"添加失败"));
        }
        return ResultUtils.success();
    }

    @Override
    public Result deleteUser(String uid) {
        if(uid==null||uid.length()==0){
            return ResultUtils.error(new ResultMessage(14003,"用户id为空"));
        }
        if(userMapper.deleteById(uid)<=0){
            return ResultUtils.error(new ResultMessage(14004,"删除用户记录失败"));
        }
        return ResultUtils.success();
    }

    @Override
    public Result updateUserInfo(User user) {
        if(user==null){
            return ResultUtils.error(new ResultMessage(14005,"用户信息为空"));
        }
        if(userMapper.updateById(user)<=0){
            return ResultUtils.error(new ResultMessage(14006,"用户信息更新失败"));
        }
        return ResultUtils.success();
    }

    @Override
    public Result selectUserList(String colName, String value, String orderBy, String aOrD, int from, int limit) {
        List<User> users = userMapper.universalUserSelect(colName, value, orderBy, aOrD, from, limit);
        return ResultUtils.success(users);
    }

}
