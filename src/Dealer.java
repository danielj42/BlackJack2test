import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dealer {
	Scanner scanner = new Scanner(System.in);
	Deck deck = new Deck();
	Player player = new Player();
	private List<Card> cards = new ArrayList<Card>();
	private List<Card> playersCard = new ArrayList();
	private List<Card> dealersCard = new ArrayList();
	private int dealerPoints;

	public Dealer(){	
		
	}

	public void shareCard(){
		System.out.println("Hej inne i från ShareCards");
		for(Card card : cards){
			playersCard.add(card);
			System.out.println("You get: " + playersCard.get(0));
			dealersCard.add(card);
			System.out.println("Dealer gets: " + dealersCard.get(0));
			playersCard.add(card);
			System.out.println("You get: " + playersCard.get(1));
			dealersCard.add(card);
			System.out.println("Would you like to \"hit\" or \"stay\"? ");
			checkHitOrStay();
		}

	}

	private void checkHitOrStay() {
		String hitStay = scanner.next();

		int cardCount = 4; 
		if (hitStay.equalsIgnoreCase("hit")){
			while (player.getPlayerRoundPoints() < 21 && hitStay.equalsIgnoreCase("hit")){
				if (hitStay.equalsIgnoreCase("hit")){
					System.out.println("You drew a " + (playersCard.get(0).getFace() +" "+ playersCard.get(0).getSuit() + "."));
					System.out.println("Your total is " + player.getPlayerRoundPoints());

				}else if(player.getPlayerRoundPoints() > 21){
					System.out.println("You are bust, You lose.");
					System.exit(0);
				}else if(player.getPlayerRoundPoints() == 21){
					System.out.println("Blackjack, you win.");
					System.exit(0);
				}else{
					System.out.print("Would you like to \"hit\" or \"stay\"? ");
					hitStay = scanner.next();
					System.out.println();        
				}

			}       
		}
	}

		public void rememberCard(){

		}

		public List<Card> getCards() {
			return cards;
		}

		public void setCards(List<Card> cards) {
			this.cards = cards;
		}

		public List<Card> getPlayersCard() {
			return playersCard;
		}

		public void setPlayersCard(List<Card> playersCard) {
			this.playersCard = playersCard;
		}

		public List<Card> getDealersCard() {
			return dealersCard;
		}

		public void setDealersCard(List<Card> dealersCard) {
			this.dealersCard = dealersCard;
		}

		public int getDealerPoints() {
			return dealerPoints;
		}

		public void setDealerPoints(int dealerPoints) {
			this.dealerPoints = dealerPoints;
		}



	}
