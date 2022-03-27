package com.example.secondkill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.ResultMessage;
import com.example.secondkill.entity.pojo.User;
import com.example.secondkill.mapper.UserMapper;
import com.example.secondkill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondkill.utils.DateUtils;
import com.example.secondkill.utils.ResultUtils;
import io.lettuce.core.pubsub.PubSubEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    //一天的有效登录
    private static final long EXPIRE_TIME=1*24*60*1000;

    public Result<User> userLogin(String userName, String password, HttpServletResponse response){
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password))
            return ResultUtils.error(new ResultMessage(12001,"用户名或密码为空", DateUtils.dateFormat(new Date()),System.currentTimeMillis()));
        User user=new User();
        user.setName(userName);
        user.setPassword(password);
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("name", userName);
        columnMap.put("password", password);
        List<User> users = userMapper.selectByMap(columnMap);
        if(users.size()==0)
            return ResultUtils.error(new ResultMessage(12002,"用户名或密码错误",DateUtils.dateFormat(new Date()),System.currentTimeMillis()));
        //查到有相关用户
        User u = users.get(0);
        redisTemplate.opsForValue().set(u.getId()+"token",u.getId(),EXPIRE_TIME, TimeUnit.MILLISECONDS);
        Cookie cookie=new Cookie("userToken",u.getId());
        response.addCookie(cookie);
        return ResultUtils.success(u);
    }

}
