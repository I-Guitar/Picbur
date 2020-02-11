package com.joe.picBed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:conf.properties")
public class PicBedApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicBedApplication.class, args);
    }

}
