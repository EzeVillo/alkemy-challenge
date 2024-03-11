package com.villo.alkemychallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.villo.alkemychallenge")
public class AlkemyChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlkemyChallengeApplication.class, args);
    }

}
