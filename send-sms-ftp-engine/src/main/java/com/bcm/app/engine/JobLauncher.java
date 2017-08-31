package com.bcm.app.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class JobLauncher is the entrance of the application. It is
 * invoked when user runs the JAR file with JVM. It initiates
 * a SendSMSJob object, set its configuration and kicks it off.
 *
 */
@Component
public class JobLauncher implements CommandLineRunner {

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    private JobConfig jobConfig;

    /*
     * Bean get/autowire from the pool(not from the ApplicationConfig
     * but from the class SendSMSJob marked with the annotation @Component
     */
    @Autowired
    private SendSMSJob sendSMSJob;

    /**
     * Method run set config to job and kicks if off
     *
     */
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Start the SMS Sending Job...");
        
        sendSMSJob.setConfig(jobConfig);
        sendSMSJob.init();
        sendSMSJob.run();

    }

}
