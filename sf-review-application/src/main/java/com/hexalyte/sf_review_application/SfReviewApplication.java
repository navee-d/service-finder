package com.hexalyte.sf_review_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <-- ADD THIS IMPORT

@SpringBootApplication
@EnableDiscoveryClient
public class SfReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfReviewApplication.class, args);
    }

}