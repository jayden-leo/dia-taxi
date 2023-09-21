package com.jayden.apipassenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiPassenger {
    public static void main(String[] args) {
        SpringApplication.run(ApiPassenger.class);
    }
}
