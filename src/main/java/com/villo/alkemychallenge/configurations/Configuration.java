package com.villo.alkemychallenge.configurations;

import com.villo.alkemychallenge.integrations.decoders.CustomErrorBaseFormatDecoder;
import feign.codec.ErrorDecoder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@org.springframework.context.annotation.Configuration
@EnableAsync
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorBaseFormatDecoder();
    }
}
