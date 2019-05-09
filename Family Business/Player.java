
public class Player {
	private String name;
	private Card [] cards = new Card [6];
	private int numOfMob;
	
	public Player ()
	{
		name = null;
		cards = null;
		numOfMob = -1;
	}

	public Player( String name, Card [] cards, int numOfMob) {
		super();
		this.name = name;
		this.cards = cards;
		this.numOfMob = numOfMob;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card getCards( int i ) {
		return cards[i];
	}
	
	public void setArray ( Card [] cards)
	{
		this.cards = cards;
	}
	
	public void setCards(int i, Card c) {
		this.cards[i] = c;
	}

	public int getNumOfMob() {
		return numOfMob;
	}

	public void setNumOfMob(int numOfMob) {
		this.numOfMob = numOfMob;
	}
	
	
	
	
	
	
}





