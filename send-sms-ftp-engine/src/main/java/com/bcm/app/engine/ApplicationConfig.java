package com.bcm.app.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class);
    }

    @Bean
    public FtpConfigProperties ftpConfigProperties(){
        return new FtpConfigProperties("config.properties");
    }
}
