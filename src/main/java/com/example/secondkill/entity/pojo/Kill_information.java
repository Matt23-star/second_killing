package com.example.secondkill.entity.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */
@Data
@ApiModel(value = "com.example.secondkill.entity.pojo.Kill_information",description = "秒杀活动信息")
public class Kill_information implements Serializable {


    private String id;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String productId;

    private String sponsorId;

    private Integer productNum;

    private Integer surplusNum;

    private String state;

    private String ruleId;

    private String description;

    private Integer buyMaximum;

}
