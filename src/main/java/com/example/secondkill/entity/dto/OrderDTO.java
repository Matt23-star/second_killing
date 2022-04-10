package com.example.secondkill.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: Matt
 * @date: 2022/4/9/22:21
 * @description:
 */

@Data
public class OrderDTO {

    private String killInformationId;

    private Date beginTime;

    private Date endTime;

    private String userId;

    private String state;

    private Integer buyNumber;

    private Date time;

    private BigDecimal totalPrice;
}
