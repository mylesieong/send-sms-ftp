package com.bcm.app.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private FtpConfigProperties ftpConfigProperties;

    @Autowired
    private SendSMSJob sendSMSJob;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Start the SMS Sending Job...");
        
        sendSMSJob.setFtpAddress(ftpConfigProperties.getFtpAddress());
        sendSMSJob.setFtpPort(Integer.parseInt(ftpConfigProperties.getFtpPort()));
        sendSMSJob.setFtpUser(ftpConfigProperties.getFtpUser());
        sendSMSJob.setFtpPassword(ftpConfigProperties.getFtpPassword());
        sendSMSJob.setFtpFolder(ftpConfigProperties.getFtpFolder());
        sendSMSJob.setSMSFolder(ftpConfigProperties.getSMSFolder());
        sendSMSJob.setBackupFolder(ftpConfigProperties.getBackupFolder());
        sendSMSJob.setLoopInterval(Integer.parseInt(ftpConfigProperties.getLoopInterval()));
        sendSMSJob.setLogProperties(ftpConfigProperties.getLogProperties());

        sendSMSJob.init();
        sendSMSJob.run();

    }

}
