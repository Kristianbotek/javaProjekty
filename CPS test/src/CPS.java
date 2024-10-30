import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.BreakIterator;

public class CPS extends JFrame implements ActionListener {
    
    JButton button;
    JLabel label;
    int clicks;
    boolean clicker = false;
    boolean onOff;
    Timer timer;
    Timer timerClicker;
    int i = 4;
    int j = 9;
    int result;
    String zvíře;
    
    CPS() {
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i != 0) {
                    label.setText(String.valueOf(i));
                    i--;
                } else {
                    timer.stop();
                    test();
                }
            }
        });

        timerClicker = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(j != 0) {
                    label.setText(String.valueOf(j));
                    j--;
                } else {
                    timerClicker.stop();
                    výsledek();
                }
            }
        });

        label = new JLabel("CPS TEST");
        label.setFont(new Font("Comfortaa", Font.PLAIN, 65));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(0xDDD5F3));
        label.setOpaque(true);
        
        button = new JButton();
        button.setText("START");
        button.addActionListener(this);
        button.setFont(new Font("Comfortaa", Font.BOLD, 80));
        button.setFocusable(false);
        button.setBackground(new Color(0xC0AFE2));
        
        this.setTitle("CPS TEST");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,800);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2,1));
        
        this.add(label);
        this.add(button);
        this.setVisible(true);
        
    }
    
    public void test() {
        button.setEnabled(true);
        button.setText("KLIKEJ!");
        clicker = true;
        label.setText("10");
        timerClicker.start();
    }
    
    public void výsledek() {
        button.setEnabled(false);
        clicker = false;
        result = clicks / 10;
        if (result <= 3) {
            zvíře = "lenochod";
        } else if (result > 3 && result <= 6) {
            zvíře = "koala";
        } else if (result > 6 && result <= 9) {
            zvíře = "pes";
        } else if (result > 9 && result <= 12) {
            zvíře = "kočka";
        } else if (result > 12 && result <= 15) {
            zvíře = "orel";
        } else if (result > 15 && result <= 20) {
            zvíře = "gepard";
        } else {
            zvíře = "kolibřík";
        }
        
        label.setText(result + " CPS");
        button.setText("Jsi jako " + zvíře + "!");
        button.setFont(new Font("Comfortaa", Font.BOLD, 55));
        button.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == button) {
            if(!clicker) {
                onOff = !onOff;
                if (onOff) {
                    button.setEnabled(false);
                    button.setText("ČEKEJ!");
                    label.setText("5");
                    timer.start();
                }
            } else {
                clicks++;
            }
        }
        
    }
    
}
