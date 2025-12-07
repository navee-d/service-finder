package com.hexalyte.sf_booking_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class SfBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfBookingApplication.class, args);
    }
}
