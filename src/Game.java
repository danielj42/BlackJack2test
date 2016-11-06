import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Game {	

	private List<Card> playerCardsA = new ArrayList<Card>();// Om det är mer än en spelare, får de varsin ArrayList av kort
	private List<Card> playerCardsB = new ArrayList<Card>();
	private List<Card> playerCardsC = new ArrayList<Card>();
	private List<Card> playerCardsD = new ArrayList<Card>();
	private List<Card> dealerCards = new ArrayList<Card>();	// dealers kort
	private List<Player> players = new ArrayList<Player>(); // spelarnas uppgifter sparas här.
	
	private Deck deck = new Deck();
	private String version = "1.1";	// 1.1, 1.2, etc
	private Player playerA = new Player();
	private Player playerB = new Player();
	private Player playerC = new Player();
	private Player playerD = new Player();
	private Player dealer = new Player();
	private int numOfPlayers = 0;	// antal spelare i spelet som spelar samtidigt
	private int lastDealerCardIndex = -1;
	private int lastPlayerACardIndex = -1;
	private int lastPlayerBCardIndex = -1;
	private int lastPlayerCCardIndex = -1;
	private int lastPlayerDCardIndex = -1;
//	private SaveAndLoad saveAndLoad = new SaveAndLoad();
	private boolean playerAWinner = false;
	private boolean playerBWinner = false;
	private boolean playerCWinner = false;
	private boolean playerDWinner = false;
	private boolean dealerWinsPlayerA = false;
	private boolean dealerWinsPlayerB = false;
	private boolean dealerWinsPlayerC = false;
	private boolean dealerWinsPlayerD = false;
	private boolean blackjack = false;	// om någon av spelarna eller dealer får blackjack
	private boolean blackjackDealer = false; // om dealer får blackjack
	private boolean blackjackPlayerA = false;
	private boolean blackjackPlayerB = false;
	private boolean blackjackPlayerC = false;
	private boolean blackjackPlayerD = false;
	private boolean drawPlayerA = false;	//oavgjord
	private boolean drawPlayerB = false;
	private boolean drawPlayerC = false;
	private boolean drawPlayerD = false;
	private boolean hitPlayerA = true;
	private boolean hitPlayerB = true;
	private boolean hitPlayerC = true;
	private boolean hitPlayerD = true;
	private boolean stayPlayerA = false;
	private boolean stayPlayerB = false;
	private boolean stayPlayerC = false;
	private boolean stayPlayerD = false;
	
	// Konstruktorn
	public Game(){
		
		try {
			loadFromFile();
		} 
		catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public String[] getNameOfPlayers(){
		
		String s;
		Iterator<Player> pIterator = players.iterator();
		int size = players.size();
		String[] nameOfPlayers = new String[size];
		int count = 0;
		
		while(pIterator.hasNext()){
			Player p = (Player) pIterator.next();
			s = p.getName();
			nameOfPlayers[count] = s;
			++count;
		}
		return nameOfPlayers;
	}
	
	// Välj spelare, börja med A, B, C, D
	public void choosePlayer(char c, String playerName){
		
		Iterator<Player> pIterator = players.iterator();
		
		while(pIterator.hasNext()){
			Player p = (Player) pIterator.next();
			if(p.getName() == playerName){
				
				switch(c){
				case('A'):
					playerA = p;
					break;
				case('B'):
					playerB = p;					
					break;
				case('C'):
					playerC = p;					
					break;
				case('D'):
					playerD = p;						
				}
			}			
		}
		++numOfPlayers;
	}
	// Har ändrat namnet från addPlayerToList till addNewPlayer
	public void addNewPlayer(String name, int credit) throws IOException{
		Player p = new Player(name, credit, 0);
		players.add(p);
		saveToFile();
	}
	
	public int getPlayerAPoints(){
		return playerA.getPoints();
	}
	public int getPlayerBPoints(){
		return playerB.getPoints();
	}
	public int getPlayerCPoints(){
		return playerC.getPoints();
	}
	public int getPlayerDPoints(){
		return playerD.getPoints();
	}
	public int getDealerPoints(){
		return dealer.getPoints();
	}
	public boolean getHitPlayerA(){
		return hitPlayerA;
	}
	public void setHitPlayerA(boolean h){
		hitPlayerA = h;
	}
	public boolean getHitPlayerB(){
		return hitPlayerB;
	}
	public void setHitPlayerB(boolean h){
		hitPlayerB = h;
	}
	public boolean getHitPlayerC(){
		return hitPlayerC;
	}
	public void setHitPlayerC(boolean h){
		hitPlayerC = h;
	}
	public boolean getHitPlayerD(){
		return hitPlayerD;
	}
	public void setHitPlayerD(boolean h){
		hitPlayerD = h;
	}	

	public void resetPlayerCardsA(){
		playerCardsA.clear();
	}
	public void resetPlayerCardsB(){
		playerCardsB.clear();
	}
	public void resetPlayerCardsC(){
		playerCardsC.clear();
	}
	public void resetPlayerCardsD(){
		playerCardsD.clear();
	}
	public String getVersion(){
		return version;
	}
	public void setVersion(String vers){
		version = vers;
	}
	public int getNumOfPlayers(){
		return numOfPlayers;
	}		
	public boolean checkDrawPlayerA(){
		return drawPlayerA;
	}	
	public boolean checkDrawPlayerB(){
		return drawPlayerB;
	}	
	public boolean checkDrawPlayerC(){
		return drawPlayerC;
	}	
	public boolean checkDrawPlayerD(){
		return drawPlayerD;
	}	
	public int getBetPlayerA(){
		return playerA.getbet();
	}
	public void setBetPlayerA(int b){
		playerA.setBet(b);
	}
	public int getBetPlayerB(){
		return playerB.getbet();
	}	
	public void setBetPlayerB(int b){
		playerB.setBet(b);
	}
	public int getBetPlayerC(){
		return playerC.getbet();
	}
	public void setBetPlayerC(int b){
		playerC.setBet(b);
	}
	public int getBetPlayerD(){
		return playerD.getbet();
	}
	public void setBetPlayerD(int b){
		playerD.setBet(b);
	}
	
	public String dealCardToDealer(){
		
		if(dealer.getPoints() < 17){
			++lastDealerCardIndex;
			String tmpString;
			
			Card card = deck.getCard();
			dealerCards.add(lastDealerCardIndex, card);
			tmpString = card.getFaceName() + card.getSuit().name();
			dealer.updatePoints(card.getFaceName().getFaceValue()); 			

			return tmpString;
		}
		else
			return "";
	}
	
	public String dealCardToPlayerA(){		
		++lastPlayerACardIndex;
		String tmpString;
		
		Card card = deck.getCard();
		playerCardsA.add(lastPlayerACardIndex, card);
		tmpString = card.getFaceName() + card.getSuit().name();
		playerA.updatePoints(card.getFaceName().getFaceValue());
		
		return tmpString;
	}
	
	public String dealCardToPlayerB(){		
		++lastPlayerBCardIndex;
		String tmpString;
		
		Card card = deck.getCard();
		playerCardsB.add(lastPlayerBCardIndex, card);
		tmpString = card.getFaceName() + card.getSuit().name();
		playerB.updatePoints(card.getFaceName().getFaceValue());
		
		return tmpString;
	}
	
	public String dealCardToPlayerC(){		
		++lastPlayerCCardIndex;
		String tmpString;
		
		Card card = deck.getCard();
		playerCardsC.add(lastPlayerCCardIndex, card);
		tmpString = card.getFaceName() + card.getSuit().name();
		playerC.updatePoints(card.getFaceName().getFaceValue());
		
		return tmpString;
	}
	
	public String dealCardToPlayerD(){		
		++lastPlayerDCardIndex;
		String tmpString;
		
		Card card = deck.getCard();
		playerCardsD.add(lastPlayerACardIndex, card);
		tmpString = card.getFaceName() + card.getSuit().name();
		playerD.updatePoints(card.getFaceName().getFaceValue());
		
		return tmpString;
	}
	
	public boolean hasDealerWinsPlayerA(){
		checkWinner();
		return dealerWinsPlayerA;
	}	
	public boolean hasDealerWinsPlayerB(){
		checkWinner();
		return dealerWinsPlayerB;
	}	
	public boolean hasDealerWinsPlayerC(){
		checkWinner();
		return dealerWinsPlayerC;
	}	
	public boolean hasDealerWinsPlayerD(){
		checkWinner();
		return dealerWinsPlayerD;
	}	
	public boolean hasPlayerAWon(){
		checkWinner();
		return playerAWinner;
	}	
	public boolean hasPlayerBWon(){
		checkWinner();
		return playerBWinner;
	}
	public boolean hasPlayerCWon(){
		checkWinner();
		return playerCWinner;
	}	
	public boolean hasPlayerDWon(){
		checkWinner();
		return playerDWinner;
	}		
	
	public void checkBlackjack(){		
		
		if(playerA.getPoints() == 21 && playerCardsA.size() < 3 ){ 
			playerAWinner = true;			
			blackjackPlayerA = true;
			blackjack = true;
			playerA.winBlackjack();	// får vinst med (3/2 * bet)
		}		
		else if(playerB.getPoints() == 21 && playerCardsB.size() < 3 ){ 
			playerBWinner = true;			
			blackjackPlayerB = true;
			blackjack = true;
			playerB.winBlackjack();
		}
		else if(playerC.getPoints() == 21 && playerCardsC.size() < 3 ){ 
			playerCWinner = true;			
			blackjackPlayerC = true;
			blackjack = true;
			playerC.winBlackjack();
		}
		else if(playerD.getPoints() == 21 && playerCardsD.size() < 3 ){ 
			playerDWinner = true;			
			blackjackPlayerD = true;
			blackjack = true;
			playerD.winBlackjack();
		}		
		else if(dealer.getPoints() == 21 &&dealerCards.size() < 3 ){ 
			dealerWinsPlayerA = true;
			dealerWinsPlayerB = true;
			dealerWinsPlayerC = true;
			dealerWinsPlayerD = true;			
			blackjackDealer = true;
			blackjack = true;
			playerA.setBet(0);
			playerB.setBet(0);
		}		
	}	
	
	private void checkWinner(){
		
		if(version == "1.1"){			
				
			if(!blackjackPlayerA && !stayPlayerA && !playerAWinner){
					
				if(playerA.getPoints() == 21 && (dealer.getPoints() < 21 || dealer.getPoints() > 21)){
					playerAWinner = true;
					playerA.winBet();
				}
				else if(playerA.getPoints() > dealer.getPoints() && playerA.getPoints() < 22){
					playerAWinner = true;
					playerA.winBet();
				}
				else if(playerA.getPoints() == dealer.getPoints()){
					if(playerA.getPoints() >= 17 && playerA.getPoints() <= 19){
						dealerWinsPlayerA = true; // dealer vinner mot player A
						playerA.setBet(0);
					}			
					else if(playerA.getPoints() >= 20 && playerA.getPoints() <= 21){
						drawPlayerA = true; //Oavgjort
					}						
				}				
			}
			else if(!blackjackPlayerA && stayPlayerA && !playerAWinner && dealer.getPoints() > playerA.getPoints()){
				dealerWinsPlayerA = true;
				playerA.setBet(0);
			}
			
			
			if(numOfPlayers > 1){
				
				if(!blackjackPlayerB && !stayPlayerB && !playerBWinner){
					
					if(playerB.getPoints() == 21 && (dealer.getPoints() < 21 || dealer.getPoints() > 21)){
						playerBWinner = true;
						playerB.winBet();
					}
					else if(playerB.getPoints() > dealer.getPoints() && playerB.getPoints() < 22){
						playerBWinner = true;
						playerB.winBet();
					}
					else if(playerB.getPoints() == dealer.getPoints()){
						if(playerB.getPoints() >= 17 && playerB.getPoints() <= 19){
							dealerWinsPlayerB = true; // dealer vinner mot player B
							playerB.setBet(0);
						}			
						else if(playerB.getPoints() >= 20 && playerB.getPoints() <= 21){
							drawPlayerB = true; //Oavgjort
						}						
					}				
				}
				else if(!blackjackPlayerB && stayPlayerB && !playerBWinner && dealer.getPoints() > playerB.getPoints()){
					dealerWinsPlayerB = true;
					playerB.setBet(0);
				}
				
			}

			if(numOfPlayers > 2){
				
				if(!blackjackPlayerC && !stayPlayerC && !playerCWinner){
					
					if(playerC.getPoints() == 21 && (dealer.getPoints() < 21 || dealer.getPoints() > 21)){
						playerCWinner = true;
						playerC.winBet();
					}
					else if(playerC.getPoints() > dealer.getPoints() && playerC.getPoints() < 22){
						playerCWinner = true;
						playerC.winBet();
					}
					else if(playerC.getPoints() == dealer.getPoints()){
						if(playerC.getPoints() >= 17 && playerC.getPoints() <= 19){
							dealerWinsPlayerC = true; // dealer vinner mot player C
							playerC.setBet(0);
						}			
						else if(playerC.getPoints() >= 20 && playerC.getPoints() <= 21){
							drawPlayerC = true; //Oavgjort
						}						
					}				
				}
				else if(!blackjackPlayerC && stayPlayerC && !playerCWinner && dealer.getPoints() > playerC.getPoints()){
					dealerWinsPlayerC = true;
					playerC.setBet(0);
				}
				
			}
			
			if(numOfPlayers > 3){
				
				if(!blackjackPlayerD && !stayPlayerD && !playerDWinner){
					
					if(playerD.getPoints() == 21 && (dealer.getPoints() < 21 || dealer.getPoints() > 21)){
						playerDWinner = true;
						playerD.winBet();
					}
					else if(playerD.getPoints() > dealer.getPoints() && playerD.getPoints() < 22){
						playerDWinner = true;
						playerD.winBet();
					}
					else if(playerD.getPoints() == dealer.getPoints()){
						if(playerD.getPoints() >= 17 && playerD.getPoints() <= 19){
							dealerWinsPlayerD = true; // dealer vinner mot player D
							playerD.setBet(0);
						}			
						else if(playerD.getPoints() >= 20 && playerD.getPoints() <= 21){
							drawPlayerD = true; //Oavgjort
						}						
					}				
				}
				else if(!blackjackPlayerD && stayPlayerD && !playerDWinner && dealer.getPoints() > playerD.getPoints()){
					dealerWinsPlayerD = true;
					playerD.setBet(0);
				}
				
			}
			
		}
		
		else if(version == "1.2"){
			// Kommer snart!
			
			
			
		}
		else if(version == "1.3"){
			//Kommer snart!
			
			
			
		}
		
	}
	
	public void finishGame(){
		
		try {
			saveToFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	// i väntan på en fungerande SaveAndLoad klass
	public void saveToFile() throws IOException{
		File file = new File("src/saveX.txt");
		Iterator<Player> pIterator = players.iterator();
		
		
		// om filen inte finns, skapa den
		if (!file.exists())				
			file.createNewFile();				
				
		FileWriter fw = new FileWriter(file.getAbsoluteFile());				
		BufferedWriter bw = new BufferedWriter(fw);			
			
		while(pIterator.hasNext()){
			Player p = (Player) pIterator.next();			
			bw.write(p.getName() + " " + Integer.toString(p.getCredit()));
			if(pIterator.hasNext())
				bw.write("\n");
		}
		bw.close();
				
	}
	
	// i väntan på en fungerande SaveAndLoad klass
	public void loadFromFile() throws IOException{
		File file = new File("src/saveX.txt");
		
		Scanner sc = new Scanner(file);		
		String s1 = "", s2 = "";
        
		while(sc.hasNextLine()){
			s1 = sc.next();
			s2 = sc.next();
			Player p = new Player(s1, Integer.parseInt(s2), 0);
			players.add(p);
		}        
		sc.close();		
	}
	
	
	
	
	
	
	// **************TESTER*************************************
	

	public void playersTest(){
		Iterator<Player> pIterator = players.iterator();
		while(pIterator.hasNext()){
			Player p = (Player) pIterator.next();
			System.out.println(p.getName() + " " + p.getCredit() + " " + p.getPoints() + " " + p.getbet());
		}
		
	}
	
	public void playerCardsATest(){
		Iterator<Card> pIterator = playerCardsA.iterator();
		while(pIterator.hasNext()){
			Card c = (Card) pIterator.next();
			System.out.println(c.getFaceName() + c.getSuit().name());
		}
		
	}
	
	public void playerCardsBTest(){
		Iterator<Card> pIterator = playerCardsB.iterator();
		while(pIterator.hasNext()){
			Card c = (Card) pIterator.next();
			System.out.println(c.getFaceName().getFaceValue() + c.getSuit().name());
		}
		
	}
	
	public void dealerCardsTest(){
		Iterator<Card> pIterator = dealerCards.iterator();
		while(pIterator.hasNext()){
			Card c = (Card) pIterator.next();
			System.out.println(c.getFaceName().getFaceValue() + c.getSuit().name());
		}
		
	}

}


