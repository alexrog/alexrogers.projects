import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

public class Main
{
	public static Scanner input;
	public static final int HIT_LIST_SIZE = 13;
	public static final int DECK_SIZE = 57;
	public static final int NUM_OF_MOBSTERS = 4;
	public static final String CONTRACT = "      Contract";
	public static final String CONTRACT_NO_MP = "   Contract (No MP)";
	public static final String CONTRACT_NO_FI = "   Contract (No FI)";
	public static final String CONTRACT_NO_MP_FI = " Contract (No MP, FI)";
	public static final String PRIORITY_CONTRACT = "  Priority Contract";
	public static final String DOUBLE_CONTRACT = "   Double Contract";
	public static final String HIT = "         Hit";
	public static final String ST_VAL_MASS = " St Valentine's Mass";
	public static final String DOUBLE_CROSS = "     Double Cross";
	public static final String MOB_WAR = "       Mob War";
	public static final String AMBUSH = "        Ambush";
	public static final String VENDETTA = "       Vendetta";
	public static final String TAKE_IT_ON_THE_LAM = "  Take It On The Lam";
	public static final String POLICE_PROTECTION = "  Police Protection";
	public static final String SUBSTITUTION = "     Substitution";
	public static final String INTRIGUE = "       Intrigue";
	public static final String TRUCE = "        Truce";
	public static final String PAYOFF = "        Payoff";
	public static final String FEDERAL_CRACKDOWN = "  Federal Crackdown";
	public static final String FINGER = "        Finger";
	public static final String SAFE_HOUSE = "      Safe House";
	public static final String MOB_POWER = "      Mob Power";
	public static final String FAMILY_INFLUENCE = "   Family Influence";
	public static boolean mobWar = false;
	public static boolean ambush = false;
	public static int [] deadMobsters = null; 
	public static ArrayList<Card> drawPile = new ArrayList<Card> ( ); // array list for the cards in the draw pile
	public static ArrayList<Card> discardPile = new ArrayList<Card> ( ); // array list for the cards in the discard pile
	public static ArrayList<String> hitList = new ArrayList<String> ( ); // Not using constant "HIT_LIST_SIZE" in case it goes over 13 people
	public static int playerNumber = 0;
	public static Player [ ] players = null;

	public static void main( String[ ] args )
	{
		
		Scanner input = new Scanner ( System.in );

		System.out.println ( "How many players (2-6)?: " );
		int numOfPlayers = input.nextInt ( );
		players = new Player[numOfPlayers];
		deadMobsters = new int[numOfPlayers];
		for ( int i = 0; i < numOfPlayers; i++ )
		{
			deadMobsters[i] = 0;
		}
		
		initializeGame ( );
		
		do
		{
			playerTurn ( );
		} while ( !gameOverCheck() );
	}

	public static void initializeGame( )
	{
		initializeDeck ( ); // puts all the cards in deck initially
		shuffleDeck ( ); // Shuffles deck
		players = inputPlayers (  ); // array of the players, stores the cards they have, name of player, and number of mobsters
		
		
		
	}

	public static void drawBoard ( )
	{
		printPlayerTotals(  );
		printHitList (  );
		printPlayerCards ( players[playerNumber] );
	}
	
	public static Player[ ] inputPlayers( )
	{
		Scanner input = new Scanner ( System.in );
		for ( int i = 0; i < players.length; i++ )
		{
			System.out.println ( "Enter Player " + ( i + 1 ) + "'s name (3 characters only): " );
			String playerName = input.next ( );
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );

			Player temporaryPlayer = new Player ( );
			temporaryPlayer.setName ( playerName );
			temporaryPlayer.setNumOfMob ( NUM_OF_MOBSTERS );
			players[i] = temporaryPlayer;
		}
		System.out.println ( "\n" );

		for ( int i = 0; i < players.length; i++ )
		{
			Card [] cards = {null,null,null,null,null,null};
			players[i].setArray(cards);
			drawCard();
			playerNumber++;
	
		}
		playerNumber = 0;
		return players;
	}

	public static void printPlayerTotals( )
	{
		int numOfPlayers = players.length;
		System.out.printf ( "%82s%s", "# of Mobsters Left", "\n" );
		if ( numOfPlayers == 2 )
		{
			System.out.printf ( "%32s", "" );
			for ( int i = 0; i <= 1; i++ )
			{
				if ( players[i] == null )
				{
					System.out.printf( "%26s", "DEAD  ");
				}
				else
				{
					System.out.printf ( "%26s%s%d", players[i].getName ( ), ": ", players[i].getNumOfMob ( ) );
				}
				
			}
			System.out.println ( );
		}
		else if ( numOfPlayers == 3 )
		{
			System.out.printf ( "%46s", "" );
			for ( int i = 0; i <= 2; i++ )
			{
				if ( players[i] == null)
				{
					System.out.printf ( "%12s", "DEAD  ");
				}
				else
				{
					System.out.printf ( "%12s%s%d", players[i].getName ( ), ": ", players[i].getNumOfMob ( ) );
				}
			}
			System.out.println ( );
		}
		else if ( numOfPlayers == 4 )
		{
			System.out.printf ( "%43s", "" );
			for ( int i = 0; i <= 3; i++ )
			{
				if ( players[i] == null )
				{
					System.out.printf("%10s", "DEAD  ");
				}
				else
				{
					System.out.printf ( "%10s%s%d", players[i].getName ( ), ": ", players[i].getNumOfMob ( ) );
				}
			}
			System.out.println ( );
		}
		else if ( numOfPlayers == 5 )
		{
			System.out.printf ( "%38s", "" );
			for ( int i = 0; i <= 4; i++ )
			{
				if ( players[i] == null )
				{
					System.out.printf("%10s", "DEAD  ");
				}
				else
				{
					System.out.printf ( "%10s%s%d", players[i].getName ( ), ": ", players[i].getNumOfMob ( ) );
				}
			}
			System.out.println ( );
		}
		else if ( numOfPlayers == 6 )
		{
			System.out.printf ( "%30s", "" );
			for ( int i = 0; i <= 5; i++ )
			{
				if ( players[i] == null )
				{
					System.out.printf("%10s", "DEAD  ");
				}
				else
				{
					System.out.printf ( "%10s%s%d", players[i].getName ( ), ": ", players[i].getNumOfMob ( ) );
				}
			}
			System.out.println ( );
		}

	}

	public static void printHitList( )
	{
		if ( ambush )
		{
			System.out.println();
			System.out.printf( "%46s%60s", "AMBUSH ", " AMBUSH");
			System.out.println();
		}
		else if ( mobWar )
		{
			System.out.println();
			System.out.printf( "%46s%60s", "MOB WAR", " MOB WAR");
			System.out.println();
		}
		else
		{
			System.out.println ( );
		}
		System.out.printf ( "%76s%s", "HIT LIST", "\n" );
		for ( int j = 0; j < 3; j++ )
		{
			if ( j == 1 )
			{
				System.out.printf ( "%34s", "Back |" );
				for ( int i = 0; i < ( HIT_LIST_SIZE-hitList.size() + 1); i++ )
				{
					if ( i == (HIT_LIST_SIZE - hitList.size()))
					{
						for ( int g = 0; g < hitList.size(); g++ )
						{
								System.out.printf ( "%4s%2s", hitList.get(hitList.size() - g - 1), "|" );
						}
					}
					else
					{
						System.out.printf ( "%6s", "|" );
					}
				}
				System.out.print ( " Front" );
			}
			else
			{
				System.out.printf ( "%34s", "|" );
				for ( int i = 0; i < HIT_LIST_SIZE; i++ )
				{
					System.out.printf ( "%6s", "|" );
				}
			}

			System.out.println ( );
		}
		
		
		
		
		System.out.println ( );
		
		
		
	}

	public static void printPlayerCards( Player p )
	{
		System.out.printf ( "%s%73s%s%s%s", "\n", "Player ", (players[playerNumber].getName()), "'s Cards: ", "\n" );
		for ( int i = 0; i < 145; i++ )
		{
			System.out.print ( "_" );
		}
		for ( int i = 0; i < 7; i++ )
		{
			System.out.println ( );
			for ( int j = 0; j < 6; j++ )
			{
				if ( i == 1 )
				{
					System.out.printf ( "%-11s%s%d%-11s", "|", "(", ( j + 1 ), ")" );
				}
				else if ( i == 3 && j == 0 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 0 ).getName() );
				}
				else if ( i == 3 && j == 1 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 1 ).getName() );
				}
				else if ( i == 3 && j == 2 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 2 ).getName() );
				}
				else if ( i == 3 && j == 3 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 3 ).getName() );
				}
				else if ( i == 3 && j == 4 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 4 ).getName() );
				}
				else if ( i == 3 && j == 5 )
				{
					System.out.printf ( "%-2s%-22s", "|", p.getCards ( 5 ).getName() );
				}
				else
				{
					System.out.printf ( "%-24s", "|" );
				}

			}
			System.out.print ( "|" );
		}
	}

	public static void initializeDeck( )
	{
		int i;
		for ( i = 0; i < 14; i++ )
		{
			Card contract = new Card ( CONTRACT );
			drawPile.add ( contract );
		}
		for ( i = 0; i < 3; i++ )
		{
			Card contractNoMp = new Card ( CONTRACT_NO_MP );
			drawPile.add ( contractNoMp );
		}
		for ( i = 0; i < 2; i++ )
		{
			Card contractNoFi = new Card ( CONTRACT_NO_FI );
			drawPile.add ( contractNoFi );
		}
		Card contractNoMpFi = new Card ( CONTRACT_NO_MP_FI );
		drawPile.add ( contractNoMpFi );
		for ( i = 0; i < 3; i++ )
		{
			Card priorityContract = new Card ( PRIORITY_CONTRACT );
			drawPile.add ( priorityContract );
		}
		for ( i = 0; i < 3; i++ )
		{
			Card doubleContract = new Card ( DOUBLE_CONTRACT );
			drawPile.add ( doubleContract );
		}
		Card hit = new Card ( HIT );
		drawPile.add ( hit );
		Card stValMass = new Card ( ST_VAL_MASS );
		drawPile.add ( stValMass );
		Card doubleCross = new Card ( DOUBLE_CROSS );
		drawPile.add ( doubleCross );
		Card mobWar = new Card ( MOB_WAR );
		drawPile.add ( mobWar );
		Card ambush = new Card ( AMBUSH );
		drawPile.add ( ambush );
		Card vendetta = new Card ( VENDETTA );
		drawPile.add ( vendetta );
		Card truce = new Card ( TRUCE );
		drawPile.add ( truce );
		Card payoff = new Card ( PAYOFF );
		drawPile.add ( payoff );
		Card federalCrackdown = new Card ( FEDERAL_CRACKDOWN );
		drawPile.add ( federalCrackdown );
		Card safeHouse = new Card ( SAFE_HOUSE );
		drawPile.add ( safeHouse );
		for ( i = 0; i < 4; i++ )
		{
			Card takeIt = new Card ( TAKE_IT_ON_THE_LAM );
			drawPile.add ( takeIt );
		}
		for ( i = 0; i < 2; i++ )
		{
			Card policeProtection = new Card ( POLICE_PROTECTION ); 
			drawPile.add ( policeProtection );
		}
		for ( i = 0; i < 2; i++ )
		{
			Card substitution = new Card ( SUBSTITUTION );
			drawPile.add ( substitution );
		}
		for ( i = 0; i < 2; i++ )
		{
			Card intrigue = new Card ( INTRIGUE );
			drawPile.add ( intrigue );
		}
		for ( i = 0; i < 2; i++ )
		{
			Card finger = new Card ( FINGER );
			drawPile.add ( finger );
		}
		
		for ( i = 0; i < 3; i++ )
		{
			Card mobPower = new Card ( MOB_POWER );
			drawPile.add ( mobPower );
		}
		for ( i = 0; i < 6; i++ )
		{
			Card familyInfluence = new Card ( FAMILY_INFLUENCE );
			drawPile.add ( familyInfluence );
		}

	}

	public static void shuffleDeck( )
	{
		for ( int i = 0; i < 3; i++ )
		{
			Collections.shuffle ( drawPile );
		}

	}

	public static void drawCard( )
	{
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(0) == null )
		{
			players[playerNumber].setCards( 0, drawPile.get(0) );
			drawPile.remove(0);
		}
		
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(1) == null )
		{
			players[playerNumber].setCards( 1, drawPile.get(0) );
			drawPile.remove(0);
		}
		
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(2) == null )
		{
			players[playerNumber].setCards( 2, drawPile.get(0) );
			drawPile.remove(0);
		}
		
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(3) == null )
		{
			players[playerNumber].setCards( 3, drawPile.get(0) );
			drawPile.remove(0);
		}
		
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(4) == null )
		{
			players[playerNumber].setCards( 4, drawPile.get(0) );
			drawPile.remove(0);
		}
		
		if ( drawPile.isEmpty ( ) )
		{
			drawPile = discardPile;
			shuffleDeck (  );
			for ( int i = 0; i < discardPile.size ( ); i++ )
			{
				discardPile.remove ( i );
			}
		}
		else {;}
		
		if ( players[playerNumber].getCards(5) == null )
		{
			players[playerNumber].setCards( 5, drawPile.get(0) );
			drawPile.remove(0);
		}
		
	}

	public static void playerTurn ( )
	{
		Scanner input = new Scanner (System.in);
		Card cardPlayed = new Card ();
		boolean pickAgain = false;
		
		if ( mobWar )
		{
			if ( ambush )
			{
				killMobster();
				killMobster();
				if ( hitList.isEmpty() )
				{
					ambush = false;
				}
				else
				{;}
			}
			else
			{
				killMobster();
			}
		}
		else if ( ambush )
		{
			killMobster();
			killMobster();
			if ( hitList.isEmpty() )
			{
				ambush = false;
			}
			else
			{;}
		}
		
		mobWarCheck();
		
		if ( players[playerNumber] != null )
		{
			drawCard (  );
			drawBoard (  );
			System.out.println();
			do 
			{
				System.out.println( "Which card do you want to play? (Enter number): ");
				int userInput = (input.nextInt()-1);
				if ( userInput > 5 )
				{
					System.out.println("Invalid, choose again:");
					pickAgain = true;
				}
				else
				{
					cardPlayed = players[playerNumber].getCards(userInput);
					players[playerNumber].setCards(userInput, null);
				}
			}
			while ( pickAgain );
			System.out.println( "You played " + cardPlayed.getName());
			discardPile.add(cardPlayed);
			cardPlayed.use();
			
		}
		else
		{;}
	}
	
	public static void killMobster ()
	{
		for ( int i = 0; i < players.length; i++ )
		{
			if ( players[i] != null )
			{
				if ( hitList.isEmpty() )
				{
					return;
				}
				else 
				{
					if ( hitList.get(0) == players[i].getName() )
					{
						deadMobsters[i]++;
						System.out.println("A mobster from " + players[i].getName() + "'s mob was killed");
						if ( deadMobsters[i] == NUM_OF_MOBSTERS )
						{
							System.out.println( "Player " + players[i].getName() + " is out of the game.");
							players[i] = null;
						}
					}
				}
			}
			else {;}
			
		}
		hitList.remove(0);
		
	}
	
	public static void determinePlayerNumber ()
	{
		int numOfPlayers = (players.length-1);
		if ( playerNumber == numOfPlayers )
		{
			playerNumber = 0;
		}
		else
		{
			playerNumber++;
		}
		while ( players[playerNumber] == null)
		{
			if ( playerNumber == numOfPlayers )
			{
				playerNumber = 0;
			}
			else
			{
				playerNumber++;
			}
		}
	}
	
	public static boolean gameOverCheck ()
	{
		int numOfPlayers = players.length;
		int numDeadPlayers = 0;
		for ( int i = 0; i < numOfPlayers; i++ )
		{
			if ( deadMobsters[i] == NUM_OF_MOBSTERS )
			{
				numDeadPlayers++;
				players[i] = null;
			}
			else {;}
		}
		if ( numDeadPlayers == ( numOfPlayers - 1) )
		{
			System.out.println( "Game over" );
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void mobWarCheck ()
	{
		int numOfPlayers = players.length;
		int numOfDeadMobsters = 0;
		int totalNumOfMobsters = (NUM_OF_MOBSTERS * numOfPlayers);
		for ( int i = 0; i < deadMobsters.length; i++ )
		{
			numOfDeadMobsters += deadMobsters[i];
		}
		int numOfMobstersLeft = (totalNumOfMobsters - numOfDeadMobsters);
		
		if ( !mobWar )
		{
			if ( hitList.size() > 5 )
			{
				mobWar = true;
			}
			else
			{;}
			
			if ( numOfMobstersLeft < 7 )
			{
				mobWar = true;
			}
			else
			{;}
		}
		else
		{
			if ( hitList.isEmpty() && numOfMobstersLeft > 6 )
			{
				mobWar = false;
			}
			else
			{;}
		}
		
	}
	
}





