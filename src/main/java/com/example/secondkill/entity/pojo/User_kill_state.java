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
public class User_kill_state implements Serializable {


    private String id;

    private String userId;

    private String killActivityId;

    private String state;

    private LocalDateTime time;

}
