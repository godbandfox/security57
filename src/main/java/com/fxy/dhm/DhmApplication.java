package com.fxy.dhm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fxy.dhm.dao.mapper")
public class DhmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhmApplication.class, args);
    }

}
