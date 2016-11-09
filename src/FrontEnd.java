import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FrontEnd extends JFrame implements ActionListener{
	//byt ut när en game-klass finns
	Deck deck = new Deck();
	
	PlayerPanel spelarPanel = new PlayerPanel();
	PlayerPanel spelarPanel2 = new PlayerPanel();
	PlayerPanel spelarPanel3 = new PlayerPanel();
	PlayerPanel spelarPanel4 = new PlayerPanel();
	
	int highestPlayerPoints = 0;
	int numberOfPlayers;	//Denna variabel ändras för antal spelare man vill ha med i spelet (1-4)
	int currentPlayer = numberOfPlayers;
	int dealerPoints = 0;
	int dealerPointsAce = 0;
	int dealerAceNo = 0;
	int cardImageH = 175;
	int cardImageW = 120;
	int cardCounter = 2;
	int playerPoints = 0;		//OBS: Dessa är placeholders för nuvarande spelares poäng. Måste hämtas med getters
	int playerAceNo = 0;
	int playerPointsAce = 0;
	int noOfPlayersOut = 0;
	boolean allPlayersStand = false;
	public int satsning;
	String playerName = "Spelare";
	
	//Menyrad
	JMenuBar mb = new JMenuBar();
	JMenu spelMenu = new JMenu("Spel");
	JMenuItem nSpel = new JMenuItem("Nytt spel");
	JMenuItem titel = new JMenuItem("Tillbaka till titelmeny");
	JMenuItem avsluta = new JMenuItem("Avsluta");
	
	JPanel body = new JPanel();	
	JScrollPane superBody = new JScrollPane(body); //huvudpanel
	
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
	JLabel cardShoeLabel = new JLabel(new ImageIcon());
	
	//kontainers och komponenter till middlespace
	JLabel infoText = new JLabel("Dealern drar till 16 och måste stanna på 17", JLabel.CENTER);
	JLabel divider = new JLabel(new ImageIcon("src/cardimages/divider.png"));
	
	JButton stand = new JButton("Stand");	//Knappar för spelare 1
	JButton hit = new JButton("Hit");
	JButton split = new JButton("Split");

	JButton stand2 = new JButton("Stand");	//Knappar för spelare 2
	JButton hit2 = new JButton("Hit");
	JButton split2 = new JButton("Split");
	
	JButton stand3 = new JButton("Stand");	//Knappar för spelare 3	
	JButton hit3 = new JButton("Hit");
	JButton split3 = new JButton("Split");
	
	JButton stand4 = new JButton("Stand");	//Knappar för spelare 4	
	JButton hit4 = new JButton("Hit");
	JButton split4 = new JButton("Split");

	
	public FrontEnd(int nOP) throws IOException{	
		numberOfPlayers = nOP;
		
		//Drar ett kort till dealern
		drawCard(dCard1, dealerPoints, dealerPointsLabel);

		//Cardback till andra kortet
	    Image c2 = new ImageIcon("src/cardimages/CARDBACK.jpg").getImage();
		dCard2.setIcon(new ImageIcon(getScaledImage(c2, cardImageW, cardImageH)));
		
		//Kortsko grafiskt element
	    Image cardShoe = new ImageIcon("src/cardimages/cardshoe.png").getImage();
		cardShoeLabel.setIcon(new ImageIcon(getScaledImage(cardShoe, cardImageH, cardImageH)));

		setTitle("Black Jack - Team 2");
		
		//definierar layout
//		superBody.setLayout(new BorderLayout());
		body.setLayout(new BorderLayout());	
		middleSpace.setLayout(new BoxLayout(middleSpace, BoxLayout.Y_AXIS));
		infoText.setAlignmentY(BOTTOM_ALIGNMENT);
		divider.setAlignmentX(CENTER_ALIGNMENT);
		divider.setAlignmentY(BOTTOM_ALIGNMENT);
		infoText.setAlignmentX(CENTER_ALIGNMENT);
		dealerCardSpace.setLayout(new BorderLayout());
		dealerSpace.setLayout(new BorderLayout());
		
		//Lägger till meny
		add(superBody);
//		superBody.add(mb);//, BorderLayout.NORTH);
		mb.add(spelMenu);
//		spelMenu.add(nSpel);
		spelMenu.add(titel);
		spelMenu.add(avsluta);
		
		nSpel.addActionListener(this);	//lyssnare för menyval
		titel.addActionListener(this);
		avsluta.addActionListener(this);
		
//		superBody.add(body, BorderLayout.CENTER);	//Lägger ut containers och komponenter
			body.add(dealerSpace, BorderLayout.NORTH);
			dealerSpace.add(middleSpace, BorderLayout.SOUTH);
			body.add(playerSpace, BorderLayout.CENTER);
		
		dealerSpace.add(dealerCardSpace, BorderLayout.CENTER);
			dealerCardSpace.add(dealerCardsRow, BorderLayout.NORTH);
				dealerCardsRow.add(dCard1);
				dealerCardsRow.add(dCard2);
			dealerCardSpace.add(dealerPointsRow, BorderLayout.CENTER);
				dealerPointsRow.add(dealerPointsLabel);
		dealerSpace.add(cardShoeLabel, BorderLayout.EAST);
		cardShoeLabel.setAlignmentX(LEFT_ALIGNMENT);		
		
		middleSpace.add(Box.createVerticalGlue());
		middleSpace.add(infoText);
		middleSpace.add(divider);
			
		playerSpace.add(spelarPanel);	//Lägger till spelarpanel
		spelarPanel.setCardImageW(cardImageW);
		spelarPanel.setCardImageH(cardImageH);
		spelarPanel.playerButtons.add(hit);
		spelarPanel.playerButtons.add(stand);
		spelarPanel.playerButtons.add(split);
		stand.addActionListener(this);	//lyssnare till knappen "stand"
		hit.addActionListener(this);	//lyssnare till knappen "hit"
		split.setEnabled(false);	//Inaktiverar splitknappen
		spelarPanel.setPlayerNumber(1);
	
		if (numberOfPlayers >= 2) {	//Lägger till panel för spelare nr 2
			playerSpace.add(spelarPanel2);
			spelarPanel2.setCardImageW(cardImageW);
			spelarPanel2.setCardImageH(cardImageH);
			spelarPanel2.playerButtons.add(hit2);
			spelarPanel2.playerButtons.add(stand2);
			spelarPanel2.playerButtons.add(split2);
			stand2.addActionListener(this);	//lyssnare till knappen "stand2"
			hit2.addActionListener(this);	//lyssnare till knappen "hit2"
			split2.setEnabled(false);	//Inaktiverar splitknappen2
			stand2.setVisible(false);
			hit2.setVisible(false);
			split2.setVisible(false);
			spelarPanel2.setPlayerNumber(2);
		}
		
		if (numberOfPlayers >= 3) {	//Lägger till panel för spelare nr 3
			playerSpace.add(spelarPanel3);
			spelarPanel3.setCardImageW(cardImageW);
			spelarPanel3.setCardImageH(cardImageH);
			spelarPanel3.playerButtons.add(hit3);
			spelarPanel3.playerButtons.add(stand3);
			spelarPanel3.playerButtons.add(split3);
			stand3.addActionListener(this);	//lyssnare till knappen "stand3"
			hit3.addActionListener(this);	//lyssnare till knappen "hit3"
			split3.setEnabled(false);	//Inaktiverar splitknappen3
			stand3.setVisible(false);
			hit3.setVisible(false);
			split3.setVisible(false);
			spelarPanel3.setPlayerNumber(3);
		}
		
		if (numberOfPlayers >= 4) {	//Lägger till panel för spelare nr 4
			playerSpace.add(spelarPanel4);
			spelarPanel4.setCardImageW(cardImageW);
			spelarPanel4.setCardImageH(cardImageH);
			spelarPanel4.playerButtons.add(hit4);
			spelarPanel4.playerButtons.add(stand4);
			spelarPanel4.playerButtons.add(split4);
			stand4.addActionListener(this);	//lyssnare till knappen "stand4"
			hit4.addActionListener(this);	//lyssnare till knappen "hit4"
			split4.setEnabled(false);	//Inaktiverar splitknappen4
			stand4.setVisible(false);
			hit4.setVisible(false);
			split4.setVisible(false);
			spelarPanel4.setPlayerNumber(4);
		}	
	
		//ställer in fonter
		infoText.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		infoText.setForeground(Color.white);
		dealerPointsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dealerPointsLabel.setForeground(Color.white);
		dealerCondition.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		dealerCondition.setForeground(Color.white);
		
		infoText.setBackground(Color.decode("0x00B000"));	//mörkt grön färg
		infoText.setOpaque(true);
		dealerSpace.setBackground(Color.decode("0x00B000"));
		playerSpace.setBackground(Color.decode("0x00B000"));
		playerSpace.setOpaque(true);
		dealerCardsRow.setBackground(Color.decode("0x00B000"));
		dealerPointsRow.setBackground(Color.decode("0x00B000"));
		middleSpace.setBackground(Color.decode("0x00B000"));
		divider.setBackground(Color.decode("0x00B000"));
		divider.setOpaque(true);
		dealerCondition.setBackground(Color.decode("0x00B000"));
		dealerCondition.setOpaque(true);
		cardShoeLabel.setBackground(Color.decode("0x00B000"));
		cardShoeLabel.setOpaque(true);
		
		setSize(1200, 900);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		if (spelarPanel.getPlayerPoints() == 21) {	//Lägger till antal spelare som är klara om de får Black Jack direkt
			spelarPanel.setPlayerBlackJack();
			hit.setVisible(false);
			stand.setVisible(false);
			split.setVisible(false);
			noOfPlayersOut += 1;
		}
		if (spelarPanel2.getPlayerPoints() == 21) {
			spelarPanel2.setPlayerBlackJack();
			hit2.setVisible(false);
			stand2.setVisible(false);
			split2.setVisible(false);
			noOfPlayersOut += 1;
		}
		if (spelarPanel3.getPlayerPoints() == 21) {
			spelarPanel3.setPlayerBlackJack();
			hit3.setVisible(false);
			stand3.setVisible(false);
			split3.setVisible(false);
			noOfPlayersOut += 1;
		}
		if (spelarPanel4.getPlayerPoints() == 21) {
			spelarPanel4.setPlayerBlackJack();
			hit4.setVisible(false);
			stand4.setVisible(false);
			split4.setVisible(false);
			noOfPlayersOut += 1;
		}
		
		goToNextPlayer(); //Så att programmet hoppar förbi spelare som får Black Jack direkt
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
	public void drawCard(JLabel cImg, int cPoints, JLabel cPointsText) {
		Card aCard = deck.getCard();
	    Image cImage = new ImageIcon("src/cardimages/" + aCard.getFaceName() + aCard.getSuit() + ".png").getImage();
		cImg.setIcon(new ImageIcon(getScaledImage(cImage, cardImageW, cardImageH)));
		String currentCardName = "" + aCard.getFaceName();

		dealerPoints += aCard.getFaceName().getFaceValue();
		
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
	
	//lyssnare till knappar
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource() == hit) {	//lyssnare för när man trycker på "hit"
			spelarPanel.setNewPlayerCard();
			
			if (numberOfPlayers > 1) {
				hit.setVisible(false);
				stand.setVisible(false);
				split.setVisible(false);
			}
			
			if (spelarPanel.getPlayerIsBust() == true) {
				noOfPlayersOut += 1;
				hit.setEnabled(false);
				stand.setEnabled(false);
			}
			
			goToNextPlayer();
		}
		
		if (e.getSource() == stand) {	//lyssnare för när man trycker på "stand"
			spelarPanel.setPlayerStands();
			noOfPlayersOut += 1;
			hit.setEnabled(false);
			stand.setEnabled(false);
			hit.setVisible(false);
			stand.setVisible(false);
			split.setVisible(false);
			
			if (numberOfPlayers == 1) {
				dealerDraw();
				try {
					determineWinner();
					spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				goToNextPlayer();
			}
		}
		
		if (e.getSource() == hit2) {	//lyssnare för när man trycker på "hit2"		
			spelarPanel2.setNewPlayerCard();
			hit2.setVisible(false);
			stand2.setVisible(false);
			split2.setVisible(false);
			
			if (spelarPanel2.getPlayerIsBust() == true) {
				noOfPlayersOut += 1;
				hit2.setEnabled(false);
				stand2.setEnabled(false);
			}
			goToNextPlayer();
			
		}
		
		if (e.getSource() == stand2) {	//lyssnare för när man trycker på "stand2"
			spelarPanel2.setPlayerStands();
			noOfPlayersOut += 1;
			hit2.setEnabled(false);
			stand2.setEnabled(false);
			hit2.setVisible(false);
			stand2.setVisible(false);
			split2.setVisible(false);
			
			goToNextPlayer();
		}		
		
		if (e.getSource() == hit3) {	//lyssnare för när man trycker på "hit3"		
			spelarPanel3.setNewPlayerCard();
			hit3.setVisible(false);
			stand3.setVisible(false);
			split3.setVisible(false);
			
			if (spelarPanel3.getPlayerIsBust() == true) {
				noOfPlayersOut += 1;
				hit3.setEnabled(false);
				stand3.setEnabled(false);
			}
			goToNextPlayer();
			
		}
		
		if (e.getSource() == stand3) {	//lyssnare för när man trycker på "stand3"
			spelarPanel3.setPlayerStands();
			noOfPlayersOut += 1;
			hit3.setEnabled(false);
			stand3.setEnabled(false);
			hit3.setVisible(false);
			stand3.setVisible(false);
			split3.setVisible(false);
			
			goToNextPlayer();
		}		
		
		if (e.getSource() == hit4) {	//lyssnare för när man trycker på "hit4"		
			spelarPanel4.setNewPlayerCard();
			hit4.setVisible(false);
			stand4.setVisible(false);
			split4.setVisible(false);
			
			if (spelarPanel4.getPlayerIsBust() == true) {
				noOfPlayersOut += 1;
				hit4.setEnabled(false);
				stand4.setEnabled(false);
			}
			goToNextPlayer();
			
		}
		
		if (e.getSource() == stand4) {	//lyssnare för när man trycker på "stand4"
			spelarPanel4.setPlayerStands();
			noOfPlayersOut += 1;
			hit4.setEnabled(false);
			stand4.setEnabled(false);
			hit4.setVisible(false);
			stand4.setVisible(false);
			split4.setVisible(false);
			
			goToNextPlayer();
		}
		
		if (e.getSource() == nSpel){
			
		}
		
		if (e.getSource() == titel) {
			
		}
		
		if (e.getSource() == avsluta) {
			System.exit(0);
		}
	}
	
	//Metod för att bestämma om dealern blev tjock
	public void determineWinner() throws IOException{
		
		//om dealern eller spelaren har dragit ett ess och blivit bust med det används esspoängen istället
		if (dealerPoints > 21 && dealerAceNo > 0) {
			dealerPoints = dealerPointsAce;
		}
		
		if(dealerPoints > 21) {
			dealerBustText();
		}
	}
	
	//Metod som drar kort till dealern efter att alla spelare har stannat
	public void dealerDraw(){
		dCard2.setVisible(false);
		
		//if-satsen bör egentligen ändras här
		while(dealerPoints < 17) {
			
			JLabel dCard3 = new JLabel();
			dealerCardsRow.add(dCard3);
			drawCard(dCard3, dealerPoints, dealerPointsLabel);
			
			//Dealern fortsätter dra om den blir bust med ess-poängen
			if (dealerPoints > 21 && dealerAceNo > 0) {
				dealerPoints = dealerPointsAce;
				dealerAceNo = 0;
			}
		}
	}
	
	//Metoder för att skriva ut text efter omgången
	public void dealerBustText() {
		dealerCardSpace.add(dealerCondition, BorderLayout.SOUTH);
		dealerCondition.setText("Dealer BUST");
		dealerCondition.setForeground(Color.red);
	}
	
	public void goToNextPlayer() {	//Går till nästa spelare i ordningen, hoppar över spelaren om den är bust eller har stannat
		currentPlayer += 1;
		
		if (currentPlayer > numberOfPlayers) {	//om nuvarande spelare är på ett nummer högre än antalet spelare, går nuvarande tillbaka till spelare 1
			currentPlayer = 1;
		}
		
		while(noOfPlayersOut < numberOfPlayers) {
			if (currentPlayer == 4 && numberOfPlayers >= 4) {
				if (spelarPanel4.getPlayerIsBust() == true || spelarPanel4.getPlayerStands() == true) {
					currentPlayer = 1;
				} else { 
					break;
				}
			}
			
			if (currentPlayer == 1) {
				if (spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) {
					currentPlayer = 2;
				} else { 
					break;
				}
			} 
			
			if (currentPlayer == 2 && numberOfPlayers >= 2) {
				if (spelarPanel2.getPlayerIsBust() == true || spelarPanel2.getPlayerStands() == true) {
					currentPlayer = 3;
				} else { 
					break;
				}
			} 
	
			if (currentPlayer == 3 && numberOfPlayers >= 3) {
				if (spelarPanel3.getPlayerIsBust() == true || spelarPanel3.getPlayerStands() == true) {
					currentPlayer = 4;
				} else { 
					break;
				}
			}
			
			if (currentPlayer > numberOfPlayers) {
				currentPlayer = 1;
			}
		}
		
		if (currentPlayer == 4 && spelarPanel4.getPlayerIsBust() == false && spelarPanel4.getPlayerStands() == false){
			hit4.setEnabled(true);
			stand4.setEnabled(true);
			hit4.setVisible(true);
			stand4.setVisible(true);
			split4.setVisible(true);
		} 
		
		if (currentPlayer == 3 && spelarPanel3.getPlayerIsBust() == false && spelarPanel3.getPlayerStands() == false){
			hit3.setEnabled(true);
			stand3.setEnabled(true);
			hit3.setVisible(true);
			stand3.setVisible(true);
			split3.setVisible(true);
		} 
		
		if (currentPlayer == 2 && spelarPanel2.getPlayerIsBust() == false && spelarPanel2.getPlayerStands() == false){
			hit2.setEnabled(true);
			stand2.setEnabled(true);
			hit2.setVisible(true);
			stand2.setVisible(true);
			split2.setVisible(true);
		} 
		
		if (currentPlayer == 1 && spelarPanel.getPlayerIsBust() == false && spelarPanel.getPlayerStands() == false){
			hit.setEnabled(true);
			stand.setEnabled(true);
			hit.setVisible(true);
			stand.setVisible(true);
			split.setVisible(true);
		} 
		
		if (numberOfPlayers == 1 && (spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true)) {
			dealerDraw();
			try {
				determineWinner();
				spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (numberOfPlayers == 2 && ((spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) && (spelarPanel2.getPlayerIsBust() 
				== true || spelarPanel2.getPlayerStands() == true))) {
			dealerDraw();
			try {
				determineWinner();
				spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel2.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (numberOfPlayers == 3 && ((spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) && (spelarPanel2.getPlayerIsBust() 
				== true || spelarPanel2.getPlayerStands() == true) && (spelarPanel3.getPlayerIsBust() == true || spelarPanel3.getPlayerStands() == true))) {
			dealerDraw();
			try {
				determineWinner();
				spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel2.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel3.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (numberOfPlayers == 4 && ((spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) && (spelarPanel2.getPlayerIsBust() 
				== true || spelarPanel2.getPlayerStands() == true) && (spelarPanel3.getPlayerIsBust() == true || spelarPanel3.getPlayerStands() == true) &&
				(spelarPanel4.getPlayerIsBust() == true || spelarPanel4.getPlayerStands() == true))) {
			dealerDraw();
			try {
				determineWinner();
				spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel2.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel3.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel4.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// Setters för Menu att använda
	public void setNumberOfPlayers(int noOfPlayers) {
		numberOfPlayers = noOfPlayers;
	}
	
	public void setSatsning1(int satsning) {
		spelarPanel.setBet(satsning);
	}	
	public void setSatsning2(int satsning) {
		spelarPanel2.setBet(satsning);
	}	
	public void setSatsning3(int satsning) {
		spelarPanel3.setBet(satsning);
	}	
	public void setSatsning4(int satsning) {
		spelarPanel4.setBet(satsning);
	}
	
	public void setPlayerName1(String playerName) {
		spelarPanel.setPlayerName(playerName);
	}
	public void setPlayerName2(String playerName) {
		spelarPanel2.setPlayerName(playerName);
	}
	public void setPlayerName3(String playerName) {
		spelarPanel3.setPlayerName(playerName);
	}
	public void setPlayerName4(String playerName) {
		spelarPanel4.setPlayerName(playerName);
	}
	
	public void setPlayerCredit1(int playerCredit) {
		spelarPanel.setPlayerCredit(playerCredit);
	}
	public void setPlayerCredit2(int playerCredit) {
		spelarPanel2.setPlayerCredit(playerCredit);
	}
	public void setPlayerCredit3(int playerCredit) {
		spelarPanel3.setPlayerCredit(playerCredit);
	}
	public void setPlayerCredit4(int playerCredit) {
		spelarPanel4.setPlayerCredit(playerCredit);
	}
}
