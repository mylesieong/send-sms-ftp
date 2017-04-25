package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.bcm.app.engine.FtpConfigProperties;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.bcm.app.ui", "com.bcm.app.engine"})
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
