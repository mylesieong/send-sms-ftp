package com.bcm.app.core;

import java.io.File;

import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Class FileBackuper copies target file and paste to a backup folder.
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
                
                // Create empty backup file with name appended with timestamp
                String fileName = FilenameUtils.getBaseName(mFile.toString());
                String fileExtension = FilenameUtils.getExtension(mFile.toString());
                File backupFile = new File(mPath + "\\" + fileName + "." + fileExtension);
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
