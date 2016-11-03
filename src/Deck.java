import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
 
public class Deck
{
  private ArrayList <Card> deck;
  
  public Deck ()
  {
    
	fillDeck();
//  getCard();
//  System.out.println(getCard().getCardValue() +" "+ getCard().getSuit());
  }
  
  public Card getCard(){
	  	
		Card aCard = deck.get(0); 
		deck.remove(0);
		return aCard;
	  	
	  }
  
  public void fillDeck(){
	  
	  this.deck = new ArrayList<Card>();
	    for (int i=0; i<13; i++)
	    {
	      Face face = Face.values()[i];
	 
	      for (int j=0; j<4; j++)
	      {
	        Card card = new Card(face, Suit.values()[j]);
	        this.deck.add(card);
	      }
	    }
	 
	    Collections.shuffle(deck);
  }
  
  public void Reset(){
	  deck.clear();
	  fillDeck();
	  
  }
}