package com.bcm.app.core;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.io.File;

public class FileRegisterTest{

    private FileManipulator messageRegister;
    
    @Before
    public void createInstance(){
        // Reset the object e
        messageRegister = new FileRegister();
    }
    
    @Test
    public void testGetFileEmpty() {
        assertEquals(messageRegister.getFile(), null);
    }
   
    @Test 
    public void testSetNullFile(){
        try{
            messageRegister.setFile(null);
            assertEquals(true, true);  //Exception test: shd not throws exception
        }catch(Exception e ){
            assertEquals(true, false);  //Exception test
        }
    }
    
    @Test
    public void testGetFile() {
        File f = new File("test");
        messageRegister.setFile(f);
        assertEquals(messageRegister.getFile(), f);
    }
        
    @Test 
    public void testFailBeforeManipulate(){
        assertEquals(messageRegister.isSuccess(), false);
    }
    
    @Test 
    public void testFailAfterManipulateWithoutSetFile(){
        messageRegister.manipulate();
        assertEquals(messageRegister.isSuccess(), false);
    }
    
    @Test 
    public void testFailAfterManipulateWithNonExistingFile(){
        File f = new File("test");
        messageRegister.setFile(f);
        messageRegister.manipulate();
        assertEquals(messageRegister.isSuccess(), false);
    }
    
    @Test 
    public void testSuccessAfterManipulateWithExistingFile(){
        try{
            String path = System.getProperty("user.dir");
            File f = new File(path + "\\test.txt");
            if(!f.exists()){
                f.createNewFile();
            }
            messageRegister.setFile(f);        
            messageRegister.manipulate();
            assertEquals(messageRegister.isSuccess(), true);
            f.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testIsSuccessResetWhenSetExistingFileThenSetNonExistingFile(){
        try{
            String path = System.getProperty("user.dir");
            File f1 = new File(path + "\\test1.txt");
            if(!f1.exists()){
                f1.createNewFile();
            }
            File f2 = new File(path + "\\test2.txt");
            if(f2.exists()){
                f2.delete();
            }
            messageRegister.setFile(f1);        
            messageRegister.manipulate();
            boolean firstTimeStatus = messageRegister.isSuccess();
            messageRegister.setFile(f2);        
            boolean secondTimeStatus = messageRegister.isSuccess();
            assertEquals(firstTimeStatus && !secondTimeStatus, true);
            f1.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
   
}
