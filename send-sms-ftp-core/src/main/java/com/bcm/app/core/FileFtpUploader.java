package com.bcm.app.core;

import java.io.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FileFtpUploader implements FileManipulator{
    
    private File mFile;
    private boolean mIsSuccess = false;
    private String mFtpAddress;
    private int mFtpPort;
    private String mFtpUser;
    private String mFtpPassword;
    private String mFtpFolder;
    
    /* --- Setters ---*/
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    public void setFtpPort(int port){
        this.mFtpPort = port;
    }
    
    public void setFtpUser(String user){
        this.mFtpUser = user;
    }
    
    public void setFtpPassword(String password){
        this.mFtpPassword = password;
    }
    
    public void setFtpFolder(String folder){
        this.mFtpFolder = folder;
    }
    
    public String getFtpAddress(){
        return this.mFtpAddress;
    }
    
    public int getFtpPort(){
        return this.mFtpPort;
    }
    
    public String getFtpUser(){
        return this.mFtpUser;
    }
    
    public String getFtpPassword(){
        return this.mFtpPassword;
    }
    
    public String getFtpFolder(){
        return this.mFtpFolder;
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
     * Method manipulate in FileUploader will check the 
     * existence of the file set to the object
     */
    @Override
    public void manipulate(){
        try{
            this.mIsSuccess = false;
            
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(this.getFtpAddress(), this.getFtpPort());
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                this.mIsSuccess = false;
                return ;
            }
            boolean success = ftpClient.login(this.getFtpUser(), this.getFtpPassword());
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                this.mIsSuccess = false;
                return ;
            }
            ftpClient.changeWorkingDirectory(this.getFtpFolder());
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                this.mIsSuccess = false;
                return ; 
            }
            InputStream input = new FileInputStream(this.getFile());  //fileName includes filetype
            ftpClient.appendFile(this.getFile().getName(), input);
            input.close();
            ftpClient.logout();
            ftpClient.disconnect();
            this.mIsSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSuccess(){
        return mIsSuccess;
    }   

}
