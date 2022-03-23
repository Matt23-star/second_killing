package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class ScreenResult implements Serializable {


    private String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32);

    private String userId;

    private String killInfoId;

    private String result;

    private LocalDateTime time;
}
