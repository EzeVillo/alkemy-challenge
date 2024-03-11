package com.villo.alkemychallenge.email.integrations;

import com.villo.alkemychallenge.email.stos.requests.MailRequestSTO;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mailjet", url = "https://api.mailjet.com/v3.1", configuration = MailjetClient.ConfigurationClient.class)
public interface MailjetClient {
    @PostMapping(value = "send")
    void send(@RequestBody MailRequestSTO mailRequestSTO);

    class ConfigurationClient {
        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${mailjet.username}") String username,
                                                                       @Value("${mailjet.password}") String password) {
            return new BasicAuthRequestInterceptor(username, password);
        }
    }
}
