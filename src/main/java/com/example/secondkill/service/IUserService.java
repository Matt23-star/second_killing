package com.example.secondkill.service;

import com.example.secondkill.entity.Result;
import com.example.secondkill.entity.dto.UserDTO;
import com.example.secondkill.entity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
public interface IUserService extends IService<User> {
    Result register(UserDTO userDTO);

    Result<User> userLogin(String userName, String password, HttpServletResponse response);
}
