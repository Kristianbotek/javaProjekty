import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class ReflexGame extends JFrame implements ActionListener {
    
    final int SCREEN_WIDTH = 1200;
    final int SCREEN_HEIGHT = 1000;
    final int CUBE_SIZE = 150;
    JButton startButton;
    Random random;
    JButton button;
    JPanel buttonPanel;
    JLabel label;
    JLabel timerLabel;
    int x;
    int y;
    int score;
    Timer timer;
    Timer gameTimer;
    int secs = 5;
    int gameSecs = 15;
    
    ReflexGame() {
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secs--;
                label.setText(String.valueOf(secs));
                if(secs == 0) {
                    timer.stop();
                    game();
                }
            }
        });

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameSecs--;
                if(gameSecs == 0)  {
                    gameTimer.stop();
                    result();
                }
            }
        });
        
        random = new Random();
        x = random.nextInt(SCREEN_WIDTH - CUBE_SIZE);
        y = random.nextInt(SCREEN_HEIGHT - CUBE_SIZE);
        
        button = new JButton();
        button.setBounds(x,y,CUBE_SIZE,CUBE_SIZE);
        //Petr Sýkora je gay
        button.addActionListener(this);
        button.setBorderPainted(false);
        button.setBackground(new Color(0X7392B7));
        button.setVisible(true);
        
        startButton = new JButton();
        startButton.setText("START THE GAME");
        startButton.setBackground(new Color(0x8FABCE));
        startButton.setBorder(BorderFactory.createEtchedBorder());
        startButton.setFont(new Font("Comfortaa", Font.BOLD, 50));
        startButton.setFocusable(false);
        startButton.setPreferredSize(new Dimension(550,160));
        startButton.addActionListener(this);
        startButton.setVisible(true);
        
        label = new JLabel("Reflex game");
        label.setFont(new Font("Comfortaa", Font.BOLD, 90));
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVisible(true);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout(0,250));
        buttonPanel.add(label, BorderLayout.NORTH);
        buttonPanel.add(startButton, BorderLayout.CENTER);
        buttonPanel.setOpaque(false);
        buttonPanel.setVisible(true);
        
        this.setTitle("Reflex game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0xC5D5EA));
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,250));
        
        this.add(buttonPanel);
        this.setVisible(true);
    }
    
    public void game() {
        this.setLayout(null);
        this.remove(buttonPanel);
        this.add(button);
        this.repaint();
        gameTimer.start();
    }
    
    public void result() {
        this.remove(button);
        this.add(buttonPanel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,250));
        label.setText("Konec! Tvé skóre: " + score + "!");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton) {
            buttonPanel.remove(startButton);
            label.setText("5");
            this.repaint();
            timer.start();
        }
        if(e.getSource()==button) {
            score++;
            x = random.nextInt(SCREEN_WIDTH - CUBE_SIZE);
            y = random.nextInt(SCREEN_HEIGHT - CUBE_SIZE);
            button.setBounds(x,y,CUBE_SIZE,CUBE_SIZE);
            this.repaint();
        }
    }
    
}
