package com.bcm.app.ui;

import org.springframework.stereotype.Component;

public class FtpConfig {

    private String mFtpAddress;
    private String mFtpPort;

    public FtpConfig(String config){
        this.mFtpAddress = "address";
        this.mFtpPort = "port";
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

}
