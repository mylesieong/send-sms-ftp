package com.bcm.app.core;

import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 * Class FileRemover performs deletion of target file in method
 * manipulate(). Note that this is an un-reversible operation so
 * use carefully.
 *
 */
public class FileRemover extends FileManipulator{
    
    /**
     * Method manipulate() performs deletion of target file.
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

