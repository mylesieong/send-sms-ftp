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
     * @param address Address of ftp (can be hostname or ip)
     */
    public void setFtpAddress(String address){
        this.mFtpAddress = address;
    }
    
    /**
     * Setter of member
     * @param port number of ftp port 
     */
    public void setFtpPort(int port){
        this.mFtpPort = port;
    }
    
    /**
     * Setter of member
     * @param user String of username
     */
    public void setFtpUser(String user){
        this.mFtpUser = user;
    }
    
    /**
     * Setter of member
     * @param password String of password
     */
    public void setFtpPassword(String password){
        this.mFtpPassword = password;
    }
    
    /**
     * Setter of member
     * @param folder String of ftp target folder path
     */
    public void setFtpFolder(String folder){
        this.mFtpFolder = folder;
    }
    
    /**
     * Getter of member
     * @return String of ftp address
     */
    public String getFtpAddress(){
        return this.mFtpAddress;
    }
    
    /**
     * Getter of member
     * @return number of ftp port
     */
    public int getFtpPort(){
        return this.mFtpPort;
    }
    
    /**
     * Getter of member
     * @return string of ftp username
     */
    public String getFtpUser(){
        return this.mFtpUser;
    }
    
    /**
     * Getter of member
     * @return String of ftp password
     */
    public String getFtpPassword(){
        return this.mFtpPassword;
    }
    
    /**
     * Getter of member
     * @return string of target folder path on ftp
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
                this.mSuccess = false;
                return ;
            }
            
            // Check whether login success, if not then stop action
            boolean success = ftpClient.login(this.getFtpUser(), this.getFtpPassword());
            if (!success){            
                System.out.println("Wrong ftp settings, operation quited.");
                this.mSuccess = false;
                return ;
            }

            // Check whether target upload folder on ftp exists
            ftpClient.changeWorkingDirectory(this.getFtpFolder());
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, operation quited. ");
                this.mSuccess = false;
                return ; 
            }
            
            // Perform upload 
            input = new FileInputStream(this.getFile());  //fileName includes filetype
            ftpClient.appendFile(this.getFile().getName(), input);
            this.mSuccess = true;

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
                    ftpClient.logout();
                    ftpClient.disconnect();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }

        }

    }

}
