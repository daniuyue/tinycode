package com.tinycode;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tinycode")
public class DGateWayApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(DGateWayApplicationStarter.class, args);
    }
}
