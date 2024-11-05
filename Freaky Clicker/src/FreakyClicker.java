import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;

public class FreakyClicker extends JFrame implements ActionListener {
    
    JLabel scoreLabel;
    JLabel michal;
    JLabel sign;
    JButton shopButton;
    JPanel mainPanel;
    JPanel shopPanel;
    JPanel skinsShop;
    JPanel abilitiesShop;
    JPanel insideShop;
    Timer timer;
    Action admin;
    Action secret;
    Image resized;
    ImageIcon original;
    String[] skins = {"mich1.png","blond1.png","beard1.png","petr.png","honz.png", "old1.png","adam.png", "final.png"};
    String[] abilities = {"Petrův toast","Honzův rage", "Sol's RNG s Adamem"};
    String[] toolTips = {"Petrův toast ti dodá energii a výdrž. Tvůj klik má teď sílu 5 kliknutí! Otevře si při 500 kliknutí.","Honza zuří! Michal mu zase ukradl penál, Honzův vztek je nakažlivý. Tvůj klik má teď sílu 10 kliknutí! Otevře si při 5000 kliknutí.","Adam si chce s tebou zahrát Sol's RNG a bohužel jsi propadl gamblení... máš 50% šanci na to si zdvojit nebo vymazat úplně skóre! Otevře si při 50000 kliknutí."};
    String[] toolTipsSkins = {"Defaultní Michal","Blondátý Michal - Otevře se na 100 kliknutí.","Vousatý Michal - Otevře se na 500 kliknutí.","Freak - Otevře se na 1000 kliknutí.","Lineární graf - Otevře se na 2500 kliknutí.","Starý Michal - Otevře se na 5000 kliknutí.","Forbidden Michal - Otevře se na 10000 kliknutí.", "FINAL BOSS MICHAL - ???"};
    JButton[] buttons = new JButton[skins.length];
    JButton[] buttons2 = new JButton[abilities.length];    
    ImageIcon icon1 = new ImageIcon("pics/mich1.png");
    ImageIcon icon2 = new ImageIcon("pics/mich2.png");
    Random random;
    
    int currentSkin;
    int clicks;
    boolean shop;
    int clickBonus = 1;
    
    FreakyClicker() {
        
        timer = new Timer(750, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                michal.setIcon(icon1);
                timer.stop();
            }
        });
        
        scoreLabel = new JLabel("Clicks: 0");
        scoreLabel.setFont(new Font("Comfortaa", Font.BOLD, 70));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        michal = new JLabel();
        michal.setIcon(icon1);
        michal.setAlignmentX(Component.CENTER_ALIGNMENT);
        michal.setFocusable(true);
        michal.setVisible(true);
        michal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clicks += clickBonus;
                scoreLabel.setText("Clicks: " + clicks);
                michal.setIcon(icon2);
                timer.start();
                check();
                switch(currentSkin) {
                    case 3:
                        playSound("Petr");
                        break;
                    case 4:
                        playSound("Honza");
                        break;
                    case 6:
                        playSound("Adam");
                        break;
                    case 5:
                        playSound("Michal");
                        break;
                    default:
                        break;
                }
            }
        });
        
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0xB4D4FF));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        mainPanel.add(Box.createRigidArea(new Dimension(0,60)));
        mainPanel.add(scoreLabel);
        mainPanel.add(michal);
        mainPanel.setVisible(true);
        
        shopButton = new JButton();
        shopButton.setText("OBCHOD");
        shopButton.setFont(new Font("Comfortaa", Font.BOLD, 55));
        shopButton.setFocusable(false);
        shopButton.setBackground(new Color(0xA5BDBA));

        shopButton.setPreferredSize(new Dimension(320,120 ));
        shopButton.addActionListener(this);

        sign = new JLabel("Obchod");
        sign.setFont(new Font("Comfortaa", Font.BOLD, 70));
        sign.setHorizontalAlignment(SwingConstants.CENTER); // Centers text horizontally
        sign.setVerticalAlignment(SwingConstants.CENTER);   // Centers text vertically


        shopPanel = new JPanel();
        shopPanel.setBackground(new Color(0x8BA4C2));
        shopPanel.setVisible(true);
        shopPanel.add(shopButton);
        
        skinsShop = new JPanel();
        skinsShop.setLayout(new GridLayout(2,0,0,0));
        skinsShop.setBackground(new Color(0xB4D4FF));
        for(int i = 0; i < (skins.length); i++) {
            buttons[i] = new JButton();
            buttons[i].setEnabled(false);
            buttons[0].setEnabled(true);
            if(!buttons[i].isEnabled()) {
                original = new ImageIcon("pics/lock.png");
            } else {
                original = new ImageIcon("pics/" + skins[i]);
            }
            resized = original.getImage().getScaledInstance(140,140, Image.SCALE_SMOOTH);
            buttons[i].setIcon(new ImageIcon(resized));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(0xced0ce));
            buttons[i].setToolTipText(toolTipsSkins[i]);
            buttons[i].addActionListener(this);
            skinsShop.add(buttons[i]);
        }
        
        abilitiesShop = new JPanel();
        abilitiesShop.setLayout(new GridLayout(1,3,0,50));
        abilitiesShop.setBackground(new Color(0xB4D4FF));
        for(int j = 0; j < (abilities.length); j++) {
            buttons2[j] = new JButton();
            buttons2[j].setText(abilities[j]);
            buttons2[j].setFocusable(false);
            buttons2[j].setToolTipText(toolTips[j]);
            buttons2[j].setBackground(new Color(0xced0ce));
            buttons2[j].setEnabled(false);
            buttons2[j].setPreferredSize(new Dimension(175, 175));
            buttons2[j].addActionListener(this);
            abilitiesShop.add(buttons2[j]);
        }
        
        insideShop = new JPanel();
        insideShop.setLayout(new BorderLayout());
        insideShop.setBackground(new Color(0xB4D4FF));
        insideShop.add(abilitiesShop, BorderLayout.SOUTH);
        insideShop.add(skinsShop, BorderLayout.CENTER);
        
        this.setTitle("Freaky Clicker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,850);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("pics/icon.jpg");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(0xB4D4FF));
        
        this.setVisible(true);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(shopPanel, BorderLayout.SOUTH);
        
        admin = new Admin();
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("shift G"), "admin");
        mainPanel.getActionMap().put("admin", admin);
        secret = new Secret();
        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control shift L"), "secret");
        mainPanel.getActionMap().put("secret", secret);
        
    }
    
    public void setButtonIcon(JButton button, String skin) {
        ImageIcon original = new ImageIcon("pics/" + skin);
        Image resized = original.getImage().getScaledInstance(140,140, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resized));
    }
    
    public void check() {
        if(clicks >= 500 && clicks <= 2000) {
            buttons2[0].setEnabled(true);
        } else if(clicks >= 2000 && clicks <= 10000) {
            buttons2[0].setEnabled(true);
            buttons2[1].setEnabled(true);
        } else if(clicks >= 10000) {
            buttons2[0].setEnabled(true);
            buttons2[1].setEnabled(true);
            buttons2[2].setEnabled(true);
        }

        if(clicks >= 100 && clicks <= 500) {
            buttons[1].setEnabled(true);
            setButtonIcon(buttons[1], skins[1]);
        } else if (clicks >= 500 && clicks <= 1000){           
            buttons[2].setEnabled(true);
            setButtonIcon(buttons[2], skins[2]);
        } else if (clicks >= 1000 && clicks <= 2500){
            buttons[3].setEnabled(true);
            setButtonIcon(buttons[3], skins[3]);
        } else if (clicks >= 2500 && clicks <= 5000){
            buttons[4].setEnabled(true);
            setButtonIcon(buttons[4], skins[4]);
        } else if (clicks >= 5000 && clicks <= 10000){
            buttons[5].setEnabled(true);
            setButtonIcon(buttons[5], skins[5]);
        } else if (clicks >= 10000){
            buttons[6].setEnabled(true);
            setButtonIcon(buttons[6], skins[6]);
            for (int i = 1; i <= 5; i++) {
                buttons[i].setEnabled(true);
                setButtonIcon(buttons[i], skins[i]);
            }
        } 
        
    }

    public void playSound(String jméno) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/"+jméno+".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NASTALA CHYBA PŘI ZVUKU");
        }
    }
    
    public void obchod() {
        this.remove(mainPanel);
        this.add(sign, BorderLayout.NORTH);
        this.add(insideShop, BorderLayout.CENTER);
        
        this.repaint();
    }
    
    public void back() {
        this.remove(insideShop);
        this.remove(sign);
        this.add(mainPanel, BorderLayout.CENTER);

        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==shopButton) {
            shop = !shop;
            if(!shop) {
                shopButton.setText("OBCHOD");
                back();
            } else {
                shopButton.setText("ZPĚT");
                obchod();
            } 
        }
        
        for(int i = 0; i < skins.length; i++) {
            if(e.getSource()==buttons[i]) {
                icon1 = new ImageIcon("pics/" + skins[i]);
                icon2 = new ImageIcon("pics/" + skins[i].replace("1","2"));
                michal.setIcon(icon1);
                currentSkin = i;
            }
        }
        
        if(e.getSource()==buttons2[0]) {
            if(clickBonus == 1) {
                clickBonus = 5;
                buttons2[0].setEnabled(false);
                buttons2[0].setText(abilities[0] + " KOUPENO!");
            }
        }
        
        if(e.getSource()==buttons2[1]) {
            if(clickBonus <= 5) {
                clickBonus = 10;
                buttons2[0].setEnabled(false);
                buttons2[0].setText(abilities[0] + " KOUPENO!");
                buttons2[1].setEnabled(false);
                buttons2[1].setText(abilities[1] + " KOUPENO!");
            }
        }
        
        if(e.getSource()==buttons2[2]) {
            random = new Random();
            int chance = random.nextInt(99)+1;
            System.out.println(chance);
            if(chance >= 50) {
                clicks *= 2;
            } else {
                clicks = 0;
            }
            scoreLabel.setText("Clicks:" + clicks);
            this.repaint();
        }
        
    }
    
    public class Admin extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            clicks += 100000000;
            scoreLabel.setText("Clicks: " + clicks);
        }
    }
    
    public class Secret extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttons[7].setEnabled(true);
            setButtonIcon(buttons[7], skins[7]);
    }
    
    }
}
