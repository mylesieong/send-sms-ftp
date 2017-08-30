package com.bcm.app.core;

import java.io.File;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Class FileBackuper will copy input file and paste to a backup folder path
 * with the copied file name appending a datetime (YYYYMMDDTTMMSS)
 * 
 * note: FileBackuper will not change the original file.
 */
public class FileBackuper implements FileManipulator{
    
    private File mFile;
    private boolean mIsSuccess = false;
    private String mPath;
    
    /* --- getters and setters ---*/
    public void setPath(String path){
        this.mPath = path;
    }
        
    public String getPath(){
        return this.mPath;
    }
    
    /* --- interface override ---*/
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
        try{
            if (this.mFile != null && this.mFile.exists()){
                /* get current date and time*/
                DateTime datetime = new DateTime();
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
                
                String fileName = FilenameUtils.getBaseName(this.getFile().toString());
                String fileExtension = FilenameUtils.getExtension(this.getFile().toString());
                
                File backupFile = new File(this.getPath() + "\\" + fileName + datetime.toString(fmt) + "." + fileExtension);
                backupFile.createNewFile();
                FileUtils.copyFile(this.getFile(), backupFile);
                
                this.mIsSuccess = true;
                
            }else{
                this.mIsSuccess = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSuccess(){
        return mIsSuccess;
    }   
}
