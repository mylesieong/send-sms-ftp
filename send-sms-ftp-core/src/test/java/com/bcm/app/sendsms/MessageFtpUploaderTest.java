package com.bcm.app.sendsms;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class MessageFtpUploaderTest{
    
    private static final String FTP_ADDRESS = "172.18.255.108";
    private static final int FTP_PORT = 21;
    private static final String FTP_USER = "itoper";
    private static final String FTP_PASSWORD = "IToper01";
    private static final String FTP_FOLDER = "Test";
    
    private MessageFtpUploader messageFtpUploader;
    
    @Before
    public void createInstance(){
        // Reset the object e
        messageFtpUploader = new MessageFtpUploader();
        messageFtpUploader.setFtpAddress(FTP_ADDRESS);
        messageFtpUploader.setFtpPort(FTP_PORT);
        messageFtpUploader.setFtpUser(FTP_USER);
        messageFtpUploader.setFtpPassword(FTP_PASSWORD);
        messageFtpUploader.setFtpFolder(FTP_FOLDER);
    }
    
    @Test
    public void testGetFileEmpty() {
        assertEquals(messageFtpUploader.getFile(), null);
    }
   
    @Test 
    public void testSetNullFile(){
        try{
            messageFtpUploader.setFile(null);
            assertEquals(true, true);  //Exception test: shd not throws exception
        }catch(Exception e ){
            assertEquals(true, false);  //Exception test
        }
    }
    
    @Test
    public void testGetFile() {
        File f = new File("test");
        messageFtpUploader.setFile(f);
        assertEquals(messageFtpUploader.getFile(), f);
    }
        
    @Test
    public void testUploadCapability(){
        File f = new File("testConnectionSuccess.txt");
        FTPClient ftpClient = new FTPClient();
        try{
            /* Prepare FTP connection */
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return ;
            }
            boolean success = ftpClient.login(FTP_USER, FTP_PASSWORD);
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                return ;
            }
            ftpClient.changeWorkingDirectory(FTP_FOLDER);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                return ; 
            }
            /* Prepare the to-be-uploaded file*/
            OutputStream fop = new FileOutputStream(f);
            String fileContent = "testConnectionSuccess content";
            if (!f.exists()){    
                f.createNewFile();
            }
            fop.write(fileContent.getBytes());
            fop.flush();
            fop.close();
            
            /* Ensure the absence of file on ftp */
            ftpClient.deleteFile(f.getName());

            /* Perform the upload*/
            messageFtpUploader.setFile(f);
            messageFtpUploader.manipulate();

            /* Verify the presence of file on ftp */
            InputStream fip;
            fip = ftpClient.retrieveFileStream(f.getName());
            assertEquals(true, fip != null ); //if the file is uploaded, fip should not be null

            /* delete remote test file*/
            ftpClient.deleteFile(f.getName());

            /* delete local test file*/
            if (f.exists()){    
                f.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void testCorrectInputReturnTrue(){
        File f = new File("testCorrectInputReturnTrue.txt");
        FTPClient ftpClient = new FTPClient();
        try{
            /* Prepare FTP connection */
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return ;
            }
            boolean success = ftpClient.login(FTP_USER, FTP_PASSWORD);
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                return ;
            }
            ftpClient.changeWorkingDirectory(FTP_FOLDER);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                return ; 
            }
            /* Prepare the to-be-uploaded file*/
            OutputStream fop = new FileOutputStream(f);
            String fileContent = "testCorrectInputReturnTrue content";
            if (!f.exists()){    
                f.createNewFile();
            }
            fop.write(fileContent.getBytes());
            fop.flush();
            fop.close();
            
            /* Ensure the absence of file on ftp */
            ftpClient.deleteFile(f.getName());

            /* Perform the upload*/
            messageFtpUploader.setFile(f);
            messageFtpUploader.manipulate();

            /* Verify the return status */
            assertEquals(true, messageFtpUploader.isSuccess()); //if the file is uploaded, fip should not be null

            /* delete remote test file*/
            ftpClient.deleteFile(f.getName());

            /* delete local test file*/
            if (f.exists()){    
                f.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
   
    @Test
    public void testWrongSettingReturnFalse(){
        File f = new File("testWrongSettingReturnFalse.txt");
        FTPClient ftpClient = new FTPClient();
        try{
            /* Prepare FTP connection */
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return ;
            }
            boolean success = ftpClient.login(FTP_USER, FTP_PASSWORD);
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                return ;
            }
            ftpClient.changeWorkingDirectory(FTP_FOLDER);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                return ; 
            }
            /* Prepare the to-be-uploaded file*/
            OutputStream fop = new FileOutputStream(f);
            String fileContent = "testWrongSettingReturnFalse content";
            if (!f.exists()){    
                f.createNewFile();
            }
            fop.write(fileContent.getBytes());
            fop.flush();
            fop.close();
            
            /* Ensure the absence of file on ftp */
            ftpClient.deleteFile(f.getName());

            /* Perform the upload*/
            messageFtpUploader.setFile(f);
            messageFtpUploader.setFtpPassword("wrong_pwd");
            messageFtpUploader.manipulate();

            /* Verify the return status */
            assertEquals(false, messageFtpUploader.isSuccess()); //if the file is uploaded, fip should not be null

            /* delete remote test file*/
            ftpClient.deleteFile(f.getName());

            /* delete local test file*/
            if (f.exists()){    
                f.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testNonExistFileReturnFalse(){
        File f = new File("testNonExistFileReturnFalse.txt");
        FTPClient ftpClient = new FTPClient();
        try{
            /* Prepare FTP connection */
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return ;
            }
            boolean success = ftpClient.login(FTP_USER, FTP_PASSWORD);
            if (!success){            
                System.out.println("Wrong ftp settings, test skiped.");
                return ;
            }
            ftpClient.changeWorkingDirectory(FTP_FOLDER);
            if (!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("Cannot find target ftp folder, test skipped. ");
                return ; 
            }
            /* Ensure the absence of the to-be-uploaded file*/
            if (f.exists()){    
                f.delete();
            }
            
            /* Ensure the absence of file on ftp */
            ftpClient.deleteFile(f.getName());

            /* Perform the upload*/
            messageFtpUploader.setFile(f);
            messageFtpUploader.manipulate();

            /* Verify the return status */
            assertEquals(false, messageFtpUploader.isSuccess()); //if the file is uploaded, fip should not be null

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // @Test
    public void testFtpExistFileReplacement(){
        File f = new File("testFtpExistFileReplacement.txt");
        FTPClient ftpClient = new FTPClient();
        try{
            /* Create and Upload file with content A to FTP */
           
            /* Change file content to content B and inject to FileManipulator */
            
            /* Invoke manipulation */
            
            /* If success, check the ftp file content, it should be the content B */
        
        
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
