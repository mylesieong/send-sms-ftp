package com.bcm.app.core;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class FileRemover implements FileManipulator{
    
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
     * 
     *
     */
    @Override
    public void manipulate(){

        if (this.mFile == null){
            this.mIsSuccess = false;
        }else{
            try{
                FileUtils.forceDelete(this.mFile);
                this.mIsSuccess = true;
            }catch(Exception e){
                e.printStackTrace();
                this.mIsSuccess = false;
            }
        }

    }

    @Override
    public boolean isSuccess(){
        return mIsSuccess;
    }   
}

