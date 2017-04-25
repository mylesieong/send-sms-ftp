package com.bcm.app.engine;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;   

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.lang.*; 

import com.bcm.app.core.*;

public class SendSMSJob extends Thread {

    final static Logger mLogger = Logger.getLogger(SendSMSJob.class);

    private boolean mIsActive;
    private List<FileManipulator> mProcessChain;
    
    private String FTP_ADDRESS;
    private int FTP_PORT;
    private String FTP_USER;
    private String FTP_PASSWORD;
    private String FTP_FOLDER;
    private String SMS_FILE;
    private String SMS_FOLDER;
    private String BKUP_FOLDER;
    private String CUR_DIR;
    private String INMSG;
    private String OUTMSG;
    private int LOOP_INTERVAL;
    private String LOG_PROP;   

    public SendSMSJob(){
        super();
        this.mProcessChain = new ArrayList<FileManipulator>();
        this.mIsActive = false;
    }
    
    public SendSMSJob(String propertiesPath) {
        super();
        this.mProcessChain = new ArrayList<FileManipulator>();
        this.mIsActive = false;
        this.setProperties(propertiesPath);        
    }
    
    public void setProperties(String propertiesPath){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(propertiesPath);
            prop.load(input);
            FTP_ADDRESS = prop.getProperty("FTP_ADDRESS");
            FTP_PORT = Integer.parseInt(prop.getProperty("FTP_PORT"));
            FTP_USER = prop.getProperty("FTP_USER");
            FTP_PASSWORD = prop.getProperty("FTP_PASSWORD");
            FTP_FOLDER = prop.getProperty("FTP_FOLDER");
            SMS_FILE = prop.getProperty("SMS_FILE");
            SMS_FOLDER = prop.getProperty("SMS_FOLDER");
            BKUP_FOLDER = prop.getProperty("BKUP_FOLDER");
            CUR_DIR = System.getProperty("user.dir");
            INMSG = prop.getProperty("INMSG");
            OUTMSG = prop.getProperty("OUTMSG");
            LOOP_INTERVAL = Integer.parseInt(prop.getProperty("LOOP_INTERVAL"));
            LOG_PROP = prop.getProperty("LOG_PROP");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        /* Set up MessageRegisterLogProxy */
        MessageRegisterLogProxy messageRegisterLogProxy = new MessageRegisterLogProxy();
        
        /* Set up MessageFtpUploaderLogProxy */
        MessageFtpUploaderLogProxy messageFtpUploaderLogProxy = new MessageFtpUploaderLogProxy();
        messageFtpUploaderLogProxy.setFtpAddress(FTP_ADDRESS);
        messageFtpUploaderLogProxy.setFtpPort(FTP_PORT);
        messageFtpUploaderLogProxy.setFtpUser(FTP_USER);
        messageFtpUploaderLogProxy.setFtpPassword(FTP_PASSWORD);
        messageFtpUploaderLogProxy.setFtpFolder(FTP_FOLDER);
        
        /* Set up MessageBackuperLogProxy */
        MessageBackuperLogProxy messageBackuperLogProxy = new MessageBackuperLogProxy();
        messageBackuperLogProxy.setPath(BKUP_FOLDER);
        
        this.mProcessChain.add(messageRegisterLogProxy);
        this.mProcessChain.add(messageFtpUploaderLogProxy);
        this.mProcessChain.add(messageBackuperLogProxy);
        
        /* Ddynamic configuration of log setting */
        PropertyConfigurator.configure(LOG_PROP);
        
    }
    
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
                File targetFoler = new File(SMS_FOLDER);
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
                Thread.sleep(LOOP_INTERVAL);
                mLogger.debug("Job loops.");
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        mLogger.debug("Job Ends.");
    }
    
    /*
    public static void main(String[] args) {
        SendSMSJob job = new SendSMSJob(args[0]);
        job.run(); //not using thread, start using the current thread, terminated by Ctrl C in bash
    }
    */
    
}
