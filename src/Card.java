
public class Card {
	private Suit suit;
	private Face faceValue;
	
	public Card(Face face,Suit suit) {
		this.suit = suit;
		this.faceValue = face;
	}
	public Suit getSuit() {
		return suit;
	}
	
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public Face getFaceValue() {
		return faceValue;
	}
	
	public void setFaceValue() {
		this.faceValue = faceValue;
	}
	
}
