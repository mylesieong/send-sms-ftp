package com.bcm.app.sendsms;

import org.apache.log4j.Logger;
import java.io.File;

public class MessageBackuperLogProxy extends MessageBackuper{
    
    private final Logger mLogger = Logger.getLogger(MessageBackuperLogProxy.class);
    
    private final String LOG_MANIPULATE_BEGINNING = "Prepare to back up message file: ";
    private final String LOG_MANIPULATE_DONE_SUCCESS = "Back-up performed for file: ";
    private final String LOG_MANIPULATE_DONE_FAILURE = "Failed to back-up file, file may not exist.";
    
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