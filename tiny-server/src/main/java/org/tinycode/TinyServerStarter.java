package org.tinycode;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.tinycode")
public class TinyServerStarter {

    public static void main(String[] args) {
        SpringApplication.run(TinyServerStarter.class, args);
    }
}
