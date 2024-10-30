import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Stopwatch extends JFrame implements ActionListener, KeyListener {
    
    JButton button1;
    JButton button2;
    JPanel panelButtons;
    JLabel label;
    boolean onOff;
    double time;
    Timer timer;
    
    Stopwatch() {
        
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += 0.1;
                String timeString = String.format("%.1f", time);
                label.setText(timeString);
            }
        });
        
        button1 = new JButton();
        button1.setText("START");
        button1.setBackground(Color.lightGray);
        button1.setBorder(BorderFactory.createEtchedBorder());
        button1.setFocusable(false);
        button1.setFont(new Font("Comfortaa", Font.PLAIN, 28));
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setVerticalTextPosition(JButton.CENTER);
        button1.addActionListener(this);
        button1.setPreferredSize(new Dimension(220,115));

        button2 = new JButton();
        button2.setText("RESET");
        button2.setBackground(Color.lightGray);
        button2.setBorder(BorderFactory.createEtchedBorder());
        button2.setFocusable(false);
        button2.setFont(new Font("Comfortaa", Font.PLAIN, 28));
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setVerticalTextPosition(JButton.CENTER);
        button2.addActionListener(this);
        button2.setPreferredSize(new Dimension(220,115));

        panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelButtons.setBackground(new Color(0xE3E3E3));
        panelButtons.add(button1);
        panelButtons.add(button2);
        
        label = new JLabel("0,0");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Comfortaa", Font.BOLD, 75));
        
        this.setTitle("Stopwatch");
        this.setBackground(new Color(0xFBFBFB));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(600,700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        
        this.addKeyListener(this);
        this.add(label, BorderLayout.CENTER);
        this.add(panelButtons, BorderLayout.SOUTH);
        
    }
    
    public void start() {
        timer.start();
        button1.setText("STOP");
    }
    
    public void stop() {
        timer.stop();
        button1.setText("START");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==button1) {
            onOff = !onOff;
            if(onOff) {
                start();
            } else {
                stop();
            }
            
        }
        
        if(e.getSource()==button2) {
            if(onOff) {
                stop();
                onOff = !onOff;
            }
            label.setText("0,0");
            time = 0;
        }
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            onOff = !onOff;
            if(onOff) {
                start();
            } else {
                stop();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            if(onOff) {
                stop();
                onOff = !onOff;
            }
            label.setText("0,0");
            time = 0;
        }
        
    }
    
    //NEPOTŘEBUJU    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
