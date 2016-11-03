
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
 
public class Deck
{
  private ArrayList<Card> deck;	//lista för deck
 
  public Deck ()
  {
    this.deck = new ArrayList<Card>();
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
 
    //För Testa utskrift
//    Iterator<Card> cardIterator = deck.iterator();
//    while (cardIterator.hasNext())
//    {
//      Card aCard = (Card) cardIterator.next();
//      //System.out.println(aCard.getFaceName() + " of " + aCard.getSuit());
//    }
//    
    
    
  }
  
//  public void getCard(){
//		 int i = (int)Math.random()*(deck.size());
//		 System.out.println("Kortet blev: "+deck.get(i));
//	  }
}
