package com.example.sf_discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SfDiscoveryServer {

    public static void main(String[] args) {
        SpringApplication.run(SfDiscoveryServer.class, args);
    }

}
