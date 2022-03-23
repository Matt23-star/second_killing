package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
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
public class RuleInformation implements Serializable {

    private String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32);

}
