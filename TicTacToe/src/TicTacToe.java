import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.List;
import java.util.*;

public class TicTacToe extends JFrame implements ActionListener {
    
    JButton[] buttons = new JButton[9];
    JPanel panel;
    JLabel label;
    boolean player;
    ArrayList<Integer> xMoves = new ArrayList<>();
    ArrayList<Integer> oMoves = new ArrayList<>();
    List<List<Integer>> win = Arrays.asList(
            Arrays.asList(0,1,2),
            Arrays.asList(3,4,5),
            Arrays.asList(6,7,8),
            Arrays.asList(0,3,6),
            Arrays.asList(1,4,7),
            Arrays.asList(2,5,8),
            Arrays.asList(0,4,8), 
            Arrays.asList(2,4,6)
    );
    
    TicTacToe() {
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        
        label = new JLabel("TIC TAC TOE");
        label.setFont(new Font("Comfortaa", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.white);

        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setBackground(Color.lightGray);
            buttons[i].setFont(new Font("Comfortaa", Font.BOLD, 60));
            panel.add(buttons[i]);
        }
        
        this.setTitle("Tic Tac Toe");
        this.setSize(650,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        this.add(panel, BorderLayout.CENTER);
        this.add(label, BorderLayout.NORTH);
        
    }
    
    public void check() {
        for(List<Integer> list : win) {
            if(xMoves.containsAll(list)) {
                label.setText("PLAYER X WON!");
                for(JButton button : buttons) {
                    button.setEnabled(false);
                }
                for(Integer i : list) {
                    buttons[i].setEnabled(true);
                    buttons[i].setForeground(Color.green);
                }
            } else if(oMoves.containsAll(list)) {
                label.setText("PLAYER O WON!");
                for(JButton button : buttons) {
                    button.setEnabled(false);
                }
                for(Integer i : list) {
                    buttons[i].setEnabled(true);
                    buttons[i].setForeground(Color.green);
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                player = !player;
                if(!player) {
                    buttons[i].setText("O");
                    label.setText("PLAYER'S X TURN");
                    oMoves.add(i);
                } else {
                    buttons[i].setText("X");
                    label.setText("PLAYER'S O TURN");
                    xMoves.add(i);
                }
                buttons[i].removeActionListener(this);
                this.repaint();
                check();
            }
        }
    }
    
}
