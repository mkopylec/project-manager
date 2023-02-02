package com.github.mkopylec.projectmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.SpringApplication.run;

@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        run(ApplicationStarter.class, args);
    }
}
