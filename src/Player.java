/* Player.java
 * Spelare klassen för programmet Blackjack2
 * Java 16, Kursen agila metoder
 * Parviz Mozaffari
 */
 
public class Player {
	
	private String name;
	private int credit;
	
	public Player(){
		name = "Unknown";
		credit = 0;
	}
	
	public Player(String aName, int someCredit){
		name = aName;
		credit = someCredit;
	}
	
	public setName(String aName){
		name = aName;
	}
	
	public String getName(){
		return name;
	}	
	
	public int getCredit(){
		return credit;
	}
	
	public void updateCredit(int aCredit){
		credit += nCredit;
	}

}
