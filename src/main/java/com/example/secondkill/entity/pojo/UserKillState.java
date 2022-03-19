package com.example.secondkill.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Matt
 * @since 2022-02-25
 */

@Data
public class UserKillState implements Serializable {


    private String id;

    private String userId;

    private String killActivityId;

    private String state;

    private Date time;
}
