package com.example.secondkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.secondkill.mapper")
public class SecondkillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondkillingApplication.class, args);
    }

}
