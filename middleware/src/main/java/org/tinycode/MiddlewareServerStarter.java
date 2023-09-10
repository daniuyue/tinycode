package org.tinycode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.tinycode.mappers")
@SpringBootApplication(scanBasePackages = "org.tinycode")
public class MiddlewareServerStarter {
    public static void main(String[] args) {
        SpringApplication.run(MiddlewareServerStarter.class, args);
    }
}
