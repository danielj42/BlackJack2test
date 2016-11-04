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
	Deck deck = new Deck();
	
	PlayerPanel spelarPanel = new PlayerPanel();
	PlayerPanel spelarPanel2 = new PlayerPanel();
	SaveAndLoad sAL = new SaveAndLoad();
	
	int currentPlayer = 1;
	int numberOfPlayers = 2;
	int dealerPoints = 0;		//OBS: Dessa är placeholders för nuvarande spelares poäng. Skall hämtas med getters
	int dealerPointsAce = 0;
	int dealerAceNo = 0;
	int cardImageH = 175;
	int cardImageW = 120;
	int cardCounter = 2;
	int playerPoints = 0;
	int playerAceNo = 0;
	int playerPointsAce = 0;
	boolean allPlayersStand = false;
	public int satsning;
	
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
	JPanel playerSpace = new JPanel(); //Byt ut mot PlayerPanel-klassen efter antal spelare

	JPanel dealerCardSpace = new JPanel();	//subpaneler och komponenter till dealerSpace
	JPanel dealerCardsRow = new JPanel();
	JPanel dealerPointsRow = new JPanel();
	JLabel dealerPointsLabel = new JLabel(Integer.toString(dealerPoints), JLabel.CENTER);
	JLabel dealerCondition = new JLabel("Kondition", JLabel.CENTER);
	JLabel dCard1 = new JLabel();
	JLabel dCard2 = new JLabel();
	
	//kontainers och komponenter till middlespace
	JLabel infoText = new JLabel("Dealern drar till 16 och måste stanna på 17", JLabel.CENTER);
	JLabel divider = new JLabel(new ImageIcon("src/cardimages/divider.png"));
	
	JButton stand = new JButton("Stand");	
	JButton hit = new JButton("Hit");
	JButton split = new JButton("Split");

	JButton stand2 = new JButton("Stand");	
	JButton hit2 = new JButton("Hit");
	JButton split2 = new JButton("Split");

	
	public FrontEnd() throws IOException{	
		
		//Satsa pengar
//		betQuestion();
		
		//Drar ett kort till dealern
		drawCard(dCard1, dealerPoints, dealerPointsLabel);

		//Cardback till andra kortet
	    Image c2 = new ImageIcon("src/cardimages/CARDBACK.jpg").getImage();
		dCard2.setIcon(new ImageIcon(getScaledImage(c2, cardImageW, cardImageH)));

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
				
		playerSpace.add(spelarPanel);	//Lägger till spelarpanel
		spelarPanel.setCardImageW(cardImageW);
		spelarPanel.setCardImageH(cardImageH);
		spelarPanel.playerButtons.add(hit);
		spelarPanel.playerButtons.add(stand);
		spelarPanel.playerButtons.add(split);
		stand.addActionListener(this);	//lyssnare till knappen "stand"
		hit.addActionListener(this);	//lyssnare till knappen "hit"
		split.setEnabled(false);	//Inaktiverar splitknappen
	
		if (numberOfPlayers == 2) {	//Lägger till panel för spelare nr 2
			playerSpace.add(spelarPanel2);
			spelarPanel2.setCardImageW(cardImageW);
			spelarPanel2.setCardImageH(cardImageH);
			spelarPanel2.playerButtons.add(hit2);
			spelarPanel2.playerButtons.add(stand2);
			spelarPanel2.playerButtons.add(split2);
			stand2.addActionListener(this);	//lyssnare till knappen "stand"
			hit2.addActionListener(this);	//lyssnare till knappen "hit"
			split2.setEnabled(false);	//Inaktiverar splitknappen
			stand2.setVisible(false);
			hit2.setVisible(false);
			split2.setVisible(false);
			spelarPanel2.setPlayerNumber(2);
		}
		
		spelarPanel.setPlayerNumber(1);
		
		
		middleSpace.add(Box.createVerticalGlue());
		middleSpace.add(infoText);
		middleSpace.add(divider);
			
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
		
		setSize(1000, 900);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Om man får 21 poäng (Black Jack) "vinner" man direkt. gör om denna för två+ spelare
//		if (playerPoints == 21) {
//			dealerDraw();
//			determineWinner();
//		}
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
		if (e.getSource() == stand) {
			spelarPanel.setPlayerStands(true);
			
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
		
		if (e.getSource() == hit) {	//lyssnare för när man trycker på "hit"
			spelarPanel.setNewPlayerCard();
			
			if (numberOfPlayers > 1) {
				hit.setVisible(false);
				stand.setVisible(false);
				split.setVisible(false);
			}
			
			if (spelarPanel.getPlayerIsBust() == true) {
				hit.setEnabled(false);
				stand.setEnabled(false);
			}
			
			goToNextPlayer();
		}
		
		if (e.getSource() == hit2) {	//lyssnare för när man trycker på "hit2"		
			spelarPanel2.setNewPlayerCard();
			hit2.setVisible(false);
			stand2.setVisible(false);
			split2.setVisible(false);
			
			if (spelarPanel2.getPlayerIsBust() == true) {
				hit2.setEnabled(false);
				stand2.setEnabled(false);
			}
			goToNextPlayer();
			
		}
		
		if (e.getSource() == stand2) {
			spelarPanel2.setPlayerStands(true);
			
			hit2.setEnabled(false);
			stand2.setEnabled(false);
			hit2.setVisible(false);
			stand2.setVisible(false);
			split2.setVisible(false);
			
			goToNextPlayer();
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
		
		while((dealerPoints < 16 && (playerPoints < 16 || playerPoints > 21)) || (dealerPoints < 17 && (playerPoints >= 16 && playerPoints <= 21))) {
			
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
	
	//Lägger till pengar till fil och skriver ut på skärmen om man vinner
	public void winMoney() throws IOException{
		spelarPanel.setPlayerWinText();
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
	
	//Starta nytt spel. Ej färdigställd metod
	public void startNewGame() throws IOException{

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
				sAL.setMoney(sAL.getMoney() - satsning);
				sAL.Save();
			}
		}
		
		spelarPanel.setBet(satsning);	//lägger in satsningen till spelarpanelen
	}
	
	public int getSatsning() {
		return this.satsning;
	}
	
	public void goToNextPlayer() {
		currentPlayer += 1;
	
		if (currentPlayer > numberOfPlayers) {
			currentPlayer = 1;
		}
		
		if (currentPlayer == 1) {
			if (spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) {
				currentPlayer = 2;
			}
		} else if (currentPlayer == 2) {
			if (spelarPanel2.getPlayerIsBust() == true || spelarPanel2.getPlayerStands() == true) {
				currentPlayer = 1;
			}
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
		} else if (numberOfPlayers == 2 && ((spelarPanel.getPlayerIsBust() == true || spelarPanel.getPlayerStands() == true) && (spelarPanel2.getPlayerIsBust() == true || spelarPanel2.getPlayerStands() == true))) {
			dealerDraw();
			try {
				determineWinner();
				spelarPanel.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
				spelarPanel2.didPlayerWin(dealerPoints, dealerPointsAce, dealerAceNo);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
