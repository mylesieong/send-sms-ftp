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
    private JobConfig mConfig;

    public void setConfig(JobConfig config){
        this.mConfig = config;
    }

    public JobConfig getConfig(){
        return this.mConfig;
    }

    public SendSMSJob(){
        super();
    }

    public void init(){
        this.mIsActive = false;

        this.mProcessChain = new ArrayList<FileManipulator>();

        /* Set up FileRegister, inject into LogProxy and push into ProcessChain */
        String fileType = this.mConfig.getConfigEntry(JobConfig.FILE_TYPE_PROPERTY);

        FileManipulatorLogProxy register = new FileManipulatorLogProxy();
        FileRegister realRegister = new FileRegister(); 
        realRegister.setFileType(fileType);
        register.setName("REGISTER");
        register.setRealObject(realRegister);
        this.mProcessChain.add(register);
        
        /* Set up FileFtpUploader, inject into LogProxy and push into ProcessChain */
        String ftpAddress = this.mConfig.getConfigEntry(JobConfig.FTP_ADDRESS_PROPERTY);
        int ftpPort = Integer.parseInt(this.mConfig.getConfigEntry(JobConfig.FTP_PORT_PROPERTY));
        String ftpUser = this.mConfig.getConfigEntry(JobConfig.FTP_USER_PROPERTY);
        String ftpPassword = this.mConfig.getConfigEntry(JobConfig.FTP_PASSWORD_PROPERTY);
        String ftpFolder = this.mConfig.getConfigEntry(JobConfig.FTP_FOLDER_PROPERTY);

        FileManipulatorLogProxy ftpUploader = new FileManipulatorLogProxy();
        FileFtpUploader realFtpUploader = new FileFtpUploader();
        realFtpUploader.setFtpAddress(ftpAddress);
        realFtpUploader.setFtpPort(ftpPort);
        realFtpUploader.setFtpUser(ftpUser);
        realFtpUploader.setFtpPassword(ftpPassword);
        realFtpUploader.setFtpFolder(ftpFolder);
        ftpUploader.setName("FTPUPLOAD");
        ftpUploader.setRealObject(realFtpUploader);
        this.mProcessChain.add(ftpUploader);
        
        /* Set up FileBackuper, inject into LogProxy and push into ProcessChain */
        String backupFolder = this.mConfig.getConfigEntry(JobConfig.BACKUP_FOLDER_PROPERTY);

        FileManipulatorLogProxy backuper = new FileManipulatorLogProxy();
        FileBackuper realBackuper = new FileBackuper();
        realBackuper.setPath(backupFolder);
        backuper.setName("BACKUP");
        backuper.setRealObject(realBackuper);
        this.mProcessChain.add(backuper);

        /* Set up FileRemover, inject into LogProxy and push into ProcessChain */
        FileManipulatorLogProxy remover = new FileManipulatorLogProxy();
        FileRemover realRemover = new FileRemover();
        remover.setName("DELETE");
        remover.setRealObject(realRemover);
        this.mProcessChain.add(remover);

        /* Dynamic configuration of log setting */
        String logProperties = this.mConfig.getConfigEntry(JobConfig.LOG_PROPERTIES_PROPERTY);
        PropertyConfigurator.configure(logProperties);
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

        int interval = Integer.parseInt(this.mConfig.getConfigEntry(JobConfig.LOOP_INTERVAL_PROPERTY));
        String smsFolder = this.mConfig.getConfigEntry(JobConfig.SMS_FOLDER_PROPERTY);
        this.mIsActive = true;

        while(this.isActive()) {
            try {
                //File operations
                File targetFoler = new File(smsFolder);
                if (targetFoler.exists() && targetFoler.isDirectory()){
                    for (File f : targetFoler.listFiles()){
                        for (FileManipulator fm : mProcessChain){
                            fm.setFile(f);
                            fm.manipulate();
                            if (!fm.isSuccess()){
                                break;
                            }
                        }
                    }
                }
                
                //Set thread to sleep for an interval(ms)
                Thread.sleep(interval);
                mLogger.debug("Job loops.");
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        mLogger.debug("Job Ends.");
    }
    
}
