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

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcm.app.engine.SendSMSJob;
import com.bcm.app.engine.FtpConfigProperties;

@Component
public class MainFrame extends JFrame implements ActionListener {

    @Autowired
    private SendSMSJob mJob;

    @Autowired
    private FtpConfigProperties ftpConfigProperties;

    @Autowired
    private ConfigFrame mConfigFrame;
    
    /* Main Frame properties */
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
    
    public MainFrame() {
    }

    /**
     * Init this
     */
    public void initialize() {
        /* Init sms send job */
        mJob.setFtpAddress(ftpConfigProperties.getFtpAddress());
        mJob.setFtpPort(Integer.parseInt(ftpConfigProperties.getFtpPort()));
        mJob.setFtpUser(ftpConfigProperties.getFtpUser());
        mJob.setFtpPassword(ftpConfigProperties.getFtpPassword());
        mJob.setFtpFolder(ftpConfigProperties.getFtpFolder());
        mJob.setSMSFolder(ftpConfigProperties.getSMSFolder());
        mJob.setBackupFolder(ftpConfigProperties.getBackupFolder());
        mJob.setLoopInterval(Integer.parseInt(ftpConfigProperties.getLoopInterval()));
        mJob.setLogProperties(ftpConfigProperties.getLogProperties());
        mJob.init();

        /* init config Frame*/
        mConfigFrame.initialize();

        this.setBounds(100, 100, 360, 360);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        
        /*Last sent time */
        mLastSentTimeTagLabel = new JLabel("Last Sent Time: ");
        mLastSentTimeTagLabel.setBounds(26, 32, 100, 16);
        this.getContentPane().add(mLastSentTimeTagLabel);
        
        mLastSentTimeLabel = new JLabel("[...]");
        mLastSentTimeLabel.setBounds(136, 33, 175, 14);
        this.getContentPane().add(mLastSentTimeLabel);
        
        /* Job status */
        mJobStatusTagLabel = new JLabel("Job Status: ");
        mJobStatusTagLabel.setBounds(26, 59, 100, 14);
        this.getContentPane().add(mJobStatusTagLabel);
        
        mJobStatusLabel = new JLabel("[...]");
        mJobStatusLabel.setBounds(136, 59, 175, 14);
        this.getContentPane().add(mJobStatusLabel);
        
        /* Moment status */
        mMomentLabel = new JLabel("MOMENT");
        mMomentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mMomentLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        mMomentLabel.setBounds(0, 84, 344, 48);
        this.getContentPane().add(mMomentLabel);
        
        /* Button: Start and stop */
        mStartButton = new JButton("Start Send SMS");
        mStartButton.setBounds(26, 154, 125, 25);
        mStartButton.addActionListener(this);
        this.getContentPane().add(mStartButton);
        
        mStopButton = new JButton("Stop Send SMS");
        mStopButton.setBounds(186, 154, 125, 25);
        mStopButton.addActionListener(this);
        this.getContentPane().add(mStopButton);
        
        /* Button: log export and log clear */
        mExportLogButton = new JButton("Export Log File");
        mExportLogButton.setBounds(26, 188, 125, 25);
        this.getContentPane().add(mExportLogButton);
        
        mClearLogButton = new JButton("Clear Log File");
        mClearLogButton.setBounds(186, 188, 125, 25);
        this.getContentPane().add(mClearLogButton);
        
        /* Change config button*/
        mLoadConfigButton = new JButton("Change Configuration");
        mLoadConfigButton.setBounds(26, 224, 285, 25);
        mLoadConfigButton.addActionListener(this);
        this.getContentPane().add(mLoadConfigButton);
        
        /* Exit button */
        mExitButton = new JButton("Exit");
        mExitButton.setBounds(26, 260, 285, 25);
        this.getContentPane().add(mExitButton);
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

        if (e.getSource() == this.mExitButton){
            this.dispose();
        }
                
        if (e.getSource() == this.mLoadConfigButton){
            this.mConfigFrame.setVisible(true);
        }
    }
    

}
