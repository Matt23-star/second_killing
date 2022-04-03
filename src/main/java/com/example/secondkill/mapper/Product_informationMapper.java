package com.example.secondkill.mapper;

import com.example.secondkill.entity.pojo.KillInformation;
import com.example.secondkill.entity.pojo.ProductInformation;
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
public interface Product_informationMapper extends BaseMapper<ProductInformation> {
    List<ProductInformation> universalProductSelect(@Param("colName") String colName,
                                              @Param("value") String value,
                                              @Param("orderBy") String orderBy,
                                              @Param("aORd") String aOrD,
                                              @Param("from") int from,
                                              @Param("limit") int limit
    );
}
