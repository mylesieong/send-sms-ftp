package com.bcm.app.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SendGUIApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SendGUIApp.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    @Bean
    public MainFrame frame() {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        return frame;
    }

}
