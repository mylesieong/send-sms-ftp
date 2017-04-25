package com.bcm.app.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncher implements CommandLineRunner {

    @Autowired
    private FtpConfigProperties ftpConfigProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Run the job launcher.");
    }

}
