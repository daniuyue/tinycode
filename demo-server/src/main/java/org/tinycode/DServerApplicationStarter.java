package org.tinycode;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.tinycode")
public class DServerApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(DServerApplicationStarter.class, args);
    }
}
