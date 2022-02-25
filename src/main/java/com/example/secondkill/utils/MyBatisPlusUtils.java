package com.example.secondkill.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class MyBatisPlusUtils {

    public static void Generator() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://106.52.2.132:3306/second_killing?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF8",
                "root",
                "333333").build();
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .openDir(false)
                .outputDir(System.getProperty("user.dir")+"/src/main/java")
                .author("Matt")
                .openDir(false)
                .build();

        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.example.secondkill")
                .entity("entity")
                .controller("controller")
                .mapper("mapper")
                .service("service")
                .build();
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .build();

        AutoGenerator autoGenerator =new AutoGenerator(dataSourceConfig)
                .global(globalConfig)
                .packageInfo(packageConfig)
                .strategy(strategyConfig);
        autoGenerator.execute();
    }

}