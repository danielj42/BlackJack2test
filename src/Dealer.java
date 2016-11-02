import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Dealer {
	Scanner scanner = new Scanner(System.in);
	Player player = new Player();
	
	private ArrayList deck;
	Iterator cardIterator;
	int cardCounter;

	public Dealer(){			
		   
	}
	
	//metod som get ett kort from deck och returnera det
	public Card getCardFromDeck(){
		System.out.println("Hej inne i från getCardFromDeck");
		Card cardFromIterator = (Card) cardIterator.next();
		cardCounter =+ 1;
		return cardFromIterator;
		
		}

	}

	