package com.bcm.app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
 
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import com.bcm.app.engine.FtpConfigProperties;

@Component
public class ConfigFrame extends JFrame implements ActionListener, KeyListener{

    /* Load Config Frame properties */
    private JLabel mFtpAddressTagLabel;
    private JTextField mFtpAddressField;
    
    private JLabel mFtpPortTagLabel;
    private JTextField mFtpPortField;

    private JLabel mFtpUserTagLabel;
    private JTextField mFtpUserField;

    private JLabel mFtpPasswordTagLabel;
    private JPasswordField mFtpPasswordField;

    private JLabel mFtpFolderTagLabel;
    private JTextField mFtpFolderField;

    private JLabel mSMSFolderTagLabel;
    private JTextField mSMSFolderField;

    private JLabel mBackupFolderTagLabel;
    private JTextField mBackupFolderField;
    
    private JLabel mLoopIntervalTagLabel;
    private JTextField mLoopIntervalField;
    
    private JLabel mLogPropTagLabel;
    private JTextField mLogPropField;

    private JLabel mVerifyResultLabel;
    
    private MainFrame mCallbackFrame;

    /* Buttons */
    public JButton mVerifyConfigButton;
    public JButton mSaveConfigButton;
    public JButton mCancelButton;

    /* Ftp Config */
    @Autowired
    private FtpConfigProperties ftpConfigProperties;

    public ConfigFrame() {
    }

    private void initializeComponent(){
        this.setBounds(100, 100, 360, 360);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        /*Ftp address */
        mFtpAddressTagLabel = new JLabel("Ftp Address: ");
        mFtpAddressTagLabel.setBounds(26, 25, 100, 16);
        this.getContentPane().add(mFtpAddressTagLabel);
        
        mFtpAddressField = new JTextField();
        mFtpAddressField.setBounds(136, 25, 175, 16);
        mFtpAddressField.addKeyListener(this);
        this.getContentPane().add(mFtpAddressField);
        
        /*Ftp port */
        mFtpPortTagLabel = new JLabel("Ftp Port: ");
        mFtpPortTagLabel.setBounds(26, 50, 100, 16);
        this.getContentPane().add(mFtpPortTagLabel);
        
        mFtpPortField= new JTextField();
        mFtpPortField.setBounds(136, 50, 175, 16);
        mFtpPortField.addKeyListener(this);
        this.getContentPane().add(mFtpPortField);

        /*Ftp user */
        mFtpUserTagLabel = new JLabel("Ftp User: ");
        mFtpUserTagLabel.setBounds(26, 75, 100, 16);
        this.getContentPane().add(mFtpUserTagLabel);
        
        mFtpUserField= new JTextField();
        mFtpUserField.setBounds(136, 75, 175, 16);
        mFtpUserField.addKeyListener(this);
        this.getContentPane().add(mFtpUserField);

        /*Ftp password */
        mFtpPasswordTagLabel = new JLabel("Ftp Password: ");
        mFtpPasswordTagLabel.setBounds(26, 100, 100, 16);
        this.getContentPane().add(mFtpPasswordTagLabel);
        
        mFtpPasswordField= new JPasswordField();
        mFtpPasswordField.setBounds(136, 100, 175, 16);
        mFtpPasswordField.addKeyListener(this);
        this.getContentPane().add(mFtpPasswordField);

        /*Ftp Folder */
        mFtpFolderTagLabel = new JLabel("Ftp Folder: ");
        mFtpFolderTagLabel.setBounds(26, 125, 100, 16);
        this.getContentPane().add(mFtpFolderTagLabel);
        
        mFtpFolderField= new JTextField();
        mFtpFolderField.setBounds(136, 125, 175, 16);
        mFtpFolderField.addKeyListener(this);
        this.getContentPane().add(mFtpFolderField);

        /*SMS Folder */
        mSMSFolderTagLabel = new JLabel("SMS Folder: ");
        mSMSFolderTagLabel.setBounds(26, 150, 100, 16);
        this.getContentPane().add(mSMSFolderTagLabel);
        
        mSMSFolderField= new JTextField();
        mSMSFolderField.setBounds(136, 150, 175, 16);
        mSMSFolderField.addKeyListener(this);
        this.getContentPane().add(mSMSFolderField);

        /*Backup Folder */
        mBackupFolderTagLabel = new JLabel("Backup Folder: ");
        mBackupFolderTagLabel.setBounds(26, 175, 100, 16);
        this.getContentPane().add(mBackupFolderTagLabel);
        
        mBackupFolderField= new JTextField();
        mBackupFolderField.setBounds(136, 175, 175, 16);
        mBackupFolderField.addKeyListener(this);
        this.getContentPane().add(mBackupFolderField);

        /*Loop Interval */
        mLoopIntervalTagLabel = new JLabel("Loop Interval: ");
        mLoopIntervalTagLabel.setBounds(26, 200, 100, 16);
        this.getContentPane().add(mLoopIntervalTagLabel);
        
        mLoopIntervalField= new JTextField();
        mLoopIntervalField.setBounds(136, 200, 175, 16);
        mLoopIntervalField.addKeyListener(this);
        this.getContentPane().add(mLoopIntervalField);

        /* Log Config */
        mLogPropTagLabel = new JLabel("Log Config: ");
        mLogPropTagLabel.setBounds(26, 225, 100, 16);
        this.getContentPane().add(mLogPropTagLabel);
        
        mLogPropField = new JTextField();
        mLogPropField.setBounds(136, 225, 175, 16);
        mLogPropField.addKeyListener(this);
        this.getContentPane().add(mLogPropField);

        /* Button: verify */
        mVerifyConfigButton = new JButton("Verify");
        mVerifyConfigButton.setBounds(26, 260, 80, 35);
        mVerifyConfigButton.addActionListener(this);
        this.getContentPane().add(mVerifyConfigButton);
        
        /* Button: Save */
        mSaveConfigButton = new JButton("Save");
        mSaveConfigButton.setBounds(116, 260, 80, 35);
        mSaveConfigButton.addActionListener(this);
        mSaveConfigButton.setEnabled(false);
        this.getContentPane().add(mSaveConfigButton);

        /* Button: Cancel1 */
        mCancelButton = new JButton("Cancel");
        mCancelButton.setBounds(206, 260, 80, 35);
        mCancelButton.addActionListener(this);
        this.getContentPane().add(mCancelButton);

        /* Verify Result Label */
        mVerifyResultLabel = new JLabel();
        mVerifyResultLabel.setBounds(26, 300, 275, 16);
        this.getContentPane().add(mVerifyResultLabel);
    }

    /**
     * Init this
     */
    public void initialize() {
        initializeComponent();
        bindPropertiesToFields();
    }
    
    public void setCallbackFrame(MainFrame frame){
        this.mCallbackFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource() == this.mVerifyConfigButton){

            bindFieldsToProperties();

            if (ftpConfigProperties.verifyFtpConnection()) {
                mVerifyResultLabel.setText("Config integrity validated.");
                mSaveConfigButton.setEnabled(true);
            }else{
                mVerifyResultLabel.setText("Config not valid.");
                mSaveConfigButton.setEnabled(false);
            }
        }
        
        if (e.getSource() == this.mSaveConfigButton){

            /* Update mainframe properties and update the job settings */
            this.ftpConfigProperties.saveConfigProperties();
            this.mCallbackFrame.ftpConfigProperties.refreshConfigProperties();
            this.mCallbackFrame.initializeJob();

        }

        if (e.getSource() == this.mCancelButton){

            /* Reload properties before change */
            this.ftpConfigProperties.refreshConfigProperties();
            bindPropertiesToFields();
            this.setVisible(false);
            /* debgu*/
            /*debug*/ System.out.println(mFtpPasswordField.getText());

        }
                
    }

    private void bindFieldsToProperties(){
        ftpConfigProperties.setFtpAddress(this.mFtpAddressField.getText());
        ftpConfigProperties.setFtpPort(this.mFtpPortField.getText());
        ftpConfigProperties.setFtpUser(this.mFtpUserField.getText());
        ftpConfigProperties.setFtpPassword(this.mFtpPasswordField.getText());
        ftpConfigProperties.setFtpFolder(this.mFtpFolderField.getText());
        ftpConfigProperties.setSMSFolder(this.mSMSFolderField.getText());
        ftpConfigProperties.setBackupFolder(this.mBackupFolderField.getText());
        ftpConfigProperties.setLoopInterval(this.mLoopIntervalField.getText());
        ftpConfigProperties.setLogProperties(this.mLogPropField.getText());
    }

    private void bindPropertiesToFields(){
        mFtpAddressField.setText(ftpConfigProperties.getFtpAddress());
        mFtpPortField.setText(ftpConfigProperties.getFtpPort());
        mFtpUserField.setText(ftpConfigProperties.getFtpUser());
        mFtpPasswordField.setText(ftpConfigProperties.getFtpPassword());
        mFtpFolderField.setText(ftpConfigProperties.getFtpFolder());
        mSMSFolderField.setText(ftpConfigProperties.getSMSFolder());
        mBackupFolderField.setText(ftpConfigProperties.getBackupFolder());
        mLoopIntervalField.setText(ftpConfigProperties.getLoopInterval());
        mLogPropField.setText(ftpConfigProperties.getLogProperties());
    }

    @Override
    public void keyPressed(KeyEvent event){
        System.out.println("Key pressed");
        this.mSaveConfigButton.setEnabled(false);
    }

    @Override
    public void keyReleased(KeyEvent event){
        System.out.println("Key release");
        this.mSaveConfigButton.setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent event){
        System.out.println("Key type");
        this.mSaveConfigButton.setEnabled(false);
    }

}

