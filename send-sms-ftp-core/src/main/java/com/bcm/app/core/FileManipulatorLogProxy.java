package com.bcm.app.core;

import org.apache.log4j.Logger;
import java.io.File;

public class FileManipulatorLogProxy implements FileManipulator{
    
    final static String LOG_SUCCESS_TEXT = "Success on manipulate *";
    final static String LOG_FAILURE_TEXT = "Failure on manipulate *";
    final static String LOG_NO_REAL_OBJECT_TEXT = "No real object on manipulate *";

    final static Logger mLogger = Logger.getLogger(FileManipulatorLogProxy.class);

    private String mName;
    private FileManipulator mReal;

    public void setName(String name){
        this.mName = name;
    }

    public String getName(){
        return this.mName;
    }
    
    public void setRealObject(FileManipulator fm){
        this.mReal = fm;
    }

    public FileManipulator getRealObject(){
        return this.mReal;
    }

    @Override
    public void manipulate(){

        if (this.mReal == null){
            mLogger.debug(LOG_NO_REAL_OBJECT_TEXT + this.mName);
            return;
        }

        String filename = this.mReal.getFile().getName();

        this.mReal.manipulate();

        if (this.mReal.isSuccess()){
            mLogger.info(LOG_SUCCESS_TEXT + this.mName + " [" + filename + "]");
        }else{
            mLogger.info(LOG_FAILURE_TEXT + this.mName + " [" + filename + "]");
        }

    }

    @Override
    public void setFile(File f){
        this.mReal.setFile(f);
    }

    @Override 
    public File getFile(){
        return this.mReal.getFile();
    }

    @Override
    public boolean isSuccess(){
        return this.mReal.isSuccess();
    }   
    
}

