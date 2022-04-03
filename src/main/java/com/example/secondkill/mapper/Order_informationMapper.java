package com.example.secondkill.mapper;

import com.example.secondkill.entity.pojo.OrderInformation;
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
public interface Order_informationMapper extends BaseMapper<OrderInformation> {
    OrderInformation getOrderByUidAndKid(@Param("userId") String userId, @Param("killId") String killId);
}
