package com.bcm.app.engine;

import java.util.Properties;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import org.springframework.stereotype.Component;

public class FtpConfigProperties {

    public final static String FTP_ADDRESS_PROPERTY = "FTP_ADDRESS";
    public final static String FTP_PORT_PROPERTY = "FTP_PORT";
    public final static String FTP_USER_PROPERTY = "FTP_USER";
    public final static String FTP_PASSWORD_PROPERTY = "FTP_PASSWORD";
    public final static String FTP_FOLDER_PROPERTY = "FTP_FOLDER";
    public final static String SMS_FOLDER_PROPERTY = "SMS_FOLDER";
    public final static String BACKUP_FOLDER_PROPERTY = "BKUP_FOLDER";
    public final static String LOOP_INTERVAL_PROPERTY = "LOOP_INTERVAL";
    public final static String FILE_TYPE_PROPERTY = "FILE_TYPE";
    public final static String LOG_PROPERTIES_PROPERTY = "LOG_PROP";
    public final static String NA_VALUE = "N/A";

    private Properties mProperties;
    private String mPropertiesPath;

    public FtpConfigProperties(String config){

        this.mPropertiesPath = config;
        this.mProperties = new Properties();
        InputStream input = null;

        try{

            input = new FileInputStream(config);
            this.mProperties.load(input);

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public String getConfigEntry(String key){
        return this.mProperties.getProperty(key, NA_VALUE);
    } 

    public void setConfigEntry(String key, String value){
        this.mProperties.setProperty(key, value);
    } 

    public boolean verifyConfig(){
        
        boolean valid = this.verifyFtpConnection();
        return valid;

    }

    private boolean verifyFtpConnection(){

        String ftpAddress = this.getConfigEntry(FTP_ADDRESS_PROPERTY);
        int ftpPort = Integer.parseInt(this.getConfigEntry(FTP_PORT_PROPERTY));
        String ftpUser = this.getConfigEntry(FTP_USER_PROPERTY);
        String ftpPassword = this.getConfigEntry(FTP_PASSWORD_PROPERTY);
        String ftpFolder = this.getConfigEntry(FTP_FOLDER_PROPERTY);

        try{

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(ftpAddress, ftpPort);

            /* Check ftp address and port */
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return false;
            }

            /* Check user and password */
            boolean success = ftpClient.login(ftpUser, ftpPassword);
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                return false;
            }

            /* Check upload target ftp folder existence */
            ftpClient.changeWorkingDirectory(ftpFolder);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                return false;
            }

            ftpClient.logout();
            ftpClient.disconnect();

            return true;

        }catch (Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public void saveConfigProperties(){

        OutputStream output = null;

        try {

            output = new FileOutputStream(this.mPropertiesPath);
            this.mProperties.store(output, null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
