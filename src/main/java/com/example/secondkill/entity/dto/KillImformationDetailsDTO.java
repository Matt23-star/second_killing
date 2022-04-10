package com.example.secondkill.entity.dto;

import com.example.secondkill.entity.pojo.ProductInformation;
import com.example.secondkill.entity.pojo.Sponsor;
import lombok.Data;

import java.util.Date;

/**
 * @author: Matt
 * @date: 2022/4/8/21:21
 * @description:
 */

@Data
public class KillImformationDetailsDTO {

    private String id;

    private Integer productNum;

    private Integer surplusNum;

    private String description;

    private Integer buyMaximum;

    private Date beginTime;

    private Date endTime;

    private Sponsor sponsor;

    private ProductInformation productInformation;


}
