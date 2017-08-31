package com.bcm.app.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
        SpringApplication.run(ApplicationConfig.class);
    }

    /**
     * Bean: an instance of class JobConfig, injected with config
     * file path.
     *
     * @return JobConfig 
     */
    @Bean
    public JobConfig jobConfig(){
        return new JobConfig("config.properties");
    }
}
