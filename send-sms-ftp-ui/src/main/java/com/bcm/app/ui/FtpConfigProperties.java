package com.bcm.app.ui;

import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;

import org.springframework.stereotype.Component;

public class FtpConfigProperties {

    private String mFtpAddress;
    private String mFtpPort;

    public FtpConfigProperties(String config){
        Properties prop = new Properties();
        InputStream input = null;
        try{
            input = new FileInputStream(config);
            prop.load(input);
            this.mFtpAddress = prop.getProperty("FTP_ADDRESS");
            this.mFtpPort = prop.getProperty("FTP_PORT");
        }catch (Exception e){
            e.printStackTrace();
            this.mFtpAddress = "N/A";
            this.mFtpPort = "N/A";
        }
    }

    /* Setters */
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    public void setFtpPort(String port){
        this.mFtpPort = port;
    }

    /* Getters */
    public String getFtpAddress(){
        return this.mFtpAddress;
    }

    public String getFtpPort(){
        return this.mFtpPort;
    }

    public boolean verifyFtpConnection(){
        return true;
    }

    public void saveConfigProperties(){
    }

}
