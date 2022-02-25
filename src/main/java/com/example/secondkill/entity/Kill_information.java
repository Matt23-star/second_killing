package com.example.secondkill.entity;

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
