package com.bcm.app.core;

import org.apache.log4j.Logger;
import java.io.File;

/**
 * Class FileManipulatorLogProxy applies the <b>Proxy Design Pattern</b>. 
 * It owns a FileManipulator member and it relies on this member to 
 * perform the operations. What FileManipulatorLogProxy really does 
 * is to write logs everytime its realObject performs an action. 
 *
 * The advantage of this pattern is that it decoupled the log writing 
 * from the operation so that classes are re-usable when new File-
 * Manipulator is introduced or new logging requirements appear. 
 *
 */
public class FileManipulatorLogProxy extends FileManipulator{
    
    /*
     * Logger and its log template text
     */
    final static String LOG_SUCCESS_TEXT = "Success on manipulate *";
    final static String LOG_FAILURE_TEXT = "Failure on manipulate *";
    final static String LOG_NO_REAL_OBJECT_TEXT = "No real object on manipulate *";
    final static Logger mLogger = Logger.getLogger(FileManipulatorLogProxy.class);

    /*
     * The name of manipulator that shows on the log
     */
    private String mName;

    /*
     * Real FileManipulator that performs operations
     */
    private FileManipulator mReal;

    /**
     * Setter of member
     */
    public void setName(String name){
        this.mName = name;
    }

    /**
     * Getter of member
     */
    public String getName(){
        return this.mName;
    }
    
    /**
     * Setter of member
     */
    public void setRealObject(FileManipulator fm){
        this.mReal = fm;
    }

    /**
     * Getter of member
     */
    public FileManipulator getRealObject(){
        return this.mReal;
    }

    /**
     * Method manipulate() uses real object to performs operation. 
     * After the operation is performed, write result to the log.
     *
     */
    @Override
    public void manipulate(){

        if (this.mReal == null){
            mLogger.debug(LOG_NO_REAL_OBJECT_TEXT + this.mName);
            return;
        }

        // Real object perfroms operation
        this.mReal.manipulate();

        // Write log 
        String filename = this.mReal.getFile().getName();
        if (this.mReal.isSuccess()){
            mLogger.info(LOG_SUCCESS_TEXT + this.mName + " [" + filename + "]");
        }else{
            mLogger.info(LOG_FAILURE_TEXT + this.mName + " [" + filename + "]");
        }

    }

    /**
     * Method setFile(File) overrides FileManipulator:setFile(File)
     * that it uses real object to perfroms the operation.
     *
     * @param File target file
     * @return void
     */
    @Override
    public void setFile(File f){
        this.mReal.setFile(f);
    }

    /**
     * Method getFile() overrides FileManipulator:getFile()
     * that it uses real object to perfroms the operation.
     *
     * @return File target file
     */
    @Override 
    public File getFile(){
        return this.mReal.getFile();
    }

    /**
     * Method isSuccess() overrides FileManipulator:isSuccess()
     * that it uses real object to perfroms the operation.
     *
     * @return boolean result of operation 
     */
    @Override
    public boolean isSuccess(){
        return this.mReal.isSuccess();
    }   
    
}

