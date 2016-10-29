/* Player.java
 * Spelare klassen för programmet Blackjack2
 * Java 16, Kursen agila metoder
 * Parviz Mozaffari
 */
 
public class Player {
	private String name;
	private int credit;
	int points;
	
	public Player(){
		name = "Unknown";
		credit = 0;
		points = 0;			
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
	
	public int getPlayerRoundPoints(){
		return points;
	}
	
	public void setPlayerRoundPoints(int p){
		points = p;
	}	
}
