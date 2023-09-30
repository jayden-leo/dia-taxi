package com.jayden.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder -> { // 设置项目的主目录
                    builder.author("Jayden").fileOverride().outputDir("E:\\Java\\dia_taxi\\dia_taxi\\service-dirver-user\\src\\main\\java");
                })
                .packageConfig(builder -> { // 设置包的属性
                    builder.parent("com.jayden.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "E:\\Java\\dia_taxi\\dia_taxi\\service-dirver-user\\src\\main\\java\\com\\jayden\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("car");
//                    builder.addInclude("driver_user_work_status");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
