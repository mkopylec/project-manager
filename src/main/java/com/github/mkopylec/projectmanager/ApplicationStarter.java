package com.github.mkopylec.projectmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.SpringApplication.run;

@EnableAsync
@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        run(ApplicationStarter.class, args);
    }
}
