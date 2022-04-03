package com.example.secondkill.mapper;

import com.example.secondkill.entity.pojo.UserKillState;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Component
public interface User_kill_stateMapper extends BaseMapper<UserKillState> {
    UserKillState selectByUidAndKid(@Param("userId") String userId, @Param("killId") String killid);
}
