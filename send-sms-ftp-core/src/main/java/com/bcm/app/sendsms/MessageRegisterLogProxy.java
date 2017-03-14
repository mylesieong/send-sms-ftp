package com.bcm.app.sendsms;

import org.apache.log4j.Logger;
import java.io.File;

public class MessageRegisterLogProxy extends MessageRegister{
    
    private final Logger mLogger = Logger.getLogger(MessageRegisterLogProxy.class);
    
    private final String LOG_MANIPULATE_BEGINNING = "Verifying file: ";
    private final String LOG_MANIPULATE_DONE_SUCCESS = "Register performed for file: ";
    private final String LOG_MANIPULATE_DONE_FAILURE = "Failed to register file, file may not exist.";
    
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