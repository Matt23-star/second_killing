package com.example.secondkill.mapper;

import com.example.secondkill.entity.pojo.ScreenResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Component
public interface Screen_resultMapper extends BaseMapper<ScreenResult> {
    List<ScreenResult> getByUserId(@Param("userId") String userId,
                                   @Param("from") Integer from,
                                   @Param("num") Integer num);
}
