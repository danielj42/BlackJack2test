import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

public class FrontEnd extends JFrame{
	Random randomGen = new Random();
	int dealerPoints = 0, playerPoints = 0;
	int nextPoints = 0;
	public int satsning;
	
	//Spara pengar
	SaveAndLoad sAL = new SaveAndLoad();
	
	public FrontEnd() throws IOException{
		Scanner sc = new Scanner(System.in);
		
		//Grafiskt gränssnitt
//		JFrame bJack = new JFrame();
//		JLabel spel = new JLabel("BlackJack Team 2");
//		JPanel dealerYta = new JPanel();
//		JPanel spelarYta = new JPanel();
//		
//		setLayout(new GridLayout (1, 2));
//		spel.add(dealerYta); hej
//		spel.add(spelarYta);
//		
//		spel.setBackground(Color.GREEN);
//		spel.setOpaque(true);
//		
//		setSize(900, 600);	//Storlek på fönstret (JLabeln)
//		setVisible(true);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Hämta hur mycket pengar man har i kassan
		
		sAL.Load();
		
		showCash();
		System.out.println("Hur mycket vill du satsa?");
		satsning = sc.nextInt();
		sAL.setMoney(sAL.getMoney() - satsning);
		showCash();
		sAL.Save();
		
		//Delar ut kort till spelare och dealer
		dealCards();
		dealCards();
		
		//Spel while-sats som körs tills spelaren har fler än 21 poäng och blir tjock
		while(playerPoints <= 21 && dealerPoints <= 21) {
			System.out.println("Vill du dra ett till kort? Mata in 1 för ja, 2 för stanna.");
		
			if (sc.next().equals("1")) {
				dealCards();
			} else {
				break;
			}
		}
		
		//Delar ut kort till dealern till hen har över 17 poäng
		while (dealerPoints<17) {
			dealerDraw();
		}
		
		//Skriver ut resultatet av omgången
		System.out.println("\nResultat av omgång:");
		System.out.printf("Dealern fick %d poäng. Spelaren fick %d poäng.\n", dealerPoints, playerPoints);
		
		if(dealerPoints <= 21 && playerPoints <= 21) {
			if (dealerPoints > playerPoints) {
				System.out.println("Du förlorade tyvärr!");
			} else if (playerPoints > dealerPoints){
				System.out.println("Du vann!");
				winMoney();
			}
		} else if (dealerPoints <= 21) {
			System.out.println("Du blev tjock. Dealern vann.");
		} else if (playerPoints <= 21) {
			System.out.println("Dealern blev tjock. Du vann!");
			winMoney();
		} else {
			System.out.println("Det blev en likaomgång.");
		}
		
	}
	
	public String getCardName(int points) {		//Ta bort denna och använd kort-enum
		String cardName = "";
		
		if (points >= 10) {
			int i = randomGen.nextInt(2) + 1;

			switch (i) {
			case 1: cardName = "kung";
					break;
			case 2: cardName = "dam";
					break;
			case 3: cardName = "knekt";
					break;
			} 
		}else if (points == 1) {
			cardName = "ess";
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
		
		if (dealerPoints <= 0) {
			nextPoints = randomGen.nextInt(10) + 1;
			dealerPoints += nextPoints;
			System.out.println("Dealern drog en " + getCardSuit() + " " + getCardName(nextPoints) + ". Hen har " + dealerPoints + " poäng.");
		} else {
			System.out.println("Dealern har " + dealerPoints + " poäng.");
		}
		
		nextPoints = randomGen.nextInt(10) + 1;
		playerPoints += nextPoints;
		System.out.println("Spelaren drog en " + getCardSuit() + " " + getCardName(nextPoints) + ". Hen har " + playerPoints + " poäng.\n");
	}
	
	public void dealerDraw() {	//Delar ut kort bara till dealern
		nextPoints = randomGen.nextInt(10) + 1;
		dealerPoints += nextPoints;
		System.out.println("Dealern drog en " + getCardSuit() + " " + getCardName(nextPoints) + ". Hen har " + dealerPoints + " poäng.");

	}
	
	public void winMoney() throws IOException{
		System.out.println("Du vann " + satsning * 2 + " kronor!");
		int kassa = sAL.getMoney();
		sAL.setMoney(satsning * 2 + kassa);
		showCash();
		sAL.Save();
	}
	
	public void showCash() {
		System.out.println("Du har " + sAL.getMoney() + " kr i kassan.");
	}
}
