package com.bcm.app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
 
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

@Component
public class ConfigFrame extends JFrame implements ItemListener {

    /* Load Config Frame properties */
    private JLabel mFtpAddressTagLabel;
    private JLabel mFtpAddressLabel;
    // private JLabel mFtpPortTagLabel;
    // private JLabel mFtpPortLabel;
    // private JLabel mFtpUserTagLabel;
    // private JLabel mFtpUserLabel;
    // private JLabel mFtpPasswrodTagLabel;
    // private JLabel mFtpPasswordLabel;
    // more label to be added...
    private JCheckBox mFetchConfigCheckBox;
    private JButton mLoadConfirmButton;
    private JButton mLoadCancelButton;

    public ConfigFrame() {
        initialize();
    }

    /**
     * Init this
     */
    private void initialize() {
        this.setBounds(100, 100, 360, 360);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        /*Ftp address */
        mFtpAddressTagLabel = new JLabel("Ftp Address: ");
        mFtpAddressTagLabel.setBounds(26, 32, 100, 16);
        this.getContentPane().add(mFtpAddressTagLabel);
        
        mFtpAddressLabel = new JLabel("[...] ");
        mFtpAddressLabel.setBounds(136, 33, 175, 14);
        this.getContentPane().add(mFtpAddressLabel);
        
        /* Fetch Config from SQL server checkbox*/
        mFetchConfigCheckBox = new JCheckBox("Load Configuration from SQL Server");
        mFetchConfigCheckBox.setBounds(26, 70, 250, 16);
        mFetchConfigCheckBox.addItemListener(this);
        this.getContentPane().add(mFetchConfigCheckBox);
        
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.mFetchConfigCheckBox){
            if (e.getStateChange() == 1){ //checked
                try {
                    SQLiteConfig config = new SQLiteConfig();
                    // config.setReadOnly(true);   
                    config.setSharedCache(true);
                    config.enableRecursiveTriggers(true);

                    SQLiteDataSource ds = new SQLiteDataSource(config); 
                    ds.setUrl("jdbc:sqlite:sms.db");
                    Connection con = ds.getConnection();
                    //ds.setServerName("sample.db");
                    String sql = "select * from sms_properties";
                    Statement stat = null;
                    ResultSet rs = null;
                    stat = con.createStatement();
                    rs = stat.executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("spftpadd")+"\t"+rs.getString("spftpprt"));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                this.mFtpAddressLabel.setText("to checked");
            }
            if (e.getStateChange() != 1){ //unchecked
                this.mFtpAddressLabel.setText("empty");
            }
        }
    }   
}

