package com.example.secondkill.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: Matt
 * @date: 2022/3/23/10:44
 * @description:
 */

@Data
public class KillImformationDTO {
    private String productId;

    private String sponsorId;

    private Integer productNum;

    private String description;

    private Integer buyMaximum;

    private Date beginTime;

    private Date endTime;
}
