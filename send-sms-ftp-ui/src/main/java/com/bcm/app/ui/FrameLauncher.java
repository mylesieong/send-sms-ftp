package com.bcm.app.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class FrameLauncher is the entrance of the application. It is
 * invoked when user runs the JAR file with JVM. It initiates
 * a MainFrame object and kicks it off.
 *
 */
@Component
public class FrameLauncher implements CommandLineRunner {

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    private MainFrame frame;

    /**
     * Method run() initialize frame and kicks if off
     *
     */
    @Override
    public void run(String... args) throws Exception {

        // display the frame with EventQueue 
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("******In run method******");
                frame.initialize();
                frame.setVisible(true);
            }
        });
    }

}
