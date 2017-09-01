package com.bcm.app.core;

import java.io.File;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Class FileBackuper copies target file and paste to a backup folder
 * with the copied file's name appended with a timestamp(YYYYMMDDTTMMSS).
 * The FileBackuper should not change the original file.
 *
 */
public class FileBackuper extends FileManipulator{
    
    /*
     * Path of the backup folder
     */
    private String mPath;
    
    /**
     * Setter of member mPath.
     *
     * @param String path of backup folder
     */
    public void setPath(String path){
        this.mPath = path;
    }
        
    /**
     * Getter of member mPath
     *
     * @return String
     */
    public String getPath(){
        return this.mPath;
    }
    
    /**
     * Method manipulate copies the file to backup folder.
     * Note that the original file stay unchanged.
     * 
     */
    @Override
    public void manipulate(){
        try{

            if (this.mFile != null && this.mFile.exists()){
                // get current date and time
                DateTime datetime = new DateTime();
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
                
                // Create empty backup file with name appended with timestamp
                String fileName = FilenameUtils.getBaseName(this.getFile().toString());
                String fileExtension = FilenameUtils.getExtension(this.getFile().toString());
                File backupFile = new File(this.getPath() + "\\" + fileName + datetime.toString(fmt) + "." + fileExtension);
                backupFile.createNewFile();

                // Copy content to backup file and set result
                FileUtils.copyFile(this.getFile(), backupFile);
                this.mSuccess = true;
                
            }else{
                this.mSuccess = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
