package com.example.secondkill.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Data
public class Product_information implements Serializable {


    private String id;

    private String name;

    private BigDecimal price;

    private String description;

    private Integer surplusNum;

    private String type;

}
