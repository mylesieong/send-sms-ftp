package com.bcm.app.ui;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
 
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import com.bcm.app.engine.JobConfig;

/**
 * Class ConfigFrame is the configuration screen. It allows user
 * to read/write and verify application configuration(ftp settings,
 * folder locations, job interval and etc.)
 *
 */
@Component
public class ConfigFrame extends JFrame implements ActionListener, KeyListener{

    /* 
     * Config Frame screen members 
     */
    private JLabel mFtpAddressTagLabel;
    private JTextField mFtpAddressField;
    
    private JLabel mFtpPortTagLabel;
    private JTextField mFtpPortField;

    private JLabel mFtpUserTagLabel;
    private JTextField mFtpUserField;

    private JLabel mFtpPasswordTagLabel;
    private JPasswordField mFtpPasswordField;

    private JLabel mFtpFolderTagLabel;
    private JTextField mFtpFolderField;

    private JLabel mSMSFolderTagLabel;
    private JTextField mSMSFolderField;

    private JLabel mBackupFolderTagLabel;
    private JTextField mBackupFolderField;
    
    private JLabel mLoopIntervalTagLabel;
    private JTextField mLoopIntervalField;
    
    private JLabel mLogPropTagLabel;
    private JTextField mLogPropField;

    private JLabel mFileTypeTagLabel;
    private JTextField mFileTypeField;

    private JLabel mVerifyResultLabel;
    
    /*
     * Callback of mainframe
     */
    private MainFrame mCallbackFrame;

    /*
     * Config Frame Button members
     */
    private JButton mVerifyConfigButton;
    private JButton mSaveConfigButton;
    private JButton mCancelButton;

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    private JobConfig jobConfig;

    /**
     * Method initialize() initialize ConfigFrame and its components, and 
     * write the value to different fields.
     *
     */
    public void initialize() {
        initializeComponent();
        bindPropertiesToFields();
    }
    
    /**
     * [Private] Initialize JComponents on ConfigFrame
     *
     */
    private void initializeComponent(){

        // Main Frame basic 
        this.setBounds(100, 100, 360, 385);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        //Ftp address 
        mFtpAddressTagLabel = new JLabel("Ftp Address: ");
        mFtpAddressTagLabel.setBounds(26, 25, 100, 16);
        this.getContentPane().add(mFtpAddressTagLabel);
        
        mFtpAddressField = new JTextField();
        mFtpAddressField.setBounds(136, 25, 175, 16);
        mFtpAddressField.addKeyListener(this);
        this.getContentPane().add(mFtpAddressField);
        
        //Ftp port 
        mFtpPortTagLabel = new JLabel("Ftp Port: ");
        mFtpPortTagLabel.setBounds(26, 50, 100, 16);
        this.getContentPane().add(mFtpPortTagLabel);
        
        mFtpPortField= new JTextField();
        mFtpPortField.setBounds(136, 50, 175, 16);
        mFtpPortField.addKeyListener(this);
        this.getContentPane().add(mFtpPortField);

        //Ftp user 
        mFtpUserTagLabel = new JLabel("Ftp User: ");
        mFtpUserTagLabel.setBounds(26, 75, 100, 16);
        this.getContentPane().add(mFtpUserTagLabel);
        
        mFtpUserField= new JTextField();
        mFtpUserField.setBounds(136, 75, 175, 16);
        mFtpUserField.addKeyListener(this);
        this.getContentPane().add(mFtpUserField);

        //Ftp password 
        mFtpPasswordTagLabel = new JLabel("Ftp Password: ");
        mFtpPasswordTagLabel.setBounds(26, 100, 100, 16);
        this.getContentPane().add(mFtpPasswordTagLabel);
        
        mFtpPasswordField= new JPasswordField();
        mFtpPasswordField.setBounds(136, 100, 175, 16);
        mFtpPasswordField.addKeyListener(this);
        this.getContentPane().add(mFtpPasswordField);

        //Ftp Folder 
        mFtpFolderTagLabel = new JLabel("Ftp Folder: ");
        mFtpFolderTagLabel.setBounds(26, 125, 100, 16);
        this.getContentPane().add(mFtpFolderTagLabel);
        
        mFtpFolderField= new JTextField();
        mFtpFolderField.setBounds(136, 125, 175, 16);
        mFtpFolderField.addKeyListener(this);
        this.getContentPane().add(mFtpFolderField);

        //SMS Folder 
        mSMSFolderTagLabel = new JLabel("SMS Folder: ");
        mSMSFolderTagLabel.setBounds(26, 150, 100, 16);
        this.getContentPane().add(mSMSFolderTagLabel);
        
        mSMSFolderField= new JTextField();
        mSMSFolderField.setBounds(136, 150, 175, 16);
        mSMSFolderField.addKeyListener(this);
        this.getContentPane().add(mSMSFolderField);

        //Backup Folder 
        mBackupFolderTagLabel = new JLabel("Backup Folder: ");
        mBackupFolderTagLabel.setBounds(26, 175, 100, 16);
        this.getContentPane().add(mBackupFolderTagLabel);
        
        mBackupFolderField= new JTextField();
        mBackupFolderField.setBounds(136, 175, 175, 16);
        mBackupFolderField.addKeyListener(this);
        this.getContentPane().add(mBackupFolderField);

        //Loop Interval 
        mLoopIntervalTagLabel = new JLabel("Loop Interval: ");
        mLoopIntervalTagLabel.setBounds(26, 200, 100, 16);
        this.getContentPane().add(mLoopIntervalTagLabel);
        
        mLoopIntervalField= new JTextField();
        mLoopIntervalField.setBounds(136, 200, 175, 16);
        mLoopIntervalField.addKeyListener(this);
        this.getContentPane().add(mLoopIntervalField);

        // Log Config 
        mLogPropTagLabel = new JLabel("Log Config: ");
        mLogPropTagLabel.setBounds(26, 225, 100, 16);
        this.getContentPane().add(mLogPropTagLabel);
        
        mLogPropField = new JTextField();
        mLogPropField.setBounds(136, 225, 175, 16);
        mLogPropField.addKeyListener(this);
        this.getContentPane().add(mLogPropField);

        // File type 
        mFileTypeTagLabel = new JLabel("File Type: ");
        mFileTypeTagLabel.setBounds(26, 250, 100, 16);
        this.getContentPane().add(mFileTypeTagLabel);
        
        mFileTypeField = new JTextField();
        mFileTypeField.setBounds(136, 250, 175, 16);
        mFileTypeField.addKeyListener(this);
        this.getContentPane().add(mFileTypeField);

        // Button: verify 
        mVerifyConfigButton = new JButton("Verify");
        mVerifyConfigButton.setBounds(26, 285, 80, 35);
        mVerifyConfigButton.addActionListener(this);
        this.getContentPane().add(mVerifyConfigButton);
        
        // Button: Save 
        mSaveConfigButton = new JButton("Save");
        mSaveConfigButton.setBounds(116, 285, 80, 35);
        mSaveConfigButton.addActionListener(this);
        mSaveConfigButton.setEnabled(false);
        this.getContentPane().add(mSaveConfigButton);

        // Button: Cancel1 
        mCancelButton = new JButton("Cancel");
        mCancelButton.setBounds(206, 285, 80, 35);
        mCancelButton.addActionListener(this);
        this.getContentPane().add(mCancelButton);

        // Verify Result Label 
        mVerifyResultLabel = new JLabel();
        mVerifyResultLabel.setBounds(26, 325, 275, 16);
        this.getContentPane().add(mVerifyResultLabel);

    }

    /**
     * [Private] Write value on JTextField to config file
     *
     */
    private void bindFieldsToProperties(){
        jobConfig.setConfigEntry(JobConfig.FTP_ADDRESS_PROPERTY, this.mFtpAddressField.getText());
        jobConfig.setConfigEntry(JobConfig.FTP_PORT_PROPERTY, this.mFtpPortField.getText());
        jobConfig.setConfigEntry(JobConfig.FTP_USER_PROPERTY, this.mFtpUserField.getText());
        jobConfig.setConfigEntry(JobConfig.FTP_PASSWORD_PROPERTY, this.mFtpPasswordField.getText());
        jobConfig.setConfigEntry(JobConfig.FTP_FOLDER_PROPERTY, this.mFtpFolderField.getText());
        jobConfig.setConfigEntry(JobConfig.SMS_FOLDER_PROPERTY, this.mSMSFolderField.getText());
        jobConfig.setConfigEntry(JobConfig.BACKUP_FOLDER_PROPERTY, this.mBackupFolderField.getText());
        jobConfig.setConfigEntry(JobConfig.LOOP_INTERVAL_PROPERTY, this.mLoopIntervalField.getText());
        jobConfig.setConfigEntry(JobConfig.LOG_PROPERTIES_PROPERTY, this.mLogPropField.getText());
        jobConfig.setConfigEntry(JobConfig.FILE_TYPE_PROPERTY, this.mFileTypeField.getText());
    }

    /**
     * [Private] Write value from config file to JTextField 
     *
     */
    private void bindPropertiesToFields(){
        mFtpAddressField.setText(jobConfig.getConfigEntry(JobConfig.FTP_ADDRESS_PROPERTY));
        mFtpPortField.setText(jobConfig.getConfigEntry(JobConfig.FTP_PORT_PROPERTY));
        mFtpUserField.setText(jobConfig.getConfigEntry(JobConfig.FTP_USER_PROPERTY));
        mFtpPasswordField.setText(jobConfig.getConfigEntry(JobConfig.FTP_PASSWORD_PROPERTY));
        mFtpFolderField.setText(jobConfig.getConfigEntry(JobConfig.FTP_FOLDER_PROPERTY));
        mSMSFolderField.setText(jobConfig.getConfigEntry(JobConfig.SMS_FOLDER_PROPERTY));
        mBackupFolderField.setText(jobConfig.getConfigEntry(JobConfig.BACKUP_FOLDER_PROPERTY));
        mLoopIntervalField.setText(jobConfig.getConfigEntry(JobConfig.LOOP_INTERVAL_PROPERTY));
        mLogPropField.setText(jobConfig.getConfigEntry(JobConfig.LOG_PROPERTIES_PROPERTY));
        mFileTypeField.setText(jobConfig.getConfigEntry(JobConfig.FILE_TYPE_PROPERTY));
    }

    /**
     * Setter of mCallbackFrame. This callback is used when user updates 
     * the config successfully so that the Method MainFrame:initializeJob
     * will be call to refresh the running job.
     *
     * @param MainFrame 
     */
    public void setCallbackFrame(MainFrame frame){
        this.mCallbackFrame = frame;
    }

    /**
     * Method actionPerformed defines button actions
     *
     */
    @Override
    public void actionPerformed(ActionEvent e){
        
        // For Verify Button
        if (e.getSource() == this.mVerifyConfigButton){

            bindFieldsToProperties();

            if (jobConfig.verifyConfig()) {
                mVerifyResultLabel.setText("Config integrity validated.");
                mSaveConfigButton.setEnabled(true);
            }else{
                mVerifyResultLabel.setText("Config not valid.");
                mSaveConfigButton.setEnabled(false);
            }

        }
        
        // For Save Button
        // Any change of field will disable the save button, user can only
        // enable save button by clicking verify button and pass the 
        // verification
        if (e.getSource() == this.mSaveConfigButton){

            // Get real pwd from config file
            String pwd = jobConfig.getConfigEntry(JobConfig.FTP_PASSWORD_PROPERTY);

            // Prompt user for password input
            String pwdI = (String)JOptionPane.showInputDialog(this, "Enter Password", "Password", JOptionPane.PLAIN_MESSAGE);

            // If user's password matches the real one, save mainframe 
            // properties and update the job settings 
            if (pwd.compareTo(pwdI) == 0){
                this.jobConfig.saveConfig();
                this.mCallbackFrame.refreshJob();
                JOptionPane.showMessageDialog(this, "Save Success!", "Result", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(this, "Wrong password!", "Result", JOptionPane.ERROR_MESSAGE);
            }

        }

        // For Cancel Button
        if (e.getSource() == this.mCancelButton){

            // Reload properties before change 
            bindPropertiesToFields();
            this.setVisible(false);

        }
                
    }

    /**
     * Method disables save button so that user has to
     * click the verify button before he/she can save 
     * the changed config.
     *
     */
    @Override
    public void keyPressed(KeyEvent event){
        System.out.println("Key pressed");
        this.mSaveConfigButton.setEnabled(false);
    }

    /**
     * Method disables save button so that user has to
     * click the verify button before he/she can save 
     * the changed config.
     *
     */
    @Override
    public void keyReleased(KeyEvent event){
        System.out.println("Key release");
        this.mSaveConfigButton.setEnabled(false);
    }

    /**
     * Method disables save button so that user has to
     * click the verify button before he/she can save 
     * the changed config.
     *
     */
    @Override
    public void keyTyped(KeyEvent event){
        System.out.println("Key type");
        this.mSaveConfigButton.setEnabled(false);
    }

}

