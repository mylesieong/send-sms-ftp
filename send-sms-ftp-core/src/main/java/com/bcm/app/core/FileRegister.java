package com.bcm.app.core;

import java.io.File;

public class FileRegister extends FileManipulator{
    
    private String mFileType;

    public void setFileType(String type){
        this.mFileType = type;
    }

    public String getFileType(){
        return this.mFileType;
    }
    
    /**
     * Method manipulate in FileRegister will check the 
     * existence of the file set to the object
     */
    @Override
    public void manipulate(){

        if (this.mFile == null){

            this.mIsSuccess = false;

        }else{

            boolean fileExist;
            boolean fileFiltered = false;

            fileExist = this.mFile.exists();
            
            if (fileExist){
                String type = this.mFile.getName().substring(this.mFile.getName().lastIndexOf(".") + 1);
                fileFiltered = type.compareToIgnoreCase(this.mFileType) != 0;
            }

            this.mIsSuccess = fileExist && !fileFiltered;

        }

    }

}
