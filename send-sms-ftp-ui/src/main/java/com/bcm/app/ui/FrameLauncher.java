package com.bcm.app.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FrameLauncher implements CommandLineRunner {

    @Autowired
    private MainFrame frame;

    @Override
    public void run(String... args) throws Exception {

        /* display the frame with EventQueue */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("******In run method******");
                frame.setVisible(true);
            }
        });
    }

}
