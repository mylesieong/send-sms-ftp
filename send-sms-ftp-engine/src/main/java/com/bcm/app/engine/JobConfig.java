package com.bcm.app.engine;

import java.util.Properties;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import org.springframework.stereotype.Component;

/**
 * Class JobConfig reads and writes client's configuration file.
 * It also provides self-validation feature. 
 *
 * There is a list of property keys(String) defined in class JobConfig 
 * for the convenience of properties retrieval. But client can also 
 * read a newly defined property with method getConfigEntry(String) to
 * which the key(String) of new property is injected.
 *
 */
public class JobConfig {

    /*
     * List of property keys(String)
     */
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

    /*
     * Properties read/write related members
     */
    private Properties mProperties;
    private String mPropertiesPath;

    /**
     * Constructor of JobConfig. It loads the properties and remember
     * the path of property file for future read/write use.
     * 
     */
    public JobConfig(String path){

        this.mPropertiesPath = path;
        this.mProperties = new Properties();
        InputStream input = null;

        try{

            input = new FileInputStream(path);
            this.mProperties.load(input);

        }catch (Exception e){

            e.printStackTrace();
            
        }finally{

            // Close stream
            if (input != null){
                try{
                    input.close();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }

        }

    }

    /**
     * Method getConfigEntry(key) retrieves property from properties
     * file. There is a list of property keys(String) defined in class 
     * JobConfig for the convenience of properties retrieval. But client 
     * can also use their own key strings.
     *
     * @param String key of property. e.g. "FTP_ADDRESS", "FILE_TYPE"
     * @return String
     */
    public String getConfigEntry(String key){
        return this.mProperties.getProperty(key, NA_VALUE);
    } 

    /**
     * Method setConfigEntry(key, value) sets the value by key to 
     * the properties member.
     *
     * @param String key of property e.g. "FTP_ADDRESS", "FILE_TYPE"
     * @param String value of property
     * @return void
     */
    public void setConfigEntry(String key, String value){
        this.mProperties.setProperty(key, value);
    } 

    /** 
     * Method verifyConfig() is a self-validation method. At this version
     * it only checks the ftp connection integrity. Developer is suggested
     * to add new validation in this method and build the validation body 
     * in another private method like verifyFtpConnection()
     *
     * @return boolean
     */ 
    public boolean verifyConfig(){
        
        boolean valid = this.verifyFtpConnection();
        return valid;

    }

    /**
     * Method verifyFtpConnection() verify the ftp connection and target 
     * folder existence. Note that it should not be invoked directly by
     * client.
     *
     * @return boolean
     */
    private boolean verifyFtpConnection(){

        String ftpAddress = this.getConfigEntry(FTP_ADDRESS_PROPERTY);
        int ftpPort = Integer.parseInt(this.getConfigEntry(FTP_PORT_PROPERTY));
        String ftpUser = this.getConfigEntry(FTP_USER_PROPERTY);
        String ftpPassword = this.getConfigEntry(FTP_PASSWORD_PROPERTY);
        String ftpFolder = this.getConfigEntry(FTP_FOLDER_PROPERTY);
            
        FTPClient ftpClient = new FTPClient();

        try{

            ftpClient.connect(ftpAddress, ftpPort);

            // Check ftp address and port
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Validation failed. Server reply code: " + replyCode);
                return false;
            }

            // Check user and password 
            boolean success = ftpClient.login(ftpUser, ftpPassword);
            if (!success){            
                System.out.println("Wrong ftp settings, validation failed.");
                return false;
            }

            // Check upload target ftp folder existence 
            ftpClient.changeWorkingDirectory(ftpFolder);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, validation failed. ");
                return false;
            }

            return true;

        }catch (Exception e){

            e.printStackTrace();
            return false;

        }finally{

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

    /**
     * Method saveConfig() saves current properties in member mProperties
     * to the configuration file.
     *
     * @return void
     */
    public void saveConfig(){

        OutputStream output = null;

        try {

            output = new FileOutputStream(this.mPropertiesPath);
            this.mProperties.store(output, null); //2nd parm is store message, leave null

        }catch (Exception e){

            e.printStackTrace();

        }finally{

            //Close Stream
            if (output != null){
                try{
                    output.close();
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }

        }

    }

}
