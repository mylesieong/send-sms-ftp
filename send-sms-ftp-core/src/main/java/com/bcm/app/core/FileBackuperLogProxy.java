package com.bcm.app.core;

import org.apache.log4j.Logger;
import java.io.File;

public class FileBackuperLogProxy extends FileBackuper{
    
    final static Logger mLogger = Logger.getLogger(FileBackuperLogProxy.class);
    
    final static String LOG_MANIPULATE_BEGINNING = "Prepare to back up message file: ";
    final static String LOG_MANIPULATE_DONE_SUCCESS = "Back-up performed for file: ";
    final static String LOG_MANIPULATE_DONE_FAILURE = "Failed to back-up file, file may not exist.";
    
    @Override
    public void manipulate(){
        mLogger.debug(LOG_MANIPULATE_BEGINNING + this.getFile().getName());
        super.manipulate();
        if (super.isSuccess()){
            mLogger.debug(LOG_MANIPULATE_DONE_SUCCESS + this.getFile().getName());
        }else{
            mLogger.debug(LOG_MANIPULATE_DONE_FAILURE);
        }
    }
    
}
