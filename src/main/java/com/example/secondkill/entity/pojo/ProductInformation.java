package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * <p>
 * 
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Data
public class ProductInformation implements Serializable {


    private String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32);

    private String name;

    private BigDecimal price;

    private String description;

    private Integer surplusNum;

    private String type;

}
