package com.bcm.app.sendsms;

import java.io.File;

public class MessageRegister implements FileManipulator{
    
    private File mFile;
    private boolean mIsSuccess = false;
    
    @Override
    public void setFile(File file){
        this.mFile = file;
        this.mIsSuccess = false;
    };

    @Override
    public File getFile(){
        return this.mFile;
    };    
    
    /**
     * Method manipulate in MessageRegister will check the 
     * existence of the file set to the object
     */
    @Override
    public void manipulate(){
        if (this.mFile != null){
            this.mIsSuccess = this.mFile.exists();
        }else{
            this.mIsSuccess = false;
        }
    }

    @Override
    public boolean isSuccess(){
        return mIsSuccess;
    }   
}