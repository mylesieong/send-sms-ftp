package com.bcm.app.core;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class FileRemover extends FileManipulator{
    
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

}

