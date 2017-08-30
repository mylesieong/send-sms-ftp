package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.bcm.app.engine.JobConfig;
import com.bcm.app.engine.SendSMSJob;

@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationConfig.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    @Bean
    public JobConfig mJobConfig(){
        return new JobConfig("config.properties");
    }

    @Bean
    public SendSMSJob mJob(){
        return new SendSMSJob();
    }

}
