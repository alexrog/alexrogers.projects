import java.util.Scanner;

public class Card
{
	private String name;
	public static Scanner input = new Scanner (System.in);
	
	public Card ( )
	{
		name = null;
	}
	
	public Card ( String name )
	{
		this.name = name;
	}
	
	public void use ( )
	{
		if ( name == Main.CONTRACT )
		{
			contract();
		}
		else if ( name == Main.CONTRACT_NO_FI )
		{
			contractNoFI();
		}
		else if ( name == Main.CONTRACT_NO_MP )
		{
			contractNoMp();
		}
		else if ( name == Main.CONTRACT_NO_MP_FI )
		{
			contractNoMpFi();
		}
		else if ( name == Main.AMBUSH )
		{
			ambush();
		}
		else if ( name == Main.DOUBLE_CONTRACT )
		{
			doubleContract();
		}
		else if ( name == Main.DOUBLE_CROSS )
		{
			doubleCross();
		}
		else if ( name == Main.FAMILY_INFLUENCE )
		{
			familyInfluence();
		}
		else if ( name == Main.FEDERAL_CRACKDOWN)
		{
			federalCrackdown();
		}
		else if ( name == Main.FINGER )
		{
			finger();
		}
		else if ( name == Main.HIT )
		{
			hit();
		}
		else if ( name == Main.MOB_POWER )
		{
			mobPower();
		}
		else if ( name == Main.MOB_WAR )
		{
			mobWar();
		}
		else if ( name == Main.PAYOFF )
		{
			payoff();
		}
		else if ( name == Main.POLICE_PROTECTION )
		{
			policeProtection();
		}
		else if ( name == Main.PRIORITY_CONTRACT )
		{
			priorityContract();
		}
		else if ( name == Main.SAFE_HOUSE )
		{
			safeHouse();
		}
		else if ( name == Main.ST_VAL_MASS )
		{
			stValMass();
		}
		else if ( name == Main.SUBSTITUTION )
		{
			substitution();
		}
		else if ( name == Main.TAKE_IT_ON_THE_LAM )
		{
			takeItOnTheLamb(0);
		}
		else if ( name == Main.TRUCE )
		{
			truce();
		}
		else if ( name == Main.VENDETTA )
		{
			vendetta();
		}
	}
	
	

	private static void vendetta( )
	{
		boolean hasSafeHouse = false;
		int safeHouseIndex = 0;
		boolean valid = false;
		for ( int i = 0; i < Main.players.length; i++ )
		{
			if ( Main.players[i] != null)
			{
				hasSafeHouse = false;
				for ( int j = 0; j < 6; j++ )
				{
					if ( Main.players[i].getCards ( j ) == null )
					{;}
					else
					{
						if ( Main.players[i].getCards ( j ).getName ( ).equals ( Main.SAFE_HOUSE ))
						{
							safeHouseIndex = j;
							hasSafeHouse = true;
						}
					}
				}
				if ( i == Main.playerNumber )
				{;}
				else if ( hasSafeHouse)
				{
					System.out.println ( "Player " + Main.players[i].getName ( ) + " would you like to use a safe house? (y/n)" );
					char userInput = input.next ( ).charAt ( 0 );
					do
					{
						if ( userInput == 'y' )
						{
							valid = true;
							Main.players[i].setCards(safeHouseIndex, null);
						}
						else if ( userInput == 'n' )
						{
							valid = true;
							int currentNumOfMob = Main.players[i].getNumOfMob();
							if ( currentNumOfMob != 0 )
							{
								Main.hitList.add(Main.players[i].getName());
								Main.players[i].setNumOfMob(currentNumOfMob-1);
							}
							else
							{;}
						}
						else
						{
							System.out.println ( "That is an invalid response, try again." );
						}
					} while ( !valid );
				}
				else
				{
					if ( Main.players[i] != null )
					{
						int currentNumOfMob = Main.players[i].getNumOfMob();
						if ( currentNumOfMob != 0 && currentNumOfMob != 1 )
						{
							Main.hitList.add(Main.players[i].getName());
							Main.hitList.add(Main.players[i].getName());
							Main.players[i].setNumOfMob(currentNumOfMob-2);
						}
						else if ( currentNumOfMob != 0 )
						{
							Main.hitList.add(Main.players[i].getName());
							Main.players[i].setNumOfMob(currentNumOfMob-1);
						}
						else
						{;}
					}
					else
					{;}
				}
			}
			else
			{;}
		}
		Main.ambush = true;
		Main.determinePlayerNumber();
		
	}

	private static void truce( )
	{
		Main.mobWar = false;
		Main.ambush = false;
		Main.determinePlayerNumber();
		
	}

	private static void takeItOnTheLamb(int playerIndex )
	{
		boolean hasFinger = false;
		int fingerLocation = 0;
		System.out.println( "Player " + Main.players[Main.playerNumber].getName() + " is taking one of their mobsters off the hitlist");
		for (int i = playerIndex; i < Main.players.length; i++)
		{
			if ( Main.players[i] != null )
			{
				if (i != Main.playerNumber)
				{	
					for ( int j = 0; j < 6; j++ )
					{
						if ( Main.players[i].getCards(j) != null  )
						{
							String cardName = Main.players[i].getCards(j).getName();
							if ( cardName.equals(Main.FINGER) )
							{
								hasFinger = true;
								fingerLocation = j;
								playerIndex = i;
								j = 6;
								i = Main.players.length;
							}
							else
							{;}
						}
						else
						{;}
					}
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasFinger)
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter Take it On the Lam with Finger? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Finger." );
					
					Main.players[playerIndex].setCards(fingerLocation, null);
				}
				else if ( response == 'n' )
				{
					valid = true;
					takeItOnTheLamb(playerIndex + 1);
					return;
				}					
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
		}
		else
		{	
			for ( int i = 0; i < Main.hitList.size(); i++ )
			{
				if ( Main.hitList.get(i).equals(Main.players[Main.playerNumber].getName()) )
				{
					Main.hitList.remove(i);
					int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
					Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob+1);
					i = Main.hitList.size();
				}
				else
				{;}
			}
		}
		Main.determinePlayerNumber();

		
	}

	private static void substitution( )
	{
		int hitListPlayerIndex = -1;
		int hitListPlayerNameIndex = -1;
		int substitutePlayerIndex = 0;
		String hitListPlayerName;
		do
		{
			System.out.println( "Which player from the hit list would you like to sub out?" );
			hitListPlayerName = input.next ( );
			if ( hitListPlayerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			hitListPlayerName = hitListPlayerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.hitList.size(); i++ )
			{
				String actualName = Main.hitList.get(i);
				if ( actualName.equals(hitListPlayerName) )
				{
					hitListPlayerIndex = i;
					i = Main.hitList.size();
				}
			}
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(hitListPlayerName) )
					{
						hitListPlayerNameIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( hitListPlayerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while ( hitListPlayerIndex == -1 );
		
		String playerName;
		boolean valid = false;
		do
		{
			System.out.println( "Which player do you want to put onto the hit list?" );
			playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						valid = true;
						substitutePlayerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[substitutePlayerIndex].getNumOfMob() == 0 )
			{
				valid = false;
			}
			else
			{;}
			if ( !valid )
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while ( !valid );
		
		Main.hitList.set(hitListPlayerIndex, playerName);
		int currentNumOfMob = Main.players[hitListPlayerNameIndex].getNumOfMob();
		Main.players[hitListPlayerNameIndex].setNumOfMob(currentNumOfMob + 1);
		currentNumOfMob = Main.players[substitutePlayerIndex].getNumOfMob();
		Main.players[substitutePlayerIndex].setNumOfMob(currentNumOfMob - 1);
		Main.determinePlayerNumber();
	}

	private static void stValMass( )
	{
		int hitListSize = Main.hitList.size();
		for ( int i = 0; i < hitListSize; i++ )
		{
			String name = Main.hitList.get(0);
			System.out.println("A mobster from " + name + "'s mob was killed");
			for ( int j = 0; j < Main.players.length; j++ )
			{
				if ( Main.players[j] != null )
				{
					if ( name.equals(Main.players[j].getName()) )
					{
						Main.hitList.remove(0);
						Main.deadMobsters[j]++;
						j = Main.players.length;
					}
					else
					{;}
				}
				else
				{;}
			}
		}
		Main.determinePlayerNumber();
	}

	private static void safeHouse( )
	{
		Main.determinePlayerNumber ( );
		
	}

	private static void priorityContract( )
	{
		int playerIndex = -1;
		boolean hasFI = false;
		boolean hasMP = false;
		int fiLocation = 0;
		int mpLocation = 0;
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		for ( int i = 0; i < 6; i++ )
		{
			if ( Main.players[playerIndex].getCards(i) != null )
			{
				String cardName = Main.players[playerIndex].getCards(i).getName();
				if ( cardName.equals(Main.FAMILY_INFLUENCE) )
				{
					hasFI = true;
					fiLocation = i;
				}
				else if ( cardName.equals(Main.MOB_POWER) )
				{
					hasMP = true;
					mpLocation = i;
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasMP )
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Mob Power? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Mob Power, it is now their turn" );
					System.out.println( "A mobster from " + Main.players[Main.playerNumber].getName() + " has been added to the hitlist");
					int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
					if ( currentNumOfMob != 0 )
					{
						Main.hitList.add(0, Main.players[Main.playerNumber].getName());
						Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob-1);
					}
					
					Main.players[playerIndex].setCards(mpLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(0, Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
	
		}
		else if ( hasFI)
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Family Influence? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Family Influence, it is now their turn" );
					
					Main.players[playerIndex].setCards(fiLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(0, Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
		}
		else
		{
			System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
			Main.hitList.add(0, Main.players[playerIndex].getName());
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
			Main.determinePlayerNumber();
		}
		
	}

	private static void policeProtection( )
	{
		System.out.println( "Player " + Main.players[Main.playerNumber].getName() + " is taking one of their mobsters off the hitlist");
		for ( int i = 0; i < Main.hitList.size(); i++ )
		{
			if ( Main.hitList.get(i).equals(Main.players[Main.playerNumber].getName()) )
			{
				Main.hitList.remove(i);
				int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
				Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob+1);
				i = Main.hitList.size();
			}
			else
			{;}
		}
		Main.determinePlayerNumber();
	}

	private static void payoff( )
	{
		int playerIndex = -1;
		do
		{
			System.out.println( "Which player would you like to take off the hitlist?");
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while ( playerIndex == -1 );
		
		int hitListSize = Main.hitList.size();
		int [] indexArray = new int [10];
		for ( int i = 0; i < 10; i++ )
		{
			indexArray[i] = -1;
		}
		int j = 0;
		for ( int i = 0; i < hitListSize; i++ )
		{
			if ( Main.players[playerIndex] != null )
			{
				if ( Main.hitList.get(i).equals(Main.players[playerIndex].getName()) )
				{
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob+1);
					indexArray[j] = i;
					j++;
				}
				else
				{;}
			}
			else
			{;}
			
		}
		
		for ( int i = 0; i < 10; i++ )
		{
			if ( indexArray[i] != -1 )
			{
				int value = indexArray[i] - i;
				Main.hitList.remove(value);
			}
			else
			{;}
		}
		Main.determinePlayerNumber();
		
	}

	private static void mobWar( )
	{
		Main.mobWar = true;
		Main.determinePlayerNumber();
		
	}

	private static void mobPower( )
	{
		Main.determinePlayerNumber();
		
	}

	private static void hit( )
	{
		int playerIndex = -1;
		boolean noMobsters = false;
		String playerName;
		do
		{
			System.out.println( "Which player would you like to hit?" );
			playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				noMobsters = true;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while ( playerIndex == -1 );
		if ( noMobsters )
		{
			for ( int i = 0; i < Main.hitList.size(); i++ )
			{
				if ( Main.hitList.get(i).equals(playerName) )
				{
					Main.hitList.remove(i);
					Main.deadMobsters[playerIndex]++;
					i = Main.hitList.size();
					System.out.println("A mobster from " + playerName + "'s mob was killed");
				}
			}
			Main.hitList.add(Main.players[Main.playerNumber].getName());
		}
		else 
		{
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
			currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
			if (currentNumOfMob == 0)
			{;}
			else
			{
				Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob-1);
			}
			Main.deadMobsters[playerIndex]++;
			System.out.println("A mobster from " + playerName + "'s mob was killed");
			Main.hitList.add(Main.players[Main.playerNumber].getName());
		}
		if ( Main.deadMobsters[playerIndex] == Main.NUM_OF_MOBSTERS )
		{
			System.out.println ( "Player " + Main.players[playerIndex].getName ( ) + " is out of the game" );
			Main.players[playerIndex] = null;
		}
		else
		{;}
		Main.determinePlayerNumber();
	}

	private static void finger( )
	{
		Main.determinePlayerNumber();
		
	}

	private static void federalCrackdown( )
	{
		int hitListSize = Main.hitList.size();
		for ( int i = 0; i < hitListSize; i++ )
		{
			
			for ( int j = 0; j < Main.players.length; j++ )
			{
				if ( Main.players[j] != null )
				{
					if ( Main.hitList.get(0).equals(Main.players[j].getName()) )
					{
						int currentNumOfMob = Main.players[j].getNumOfMob();
						Main.players[j].setNumOfMob(currentNumOfMob+1);
					}
				}
				else
				{;}
			}
			Main.hitList.remove(0);
		}
		Main.determinePlayerNumber();
	}

	private static void familyInfluence( )
	{
		Main.determinePlayerNumber();
		
	}

	private static void doubleCross( )
	{
		for ( int i = 0; i < Main.players.length; i++ )
		{
			if ( i == Main.playerNumber )
			{;}
			else if ( Main.players[i] != null )
			{
				int currentNumOfMob = Main.players[i].getNumOfMob();
				if ( currentNumOfMob != 0 )
				{
					Main.hitList.add(Main.players[i].getName());
					Main.players[i].setNumOfMob(currentNumOfMob-1);
				}
				else
				{;}
			}
			else
			{;}
		}
		Main.determinePlayerNumber();
	}

	private static void doubleContract( )
	{
		int playerIndex = -1;
		boolean hasFI = false;
		boolean hasMP = false;
		int fiLocation = 0;
		int mpLocation = 0;
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 || Main.players[playerIndex].getNumOfMob() == 1)
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		for ( int i = 0; i < 6; i++ )
		{
			if ( Main.players[playerIndex].getCards(i) != null )
			{
				String cardName = Main.players[playerIndex].getCards(i).getName();
				if ( cardName.equals(Main.FAMILY_INFLUENCE) )
				{
					hasFI = true;
					fiLocation = i;
				}
				else if ( cardName.equals(Main.MOB_POWER) )
				{
					hasMP = true;
					mpLocation = i;
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasMP )
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Mob Power? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Mob Power, it is now their turn" );
					System.out.println( "A mobster from " + Main.players[Main.playerNumber].getName() + " and " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					
					Main.hitList.add(Main.players[playerIndex].getName());
					
					int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
					if ( currentNumOfMob != 0 )
					{
						Main.hitList.add(Main.players[Main.playerNumber].getName());
						Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob-1);
					}
					else {;}
					currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					
					Main.players[playerIndex].setNumOfMob(currentNumOfMob - 1);
					
					Main.players[playerIndex].setCards(mpLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "Two mobsters from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-2);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
	
		}
		else if ( hasFI)
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Family Influence? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Family Influence, it is now their turn, \n A Mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist." );
					
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					
					Main.players[playerIndex].setCards(fiLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "Two mobsters from " + Main.players[playerIndex].getName() + " have been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-2);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
		}
		else
		{
			System.out.println( "Two mobsters from " + Main.players[playerIndex].getName() + " have been added to the hitlist");
			Main.hitList.add(Main.players[playerIndex].getName());
			Main.hitList.add(Main.players[playerIndex].getName());
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-2);
			Main.determinePlayerNumber();
		}
		
	}

	private static void ambush( )
	{
		Main.ambush = true;
		Main.determinePlayerNumber();
	}
	
	private static void contractNoMpFi() 
	{
		int playerIndex = -1;
		
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
		Main.hitList.add(Main.players[playerIndex].getName());
		int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
		Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
		Main.determinePlayerNumber();
		
	}
	
	private static void contractNoMp( )
	{
		int playerIndex = -1;
		boolean hasFI = false;
		int fiLocation = 0;
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] !=null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		for ( int i = 0; i < 6; i++ )
		{
			if ( Main.players[playerIndex].getCards(i) != null )
			{
				String cardName = Main.players[playerIndex].getCards(i).getName();
				if ( cardName.equals(Main.FAMILY_INFLUENCE) )
				{
					hasFI = true;
					fiLocation = i;
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasFI)
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Family Influence? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Family Influence, it is now their turn" );
					
					Main.players[playerIndex].setCards(fiLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
		}
		else
		{
			System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
			Main.hitList.add(Main.players[playerIndex].getName());
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
			Main.determinePlayerNumber();
		}
		
	}

	private static void contractNoFI( )
	{
		int playerIndex = -1;
		boolean hasMP = false;
		int mpLocation = 0;
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
				else
				{;}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		for ( int i = 0; i < 6; i++ )
		{
			if ( Main.players[playerIndex].getCards(i) != null )
			{
				String cardName = Main.players[playerIndex].getCards(i).getName();
				if ( cardName.equals(Main.MOB_POWER) )
				{
					hasMP = true;
					mpLocation = i;
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasMP )
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Mob Power? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Mob Power, it is now their turn" );
					
					int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
					if ( currentNumOfMob != 0 )
					{
						Main.hitList.add(Main.players[Main.playerNumber].getName());
						Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob-1);
						System.out.println( "A mobster from " + Main.players[Main.playerNumber].getName() + " has been added to the hitlist");
					}
					else
					{;}
					Main.players[playerIndex].setCards(mpLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
	
		}
		else
		{
			System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
			Main.hitList.add(Main.players[playerIndex].getName());
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
			Main.determinePlayerNumber();
		}
		
	}

	public static void contract ()
	{
		int playerIndex = -1;
		boolean hasFI = false;
		boolean hasMP = false;
		int fiLocation = 0;
		int mpLocation = 0;
		do
		{
			System.out.println( "Who would you like to put on the hit list? (enter 3 character name or \"discard\" to discard)" );
			String playerName = input.next ( );
			if ( playerName.equals("discard") )
			{
				Main.determinePlayerNumber();
				return;
			}
			else
			{;}
			playerName = playerName.toUpperCase ( ).substring ( 0, 3 );
			for ( int i = 0; i < Main.players.length; i++ )
			{
				if ( Main.players[i] != null )
				{
					String actualName = Main.players[i].getName();
					if ( actualName.equals(playerName) )
					{
						playerIndex = i;
						i = Main.players.length;
					}
				}
			}
			if ( Main.players[playerIndex].getNumOfMob() == 0 )
			{
				playerIndex = -1;
			}
			else
			{;}
			if ( playerIndex == -1)
			{
				System.out.println("That is an invalid player, choose again");
			}
			else
			{;}
		} while (playerIndex == -1);
		
		for ( int i = 0; i < 6; i++ )
		{
			if ( Main.players[playerIndex].getCards(i) != null )
			{
				String cardName = Main.players[playerIndex].getCards(i).getName();
				if ( cardName.equals(Main.FAMILY_INFLUENCE) )
				{
					hasFI = true;
					fiLocation = i;
				}
				else if ( cardName.equals(Main.MOB_POWER) )
				{
					hasMP = true;
					mpLocation = i;
				}
				else
				{;}
			}
			else
			{;}
		}
		
		if ( hasMP )
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Mob Power? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Mob Power, it is now their turn" );
					
					
					
					int currentNumOfMob = Main.players[Main.playerNumber].getNumOfMob();
					if ( currentNumOfMob != 0 )
					{
						Main.players[Main.playerNumber].setNumOfMob(currentNumOfMob-1);
						Main.hitList.add(Main.players[Main.playerNumber].getName());
						System.out.println( "A mobster from " + Main.players[Main.playerNumber].getName() + " has been added to the hitlist");
					}
					else
					{;}
					Main.players[playerIndex].setCards(mpLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
	
		}
		else if ( hasFI)
		{
			boolean valid = false;
			do
			{
				System.out.println( Main.players[playerIndex].getName() + ", would you like to counter the contract with Family Influence? (y/n)");
				char response = input.next().charAt(0);
				if ( response == 'y')
				{
					valid = true;
					System.out.println( Main.players[playerIndex].getName() + " is countering with Family Influence, it is now their turn" );
					
					Main.players[playerIndex].setCards(fiLocation, null);
					Main.playerNumber = playerIndex;
				}
				else if ( response == 'n' )
				{
					valid = true;
					System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
					Main.hitList.add(Main.players[playerIndex].getName());
					int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
					Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
					Main.determinePlayerNumber();
				}
				else
				{
					System.out.println( "That is an invalid response, try again");
				}
			} while ( !valid );
		}
		else
		{
			System.out.println( "A mobster from " + Main.players[playerIndex].getName() + " has been added to the hitlist");
			Main.hitList.add(Main.players[playerIndex].getName());
			int currentNumOfMob = Main.players[playerIndex].getNumOfMob();
			Main.players[playerIndex].setNumOfMob(currentNumOfMob-1);
			Main.determinePlayerNumber();
		}
		
		
			
	}
	
	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
	
	public String getName ( String name )
	{
		return name;
	}
	
}











