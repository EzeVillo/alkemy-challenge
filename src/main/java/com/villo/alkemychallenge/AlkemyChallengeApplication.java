package com.villo.alkemychallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.villo.alkemychallenge")
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Alkemy Challenge API",
                contact = @Contact(
                        name = "EzeVillo", email = "ezevillodev@gmail.com"
                )
        ),
        security = {@SecurityRequirement(name = "Bearer Authentication")}
)
public class AlkemyChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlkemyChallengeApplication.class, args);
    }

}
