package com.bcm.app.core;

import java.io.File;

public class FileRegister implements FileManipulator{
    
    private File mFile;
    private String mFileType;
    private boolean mIsSuccess = false;

    public void setFileType(String type){
        this.mFileType = type;
    }

    public String getFileType(){
        return this.mFileType;
    }
    
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

    @Override
    public boolean isSuccess(){
        return mIsSuccess;
    }   
}
