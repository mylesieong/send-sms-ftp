package com.bcm.app.engine;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import com.bcm.app.core.*;

public class SendSMSJob {

    // private static final Logger mLogger = Logger.getLogger(SendSMSJob.class);

    private static final String FTP_ADDRESS = "172.18.255.108";
    private static final int FTP_PORT = 21;
    private static final String FTP_USER = "itoper";
    private static final String FTP_PASSWORD = "IToper01";
    private static final String FTP_FOLDER = "Test";
    private static final String SMS_FILE = "C:\\smsfile\\*.msg";
    private static final String SMS_FOLDER = "C:\\smsfile";
    private static final String BKUP_FOLDER = "C:\\smssent";
    private static final String CUR_DIR = System.getProperty("user.dir");
    private static final String INMSG = "inmsg.txt";
    private static final String OUTMSG = "outmsg.txt";
    
    private static final List<FileManipulator> mProcessChain = new ArrayList<FileManipulator>();
    
    static {
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
        
        mProcessChain.add(messageRegisterLogProxy);
        mProcessChain.add(messageFtpUploaderLogProxy);
        mProcessChain.add(messageBackuperLogProxy);
    }
    
    public static void main(String[] args) {
        try{
            File targetFoler = new File(SMS_FOLDER);
            if (targetFoler.exists() && targetFoler.isDirectory()){
                for (File f : targetFoler.listFiles()){
                    // System.out.println("Found file(s):" + f);
                    // mLogger.info("Found file(s):" + f);
                    for (FileManipulator fm : mProcessChain){
                        fm.setFile(f);
                        fm.manipulate();
                        if (!fm.isSuccess()){
                            return;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}