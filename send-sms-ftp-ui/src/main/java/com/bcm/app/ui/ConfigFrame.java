package com.bcm.app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
    private JTextField mFtpAddressField;
    
    private JLabel mFtpPortTagLabel;
    private JTextField mFtpPortField;

    private JLabel mFtpUserTagLabel;
    private JTextField mFtpUserField;

    private JLabel mFtpPasswordTagLabel;
    private JTextField mFtpPasswordField;

    private JLabel mFtpFolderTagLabel;
    private JTextField mFtpFolderField;

    private JLabel mSMSFolderTagLabel;
    private JTextField mSMSFolderField;

    private JLabel mBackupFolderTagLabel;
    private JTextField mBackupFolderField;
    
    private JLabel mLoopIntervalTagLabel;
    private JTextField mLoopIntervalField;
    
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
        
        mFtpAddressField = new JTextField(ftpConfigProperties.getFtpAddress());
        mFtpAddressField.setBounds(136, 25, 175, 16);
        this.getContentPane().add(mFtpAddressField);
        
        /*Ftp port */
        mFtpPortTagLabel = new JLabel("Ftp Port: ");
        mFtpPortTagLabel.setBounds(26, 50, 100, 16);
        this.getContentPane().add(mFtpPortTagLabel);
        
        mFtpPortField= new JTextField(ftpConfigProperties.getFtpPort());
        mFtpPortField.setBounds(136, 50, 175, 16);
        this.getContentPane().add(mFtpPortField);

        /*Ftp user */
        mFtpUserTagLabel = new JLabel("Ftp User: ");
        mFtpUserTagLabel.setBounds(26, 75, 100, 16);
        this.getContentPane().add(mFtpUserTagLabel);
        
        mFtpUserField= new JTextField(ftpConfigProperties.getFtpUser());
        mFtpUserField.setBounds(136, 75, 175, 16);
        this.getContentPane().add(mFtpUserField);

        /*Ftp password */
        mFtpPasswordTagLabel = new JLabel("Ftp Password: ");
        mFtpPasswordTagLabel.setBounds(26, 100, 100, 16);
        this.getContentPane().add(mFtpPasswordTagLabel);
        
        mFtpPasswordField= new JTextField(ftpConfigProperties.getFtpPassword());
        mFtpPasswordField.setBounds(136, 100, 175, 16);
        this.getContentPane().add(mFtpPasswordField);

        /*Ftp Folder */
        mFtpFolderTagLabel = new JLabel("Ftp Folder: ");
        mFtpFolderTagLabel.setBounds(26, 125, 100, 16);
        this.getContentPane().add(mFtpFolderTagLabel);
        
        mFtpFolderField= new JTextField(ftpConfigProperties.getFtpFolder());
        mFtpFolderField.setBounds(136, 125, 175, 16);
        this.getContentPane().add(mFtpFolderField);

        /*SMS Folder */
        mSMSFolderTagLabel = new JLabel("SMS Folder: ");
        mSMSFolderTagLabel.setBounds(26, 150, 100, 16);
        this.getContentPane().add(mSMSFolderTagLabel);
        
        mSMSFolderField= new JTextField(ftpConfigProperties.getSMSFolder());
        mSMSFolderField.setBounds(136, 150, 175, 16);
        this.getContentPane().add(mSMSFolderField);

        /*Backup Folder */
        mBackupFolderTagLabel = new JLabel("Backup Folder: ");
        mBackupFolderTagLabel.setBounds(26, 175, 100, 16);
        this.getContentPane().add(mBackupFolderTagLabel);
        
        mBackupFolderField= new JTextField(ftpConfigProperties.getBackupFolder());
        mBackupFolderField.setBounds(136, 175, 175, 16);
        this.getContentPane().add(mBackupFolderField);

        /*Loop Interval */
        mLoopIntervalTagLabel = new JLabel("Loop Interval: ");
        mLoopIntervalTagLabel.setBounds(26, 200, 100, 16);
        this.getContentPane().add(mLoopIntervalTagLabel);
        
        mLoopIntervalField= new JTextField(ftpConfigProperties.getLoopInterval());
        mLoopIntervalField.setBounds(136, 200, 175, 16);
        this.getContentPane().add(mLoopIntervalField);

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

