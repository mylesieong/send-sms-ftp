package com.bcm.app.engine;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;   

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.lang.*; 

import org.springframework.stereotype.Component;

import com.bcm.app.core.*;

@Component
public class SendSMSJob extends Thread {

    final static Logger mLogger = Logger.getLogger(SendSMSJob.class);

    private boolean mIsActive;
    private List<FileManipulator> mProcessChain;
    
    private String mFtpAddress;
    private int mFtpPort;
    private String mFtpUser;
    private String mFtpPassword;
    private String mFtpFolder;
    private String mSMSFolder;
    private String mBackupFolder;
    private int mLoopInterval;
    private String mLogProperties;   

    public SendSMSJob(){
        super();
    }

    public void init(){
        this.mIsActive = false;

        this.mProcessChain = new ArrayList<FileManipulator>();
        /* Set up MessageRegisterLogProxy */
        MessageRegisterLogProxy messageRegisterLogProxy = new MessageRegisterLogProxy();
        
        /* Set up MessageFtpUploaderLogProxy */
        MessageFtpUploaderLogProxy messageFtpUploaderLogProxy = new MessageFtpUploaderLogProxy();
        messageFtpUploaderLogProxy.setFtpAddress(this.mFtpAddress);
        messageFtpUploaderLogProxy.setFtpPort(this.mFtpPort);
        messageFtpUploaderLogProxy.setFtpUser(this.mFtpUser);
        messageFtpUploaderLogProxy.setFtpPassword(this.mFtpPassword);
        messageFtpUploaderLogProxy.setFtpFolder(this.mFtpFolder);
        
        /* Set up MessageBackuperLogProxy */
        MessageBackuperLogProxy messageBackuperLogProxy = new MessageBackuperLogProxy();
        messageBackuperLogProxy.setPath(this.mBackupFolder);
        
        this.mProcessChain.add(messageRegisterLogProxy);
        this.mProcessChain.add(messageFtpUploaderLogProxy);
        this.mProcessChain.add(messageBackuperLogProxy);

        /* Ddynamic configuration of log setting */
        PropertyConfigurator.configure(this.mLogProperties);
    }
    
    /* Setters */
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    public void setFtpPort(int port){
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
    
    public void setLoopInterval(int interval){
        this.mLoopInterval = interval;
    }
    
    public void setLogProperties(String path){
        this.mLogProperties = path;
    }
    
    /* Status setter and getter */
    public boolean isActive(){
        return this.mIsActive;
    }

    public void setActive(boolean b){
        this.mIsActive = b;
    }
    
    @Override
    public void run(){
        mLogger.debug("Job Starts.");
        this.mIsActive = true;
        while(this.isActive()) {
            try {
                //File operations
                File targetFoler = new File(this.mSMSFolder);
                if (targetFoler.exists() && targetFoler.isDirectory()){
                    for (File f : targetFoler.listFiles()){
                        // System.out.println("Found file(s):" + f);
                        // mLogger.info("Found file(s):" + f);
                        for (FileManipulator fm : mProcessChain){
                            fm.setFile(f);
                            fm.manipulate();
                            if (!fm.isSuccess()){
                                break;
                            }
                        }
                    }
                }
                
                // 暫停目前的執行緒5秒
                Thread.sleep(this.mLoopInterval);
                mLogger.debug("Job loops.");
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        mLogger.debug("Job Ends.");
    }
    
}
