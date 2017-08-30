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
    public FtpConfigProperties ftpConfigProperties;

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
    private JButton mChangeConfigButton;
    
    public MainFrame() {
    }

    /**
     * Init this
     */
    public void initialize() {
        initializeJob();
        initializeConfigFrame();
        initializeMainFrame();
    }

    public void initializeJob() {
        /* Init sms send job */
        mJob.setConfig(ftpConfigProperties);
        mJob.init();
    }

    public void initializeConfigFrame() {
        /* init config Frame*/
        mConfigFrame.initialize();
        mConfigFrame.setCallbackFrame(this);
    }

    public void initializeMainFrame() {

        this.setBounds(100, 100, 360, 320);
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
        mStartButton.setBounds(26, 154, 300, 25);
        mStartButton.addActionListener(this);
        this.getContentPane().add(mStartButton);
        
        mStopButton = new JButton("Stop Send SMS");
        mStopButton.setBounds(26, 190, 300, 25);
        mStopButton.addActionListener(this);
        this.getContentPane().add(mStopButton);
        
        /* Change config button*/
        mChangeConfigButton = new JButton("Change Configuration");
        mChangeConfigButton.setBounds(26, 224, 300, 25);
        mChangeConfigButton.addActionListener(this);
        this.getContentPane().add(mChangeConfigButton);

        this.setResizable(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){

        System.out.println("In MainFrame actionPerformed.");
        
        if (e.getSource() == this.mStartButton){

            Thread thread = new Thread(this.mJob);
            thread.start();
            this.mJobStatusLabel.setText("job started.");

        }else if (e.getSource() == this.mStopButton){

            this.mJob.setActive(false);
            if (this.mJobStatusLabel.getText().compareTo("job started.") == 0 ){
                this.mJobStatusLabel.setText("job ended.");
            }
            
        }else if (e.getSource() == this.mChangeConfigButton){

            this.mConfigFrame.setVisible(true);

        }else{

            System.out.println("Callback to MainFrame");

        }

    }
    

}
