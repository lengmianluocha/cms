package com.pcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;



@MapperScan("com.pcms.dao")
@SpringBootApplication
public class PcmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcmsApplication.class, args);
    }
}
