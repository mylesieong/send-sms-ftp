package com.bcm.app.engine;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.lang.*; 

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;   

import org.springframework.stereotype.Component;

import com.bcm.app.core.*;

/**
 * Class SendSMSJob is a thread program. It iterates target folder and
 * processes every found files with FileManipulator(s) in its process chain
 * member. The process chain will be stopped and the rest of FileManipulators
 * in the chain will not be performed if prior FileManipulator is not 
 * success. 
 *
 * The SendSMSJob runs at an interval set by client in the config file.
 *
 * A suggested sequence of launching an SendSMSJob is:
 * 1. yourJob.setConfig(yourJobConfig)
 * 2. yourJob.init()
 * 3. yourJob.run()
 * 4. (optional) yourJob.setActive(false) to end the job 
 * 5. yourJob.run() to start the job again
 *
 * Note that SendSMSJob might not work as expected if the method init()
 * is not invoked after the method setConfig(). If client want to start
 * the job after a stop, the method run() should be called again.
 *
 */
@Component
public class SendSMSJob extends Thread {

    /*
     * Logger
     */
    final static Logger mLogger = Logger.getLogger(SendSMSJob.class);

    /*
     * Get and set by setActive and isActive method and is used to stop 
     * a running job
     */
    private boolean mIsActive;

    /*
     * Prcoess chain that stores FileManipulator(s), the process sequence
     * is from item #0 and on. 
     */
    private List<FileManipulator> mProcessChain;

    /* 
     * Config member 
     */
    private JobConfig mConfig;

    /*
     * just sent something flag
     */
    private boolean mJustSentSomethingFlag;

    /*
     * DateTime of last sent moment
     */
    private DateTime mLastSentDateTime;

    /**
     * Setter of member mConfig
     */
    public void setConfig(JobConfig config){
        this.mConfig = config;
    }

    /**
     * Getter of member mConfig
     */
    public JobConfig getConfig(){
        return this.mConfig;
    }

    /**
     * Turn off mJustSentSomethingFlag
     */
    public void turnOffJustSentSomethingFlag(){
        this.mJustSentSomethingFlag = false;
    }

    /**
     * Tell people did the job just sent something
     *
     * @return boolean
     */
    public boolean didIJustSentSomething(){
        return this.mJustSentSomethingFlag;
    }

    /**
     *
     * @return DateTime
     */
    public DateTime getLastSentDateTime(){
        return this.mLastSentDateTime;
    }

    /**
     * Method init() create instance of FileManipulator(s) and inject into 
     * process chain. Developer should modify this method if new FileManipulator
     * is introduced to the project.
     *
     */
    public void init(){

        this.mIsActive = false;
        this.mProcessChain = new ArrayList<FileManipulator>();
        this.mJustSentSomethingFlag = false;

        // Set up FileRegister, inject into LogProxy and push into ProcessChain 
        String fileType = this.mConfig.getConfigEntry(JobConfig.FILE_TYPE_PROPERTY);

        FileManipulatorLogProxy register = new FileManipulatorLogProxy();
        FileRegister realRegister = new FileRegister(); 
        realRegister.setFileType(fileType);
        register.setName("REGISTER");
        register.setRealObject(realRegister);
        this.mProcessChain.add(register);
        
        // Set up FileFtpUploader, inject into LogProxy and push into ProcessChain 
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
        
        // Set up FileBackuper, inject into LogProxy and push into ProcessChain 
        String backupFolder = this.mConfig.getConfigEntry(JobConfig.BACKUP_FOLDER_PROPERTY);

        FileManipulatorLogProxy backuper = new FileManipulatorLogProxy();
        FileBackuper realBackuper = new FileBackuper();
        realBackuper.setPath(backupFolder);
        backuper.setName("BACKUP");
        backuper.setRealObject(realBackuper);
        this.mProcessChain.add(backuper);

        // Set up FileRemover, inject into LogProxy and push into ProcessChain 
        FileManipulatorLogProxy remover = new FileManipulatorLogProxy();
        FileRemover realRemover = new FileRemover();
        remover.setName("DELETE");
        remover.setRealObject(realRemover);
        this.mProcessChain.add(remover);

        // Dynamic configuration of log setting
        String logProperties = this.mConfig.getConfigEntry(JobConfig.LOG_PROPERTIES_PROPERTY);
        PropertyConfigurator.configure(logProperties);

    }
    
    /**
     * Getter of member mIsActive
     */ 
    public boolean isActive(){
        return this.mIsActive;
    }

    /**
     * Setter of member mIsActive
     */
    public void setActive(boolean b){
        this.mIsActive = b;
    }
    
    /**
     * Method run() overrides the Thread:run(). It invokes all FileManipulators
     * and performs the looping by interval feature.
     *
     */
    @Override
    public void run(){

        mLogger.debug("Job Starts.");

        int interval = Integer.parseInt(this.mConfig.getConfigEntry(JobConfig.LOOP_INTERVAL_PROPERTY));
        String smsFolder = this.mConfig.getConfigEntry(JobConfig.SMS_FOLDER_PROPERTY);
        this.mIsActive = true;

        while(this.isActive()) {

            try {

                // Iterate files in target folder
                File targetFoler = new File(smsFolder);
                if (targetFoler.exists() && targetFoler.isDirectory()){
                    for (File f : targetFoler.listFiles()){

                        boolean chainBroke = false;

                        // Process the target file with FileManipulator one after one
                        // If the result is not success, then stop any further process
                        // on the file and move to next file
                        for (FileManipulator fm : mProcessChain){
                            fm.setFile(f);
                            fm.manipulate();
                            if (!fm.isSuccess()){
                                chainBroke = true;
                                break;
                            }
                        }

                        // If chain was processed completely, then trun on the 
                        // mJustSentSomethingFlag 
                        if (!chainBroke){
                            this.mJustSentSomethingFlag = true;
                            this.mLastSentDateTime = DateTime.now();
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
