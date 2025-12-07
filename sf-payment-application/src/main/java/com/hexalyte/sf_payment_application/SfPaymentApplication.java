package com.hexalyte.sf_payment_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SfPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfPaymentApplication.class, args);
    }

}