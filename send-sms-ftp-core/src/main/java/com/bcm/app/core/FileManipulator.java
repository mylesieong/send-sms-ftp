package com.bcm.app.core;

import java.io.File;

/**
 * Abstract class FileManipulator defines aciton contract of core components
 * and provides default methods.
 * Client can access the API power by follow steps:
 * 1. set the target file to API with setFile(File) method
 * 2. invoke manipulate() method
 * 3. (optional) check the manipulation result with isSuccess()
 * 
 */
public abstract class FileManipulator{

    protected File mFile;
    protected boolean mSuccess = false;

    /**
     * Target file should be injected to FileManipulator with this method.
     * When new file is set, the success flag will be reset to false at the
     * same time.
     *
     * @param File target file(will be manipulated)
     * @return void 
     */
    public void setFile(File file){
        this.mFile = file;
        this.mSuccess = false;
    }

    /**
     * Getter of injected target file.
     *
     * @return File
     */
    public File getFile(){
        return this.mFile;
    }
    
    /**
     * Method manipulate() perform user-defined action to target file. 
     * For example, implementation of method manipulate() in FileRegister 
     * verifies target file's existence and check wether it belongs to a 
     * certain file type. When user implements this method, be aware that
     * class member mSuccess should be evaluated. 
     *
     */
    public abstract void manipulate();

    /**
     * Return the result of the manipulation. 
     *
     * @return boolean
     */
    public boolean isSuccess(){
        return this.mSuccess;
    }

}
