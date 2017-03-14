package com.bcm.app.sendsms;

import org.apache.log4j.Logger;
import java.io.File;

public class MessageFtpUploaderLogProxy extends MessageFtpUploader{
    
    private final Logger mLogger = Logger.getLogger(MessageFtpUploaderLogProxy.class);
    
    private final String LOG_MANIPULATE_BEGINNING = "Prepare to upload message file to FTP: ";
    private final String LOG_MANIPULATE_DONE_SUCCESS = "Upload performed for file: ";
    private final String LOG_MANIPULATE_DONE_FAILURE = "Failed to upload file, file may not exist.";
    
    @Override
    public void manipulate(){
        this.mLogger.info(LOG_MANIPULATE_BEGINNING + this.getFile().getName());
        super.manipulate();
        if (super.isSuccess()){
            this.mLogger.info(LOG_MANIPULATE_DONE_SUCCESS + this.getFile().getName());
        }else{
            this.mLogger.info(LOG_MANIPULATE_DONE_FAILURE);
        }
    }
    
}