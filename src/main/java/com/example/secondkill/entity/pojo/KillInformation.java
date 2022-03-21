package com.example.secondkill.entity.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
public class KillInformation implements Serializable {


    private String id;

    private Date beginTime;

    private Date endTime;

    private String productId;

    private String sponsorId;

    private Integer productNum;

    private Integer surplusNum;

    private String state;

    private String ruleId;

    private String description;

    private Integer buyMaximum;

}
