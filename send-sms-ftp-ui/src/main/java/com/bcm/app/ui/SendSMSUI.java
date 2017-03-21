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
    private JFrame mFrame;
    private JLabel mLastSentTimeTagLabel;
    private JLabel mLastSentTimeLabel;
    private JLabel mJobStatusTagLabel;
    private JLabel mJobStatusLabel;
    private JLabel mMomentLabel;
    private JButton mStartButton;
    private JButton mStopButton;
    private JButton mExportLogButton;
    private JButton mClearLogButton;
    private JButton mChangeConfigButton;
    private JButton mExitButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SendSMSUI window = new SendSMSUI();
                    window.mFrame.setVisible(true);
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
        initialize();
    }

    /**
     * Initialize the contents of the mFrame.
     */
    private void initialize() {
        
        mJob = new SendSMSJob();
        mJob.setProperties("config.properties");
        
        mFrame = new JFrame();
        mFrame.setBounds(100, 100, 360, 360);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.getContentPane().setLayout(null);
        
        /*Last sent time */
        mLastSentTimeTagLabel = new JLabel("Last Sent Time: ");
        mLastSentTimeTagLabel.setBounds(26, 32, 100, 16);
        mFrame.getContentPane().add(mLastSentTimeTagLabel);
        
        mLastSentTimeLabel = new JLabel("[...]");
        mLastSentTimeLabel.setBounds(136, 33, 175, 14);
        mFrame.getContentPane().add(mLastSentTimeLabel);
        
        /* Job status */
        mJobStatusTagLabel = new JLabel("Job Status: ");
        mJobStatusTagLabel.setBounds(26, 59, 100, 14);
        mFrame.getContentPane().add(mJobStatusTagLabel);
        
        mJobStatusLabel = new JLabel("[...]");
        mJobStatusLabel.setBounds(136, 59, 175, 14);
        mFrame.getContentPane().add(mJobStatusLabel);
        
        /* Moment status */
        mMomentLabel = new JLabel("MOMENT");
        mMomentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mMomentLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        mMomentLabel.setBounds(0, 84, 344, 48);
        mFrame.getContentPane().add(mMomentLabel);
        
        /* Button: Start and stop */
        mStartButton = new JButton("Start Send SMS");
        mStartButton.setBounds(26, 154, 125, 25);
        mStartButton.addActionListener(this);
        mFrame.getContentPane().add(mStartButton);
        
        mStopButton = new JButton("Stop Send SMS");
        mStopButton.setBounds(186, 154, 125, 25);
        mStopButton.addActionListener(this);
        mFrame.getContentPane().add(mStopButton);
        
        /* Button: log export and log clear */
        mExportLogButton = new JButton("Export Log File");
        mExportLogButton.setBounds(26, 188, 125, 25);
        mFrame.getContentPane().add(mExportLogButton);
        
        mClearLogButton = new JButton("Clear Log File");
        mClearLogButton.setBounds(186, 188, 125, 25);
        mFrame.getContentPane().add(mClearLogButton);
        
        /* Change config button*/
        mChangeConfigButton = new JButton("Change Configuration");
        mChangeConfigButton.setBounds(26, 224, 285, 25);
        mFrame.getContentPane().add(mChangeConfigButton);
        
        /* Exit button */
        mExitButton = new JButton("Exit");
        mExitButton.setBounds(26, 260, 285, 25);
        mFrame.getContentPane().add(mExitButton);
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
    }
    
}
