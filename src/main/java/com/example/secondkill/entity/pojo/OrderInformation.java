package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class OrderInformation implements Serializable {


    private String id;

    private String killInformationId;

    private String userId;

    private String state;

    private Integer buyNumber;

    private LocalDateTime time;

    private BigDecimal totalPrice;

}
