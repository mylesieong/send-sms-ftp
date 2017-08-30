package com.bcm.app.core;

import java.io.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Class FileFtpUploader uploads target file to FTP server.
 * It has ftp related members and it uses apache commons net
 * to perform the FTP upload.
 *
 */
public class FileFtpUploader extends FileManipulator{
    
    /*
     * Ftp related members
     */
    private String mFtpAddress;
    private int mFtpPort;
    private String mFtpUser;
    private String mFtpPassword;
    private String mFtpFolder;
    
    /**
     * Setter of member
     */
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    /**
     * Setter of member
     */
    public void setFtpPort(int port){
        this.mFtpPort = port;
    }
    
    /**
     * Setter of member
     */
    public void setFtpUser(String user){
        this.mFtpUser = user;
    }
    
    /**
     * Setter of member
     */
    public void setFtpPassword(String password){
        this.mFtpPassword = password;
    }
    
    /**
     * Setter of member
     */
    public void setFtpFolder(String folder){
        this.mFtpFolder = folder;
    }
    
    /**
     * Getter of member
     */
    public String getFtpAddress(){
        return this.mFtpAddress;
    }
    
    /**
     * Getter of member
     */
    public int getFtpPort(){
        return this.mFtpPort;
    }
    
    /**
     * Getter of member
     */
    public String getFtpUser(){
        return this.mFtpUser;
    }
    
    /**
     * Getter of member
     */
    public String getFtpPassword(){
        return this.mFtpPassword;
    }
    
    /**
     * Getter of member
     */
    public String getFtpFolder(){
        return this.mFtpFolder;
    }
    
    /**
     * Method manipulate uploads the target file to FTP server.
     *
     */
    @Override
    public void manipulate(){

        FTPClient ftpClient = new FTPClient();
        InputStream input = null;

        try{
            
            ftpClient.connect(this.getFtpAddress(), this.getFtpPort());

            // Check if ftp server exists, if not then stop action
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                this.mIsSuccess = false;
                return ;
            }
            
            // Check whether login success, if not then stop action
            boolean success = ftpClient.login(this.getFtpUser(), this.getFtpPassword());
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                this.mIsSuccess = false;
                return ;
            }

            // Check whether target upload folder on ftp exists
            ftpClient.changeWorkingDirectory(this.getFtpFolder());
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                this.mIsSuccess = false;
                return ; 
            }
            
            // Perform upload 
            input = new FileInputStream(this.getFile());  //fileName includes filetype
            ftpClient.appendFile(this.getFile().getName(), input);
            this.mIsSuccess = true;

            // Clean action left over
            input.close();
            ftpClient.logout();
            ftpClient.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            // Close io stream
            if (input != null){
                try{
                    input.close();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
            // Close ftpclient
            if (ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        }

    }

}
