package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationConfig.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    @Bean
    public FtpConfigProperties ftpConfigProperties(){
        return new FtpConfigProperties("config.properties");
    }
}
