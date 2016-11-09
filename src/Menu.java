import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener {
    Game game = new Game();
    private int numbersOfPlayers;

    JPanel body = new JPanel();
    JPanel choosePlayers = new JPanel();
    JPanel bgPanel = new JPanel();
    JLabel rubrik = new JLabel("Black Jack Team 2");
    JButton nyttSpel = new JButton("Nytt Spel");
    JButton avsluta = new JButton("Avsluta");
    JLabel väljSpelare = new JLabel("Välj antal spelare");
    JButton onePlayers = new JButton("1 Spelare");
    JButton twoPlayers = new JButton("2 Spelare");
    JButton threePlayers = new JButton("3 Spelare");
    JButton fourPlayers = new JButton("4 Spelare");
    JButton backToMain = new JButton("Tillbaka");
    JPanel noOfPlayers = new JPanel();
    JPanel backPanel = new JPanel();

    // Bettingsida
    JPanel betPanel = new JPanel();
    JPanel inputP = new JPanel();
    JPanel lowerP = new JPanel();
    JPanel headerP = new JPanel();
    JPanel playerInputs = new JPanel();
    JPanel playerInputs2 = new JPanel();
    JPanel playerInputs3 = new JPanel();
    JPanel playerInputs4 = new JPanel();
    JLabel name = new JLabel("Namn", JLabel.LEFT);
    JLabel bet = new JLabel("Satsning", JLabel.RIGHT);
    JLabel credit = new JLabel("Kassa", JLabel.RIGHT);
    JTextField nameInput = new JTextField(10);
    JTextField betInput = new JTextField(5);
    JLabel playerNumber = new JLabel();
    JLabel playerCredit = new JLabel();
    JTextField nameInput2 = new JTextField(10);
    JTextField betInput2 = new JTextField(5);
    JLabel playerNumber2 = new JLabel();
    JLabel playerCredit2 = new JLabel();
    JTextField nameInput3 = new JTextField(10);
    JTextField betInput3 = new JTextField(5);
    JLabel playerNumber3 = new JLabel();
    JLabel playerCredit3 = new JLabel();
    JTextField nameInput4 = new JTextField(10);
    JTextField betInput4 = new JTextField(5);
    JLabel playerNumber4 = new JLabel();
    JLabel playerCredit4 = new JLabel();
    JButton backToChoosePlayers = new JButton("Tillbaka");
    JButton startGame = new JButton("Starta Spelet!");

    JLabel background = new JLabel(new ImageIcon("src/cardimages/Blackjack2.jpg"));

    public Menu() throws IOException{


        //BufferedImage myImage = ImageIO.read(new File("src/cardimages/Blackjack2.jpg"));
        //this.setContentPane(new ImagePanel(myImage));

        this.add(bgPanel);
        bgPanel.add(body);
        bgPanel.add(background);
        body.add(rubrik);
        body.add(nyttSpel);
        body.add(avsluta);

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        rubrik.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        rubrik.setForeground(Color.black);

        rubrik.setAlignmentX(CENTER_ALIGNMENT);
        nyttSpel.setAlignmentX(CENTER_ALIGNMENT);
        avsluta.setAlignmentX(CENTER_ALIGNMENT);

        rubrik.setOpaque(true);
        nyttSpel.setOpaque(true);
        avsluta.setOpaque(true);

        nyttSpel.addActionListener(this);
        avsluta.addActionListener(this);

        setSize(1200, 900);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nyttSpel) {    //lyssnare för när man trycker på "hit"
            // choosePlayers.setLayout(new BorderLayout());
            choosePlayers.setVisible(true);
            choosePlayers.setLayout(new BoxLayout(choosePlayers, BoxLayout.Y_AXIS));
            väljSpelare.setAlignmentX(CENTER_ALIGNMENT);
            backToMain.setAlignmentX(CENTER_ALIGNMENT);
            backToMain.setAlignmentY(TOP_ALIGNMENT);
            bgPanel.setVisible(false);
            this.add(choosePlayers);
            choosePlayers.add(väljSpelare);
            choosePlayers.add(noOfPlayers);
            noOfPlayers.add(onePlayers);
            noOfPlayers.add(twoPlayers);
            noOfPlayers.add(threePlayers);
            noOfPlayers.add(fourPlayers);
            //choosePlayers.add(backPanel);
            noOfPlayers.add(backToMain);

            väljSpelare.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
            väljSpelare.setForeground(Color.black);

            onePlayers.addActionListener(this);
            twoPlayers.addActionListener(this);
            threePlayers.addActionListener(this);
            fourPlayers.addActionListener(this);
            backToMain.addActionListener(this);


        }
        if (e.getSource() == avsluta) {    //lyssnare för när man trycker på "hit"
            System.exit(0);
        }
        if (e.getSource() == onePlayers) {    //lyssnare för när man trycker på "hit"
            numbersOfPlayers = 1;
            drawInputPanel();
        }
        if (e.getSource() == twoPlayers) {    //lyssnare för när man trycker på "hit"
            numbersOfPlayers = 2;
            drawInputPanel();
        }
        if (e.getSource() == threePlayers) {    //lyssnare för när man trycker på "hit"
            numbersOfPlayers = 3;
            drawInputPanel();
        }
        if (e.getSource() == fourPlayers) {    //lyssnare för när man trycker på "hit"
            numbersOfPlayers = 4;
            drawInputPanel();
        }
        if (e.getSource() == backToMain) {    //lyssnare för när man trycker på "hit"
            choosePlayers.setVisible(false);
            bgPanel.setVisible(true);
        }
        if (e.getSource() == backToChoosePlayers) {    //lyssnare för när man trycker på "hit"
            choosePlayers.setVisible(true);
            betPanel.setVisible(false);
        }

        if (e.getSource() == startGame) {    //lyssnare för när man trycker på "hit"
            try {
                FrontEnd fE = new FrontEnd(numbersOfPlayers);
                fE.setPlayerName1(nameInput.getText());
                fE.setSatsning1(Integer.parseInt(betInput.getText()));

                if (numbersOfPlayers >= 2) {
                    fE.setPlayerName2(nameInput2.getText());
                    fE.setSatsning2(Integer.parseInt(betInput2.getText()));
                }

                if (numbersOfPlayers >= 3) {
                    fE.setPlayerName3(nameInput3.getText());
                    fE.setSatsning3(Integer.parseInt(betInput3.getText()));
                }

                if (numbersOfPlayers >= 4) {
                    fE.setPlayerName4(nameInput4.getText());
                    fE.setSatsning4(Integer.parseInt(betInput4.getText()));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    public void drawInputPanel() {
        choosePlayers.setVisible(false);
        betPanel.setVisible(true);

        this.add(betPanel);
        betPanel.setLayout(new BorderLayout());
        betPanel.add(inputP, BorderLayout.NORTH);
        betPanel.add(lowerP, BorderLayout.CENTER);

        lowerP.add(backToChoosePlayers);
        lowerP.add(startGame);
        inputP.add(headerP);
        inputP.setLayout(new BoxLayout(inputP, BoxLayout.Y_AXIS));
        headerP.setLayout(new BoxLayout(headerP, BoxLayout.X_AXIS));
        headerP.add(Box.createRigidArea(new Dimension(30, 1)));
        headerP.add(name);
        headerP.add(Box.createRigidArea(new Dimension(80, 1)));
        headerP.add(bet);
        headerP.add(Box.createRigidArea(new Dimension(20, 1)));
        headerP.add(credit);

        startGame.addActionListener(this);
        backToChoosePlayers.addActionListener(this);

        if (numbersOfPlayers >= 1) {
            inputP.add(playerInputs);
            playerInputs.add(playerNumber);
            playerNumber.setText("1");
            playerInputs.add(nameInput);
            playerInputs.add(betInput);
            playerInputs.add(playerCredit);
            playerCredit.setText("1000");

            nameInput.addActionListener(this);
            betInput.addActionListener(this);
        }

        if (numbersOfPlayers >= 2) {
            inputP.add(playerInputs2);
            playerInputs2.add(playerNumber2);
            playerNumber2.setText("2");
            playerInputs2.add(nameInput2);
            playerInputs2.add(betInput2);
            playerInputs2.add(playerCredit2);
            playerCredit2.setText("1000");
            playerInputs2.setVisible(true);
            nameInput2.addActionListener(this);
            betInput2.addActionListener(this);

        }

        if (numbersOfPlayers >= 3) {
            inputP.add(playerInputs3);
            playerInputs3.add(playerNumber3);
            playerNumber3.setText("3");
            playerInputs3.add(nameInput3);
            playerInputs3.add(betInput3);
            playerInputs3.add(playerCredit3);
            playerCredit3.setText("1000");
            playerInputs3.setVisible(true);

            nameInput3.addActionListener(this);
            betInput3.addActionListener(this);
        }

        if (numbersOfPlayers >= 4) {
            inputP.add(playerInputs4);
            playerInputs4.add(playerNumber4);
            playerNumber4.setText("4");
            playerInputs4.add(nameInput4);
            playerInputs4.add(betInput4);
            playerInputs4.add(playerCredit4);
            playerCredit4.setText("1000");
            playerInputs4.setVisible(true);

            nameInput4.addActionListener(this);
            betInput4.addActionListener(this);
        }
        if(numbersOfPlayers < 4){
            playerInputs4.setVisible(false);
        }
        if(numbersOfPlayers < 3){
            playerInputs3.setVisible(false);
        }
        if(numbersOfPlayers < 2){
            playerInputs2.setVisible(false);
        }



    }//drawinputpanel
}

class ImagePanel extends JComponent {
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}