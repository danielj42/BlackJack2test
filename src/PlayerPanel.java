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
import java.util.Iterator;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
	Deck deck = new Deck();
	SaveAndLoad sAL = new SaveAndLoad();
	Card aCard;
	
	boolean playerIsBust = false;
	int playerNumber;
	boolean playerStands = false;
	int playerPoints = 0;
	int playerAceNo = 0;
	int playerPointsAce = 0;
	int bet = 333;
	int cardCounter = 2;
	int cardImageH = 175;
	int cardImageW = 120;
	
	Random random = new Random();
	
	//subpaneler och komponenter till playerspace
	JPanel playerCol = new JPanel(); //OBS!! testversion med bara en kolumn!!! Skall itereras för varje spelare vid fler spelare
	JPanel playerCards = new JPanel();
	JLabel playerPointsRow = new JLabel(Integer.toString(playerPoints), JLabel.CENTER);
	JLabel playerBet = new JLabel(Integer.toString(bet) + " kr", JLabel.CENTER);
	JPanel playerButtons = new JPanel();
	JLabel playerTitle = new JLabel("Spelare 1", JLabel.CENTER); //Byt ut
	JLabel pCard1 = new JLabel();
	JLabel pCard2 = new JLabel();
	JPanel playerChips = new JPanel();
	
	public PlayerPanel() {
		
		//drar två kort till spelaren
		drawCard(pCard1, playerPoints, playerPointsRow);
		drawCard(pCard2, playerPoints, playerPointsRow);
		
		//layout
		playerCol.setLayout(new BoxLayout(playerCol, BoxLayout.Y_AXIS));
		playerPointsRow.setAlignmentX(CENTER_ALIGNMENT);
		playerBet.setAlignmentX(CENTER_ALIGNMENT);
		playerTitle.setAlignmentX(CENTER_ALIGNMENT);
		playerChips.setLayout(new FlowLayout());
		
		this.add(playerCol);
		playerCol.add(playerCards);
			playerCards.add(pCard1);
			playerCards.add(pCard2);
		playerCol.add(playerPointsRow);
		playerCol.add(playerBet);
		playerCol.add(playerChips);
		playerCol.add(playerButtons);
		playerCol.add(playerTitle);
		
		placeChips(bet);	//lägger ut grafik för spelmarker

		//ställer in fonter
		playerBet.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerBet.setForeground(Color.yellow);
		playerTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerTitle.setForeground(Color.white);
		playerPointsRow.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerPointsRow.setForeground(Color.white);
		
		playerBet.setText(Integer.toString(bet) + " kr");
		
		//Bakgrundsfärg
		this.setBackground(Color.decode("0x00B000"));
		playerCol.setBackground(Color.decode("0x00B000"));
		playerCol.setOpaque(true);
		playerCards.setBackground(Color.decode("0x00B000"));
		playerButtons.setBackground(Color.decode("0x00B000"));
		playerChips.setBackground(Color.decode("0x00B000"));
	}
	
	//Drar ett nytt kort och ändrar grafik, summerar poängvärde
	public void drawCard(JLabel cImg, int cPoints, JLabel cPointsText) {
		aCard = deck.getCard();
		String currentCardName = "" + aCard.getFaceName();
	    Image cImage = new ImageIcon("src/cardimages/" + aCard.getFaceName() + aCard.getSuit() + ".png").getImage();
		cImg.setIcon(new ImageIcon(getScaledImage(cImage, cardImageW, cardImageH)));
		
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
			playerPointsAce += aCard.getFaceName().getFaceValue();
			playerPointsRow.setText(Integer.toString(playerPointsAce) + " / " + Integer.toString(playerPoints));
		}
		
		if (playerPoints > 21 && playerAceNo == 0) {
			playerIsBust = true;
			setPlayerBustText();
		} else if (playerAceNo > 0 && playerPointsAce > 21) {
			playerIsBust = true;
			setPlayerBustText();
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
	
	public void setBet(int satsning) {
		bet = satsning;
		playerBet.setText(Integer.toString(bet));
	}
	
	public void setPlayerPoints(int pPoints) {
		this.playerPoints = pPoints;
	}
	
	public int getPlayerPoints() {
		return playerPoints;
	}
	
 	public void setPlayerLoseText() {
 		playerTitle.setText("Spelare " + playerNumber + " förlorade");
 		playerTitle.setForeground(Color.red);
	
 	}
 	
 	public void setPlayerBustText() {
 		playerTitle.setText("Spelare " + playerNumber + " BUST");
 		playerTitle.setForeground(Color.red);
 	}
 	
 	public void setPlayerWinText() {
 		playerTitle.setText("Spelare " + playerNumber + " vann " + bet * 2 + " kronor! Hen har nu " + (sAL.getMoney() + bet * 2) + " kr i kassan");
		playerTitle.setForeground(Color.yellow);
 	}
 	
 	public void setPlayerLikaomgangText() throws IOException{
 		playerTitle.setText("Spelare " + playerNumber + " likaomgång. Spelaren får tillbaka sin insats, har nu " + (sAL.getMoney() + bet) + " i kassan");
 		playerTitle.setForeground(Color.white);	
 		int kassa = sAL.getMoney();
 		sAL.setMoney(bet + kassa);
 		sAL.Save();
 	}
 	
 	public void setNewPlayerCard() {
 		JLabel pCard3 = new JLabel();
 		playerCards.add(pCard3);
 		drawCard(pCard3, playerPoints, playerPointsRow);
 	}
 	
 	public int getPlayerAceNo() {
 		return playerAceNo;
 	}
 	
 	public int getPlayerPointsAce() {
 		return playerPointsAce;
 	}
 	
 	public boolean getPlayerStands() {
 		return playerStands;
 	}
 	
 	public void setPlayerStands(boolean stands) {
 		playerStands = stands;
 	}
 	
 	public boolean getPlayerIsBust() {
 		return playerIsBust;
 	}
 	
 	public void didPlayerWin(int dealerPoints, int dealerPointsAce, int dealerAceNo) {
 		//Metod för att bestämma om spelaren vann mot dealern
		
		//om spelaren har dragit ett ess och blivit bust med det används esspoängen istället
		if (playerPoints > 21 && playerAceNo > 0) {
			playerPoints = playerPointsAce;
		}
		
		if(dealerPoints <= 21 && playerPoints <= 21) {
			if (dealerPoints > playerPoints) {
					setPlayerLoseText();
				} else if (playerPoints > dealerPoints){
					setPlayerWinText();
		//				winMoney();
				} else if ((dealerPoints == 20 && playerPoints == 20) || (dealerPoints == 21 && playerPoints == 21)) {
					try {
						setPlayerLikaomgangText();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (dealerPoints == playerPoints) {
						setPlayerLoseText();
				}
		} else if (dealerPoints <= 21) {
			setPlayerBustText();
		} else if (playerPoints <= 21) {
			setPlayerWinText();
//			winMoney();
		}  else if (dealerPoints > 21 && playerPoints > 21) {
			setPlayerBustText();
		}
	}
 	
 	public void setPlayerNumber(int n) {
 		playerNumber = n;
 	}
 	
 	public void setCardImageW(int width) {
 		this.cardImageW = width;
 	}
 	
 	public void setCardImageH(int height) {
 		this.cardImageH = height;
 	}
}
