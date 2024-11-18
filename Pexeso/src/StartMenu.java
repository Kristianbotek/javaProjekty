//|----------Importy----------|
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class StartMenu extends JFrame implements ActionListener {

    //|----------Deklarace swing komponenůtu a variables----------|
    JTextField name1;
    JTextField name2;
    JButton startButton;
    JButton sizeA;
    JButton sizeB;
    JButton sizeC;
    JPanel sizePanel;
    JPanel namePanel;
    JPanel settingsPanel;
    JLabel mainLabel;
    JLabel nameLabel1;
    JLabel nameLabel2;
    JTextArea rulesArea;
    TitledBorder titledBorder;

    int size = 0;
    String player1;
    String player2;

    //|--------------------Constructor--------------------|
//- Tady se pak děje všechno. Vytvářím ty komponenty a dole pod tím, jsou funkce.
    StartMenu() {

        //|----------Labely----------|
        mainLabel = new JLabel("Pexeso", SwingConstants.CENTER);
        mainLabel.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 65f));
        mainLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainLabel.setOpaque(true);
        mainLabel.setBackground(new Color(0xD4BEE4));

        nameLabel1 = new JLabel("Jméno hráče 1:");
        nameLabel1.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 20f));

        nameLabel2 = new JLabel("Jméno hráče 2:");
        nameLabel2.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 20f));

        titledBorder = BorderFactory.createTitledBorder("Vyberte si množství karet:");
        titledBorder.setTitleFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 20f));

        //|----------TextFieldy a TextArea----------|
        name1 = new JTextField(15);
        name1.setFont(FontUtils.loadCustomFont("Comfortaa-Regular", Font.PLAIN, 20f));

        name2 = new JTextField(15);
        name2.setFont(FontUtils.loadCustomFont("Comfortaa-Regular", Font.PLAIN, 20f));

        rulesArea = new JTextArea(
                "Pravidla hry:\n" +
                        "- Hráči se střídají v odkrývání karet\n" +
                        "- Cíl: najít co nejvíce párů\n" +
                        "- Pokud hráč najde pár, hraje dále\n" +
                        "- Vyhrává hráč s největším počtem párů."
        );
        rulesArea.setFont(FontUtils.loadCustomFont("Comfortaa-Regular", Font.PLAIN, 20f));
        rulesArea.setLineWrap(true);
        rulesArea.setWrapStyleWord(true);
        rulesArea.setBackground(getContentPane().getBackground());

        //|----------Buttony----------|
        startButton = new JButton("START");
        startButton.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 55f));
        startButton.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        startButton.setFocusable(false);
        startButton.setBackground(new Color(0xD4BEE4));
        startButton.addActionListener(this);

        sizeA = new JButton("4x4");
        sizeA.setPreferredSize(new Dimension(100, 50));
        sizeA.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 30f));
        sizeA.addActionListener(this);
        sizeA.setFocusable(false);

        sizeB = new JButton("5x5");
        sizeB.setPreferredSize(new Dimension(100, 50));
        sizeB.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 30f));
        sizeB.addActionListener(this);
        sizeB.setFocusable(false);

        sizeC = new JButton("6x6");
        sizeC.setPreferredSize(new Dimension(100, 50));
        sizeC.setFont(FontUtils.loadCustomFont("Comfortaa-Bold", Font.BOLD, 30f));
        sizeC.addActionListener(this);
        sizeC.setFocusable(false);

        //|----------Panely----------|
        sizePanel = new JPanel();
        sizePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20 ,10));
        sizePanel.setBorder(titledBorder);
        sizePanel.add(sizeA);
        sizePanel.add(sizeB);
        sizePanel.add(sizeC);

        namePanel = new JPanel(new GridLayout(2, 2, 20, 10));
        namePanel.add(nameLabel1);
        namePanel.add(name1);
        namePanel.add(nameLabel2);
        namePanel.add(name2);

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        settingsPanel.add(namePanel, BorderLayout.NORTH);
        settingsPanel.add(sizePanel, BorderLayout.CENTER);
        settingsPanel.add(rulesArea, BorderLayout.SOUTH);

        //|----------JFrame----------|
        this.setTitle("Pexeso");
        this.setResizable(false);
        this.setSize(750,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(20,20));
        this.getContentPane().setBackground(new Color(0xEEEEEE));
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.add(startButton, BorderLayout.SOUTH);
        this.add(settingsPanel, BorderLayout.CENTER);
        this.add(mainLabel, BorderLayout.NORTH);

    }
    //|--------------------FUNKCE--------------------|    
    //- funkce, která změní barvu tlačítka po vybrání toho konkrétního tlačítka/velikosti.
    public void sizeButtonColor(JButton sizeButton) {
        sizeA.setBackground(null);
        sizeB.setBackground(null);
        sizeC.setBackground(null);

        sizeButton.setBackground(new Color(0xD4BEE4));
    }

    //- funkce, která se zaktivuje po kliku tlačítek.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            player1 = name1.getText();
            player2 = name2.getText();
            if(player1.isBlank() || player2.isBlank() || size == 0) {
                if(player1.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Hráč 1 nemá jméno!");
                } if(player2.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Hráč 2 nemá jméno!");
                } if(size == 0) {
                    JOptionPane.showMessageDialog(this, "Nevybral jste si množství karet!");
                }
            } else {
                //|----------Finalní kód---------|
                //- Tímhle kódem se zbavím tohohle okna a vytvořím další, kde už je hra.
                new Pexeso(size, player1, player2);
                this.dispose();
            }
        }
        //- tady pak teda jen calluje, tu funkci, aby se změnila barva toho tlačítka.
        if(e.getSource() == sizeA) {
            size = 4;
            sizeButtonColor(sizeA);
        }
        if(e.getSource() == sizeB) {
            size = 5;
            sizeButtonColor(sizeB);
        }
        if(e.getSource() == sizeC) {
            size = 6;
            sizeButtonColor(sizeC);
        }
    }
    //|----------NEZAPOMEŇTE SE KOUKNOUT DO MAINU!----------|
}