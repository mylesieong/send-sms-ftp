package com.bcm.app.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private JobConfig jobConfig;

    @Autowired
    private SendSMSJob sendSMSJob;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Start the SMS Sending Job...");
        
        sendSMSJob.setConfig(jobConfig);
        sendSMSJob.init();

        sendSMSJob.run();

    }

}
