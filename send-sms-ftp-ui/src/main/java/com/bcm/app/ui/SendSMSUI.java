package com.bcm.app.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;

import com.bcm.app.engine.SendSMSJob;

public class SendSMSUI implements ActionListener{

    private SendSMSJob mJob;
    
    /* Main Frame properties */
    private JFrame mMainFrame;
    private JLabel mLastSentTimeTagLabel;
    private JLabel mLastSentTimeLabel;
    private JLabel mJobStatusTagLabel;
    private JLabel mJobStatusLabel;
    private JLabel mMomentLabel;
    private JButton mStartButton;
    private JButton mStopButton;
    private JButton mExportLogButton;
    private JButton mClearLogButton;
    private JButton mLoadConfigButton;
    private JButton mExitButton;
    
    /* Load Config Frame properties */
    private JFrame mLoadConfigFrame;
    private JLabel mFtpAddressTagLabel;
    private JLabel mFtpAddressLabel;
    // private JLabel mFtpPortTagLabel;
    // private JLabel mFtpPortLabel;
    // private JLabel mFtpUserTagLabel;
    // private JLabel mFtpUserLabel;
    // private JLabel mFtpPasswrodTagLabel;
    // private JLabel mFtpPasswordLabel;
    // more label to be added...
    private JButton mLoadConfirmButton;
    private JButton mLoadCancelButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SendSMSUI window = new SendSMSUI();
                    window.mMainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SendSMSUI() {          
    
        mJob = new SendSMSJob();
        mJob.setProperties("config.properties");
        
        initialize();
    }

    /**
     * Initialize all Frames.
     */
    private void initialize (){
        initializeMain();
        initializeLoadConfig();
    }
        
    /**
     * Init mMainFrame
     */
    private void initializeMain() {

        mMainFrame = new JFrame();
        mMainFrame.setBounds(100, 100, 360, 360);
        mMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mMainFrame.getContentPane().setLayout(null);
        
        /*Last sent time */
        mLastSentTimeTagLabel = new JLabel("Last Sent Time: ");
        mLastSentTimeTagLabel.setBounds(26, 32, 100, 16);
        mMainFrame.getContentPane().add(mLastSentTimeTagLabel);
        
        mLastSentTimeLabel = new JLabel("[...]");
        mLastSentTimeLabel.setBounds(136, 33, 175, 14);
        mMainFrame.getContentPane().add(mLastSentTimeLabel);
        
        /* Job status */
        mJobStatusTagLabel = new JLabel("Job Status: ");
        mJobStatusTagLabel.setBounds(26, 59, 100, 14);
        mMainFrame.getContentPane().add(mJobStatusTagLabel);
        
        mJobStatusLabel = new JLabel("[...]");
        mJobStatusLabel.setBounds(136, 59, 175, 14);
        mMainFrame.getContentPane().add(mJobStatusLabel);
        
        /* Moment status */
        mMomentLabel = new JLabel("MOMENT");
        mMomentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mMomentLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        mMomentLabel.setBounds(0, 84, 344, 48);
        mMainFrame.getContentPane().add(mMomentLabel);
        
        /* Button: Start and stop */
        mStartButton = new JButton("Start Send SMS");
        mStartButton.setBounds(26, 154, 125, 25);
        mStartButton.addActionListener(this);
        mMainFrame.getContentPane().add(mStartButton);
        
        mStopButton = new JButton("Stop Send SMS");
        mStopButton.setBounds(186, 154, 125, 25);
        mStopButton.addActionListener(this);
        mMainFrame.getContentPane().add(mStopButton);
        
        /* Button: log export and log clear */
        mExportLogButton = new JButton("Export Log File");
        mExportLogButton.setBounds(26, 188, 125, 25);
        mMainFrame.getContentPane().add(mExportLogButton);
        
        mClearLogButton = new JButton("Clear Log File");
        mClearLogButton.setBounds(186, 188, 125, 25);
        mMainFrame.getContentPane().add(mClearLogButton);
        
        /* Change config button*/
        mLoadConfigButton = new JButton("Change Configuration");
        mLoadConfigButton.setBounds(26, 224, 285, 25);
        mLoadConfigButton.addActionListener(this);
        mMainFrame.getContentPane().add(mLoadConfigButton);
        
        /* Exit button */
        mExitButton = new JButton("Exit");
        mExitButton.setBounds(26, 260, 285, 25);
        mMainFrame.getContentPane().add(mExitButton);
    }
    
    /**
     * Init mMainFrame
     */
    private void initializeLoadConfig() {
        mLoadConfigFrame = new JFrame();
        mLoadConfigFrame.setBounds(100, 100, 360, 360);
        mLoadConfigFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mLoadConfigFrame.getContentPane().setLayout(null);
        
        /*Last sent time */
        mFtpAddressTagLabel = new JLabel("Ftp Address: ");
        mFtpAddressTagLabel.setBounds(26, 32, 100, 16);
        mLoadConfigFrame.getContentPane().add(mFtpAddressTagLabel);
        
        /*Last sent time */
        mFtpAddressLabel = new JLabel("[...] ");
        mFtpAddressLabel.setBounds(136, 33, 175, 14);
        mLoadConfigFrame.getContentPane().add(mFtpAddressLabel);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if (e.getSource() == this.mStartButton){
            Thread thread = new Thread(this.mJob);
            thread.start();
            this.mJobStatusLabel.setText("job started.");
        }
        
        if (e.getSource() == this.mStopButton){
            this.mJob.setActive(false);
            this.mJobStatusLabel.setText("job ended.");
        }
                
        if (e.getSource() == this.mLoadConfigButton){
            this.mLoadConfigFrame.setVisible(true);
        }
    }
    
}
