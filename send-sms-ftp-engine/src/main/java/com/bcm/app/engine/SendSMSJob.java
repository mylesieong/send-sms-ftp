package com.bcm.app.engine;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import com.bcm.app.core.*;

public class SendSMSJob extends Thread {

    final static Logger mLogger = Logger.getLogger(SendSMSJob.class);

    private final String FTP_ADDRESS = "172.18.255.108";
    private final int FTP_PORT = 21;
    private final String FTP_USER = "itoper";
    private final String FTP_PASSWORD = "IToper01";
    private final String FTP_FOLDER = "Test";
    private final String SMS_FILE = "C:\\smsfile\\*.msg";
    private final String SMS_FOLDER = "C:\\smsfile";
    private final String BKUP_FOLDER = "C:\\smssent";
    private final String CUR_DIR = System.getProperty("user.dir");
    private final String INMSG = "inmsg.txt";
    private final String OUTMSG = "outmsg.txt";
    
    private final List<FileManipulator> mProcessChain = new ArrayList<FileManipulator>();
    
    private boolean mIsActive;
    
    public SendSMSJob() {
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
        
        /* Set thread active flag */
        this.mIsActive = true;
    }
    
    public boolean isActive(){
        return this.mIsActive;
    }
    
    @Override
    public void run(){
        while(isActive()) {
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
                Thread.sleep(5000);
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void logTheStart(){
        mLogger.debug("Job Starts.");
    }
    
    public static void main(String[] args) {
        SendSMSJob job = new SendSMSJob();
        job.logTheStart();
        job.run(); //not using thread, start using the current thread, terminated by Ctrl C in bash
    }
    
}