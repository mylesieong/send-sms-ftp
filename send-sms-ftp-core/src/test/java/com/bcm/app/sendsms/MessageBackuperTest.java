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

import org.apache.commons.io.FileUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MessageBackuperTest{
    
    private MessageBackuper messageBackuper;
    
    @Before
    public void createInstance(){
        // Reset the object e
        messageBackuper = new MessageBackuper();
    }
    
    @Test
    public void testGetFileEmpty() {
        assertEquals(messageBackuper.getFile(), null);
    }
   
    @Test 
    public void testSetNullFile(){
        try{
            messageBackuper.setFile(null);
            assertEquals(true, true);  //Exception test: shd not throws exception
        }catch(Exception e ){
            assertEquals(true, false);  //Exception test
        }
    }
    
    @Test
    public void testGetFile() {
        File f = new File("test");
        messageBackuper.setFile(f);
        assertEquals(messageBackuper.getFile(), f);
    }

    @Test 
    public void testSetPath(){
        String path = "C:\\temp";
        messageBackuper.setPath(path);
        assertEquals(path, messageBackuper.getPath());
    }
     
     /** NOT STABLE **
      * note that this test can be improved becase the time stamp is real time so might 
      * differs to production time stamp
      */     
     @Test
     public void testBackupCapabilityTimeStamp(){
         File f = null;
         File backupFile = null;
         File backupFolder = null;
         try {
            /* Prepare the to-be-uploaded file*/
            f = new File("testBackupCapabilityTimeStamp.txt");
            OutputStream fop = new FileOutputStream(f);
            String fileContent = "testBackupCapabilityTimeStamp content";
            if (!f.exists()){    
                f.createNewFile();
            }
            fop.write(fileContent.getBytes());
            fop.flush();
            fop.close();
            
            /* Invoke file manipulator */
            System.out.println("Create and inject file to test: " + f.getPath());
            messageBackuper.setFile(f);
            System.out.println("Create backup folder: backup/");
            backupFolder = new File("backup");
            if(!backupFolder.exists()){
                backupFolder.mkdir();
            }
            
            messageBackuper.setPath(backupFolder.getPath());
            messageBackuper.manipulate();
            
            /* Check result*/
            DateTime datetime = new DateTime();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
            backupFile = new File("backup\\testBackupCapabilityTimeStamp" + datetime.toString(fmt) + ".txt");
            assertEquals(backupFile.exists(), true);  //for backup isPerformed
            
         }catch (Exception e){
             e.printStackTrace();
         }finally {
             try{
                /* Delete used file and folder*/
                if (f.exists()){
                    f.delete();
                }

                if (backupFile.exists()){
                    backupFile.delete();
                }
                            
                if (backupFolder.exists()){
                    backupFolder.delete();
                }
             }catch(Exception e){
                 e.printStackTrace();
             }
         }
     }
     
     /** NOT STABLE **
      * note that this test can be improved becase the time stamp is real time so might 
      * differs to production time stamp
      */
     @Test
     public void testBackupCapabilitySameContent(){
         File f = null;
         File backupFile = null;
         File backupFolder = null;
         try {
            /* Prepare the to-be-uploaded file*/
            f = new File("testBackupCapabilitySameContent.txt");
            OutputStream fop = new FileOutputStream(f);
            String fileContent = "testBackupCapabilitySameContent content";
            if (!f.exists()){    
                f.createNewFile();
            }
            fop.write(fileContent.getBytes());
            fop.flush();
            fop.close();
            
            /*Prepare a copy for content comparation*/
            File copiedFile = new File("copiedFile.txt");
            FileUtils.touch(copiedFile);
            FileUtils.copyFile(f, copiedFile);
            
            /* Invoke file manipulator */
            System.out.println("Create and inject file to test: " + f.getPath());
            messageBackuper.setFile(f);
            System.out.println("Create backup folder: backup/");
            backupFolder = new File("backup");
            if(!backupFolder.exists()){
                backupFolder.mkdir();
            }
            
            messageBackuper.setPath(backupFolder.getPath());
            messageBackuper.manipulate();
            
            /* Check result*/
            DateTime datetime = new DateTime();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");
            backupFile = new File("backup\\testBackupCapabilitySameContent" + datetime.toString(fmt) + ".txt");
            assertEquals(FileUtils.contentEquals(copiedFile, backupFile), true);  //for backup isPerformed
            
         }catch (Exception e){
             e.printStackTrace();
         }finally {
             try{
                /* Delete used file and folder*/
                if (f.exists()){
                    f.delete();
                }

                if (backupFile.exists()){
                    backupFile.delete();
                }
                            
                if (backupFolder.exists()){
                    backupFolder.delete();
                }
             }catch(Exception e){
                 e.printStackTrace();
             }
         }
     }
     
     // @Test
     // public void testNonExistingFile(){
         
     // }
     
     // @Test 
     // public void testNonExistingPath(){
         
     // }
}
