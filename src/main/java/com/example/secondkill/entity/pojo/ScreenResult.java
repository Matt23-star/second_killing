package com.example.secondkill.entity.pojo;

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
public class ScreenResult implements Serializable {


    private String id;

    private String userId;

    private String killInfoId;

    private String result;

    private LocalDateTime time;
}
