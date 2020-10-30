package com.github.mkopylec.projectmanager.io;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

import static java.time.Duration.ofMillis;

@Configuration
class RestOperationsConfiguration {

    @Bean
    RestOperations restOperations() {
        return new RestTemplateBuilder()
                .setConnectTimeout(ofMillis(200))
                .setReadTimeout(ofMillis(2000))
                .build();
    }
}
