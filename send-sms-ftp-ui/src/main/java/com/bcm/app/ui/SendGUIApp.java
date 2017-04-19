package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class SendGUIApp {

    @Autowired
    private MainFrame frame;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SendGUIApp.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    @Bean
    public String launchFrame() {
        frame.setVisible(true);
        return "Send SMS Application started.";
    }

}
