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

    private JLabel mFtpUserTagLabel;
    private JLabel mFtpUserLabel;

    private JLabel mFtpPasswordTagLabel;
    private JLabel mFtpPasswordLabel;

    private JLabel mFtpFolderTagLabel;
    private JLabel mFtpFolderLabel;

    private JLabel mSMSFolderTagLabel;
    private JLabel mSMSFolderLabel;

    private JLabel mBackupFolderTagLabel;
    private JLabel mBackupFolderLabel;
    
    private JLabel mLoopIntervalTagLabel;
    private JLabel mLoopIntervalLabel;
    
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

        /*Ftp user */
        mFtpUserTagLabel = new JLabel("Ftp User: ");
        mFtpUserTagLabel.setBounds(26, 75, 100, 16);
        this.getContentPane().add(mFtpUserTagLabel);
        
        mFtpUserLabel= new JLabel(ftpConfigProperties.getFtpUser());
        mFtpUserLabel.setBounds(136, 75, 175, 16);
        this.getContentPane().add(mFtpUserLabel);

        /*Ftp password */
        mFtpPasswordTagLabel = new JLabel("Ftp Password: ");
        mFtpPasswordTagLabel.setBounds(26, 100, 100, 16);
        this.getContentPane().add(mFtpPasswordTagLabel);
        
        mFtpPasswordLabel= new JLabel(ftpConfigProperties.getFtpPassword());
        mFtpPasswordLabel.setBounds(136, 100, 175, 16);
        this.getContentPane().add(mFtpPasswordLabel);

        /*Ftp Folder */
        mFtpFolderTagLabel = new JLabel("Ftp Folder: ");
        mFtpFolderTagLabel.setBounds(26, 125, 100, 16);
        this.getContentPane().add(mFtpFolderTagLabel);
        
        mFtpFolderLabel= new JLabel(ftpConfigProperties.getFtpFolder());
        mFtpFolderLabel.setBounds(136, 125, 175, 16);
        this.getContentPane().add(mFtpFolderLabel);

        /*SMS Folder */
        mSMSFolderTagLabel = new JLabel("SMS Folder: ");
        mSMSFolderTagLabel.setBounds(26, 150, 100, 16);
        this.getContentPane().add(mSMSFolderTagLabel);
        
        mSMSFolderLabel= new JLabel(ftpConfigProperties.getSMSFolder());
        mSMSFolderLabel.setBounds(136, 150, 175, 16);
        this.getContentPane().add(mSMSFolderLabel);

        /*Backup Folder */
        mBackupFolderTagLabel = new JLabel("Backup Folder: ");
        mBackupFolderTagLabel.setBounds(26, 175, 100, 16);
        this.getContentPane().add(mBackupFolderTagLabel);
        
        mBackupFolderLabel= new JLabel(ftpConfigProperties.getBackupFolder());
        mBackupFolderLabel.setBounds(136, 175, 175, 16);
        this.getContentPane().add(mBackupFolderLabel);

        /*Loop Interval */
        mLoopIntervalTagLabel = new JLabel("Loop Interval: ");
        mLoopIntervalTagLabel.setBounds(26, 200, 100, 16);
        this.getContentPane().add(mLoopIntervalTagLabel);
        
        mLoopIntervalLabel= new JLabel(ftpConfigProperties.getLoopInterval());
        mLoopIntervalLabel.setBounds(136, 200, 175, 16);
        this.getContentPane().add(mLoopIntervalLabel);

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

