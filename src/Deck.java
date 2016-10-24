
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
 
public class Deck
{
  private ArrayList deck;
 
  public Deck ()
  {
    this.deck = new ArrayList();
    for (int i=0; i<13; i++)
    {
      Face face = Face.values()[i];
 
      for (int j=0; j<4; j++)
      {
        Card card = new Card(face, Suit.values()[j]);
        this.deck.add(face);
      }
    }
 
    Collections.shuffle(deck);
 
    Iterator cardIterator = deck.iterator();
    while (cardIterator.hasNext())
    {
      Card aCard = (Card) cardIterator.next();
      System.out.println(aCard.getFaceValue() + " of " + aCard.getSuit());
    }
  }
}
