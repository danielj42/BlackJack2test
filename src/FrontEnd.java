import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrontEnd extends JFrame implements ActionListener{
	//ta bort när en game-klass finns
	private ArrayList deck;	//lista för deck
	
	int dealerPoints = 0;
	int playerPoints = 0;
	int cardImageH = 200;
	int cardImageW = 135;
	int cardCounter = 2;
	public int satsning = 100;
	boolean allPlayersStand = false;
	
	JPanel body = new JPanel();	//huvudpanel

	JPanel dealerSpace = new JPanel();	//tre subpaneler
	JPanel middleSpace = new JPanel();
	JPanel playerSpace = new JPanel();

	JPanel dealerCardSpace = new JPanel();	//subpaneler och komponenter till dealerSpace
	JPanel dealerCardsRow = new JPanel();
	JPanel dealerPointsRow = new JPanel();
	JLabel dealerPointsLabel = new JLabel(Integer.toString(dealerPoints), JLabel.CENTER);
	JLabel dCard1 = new JLabel();
	JLabel dCard2 = new JLabel();
	
	//subpaneler oc komponenter till playerspace
	JPanel playerCol = new JPanel(); //OBS!! testversion med bara en kolumn!!! Skall itereras för varje spelare vid fler spelare
	JPanel playerCards = new JPanel();
	JLabel playerPointsRow = new JLabel(Integer.toString(playerPoints), JLabel.CENTER);
	JLabel playerBet = new JLabel(Integer.toString(satsning) + " kr", JLabel.CENTER);
	JPanel playerButtons = new JPanel();
	JLabel playerTitle = new JLabel("Spelare 1", JLabel.CENTER); //Byt ut
	JLabel pCard1 = new JLabel();
	JLabel pCard2 = new JLabel();

	JButton hit = new JButton("Hit");
	JButton stand = new JButton("Stand");
	JButton split = new JButton("Split");
	
	//kontainers och komponenter till middlespace
	JLabel infoText = new JLabel("Dealern drar till 16 och måste stanna på 17", JLabel.CENTER);
	JLabel divider = new JLabel(new ImageIcon("src/cardimages/divider.png"));
	
	public FrontEnd() {	
		
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
	    
	    Card currentCard = drawCard(cardIterator);
	    
	    //Byts ut senare
		Image c1 = new ImageIcon("src/cardimages/" + currentCard.getFaceValue() + currentCard.getSuit() + ".png").getImage();
		dCard1.setIcon(new ImageIcon(getScaledImage(c1, cardImageW, cardImageH)));
		
		String currentCardName = "" + currentCard.getFaceValue();
		System.out.println(Enum.valueOf(Face.class, currentCardName));
//		dealerPoints += Enum.valueOf(Face.class, currentCardName);
		dealerPoints += addValue(currentCardName, dealerPoints, dealerPointsLabel);
		
	    Image c2 = new ImageIcon("src/cardimages/CARDBACK.jpg").getImage();
		dCard2.setIcon(new ImageIcon(getScaledImage(c2, cardImageW, cardImageH)));
		
		currentCard = drawCard(cardIterator);
	    Image c3 = new ImageIcon("src/cardimages/" + currentCard.getFaceValue() + currentCard.getSuit() + ".png").getImage();
		pCard1.setIcon(new ImageIcon(getScaledImage(c3, cardImageW, cardImageH)));
		currentCardName = "" + currentCard.getFaceValue();
		playerPoints += addValue(currentCardName, playerPoints, playerPointsRow);
		
		currentCard = drawCard(cardIterator);
	    Image c4 = new ImageIcon("src/cardimages/" + currentCard.getFaceValue() + currentCard.getSuit() + ".png").getImage();
		pCard2.setIcon(new ImageIcon(getScaledImage(c4, cardImageW, cardImageH)));
		currentCardName = "" + currentCard.getFaceValue();
		playerPoints += addValue(currentCardName, playerPoints, playerPointsRow);

		setTitle("Black Jack - Team 2");
		
		body.setLayout(new BorderLayout());	//definierar layout
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
		
		add(body);	//Lägger ut containers och komponenter
			body.add(dealerSpace, BorderLayout.NORTH);
			body.add(middleSpace, BorderLayout.CENTER);
			body.add(playerSpace, BorderLayout.SOUTH);
		
		dealerSpace.add(dealerCardSpace);
			dealerCardSpace.add(dealerCardsRow, BorderLayout.NORTH);
				dealerCardsRow.add(dCard1);
				dealerCardsRow.add(dCard2);
			dealerCardSpace.add(dealerPointsRow, BorderLayout.SOUTH);
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
			playerCol.add(playerButtons);
				playerButtons.add(hit);
				playerButtons.add(stand);
				playerButtons.add(split);
			playerCol.add(playerTitle);
			
		hit.addActionListener(this);	//lyssnare till knappen "hit"
		stand.addActionListener(this);	//lyssnare till knappen "stand"
			
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
		
		split.setEnabled(false);
		
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
		
		setSize(1000, 800);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	
	//Byts ut när det finns fungerande andra klasser
	public Card drawCard(Iterator cardIterator) {
	    Card aCard = (Card) cardIterator.next();
	    return aCard;
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == hit && allPlayersStand == false) {	//lyssnare för när man trycker på "hit"
			cardCounter += 1;
			Iterator cardIterator = deck.iterator();
			
			for (int i = 0; i <= cardCounter; i++) {
				Card aCard = (Card) cardIterator.next();
			}
			
			Card currentCard = drawCard(cardIterator);
		    Image c3 = new ImageIcon("src/cardimages/" + currentCard.getFaceValue() + currentCard.getSuit() + ".png").getImage();
			JLabel pCard3 = new JLabel(new ImageIcon(getScaledImage(c3, cardImageW, cardImageH)));
			playerCards.add(pCard3);
			String currentCardName = "" + currentCard.getFaceValue();
			playerPoints += addValue(currentCardName, playerPoints, playerPointsRow);
			
			repaint();
			revalidate();
			
			if (playerPoints >21) {
				dealerDraw();
				determineWinner();
			}
		}
		
		if (e.getSource() == stand && allPlayersStand == false) {	//lyssnare för stand
			allPlayersStand = true;
			hit.setEnabled(false);
			stand.setEnabled(false);
			
			dealerDraw();
			determineWinner();
		}
	}
	
	public int addValue(String sFace, int sPoints, JLabel pLabel) {	//Placeholder
		int points = 0;
		
		if (sFace.equals("TVÅ")){
			points = 2;
		} else if (sFace.equals("TRE")){
			points = 3;
		} else if (sFace.equals("FYRA")){
			points = 4;
		} else if (sFace.equals("FEM")){
			points = 5;
		} else if (sFace.equals("SEX")){
			points = 6;
		} else if (sFace.equals("SJU")){
			points = 7;
		} else if (sFace.equals("ÅTTA")){
			points = 8;
		} else if (sFace.equals("NIO")){
			points = 9;
		} else if (sFace.equals("TIO")){
			points = 10;
		} else if (sFace.equals("KNEKT")){
			points = 10;
		} else if (sFace.equals("DAM")){
			points = 10;
		} else if (sFace.equals("KUNG")){
			points = 10;
		} else if (sFace.equals("ESS")){
			points = 11;
		}
		
		pLabel.setText(Integer.toString(sPoints + points));
		return points;
	}
	
	public void determineWinner() {
		allPlayersStand = true;
		hit.setEnabled(false);
		stand.setEnabled(false);
		
		if(dealerPoints <= 21 && playerPoints <= 21) {
			if (dealerPoints > playerPoints) {
				System.out.println("Du förlorade tyvärr!");
			} else if (playerPoints > dealerPoints){
				System.out.println("Du vann!");
			}
		} else if (dealerPoints <= 21) {
			System.out.println("Du blev tjock. Dealern vann.");
		} else if (playerPoints <= 21) {
			System.out.println("Dealern blev tjock. Du vann!");
		} else if (dealerPoints == playerPoints) {
			System.out.println("Det blev en likaomgång.");
		}
	}
	
	public void dealerDraw(){
		Iterator cardIterator = deck.iterator();
		dCard2.setVisible(false);
		
		while((dealerPoints < 16 && (playerPoints < 16 || playerPoints > 21)) || (dealerPoints < 17 && (playerPoints >= 16 && playerPoints <= 21))) {
			cardCounter += 1;
			
			for (int i = 0; i <= cardCounter; i++) {
				Card aCard = (Card) cardIterator.next();
			}
			
			Card currentCard = drawCard(cardIterator);
		    Image c4 = new ImageIcon("src/cardimages/" + currentCard.getFaceValue() + currentCard.getSuit() + ".png").getImage();
			JLabel pCard4 = new JLabel(new ImageIcon(getScaledImage(c4, cardImageW, cardImageH)));
			dealerCardsRow.add(pCard4);
			String currentCardName = "" + currentCard.getFaceValue();
			dealerPoints += addValue(currentCardName, dealerPoints, dealerPointsLabel);
			
			repaint();
			revalidate();
		}
	}
}
