
public class Card {
	private Suit suit;
	private Face face;

	public Card(Suit suit, Face face) {
	    this.suit = suit;
	    this.face = face;
	 
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}
	
}
