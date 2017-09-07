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
import java.awt.Color;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcm.app.engine.SendSMSJob;
import com.bcm.app.engine.JobConfig;

/**
 * Class MainFrame is the main Graphic User Interface. It provides
 * buttons for user to control the SendSMSJob (Start/ Stop/ Change
 * Config)
 *
 */
@Component
public class MainFrame extends JFrame implements ActionListener {

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    private SendSMSJob mJob;

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    public JobConfig mJobConfig;

    /*
     * Bean get/autowire from the pool
     */
    @Autowired
    private ConfigFrame mConfigFrame;
    
    /* 
     * Main Frame properties 
     */
    private JLabel mLastSentTimeTagLabel;
    private JLabel mLastSentTimeLabel;
    private JLabel mJobStatusTagLabel;
    private JLabel mJobStatusLabel;
    private JLabel mMomentLabel;
    private JLabel mNameLabel;
    private JButton mStartButton;
    private JButton mStopButton;
    private JButton mChangeConfigButton;
    
    /**
     * Method initialize() initialize MainFrame, its components and 
     * the SendSMSJob member.
     *
     */
    public void initialize() {
        initializeJob();
        initializeConfigFrame();
        initializeMainFrame();
    }

    /**
     * [Private] Initialize SendSMSJob member
     *
     */
    private void initializeJob() {
        mJob.setConfig(mJobConfig);
        mJob.init();
    }

    /**
     * [Private] Initialize JComponents on ConfigFrame 
     *
     */
    private void initializeConfigFrame() {
        mConfigFrame.initialize();
        mConfigFrame.setCallbackFrame(this);
    }

    /**
     * [Private] Initialize JComponents on MainFrame 
     *
     */
    private void initializeMainFrame() {

        // Main Frame basic 
        this.setBounds(100, 100, 360, 320);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        //Last sent time 
        mLastSentTimeTagLabel = new JLabel("Last Sent Time: ");
        mLastSentTimeTagLabel.setBounds(26, 32, 100, 16);
        this.getContentPane().add(mLastSentTimeTagLabel);
        
        mLastSentTimeLabel = new JLabel();
        mLastSentTimeLabel.setBounds(136, 33, 175, 14);
        this.getContentPane().add(mLastSentTimeLabel);
        
        // Job status 
        mJobStatusTagLabel = new JLabel("Job Status: ");
        mJobStatusTagLabel.setBounds(26, 59, 100, 14);
        this.getContentPane().add(mJobStatusTagLabel);
        
        mJobStatusLabel = new JLabel();
        mJobStatusLabel.setBounds(136, 59, 175, 14);
        this.getContentPane().add(mJobStatusLabel);
        
        // Moment status 
        mMomentLabel = new JLabel();
        mMomentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mMomentLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        mMomentLabel.setBounds(0, 84, 344, 48);
        mMomentLabel.setForeground(Color.red);
        this.getContentPane().add(mMomentLabel);

        // Job Name
        String jobName = this.mJobConfig.getConfigEntry(JobConfig.JOB_NAME_PROPERTY);
        mNameLabel = new JLabel(jobName);
        Font biggerBoldFont = new Font(mNameLabel.getFont().getFontName(), Font.BOLD, mNameLabel.getFont().getSize() + 2);
        mNameLabel.setFont(biggerBoldFont);
        mNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mNameLabel.setBounds(0, 140, 344, 14);
        this.getContentPane().add(mNameLabel);
        
        // Button: Start and stop 
        mStartButton = new JButton("Start Send SMS");
        mStartButton.setBounds(26, 170, 300, 25);
        mStartButton.addActionListener(this);
        this.getContentPane().add(mStartButton);
        
        mStopButton = new JButton("Stop Send SMS");
        mStopButton.setBounds(26, 206, 300, 25);
        mStopButton.addActionListener(this);
        this.getContentPane().add(mStopButton);
        
        // Change config button
        mChangeConfigButton = new JButton("Change Configuration");
        mChangeConfigButton.setBounds(26, 240, 300, 25);
        mChangeConfigButton.addActionListener(this);
        this.getContentPane().add(mChangeConfigButton);

        this.setResizable(false);

        // Add status checker that updates "last sent time" and "moment"
        // by an interval of 1000(ms). 
        Thread statusChecker = new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    while (true){

                        if (MainFrame.this.mJob.isActive()){

                            //Update last sent time 
                            DateTime last = MainFrame.this.mJob.getLastSentDateTime();
                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            if (last != null){
                                MainFrame.this.mLastSentTimeLabel.setText(last.toString(fmt));
                            }

                            //Update moment
                            if (MainFrame.this.mJob.didIJustSentSomething()){
                                MainFrame.this.mMomentLabel.setForeground(Color.green);
                                MainFrame.this.mMomentLabel.setText("Sending...");
                                MainFrame.this.mJob.turnOffJustSentSomethingFlag();
                            }else{
                                MainFrame.this.mMomentLabel.setForeground(Color.red);
                                MainFrame.this.mMomentLabel.setText("Waiting SMS");
                            }

                        }

                        Thread.sleep(1000);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        statusChecker.start();
        
    }
    
    /**
     * Method refreshJob stops current job(if started) and updates
     * the config of the job. Note that it wont start the job again,
     * user need to click the start button to do so.
     *
     */
    public void refreshJob() {

        this.mJob.setActive(false);

        if (this.mJobStatusLabel.getText().compareTo("Job started.") == 0 ){
            this.mMomentLabel.setText("Stopped");
            this.mJobStatusLabel.setText("Job ended.");
            DateTime datetime = new DateTime();
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            this.mLastSentTimeLabel.setText(datetime.toString(fmt));
        }

        this.mJob.setConfig(mJobConfig);
        this.mJob.init();

    }
    /**
     * Method actionPerformed defines button actions
     *
     */
    @Override
    public void actionPerformed(ActionEvent e){

        // For Start Button
        if (e.getSource() == this.mStartButton){

            Thread thread = new Thread(this.mJob);
            thread.start();
            this.mMomentLabel.setText("Waiting SMS");
            this.mJobStatusLabel.setText("Job started.");

        }
       
        // For Stop Button
        if (e.getSource() == this.mStopButton){

            this.mJob.setActive(false);
            if (this.mJobStatusLabel.getText().compareTo("Job started.") == 0 ){
                this.mMomentLabel.setText("Stopped");
                this.mJobStatusLabel.setText("Job ended.");
            }
            
        }
       
        // For ChangeConfig Button
        if (e.getSource() == this.mChangeConfigButton){

            this.mConfigFrame.setVisible(true);

        }

    }

}
