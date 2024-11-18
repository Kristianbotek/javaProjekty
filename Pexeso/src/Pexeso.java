//|----------Importy----------|
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class Pexeso extends JFrame implements ActionListener {

    //|----------Deklarace swing komponenůtu a variables----------|
    JPanel cardsPanel;
    JButton[] cards;
    JLabel label;
    JLabel scoreLabel;

    int size;
    boolean[] flip;
    boolean player;
    String player1;
    String player2;
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    ArrayList<Integer> nums;
    ArrayList<Integer> matches = new ArrayList<>();

    //|--------------------Constructor--------------------|
    Pexeso(int size, String player1, String player2) {
        this.size = size;
        this.player1 = player1;
        this.player2 = player2;
        flip = new boolean[size * size];

        nums = new ArrayList<>(size * size);
        for(int i = 0; i < size*size/2; i++) {
            nums.add(i);
            nums.add(i);
        }
        Collections.shuffle(nums);

        //|----------Labely----------|
        label = new JLabel("Hraje hráč " + player1);
        label.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 40f));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(new Color(0xD4BEE4));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        scoreLabel = new JLabel(player1 + ": 0  |  " + player2 + ": 0");
        scoreLabel.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 30f));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        //|----------Panel----------|
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(size, size,10,10));
        cardsPanel.setBackground(new Color(0xD4BEE4));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //|----------Button----------|
        cards = new JButton[size * size];
        for(int i = 0; i < cards.length; i++) {
            cards[i] = new JButton();
            cards[i].setFocusable(false);
            cards[i].setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 30f));
            cards[i].setBackground(new Color(0xF0F0F0));
            cards[i].setBorder(BorderFactory.createRaisedBevelBorder());
            cards[i].addActionListener(this);
            cardsPanel.add(cards[i]);
        }

        //|----------JFrame----------|
        this.setTitle("Pexeso");
        this.setResizable(false);
        this.setSize(750,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(0xEEEEEE));
        this.setVisible(true);
        
        this.add(cardsPanel, BorderLayout.CENTER);
        this.add(label, BorderLayout.NORTH);
        this.add(scoreLabel, BorderLayout.SOUTH);
    }

//|--------------------FUNKCE--------------------|
    //- časová funkce, která udělá delay po zvednutí dvou karet, které nematchujou.
    Timer timer = new Timer(750, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < flip.length; i++) {
                if(cards[i].isEnabled()) {
                    flip[i] = false;
                }
            }
            cardFlip();
            playerCheck();
            timer.stop();
        }
    });
    
    //- funkce, která "otočí" tu kartu
    public void cardFlip() {
        for(int i = 0; i < cards.length; i++) {
            if(!flip[i] && cards[i].isEnabled()) {
                cards[i].setText("");
            } else {
                cards[i].setText(String.valueOf(nums.get(i)));
            }
        }
    }

    //- tahle funkce je velice důležitá, ta kontroulje, jestli karty matchují.
    //- taky nám pak přidává skóre.
    public void matchCheck() {
        matches.clear();
        for(int i = 0; i < flip.length; i++) {
            if(flip[i]) {
                matches.add(i);
            }
        }
        //- tohle se by se popisovalo dlouho, ale ve zkratce, je tohle jedna z "hlavních" mechanik té hry.
        if(matches.size() == 2) {
            int first = matches.get(0);
            int second = matches.get(1);
            if(nums.get(first).equals(nums.get(second))) {
                cards[first].setEnabled(false);
                cards[second].setEnabled(false);
                flip[first] = false;
                flip[second] = false;
                if(!player) {
                    scorePlayer1++;
                    scoreLabel.setText(player1 + ": " + scorePlayer1 + "  |  " + player2 + ": " + scorePlayer2);
                } else {
                    scorePlayer2++;
                    scoreLabel.setText(player1 + ": " + scorePlayer1 + "  |  " + player2 + ": " + scorePlayer2);
                }
                winnerCheck();
            } else {
                //- pokud ty dvě karty nematchují, tak se aktivuje, již zmíněná časová funkce.
                timer.start();
            }
        }
    }

    //- a tahle funkce pak kontroluje a pak nám píše, který hráč je na řadě.
    public void playerCheck() {
        if(matches.size() == 2) {
            player = !player;
            if(!player) {
                label.setText("Hraje hráč " + player1);
            } else {
                label.setText("Hraje hráč " + player2);
            }
        }
    }

    //- a pak poslední funkce. Tahle nám kontroluje výherce a výhru. A pak nám to i vypíšu výherce, pak nás to hodí zpátky do StartMenu. =]
    public void winnerCheck() {
        boolean allMatched = true;
        for(int i = 0; i < cards.length; i++) {
            if(cards[i].isEnabled()) {
                allMatched = false;
                break;
            }
        }
        //- a tady máme zápis výherce.
        if(allMatched) {
            if(scorePlayer1 > scorePlayer2) {
                JOptionPane.showMessageDialog(this, "Gratuluju! Vyhrál hráč " + player1);
            } else if(scorePlayer1 < scorePlayer2) {
                JOptionPane.showMessageDialog(this, "Gratuluju! Vyhrál hráč " + player2);
            } else {
                JOptionPane.showMessageDialog(this, "Byla to remíza!");
            }
            //- tímhle resetujeme hru a půjdeme zpátky do menu.
            new StartMenu();
            this.dispose();
        }
    }

    //- ActionListener funkce, která se zaktivuje při kliku na tlačítko. Bez ní by tahle hra vůbec nebyla.
    @Override
    public void actionPerformed(ActionEvent e) {
        //- když je ta "pauza", tak abych nemohl klikat na ostatní kartičky.
        if(timer.isRunning()) {
            return;
        }
        for(int i = 0; i < cards.length; i++) { 
            if(e.getSource() == cards[i] && !flip[i] && cards[i].isEnabled()) {
                flip[i] = true;
                break;
            }
        }
        //- tady pak spouštím ty všechny funkce. =]]]
        //- ale ne všechny jsou tady, nějaký jsou v ostaních funkcích.
        cardFlip();
        matchCheck();
    }
    //|----------NEZAPOMEŇTE SE KOUKNOUT DO MAINU!----------|
}

