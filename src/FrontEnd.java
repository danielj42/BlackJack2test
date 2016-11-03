import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FrontEnd extends JFrame implements ActionListener{
	//byt ut när en game-klass finns
	private ArrayList deck;	//lista för deck
	
	SaveAndLoad sAL = new SaveAndLoad();
	
	int dealerPoints = 0;
	int dealerPointsAce = 0;
	int dealerAceNo = 0;
	int playerPoints = 0;
	int playerAceNo = 0;
	int playerPointsAce = 0;
	int cardImageH = 200;
	int cardImageW = 135;
	int cardCounter = 2;
	public int satsning;
	boolean allPlayersStand = false;
	
	//Menyrad
	JMenuBar mb = new JMenuBar();
	JMenu spelMenu = new JMenu("Spel");
	JMenuItem nSpel = new JMenuItem("Nytt spel");
	JMenuItem titel = new JMenuItem("Tillbaka till titelmeny");
	JMenuItem avsluta = new JMenuItem("Avsluta");
	
	JPanel superBody = new JPanel(); //huvudpanel
	JPanel body = new JPanel();	

	JPanel dealerSpace = new JPanel();	//tre subpaneler
	JPanel middleSpace = new JPanel();
	JPanel playerSpace = new JPanel();

	JPanel dealerCardSpace = new JPanel();	//subpaneler och komponenter till dealerSpace
	JPanel dealerCardsRow = new JPanel();
	JPanel dealerPointsRow = new JPanel();
	JLabel dealerPointsLabel = new JLabel(Integer.toString(dealerPoints), JLabel.CENTER);
	JLabel dealerCondition = new JLabel("Kondition", JLabel.CENTER);
	JLabel dCard1 = new JLabel();
	JLabel dCard2 = new JLabel();
	
	//subpaneler och komponenter till playerspace
	JPanel playerCol = new JPanel(); //OBS!! testversion med bara en kolumn!!! Skall itereras för varje spelare vid fler spelare
	JPanel playerCards = new JPanel();
	JLabel playerPointsRow = new JLabel(Integer.toString(playerPoints), JLabel.CENTER);
	JLabel playerBet = new JLabel(Integer.toString(satsning) + " kr", JLabel.CENTER);
	JPanel playerButtons = new JPanel();
	JLabel playerTitle = new JLabel("Spelare 1", JLabel.CENTER); //Byt ut
	JLabel pCard1 = new JLabel();
	JLabel pCard2 = new JLabel();
	JPanel playerChips = new JPanel();

	JButton hit = new JButton("Hit");
	JButton stand = new JButton("Stand");
	JButton split = new JButton("Split");
	
	//kontainers och komponenter till middlespace
	JLabel infoText = new JLabel("Dealern drar till 16 och måste stanna på 17", JLabel.CENTER);
	JLabel divider = new JLabel(new ImageIcon("src/cardimages/divider.png"));
	
	public FrontEnd() throws IOException{	
		
		//Satsa pengar
		betQuestion();
		
		//Detta ska bytas ut när det finns andra fungerande klasser
	    this.deck = new ArrayList();
	    for (int i=0; i<13; i++)	//13 kort
	    {
	      Face face = Face.values()[i];
	 
	      for (int j=0; j<4; j++)	//4 färger
	      {
	        Card card = new Card(face, Suit.values()[j]);
	        this.deck.add(card);
	      }
	    }
	    
	    Collections.shuffle(deck);
	    
		Iterator cardIterator = deck.iterator();
	    
		//Drar ett kort till dealern
		drawCard("dealer", cardIterator, dCard1, dealerPoints, dealerPointsLabel);

		//Cardback till andra kortet
	    Image c2 = new ImageIcon("src/cardimages/CARDBACK.jpg").getImage();
		dCard2.setIcon(new ImageIcon(getScaledImage(c2, cardImageW, cardImageH)));
		
		//drar två kort till spelaren
		drawCard("player1", cardIterator, pCard1, playerPoints, playerPointsRow);
		drawCard("player1", cardIterator, pCard2, playerPoints, playerPointsRow);

		setTitle("Black Jack - Team 2");
		
		//definierar layout
		superBody.setLayout(new BorderLayout());
		body.setLayout(new BorderLayout());	
		middleSpace.setLayout(new BoxLayout(middleSpace, BoxLayout.Y_AXIS));
		infoText.setAlignmentY(BOTTOM_ALIGNMENT);
		divider.setAlignmentX(CENTER_ALIGNMENT);
		divider.setAlignmentY(BOTTOM_ALIGNMENT);
		infoText.setAlignmentX(CENTER_ALIGNMENT);
		dealerCardSpace.setLayout(new BorderLayout());
		playerCol.setLayout(new BoxLayout(playerCol, BoxLayout.Y_AXIS));
		playerPointsRow.setAlignmentX(CENTER_ALIGNMENT);
		playerBet.setAlignmentX(CENTER_ALIGNMENT);
		playerTitle.setAlignmentX(CENTER_ALIGNMENT);
		playerChips.setLayout(new FlowLayout());
		
		//Lägger till meny
		add(superBody);
		superBody.add(mb, BorderLayout.NORTH);
		mb.add(spelMenu);
		spelMenu.add(nSpel);
		spelMenu.add(titel);
		spelMenu.add(avsluta);
		
		nSpel.addActionListener(this);	//lyssnare för menyval
		titel.addActionListener(this);
		avsluta.addActionListener(this);
		
		superBody.add(body, BorderLayout.CENTER);	//Lägger ut containers och komponenter
			body.add(dealerSpace, BorderLayout.NORTH);
			body.add(middleSpace, BorderLayout.CENTER);
			body.add(playerSpace, BorderLayout.SOUTH);
		
		dealerSpace.add(dealerCardSpace);
			dealerCardSpace.add(dealerCardsRow, BorderLayout.NORTH);
				dealerCardsRow.add(dCard1);
				dealerCardsRow.add(dCard2);
			dealerCardSpace.add(dealerPointsRow, BorderLayout.CENTER);
				dealerPointsRow.add(dealerPointsLabel);
		
		middleSpace.add(Box.createVerticalGlue());
		middleSpace.add(infoText);
		middleSpace.add(divider);
		
		playerSpace.add(playerCol);
			playerCol.add(playerCards);
				playerCards.add(pCard1);
				playerCards.add(pCard2);
			playerCol.add(playerPointsRow);
			playerCol.add(playerBet);
			playerCol.add(playerChips);
			playerCol.add(playerButtons);
				playerButtons.add(hit);
				playerButtons.add(stand);
				playerButtons.add(split);
			playerCol.add(playerTitle);

		placeChips(satsning);	//lägger ut grafik för spelmarker

		hit.addActionListener(this);	//lyssnare till knappen "hit"
		stand.addActionListener(this);	//lyssnare till knappen "stand"
			
		//ställer in fonter
		infoText.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		infoText.setForeground(Color.white);
		playerBet.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerBet.setForeground(Color.yellow);
		playerTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerTitle.setForeground(Color.white);
		playerPointsRow.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerPointsRow.setForeground(Color.white);
		dealerPointsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dealerPointsLabel.setForeground(Color.white);
		dealerCondition.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dealerCondition.setForeground(Color.white);
		
		split.setEnabled(false);	//Inaktiverar splitknappen
		
		infoText.setBackground(Color.decode("0x00B000"));	//mörkt grön färg
		infoText.setOpaque(true);
		dealerSpace.setBackground(Color.decode("0x00B000"));
		playerSpace.setBackground(Color.decode("0x00B000"));
		dealerCardsRow.setBackground(Color.decode("0x00B000"));
		dealerPointsRow.setBackground(Color.decode("0x00B000"));
		playerCol.setBackground(Color.decode("0x00B000"));
		playerCards.setBackground(Color.decode("0x00B000"));
		playerButtons.setBackground(Color.decode("0x00B000"));
		middleSpace.setBackground(Color.decode("0x00B000"));
		divider.setBackground(Color.decode("0x00B000"));
		divider.setOpaque(true);
		dealerCondition.setBackground(Color.decode("0x00B000"));
		dealerCondition.setOpaque(true);
		playerChips.setBackground(Color.decode("0x00B000"));
		
		setSize(1000, 900);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Om man får 21 poäng (Black Jack) "vinner" man direkt
		if (playerPoints == 21) {
			dealerDraw();
			determineWinner();
		}
	}
	
	//Metod för att ändra storlek på en bild
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	//Drar ett nytt kort och ändrar grafik, summerar poängvärde
	public void drawCard(String whichPlayer, Iterator cardIterator, JLabel cImg, int cPoints, JLabel cPointsText) {
		Card aCard = (Card) cardIterator.next();
	    Image cImage = new ImageIcon("src/cardimages/" + aCard.getFaceName() + aCard.getSuit() + ".png").getImage();
		cImg.setIcon(new ImageIcon(getScaledImage(cImage, cardImageW, cardImageH)));
		String currentCardName = "" + aCard.getFaceName();
		
		if (whichPlayer.equals("dealer")) {
			dealerPoints += aCard.getFaceName().getFaceValue();
//			System.out.println(Enum.valueOf(Face.class, currentCardName));	//Denna ska visa en siffra (int) inte namnet posten i enumen
//			dealerPoints += Enum.valueOf(Face.class, currentCardName);
			
			if (currentCardName.equals("ESS") == false && dealerAceNo == 0) {	//delar poängen i två om dealern drog/har ett ess
				dealerPointsLabel.setText(Integer.toString(dealerPoints));
			}
			else if (currentCardName.equals("ESS")) {
				dealerAceNo += 1;
				dealerPointsAce = dealerPoints - 10;
				dealerPointsLabel.setText(Integer.toString(dealerPointsAce) + " / " + Integer.toString(dealerPoints));
			}
			else if (dealerAceNo > 0) {
				dealerPointsAce += aCard.getFaceName().getFaceValue();
				dealerPointsLabel.setText(Integer.toString(dealerPointsAce) + " / " + Integer.toString(dealerPoints));
			}
		} 
		else if (whichPlayer.equals("player1")){
			playerPoints += aCard.getFaceName().getFaceValue();
			
			if (currentCardName.equals("ESS") == false && playerAceNo == 0) {
				playerPointsRow.setText(Integer.toString(playerPoints));
			}
			else if (currentCardName.equals("ESS")) {
				playerAceNo += 1;
				playerPointsAce = playerPoints - 10;
				playerPointsRow.setText(Integer.toString(playerPointsAce) + " / " + Integer.toString(playerPoints));
			}
			else if (playerAceNo > 0) {
				playerPointsAce += aCard.getFaceName().getFaceValue(); //addValue(currentCardName);
				playerPointsRow.setText(Integer.toString(playerPointsAce) + " / " + Integer.toString(playerPoints));
			}
		}
	}
	
	//lyssnare till knappar
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == hit && allPlayersStand == false) {	//lyssnare för när man trycker på "hit"
			cardCounter += 1;
			Iterator cardIterator = deck.iterator();
			
			for (int i = 0; i <= cardCounter; i++) {
				Card aCard = (Card) cardIterator.next();
			}
			
			JLabel pCard3 = new JLabel();
			playerCards.add(pCard3);
			drawCard("player1", cardIterator, pCard3, playerPoints, playerPointsRow);
			
			if ((playerPoints > 21 && playerAceNo < 1) || (playerPoints > 21 && playerAceNo > 0 && playerPointsAce > 21)) {
				dealerDraw();
				try {
					determineWinner();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getSource() == stand && allPlayersStand == false) {	//lyssnare för stand
			allPlayersStand = true;
			hit.setEnabled(false);
			stand.setEnabled(false);
			
			dealerDraw();
			try {
				determineWinner();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == nSpel){
			try {
				startNewGame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == titel) {
			
		}
		
		if (e.getSource() == avsluta) {
			System.exit(0);
		}
	}
	
	//Metod för att bestämma vem som vann
	public void determineWinner() throws IOException{
		allPlayersStand = true;
		hit.setEnabled(false);
		stand.setEnabled(false);
		
		//om dealern eller spelaren har dragit ett ess och blivit bust med det används esspoängen istället
		if (dealerPoints > 21 && dealerAceNo > 0) {
			dealerPoints = dealerPointsAce;
		}
		if (playerPoints > 21 && playerAceNo > 0) {
			playerPoints = playerPointsAce;
		}
		
		if(dealerPoints <= 21 && playerPoints <= 21) {
			if (dealerPoints > playerPoints) {
				playerLoseText();
			} else if (playerPoints > dealerPoints){
				winMoney();
			} else if ((dealerPoints == 20 && playerPoints == 20) || (dealerPoints == 21 && playerPoints == 21)) {
				likaomgang();
			}
		} else if (dealerPoints <= 21) {
			playerBustText();
		} else if (playerPoints <= 21) {
			dealerBustText();
			winMoney();
		}  else if (dealerPoints > 21 && playerPoints > 21) {
			dealerBustText();
			playerBustText();
		}
	}
	
	//Metod som drar kort till dealern efter att alla spelare har stannat
	public void dealerDraw(){
		Iterator cardIterator = deck.iterator();
		dCard2.setVisible(false);
		
		while((dealerPoints < 16 && (playerPoints < 16 || playerPoints > 21)) || (dealerPoints < 17 && (playerPoints >= 16 && playerPoints <= 21))) {
			cardCounter += 1;
			
			for (int i = 0; i <= cardCounter; i++) {
				Card aCard = (Card) cardIterator.next();
			}
			
			JLabel dCard3 = new JLabel();
			dealerCardsRow.add(dCard3);
			drawCard("dealer", cardIterator, dCard3, dealerPoints, dealerPointsLabel);
			
			//Dealern fortsätter dra om den blir bust med ess-poängen
			if (dealerPoints > 21 && dealerAceNo > 0) {
				dealerPoints = dealerPointsAce;
				dealerAceNo = 0;
			}
		}
	}
	
	//Lägger till pengar till fil och skriver ut på skärmen om man vinner
	public void winMoney() throws IOException{
		playerTitle.setText("Spelare 1 vann " + satsning * 2 + " kronor! Hen har nu " + (sAL.getMoney() + satsning * 2) + " kr i kassan");
		playerTitle.setForeground(Color.yellow);
		int kassa = sAL.getMoney();
		sAL.setMoney(satsning * 2 + kassa);
		sAL.Save();
	}
	
	//Metoder för att skriva ut text efter omgången
	public void dealerBustText() {
		dealerCardSpace.add(dealerCondition, BorderLayout.SOUTH);
		dealerCondition.setText("Dealer BUST");
		dealerCondition.setForeground(Color.red);
	}
	
	public void playerLoseText() {
		playerTitle.setText("Spelare 1 förlorade");
		playerTitle.setForeground(Color.red);
	}
	
	public void playerBustText() {
		playerTitle.setText("Spelare 1 BUST");
		playerTitle.setForeground(Color.red);
	}
	
	public void likaomgang() throws IOException{
		playerTitle.setText("Spelare 1 likaomgång. Spelaren får tillbaka sin insats, har nu " + (sAL.getMoney() + satsning) + " i kassan");
		playerTitle.setForeground(Color.white);	
		int kassa = sAL.getMoney();
		sAL.setMoney(satsning + kassa);
		sAL.Save();
	}
	
	//Starta nytt spel. Ej färdigställd metod
	public void startNewGame() throws IOException{
		dealerPoints = 0;
		playerPoints = 0;
		cardCounter += 3;
		satsning = 0;
		allPlayersStand = false;
		dealerPoints = 0;
		dealerPointsAce = 0;
		dealerAceNo = 0;
		playerPoints = 0;
		
		//Satsa pengar
		betQuestion();
		
		hit.setEnabled(true);
		stand.setEnabled(true);
		
		dealerCardSpace.remove(dealerCardsRow);
		dealerCardSpace.add(dealerCardsRow, BorderLayout.NORTH);
		dealerCardsRow.add(dCard1);
		dealerCardsRow.add(dCard2);
		
		playerCol.remove(playerCards);
		playerCol.add(playerCards, BorderLayout.NORTH);
		playerCards.add(pCard1);
		playerCards.add(pCard2);
		
		playerTitle.setText("Spelare 1");
		playerTitle.setForeground(Color.white);
		dealerCondition.setText("");
		
		//Detta ska bytas ut när det finns andra fungerande klasser
	    this.deck = new ArrayList();
	    for (int i=0; i<13; i++)	//13 kort
	    {
	      Face face = Face.values()[i];
	 
	      for (int j=0; j<4; j++)	//4 färger
	      {
	        Card card = new Card(face, Suit.values()[j]);
	        this.deck.add(card);
	      }
	    }
	    
	    Collections.shuffle(deck);
	    
		Iterator cardIterator = deck.iterator();
		
		//Drar ett kort till dealern
		drawCard("dealer", cardIterator, dCard1, dealerPoints, dealerPointsLabel);

		//Cardback till andra kortet
	    Image c2 = new ImageIcon("src/cardimages/CARDBACK.jpg").getImage();
		dCard2.setIcon(new ImageIcon(getScaledImage(c2, cardImageW, cardImageH)));
		dCard2.setVisible(true);
		
		//drar två kort till spelaren
		drawCard("player1", cardIterator, pCard1, playerPoints, playerPointsRow);
		drawCard("player1", cardIterator, pCard2, playerPoints, playerPointsRow);
	}
	
	//i början av spelet frågas hur mycket man vill satsa
	public void betQuestion() throws IOException{
		sAL.Load();
		
		if (sAL.getMoney() == 0) {
			sAL.setMoney(Integer.parseInt(JOptionPane.showInputDialog(null, "Du har inga cash, polarn. Skriv in hur mycket pengar du vill lägga till:")));
			sAL.Save();
		}
		
		while (satsning == 0) {
			int trySatsning = Integer.parseInt(JOptionPane.showInputDialog("Du har " + sAL.getMoney() + " kr i kassan.\nHur mycket vill du satsa?"));
			
			if (trySatsning > sAL.getMoney()) {
				JOptionPane.showMessageDialog(null, "Ledsen kompis, du har inte så mycket cash. Försök igen.");
			}
			else
			{
				satsning = trySatsning;
				playerBet.setText(Integer.toString(satsning) + " kr");
				sAL.setMoney(sAL.getMoney() - satsning);
				sAL.Save();
			}
		}
	}
	
	//metod för att lägga ut marker grafiskt
	public void placeChips(int moneyBet){
		GridBagLayout c = new GridBagLayout();
		playerChips.setLayout(c);
		GridBagConstraints con = new GridBagConstraints();
		
		int bet = moneyBet;
		int i = 0;
		int j = 0;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.gridy = 0;
		con.fill = GridBagConstraints.BOTH;
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(0,0,0,0);
		
		while(moneyBet > 0) {
			con.gridx = i;
			con.ipadx = 0;
			
			if (moneyBet >= 100) {
				moneyBet -= 100;
				JLabel c100 = new JLabel(new ImageIcon("src/cardimages/chip100.png"), JLabel.CENTER);
				c.setConstraints(c100, con);
				playerChips.add(c100);
			} else if (moneyBet >= 50) {
				moneyBet -= 50;
				JLabel c50 = new JLabel(new ImageIcon("src/cardimages/chip50.png"), JLabel.CENTER);
				c.setConstraints(c50, con);
				playerChips.add(c50);
			} else if (moneyBet >= 10) {
				moneyBet -= 10;
				JLabel c10 = new JLabel(new ImageIcon("src/cardimages/chip10.png"), JLabel.CENTER);
				c.setConstraints(c10, con);
				playerChips.add(c10);
			} else if (moneyBet >= 5) {
				moneyBet -= 5;
				JLabel c5 = new JLabel(new ImageIcon("src/cardimages/chip5.png"), JLabel.CENTER);
				c.setConstraints(c5, con);
				playerChips.add(c5);
			} else if (moneyBet >= 1) {
				moneyBet -= 1;
				JLabel c1 = new JLabel(new ImageIcon("src/cardimages/chip1.png"), JLabel.CENTER);
				c.setConstraints(c1, con);
				playerChips.add(c1);
			}
			con.insets = new Insets(0,-52,0,0);
			i++;
		}
	}
}
