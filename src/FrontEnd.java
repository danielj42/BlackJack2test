import java.util.Random;
import java.util.Scanner;

public class FrontEnd {
	Random randomGen = new Random();
	int dealerPoints = 0, playerPoints = 0;
	
	public FrontEnd() {
		Scanner sc = new Scanner(System.in);

		dealCards();

		while(playerPoints <= 21 && dealerPoints <= 21) {
			System.out.println("Vill du dra ett till kort? Mata in 1 för ja, 2 för stanna.");
		
			if (sc.next().equals("1")) {
				dealCards();
			} else {
				break;
			}
		}
		
		while (dealerPoints<16) {
			dealerDraw();
		}
		
		System.out.println("\nResultat av omgång:");
		System.out.printf("Dealern fick %d poäng. Spelaren fick %d poäng.\n", dealerPoints, playerPoints);
		
		if(dealerPoints <= 21 && playerPoints <= 21) {
			if (dealerPoints > playerPoints) {
				System.out.println("Du förlorade tyvärr!");
			} else if (playerPoints > dealerPoints){
				System.out.println("Du vann!");
			}
		} else if (dealerPoints <= 21) {
			System.out.println("Du blev tjock. Dealern vann.");
		} else if (playerPoints <= 21) {
			System.out.println("Dealern blev tjock. Du vann!");
		} else {
			System.out.println("Det blev en likaomgång.");
		}
		
	}
	
	public String getCardName(int points) {		//Ta bort denna och använd kort-enum
		String cardName = "";
		
		if (points > 10) {
			int i = randomGen.nextInt(2) + 1;

			switch (i) {
			case 1: cardName = "kung";
					break;
			case 2: cardName = "dam";
					break;
			case 3: cardName = "knekt";
					break;
			} 
		}else {
			cardName = Integer.toString(points);
		}
		
		return cardName;
	}
	
	public String getCardSuit() {		//Ta bort denna och använd kort-enum
		int i = randomGen.nextInt(3) + 1;
		String cardSuit = "";
		
		switch (i) {
		case 1: cardSuit = "hjärter";
				break;
		case 2: cardSuit = "spader";
				break;
		case 3: cardSuit = "klöver";
				break;
		case 4: cardSuit = "ruter";
				break;
		}
		
		return cardSuit;
	}

	public void dealCards() {	//Skall använda sig av kort-enum
		
		if (dealerPoints < 16) {
			dealerPoints += randomGen.nextInt(10) + 1;
			System.out.println("Dealern drog en " + getCardSuit() + " " + getCardName(dealerPoints) + ". Hen har " + dealerPoints + " poäng.");
		} else {
			System.out.println("Dealern har stannat. Hen har " + dealerPoints + " poäng.");
		}
		
		playerPoints += randomGen.nextInt(10) + 1;
		System.out.println("Spelaren drog en " + getCardSuit() + " " + getCardName(playerPoints) + ". Hen har " + playerPoints + " poäng.");
	}
	
	public void dealerDraw() {
		dealerPoints += randomGen.nextInt(10) + 1;
		System.out.println("Dealern drog en " + getCardSuit() + " " + getCardName(dealerPoints) + ". Hen har " + dealerPoints + " poäng.");

	}
	
}
