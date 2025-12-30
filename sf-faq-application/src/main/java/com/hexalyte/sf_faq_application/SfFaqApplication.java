package com.hexalyte.sf_faq_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SfFaqApplication {
    public static void main(String[] args) {
        SpringApplication.run(SfFaqApplication.class, args);
    }
}