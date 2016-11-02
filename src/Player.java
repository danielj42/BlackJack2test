/* Player.java
 * Spelare klassen för programmet Blackjack2
 * Java 16, Kursen agila metoder
 * Parviz Mozaffari
 */
 
public class Player {
	private String name;
	private int credit;
	int points;
	int bet;
	
	public Player(){
		name = "Unknown";
		credit = 0;
		points = 0;
		bet = 0;
	}
	
	public Player(String pName, int pCredit, int pPoints){
		name = pName;
		credit = pCredit;
		points = pPoints;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String aName){
		name = aName;
	}	
	public int getCredit() {
		return credit;
	}
	public void setCredit(int playerRoundPoints) {
		this.credit = playerRoundPoints;
	}	
	public int getPoints(){
		return points;
	}	
	public void updatePoints(int p){
		points += p;
	}	
	public int getbet(){
		return bet;
	}
	public void setBet(int b){
		bet = b;
	}
	public void bet(int b){
		credit -= b;
		bet = b;
	}
	public void winBet(){
		credit += (2 * bet);
		bet = 0;
	}
	public void winBlackjack(){
		credit += (3/2 * bet);
		bet = 0;
	}
}
