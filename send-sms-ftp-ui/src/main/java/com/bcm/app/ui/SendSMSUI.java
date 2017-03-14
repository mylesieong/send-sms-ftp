package com.bcm.app.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SendSMSUI {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SendSMSUI window = new SendSMSUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SendSMSUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 360, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Last Sent DateTime: ");
        lblNewLabel.setBounds(26, 32, 100, 16);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Status: ");
        lblNewLabel_1.setBounds(26, 59, 100, 14);
        frame.getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("MAIN STATUS");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblNewLabel_2.setBounds(0, 84, 344, 48);
        frame.getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("[...]");
        lblNewLabel_3.setBounds(136, 33, 175, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("[...]");
        lblNewLabel_4.setBounds(136, 59, 175, 14);
        frame.getContentPane().add(lblNewLabel_4);
        
        JButton btnNewButton = new JButton("Start Send SMS");
        btnNewButton.setBounds(26, 154, 125, 25);
        frame.getContentPane().add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Stop Send SMS");
        btnNewButton_1.setBounds(186, 154, 125, 25);
        frame.getContentPane().add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Export Log File");
        btnNewButton_2.setBounds(26, 188, 125, 25);
        frame.getContentPane().add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Clear Log File");
        btnNewButton_3.setBounds(186, 188, 125, 25);
        frame.getContentPane().add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("Change Configuration");
        btnNewButton_4.setBounds(26, 224, 285, 25);
        frame.getContentPane().add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Exit");
        btnNewButton_5.setBounds(26, 260, 285, 25);
        frame.getContentPane().add(btnNewButton_5);
    }
}
