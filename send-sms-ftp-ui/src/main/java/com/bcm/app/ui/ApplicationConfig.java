package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.bcm.app.engine.JobConfig;
import com.bcm.app.engine.SendSMSJob;

/**
 * Class ApplicationConfig is the configuration of spring-boot
 * application. It is checked by the spring-boot framework first
 * at begining of application startup. All beans defined in it
 * is prepared/instantiated by the framework then and put into 
 * the bean pool for other components' usage. 
 *
 */
@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationConfig.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    /**
     * Bean: an instance of class JobConfig, injected with config
     * file path.
     *
     * @return JobConfig 
     */
    @Bean
    public JobConfig mJobConfig(){
        return new JobConfig("config.properties");
    }

    /**
     * Bean: an instance of class SendSMSJob.
     *
     * @return SendSMSJob 
     */
    @Bean
    public SendSMSJob mJob(){
        return new SendSMSJob();
    }

}
