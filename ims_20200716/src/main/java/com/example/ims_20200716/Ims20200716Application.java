package com.example.ims_20200716;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ims_20200716.dao")
public class Ims20200716Application {

    public static void main(String[] args) {
        SpringApplication.run(Ims20200716Application.class, args);
    }

}
