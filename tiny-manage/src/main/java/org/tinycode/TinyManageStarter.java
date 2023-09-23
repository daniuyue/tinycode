package org.tinycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication(scanBasePackages = "org.tinycode")
public class TinyManageStarter {

    public static void main(String[] args) {
        SpringApplication.run(TinyManageStarter.class, args);
    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        RestTemplate restTemplate = builder.build();
//        return restTemplate;
//    }

}
