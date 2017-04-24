package com.bcm.app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
 
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

@Component
public class ConfigFrame extends JFrame implements ActionListener {

    /* Load Config Frame properties */
    private JLabel mFtpAddressTagLabel;
    private JLabel mFtpAddressLabel;
    private JLabel mFtpPortTagLabel;
    private JLabel mFtpPortLabel;
    private JButton mVerifyConfigButton;
    private JButton mSaveConfigButton;
    private JButton mCancelButton;

    /* Ftp Config */
    @Autowired
    private FtpConfigProperties ftpConfigProperties;

    public ConfigFrame() {
    }

    /**
     * Init this
     */
    public void initialize() {
        this.setBounds(100, 100, 360, 360);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        /*Ftp address */
        mFtpAddressTagLabel = new JLabel("Ftp Address: ");
        mFtpAddressTagLabel.setBounds(26, 25, 100, 16);
        this.getContentPane().add(mFtpAddressTagLabel);
        
        mFtpAddressLabel = new JLabel(ftpConfigProperties.getFtpAddress());
        mFtpAddressLabel.setBounds(136, 25, 175, 16);
        this.getContentPane().add(mFtpAddressLabel);
        
        /*Ftp port */
        mFtpPortTagLabel = new JLabel("Ftp Port: ");
        mFtpPortTagLabel.setBounds(26, 50, 100, 16);
        this.getContentPane().add(mFtpPortTagLabel);
        
        mFtpPortLabel= new JLabel(ftpConfigProperties.getFtpPort());
        mFtpPortLabel.setBounds(136, 50, 175, 16);
        this.getContentPane().add(mFtpPortLabel);

        /* Button: verify */
        mVerifyConfigButton = new JButton("Verify");
        mVerifyConfigButton.setBounds(26, 260, 80, 35);
        mVerifyConfigButton.addActionListener(this);
        this.getContentPane().add(mVerifyConfigButton);
        
        /* Button: Save */
        mSaveConfigButton = new JButton("Save");
        mSaveConfigButton.setBounds(116, 260, 80, 35);
        mSaveConfigButton.addActionListener(this);
        this.getContentPane().add(mSaveConfigButton);

        /* Button: Cancel1 */
        mCancelButton = new JButton("Cancel");
        mCancelButton.setBounds(206, 260, 80, 35);
        mCancelButton.addActionListener(this);
        this.getContentPane().add(mCancelButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource() == this.mVerifyConfigButton){
            System.out.println("Verify config");
        }
        
        if (e.getSource() == this.mSaveConfigButton){
            System.out.println("Save config");
        }

        if (e.getSource() == this.mCancelButton){
            this.setVisible(false);
        }
                
    }

}

