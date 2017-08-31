package com.bcm.app.core;

import java.io.File;

/**
 * Class FileRegister checks the file type and the existence 
 * of target file.
 *
 */
public class FileRegister extends FileManipulator{
    
    /*
     * File type for checking
     */
    private String mFileType;

    /**
     * Setter of member mFileType
     *
     * @param String filetype e.g. "msg", "txt"
     */
    public void setFileType(String type){
        this.mFileType = type;
    }

    /**
     * Getter of member mFileType
     */
    public String getFileType(){
        return this.mFileType;
    }
    
    /**
     * Method manipulate checks the 
     * existence of the file set to the object
     *
     */
    @Override
    public void manipulate(){

        if (this.mFile == null){

            this.mIsSuccess = false;

        }else{

            boolean fileExist = false;
            boolean fileTypeOK = false;

            fileExist = this.mFile.exists();

            if (fileExist){
                int index = this.mFile.getName().lastIndexOf(".") + 1;
                String type = this.mFile.getName().substring(index);
                fileTypeOK = type.compareToIgnoreCase(this.mFileType) == 0;
            }

            this.mIsSuccess = fileExist && fileTypeOK;

        }

    }

}
