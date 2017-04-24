package com.bcm.app.ui;

import java.util.Properties;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

public class FtpConfigProperties {

    public final static String FTP_ADDRESS_PROPERTY = "FTP_ADDRESS";
    public final static String FTP_PORT_PROPERTY = "FTP_PORT";
    public final static String FTP_USER_PROPERTY = "FTP_USER";
    public final static String FTP_PASSWORD_PROPERTY = "FTP_PASSWORD";
    public final static String FTP_FOLDER_PROPERTY = "FTP_FOLDER";
    public final static String SMS_FOLDER_PROPERTY = "SMS_FOLDER";
    public final static String BACKUP_FOLDER_PROPERTY = "BKUP_FOLDER";
    public final static String LOOP_INTERVAL_PROPERTY = "LOOP_INTERVAL";
    public final static String NA_VALUE = "N/A";

    private String mConfigPath;

    private String mFtpAddress;
    private String mFtpPort;
    private String mFtpUser;
    private String mFtpPassword;
    private String mFtpFolder;
    private String mSMSFolder;
    private String mBackupFolder;
    private String mLoopInterval;

    public FtpConfigProperties(String config){

        this.mConfigPath = config;

        Properties prop = new Properties();
        InputStream input = null;

        try{

            input = new FileInputStream(config);
            prop.load(input);
            this.mFtpAddress = prop.getProperty(FTP_ADDRESS_PROPERTY);
            this.mFtpPort = prop.getProperty(FTP_PORT_PROPERTY);
            this.mFtpUser = prop.getProperty(FTP_USER_PROPERTY);
            this.mFtpPassword = prop.getProperty(FTP_PASSWORD_PROPERTY);
            this.mFtpFolder = prop.getProperty(FTP_FOLDER_PROPERTY);
            this.mSMSFolder = prop.getProperty(SMS_FOLDER_PROPERTY);
            this.mBackupFolder = prop.getProperty(BACKUP_FOLDER_PROPERTY);
            this.mLoopInterval = prop.getProperty(LOOP_INTERVAL_PROPERTY);

        }catch (Exception e){

            e.printStackTrace();
            this.mFtpAddress = NA_VALUE;
            this.mFtpPort = NA_VALUE;
            this.mFtpUser = NA_VALUE;
            this.mFtpPassword = NA_VALUE;
            this.mFtpFolder = NA_VALUE;
            this.mSMSFolder = NA_VALUE;
            this.mBackupFolder = NA_VALUE;
            this.mLoopInterval = NA_VALUE;

        }

    }

    /* Setters */
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    public void setFtpPort(String port){
        this.mFtpPort = port;
    }

    public void setFtpUser(String user){
        this.mFtpUser = user;
    }
    
    public void setFtpPassword(String password){
        this.mFtpPassword = password;
    }
    
    public void setFtpFolder(String folder){
        this.mFtpFolder = folder;
    }
    
    public void setSMSFolder(String folder){
        this.mSMSFolder = folder;
    }
    
    public void setBackupFolder(String folder){
        this.mBackupFolder = folder;
    }
    
    public void setLoopInterval(String interval){
        this.mLoopInterval = interval;
    }
    
    /* Getters */
    public String getFtpAddress(){
        return this.mFtpAddress;
    }

    public String getFtpPort(){
        return this.mFtpPort;
    }

    public String getFtpUser(){
        return this.mFtpUser;
    }

    public String getFtpPassword(){
        return this.mFtpPassword;
    }

    public String getFtpFolder(){
        return this.mFtpFolder;
    }

    public String getSMSFolder(){
        return this.mSMSFolder;
    }

    public String getBackupFolder(){
        return this.mBackupFolder;
    }

    public String getLoopInterval(){
        return this.mLoopInterval;
    }

    public boolean verifyFtpConnection(){
        /* Not impl yet */
        return true;
    }

    public void saveConfigProperties(){
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(this.mConfigPath);
            prop.setProperty(FTP_ADDRESS_PROPERTY, this.mFtpAddress);
            prop.setProperty(FTP_PORT_PROPERTY, this.mFtpPort);
            prop.setProperty(FTP_USER_PROPERTY, this.mFtpUser);
            prop.setProperty(FTP_PASSWORD_PROPERTY, this.mFtpPassword);
            prop.setProperty(FTP_FOLDER_PROPERTY, this.mFtpFolder);
            prop.setProperty(SMS_FOLDER_PROPERTY, this.mSMSFolder);
            prop.setProperty(BACKUP_FOLDER_PROPERTY, this.mBackupFolder);
            prop.setProperty(LOOP_INTERVAL_PROPERTY, this.mLoopInterval);
            prop.store(output, null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
