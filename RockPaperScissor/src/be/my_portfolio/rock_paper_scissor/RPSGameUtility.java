package be.my_portfolio.rock_paper_scissor;

import java.util.Random;

public class RPSGameUtility {
	// Store the amount of real players
	private int players;
	// Store the amount of computer players
	private int npcPlayers = 0;
	// Array of the scores of each player (real and npc)
	private int[] scores;
	// Array with the player names
	private String[] playerNames;
	// Initialize the kbd
	private KbdUtility kbd = new KbdUtility();
	// Array of the options
	private String[] options = {"rock", "paper", "scissor"};
	// array of the chosen option for each player (real and npc)
	private int[] chosen;
	// Initialize the Random class
	private Random randomizer = new Random();
	// Store the amounts of rounds that will be played
	private int bestOf;
	
	/**
	 * Ask for the amount of players that will participate.
	 * You can go from 1 to ..., if 1 player is playing, there will be a game started against 1 npc player.
	 * Otherwise you can choose how many of the chosen players are npc players.
	 */
	public void askForPlayerAmount() {
		System.out.println("How many players are playing?");
		// Get the amount of players
		this.players = this.kbd.nextInt();
		/*
		 * If there will be more than 1 player be playing, ask how many of them are npc's
		 */
		if (this.players > 1) {
			System.out.println("How many of the players are computer players?");
			this.npcPlayers = this.kbd.nextInt();
			this.players -= this.npcPlayers;
		} else {
			// Is there only 1 player, add 1 npc player
			this.npcPlayers = 1;
		}
		// Set the amount of the player names array.
		this.playerNames = new String[this.players];
		// Set the amount of the scores array
		this.scores = new int[this.players + this.npcPlayers];
		// Set the amount of the chosen array
		this.chosen = new int[this.players + this.npcPlayers];
		// This is needed to add an "\n" to the console, otherwise the program will continue after asking for something.
		this.kbd.nextLine();
	}
	
	/**
	 * Set the names of the real players
	 */
	public void setPlayerNames() {
		System.out.println("Geef de namen van de spelers op: ");
		int i = 0;
		// As long as the counter "i" is less than the amount of real players, ask for the player's name
		while(i < this.players) {
			System.out.println("Speler " + (i + 1) +": ");
			String name = this.kbd.nextLine();
			// Store the given name in the player names array
			this.playerNames[i] = name;
			i++;
		}
	}
	
	/**
	 * Ask for how many rounds that is played.
	 */
	public void askForPlayRounds() {
		System.out.println("Best of how much?");
		this.bestOf = this.kbd.nextInt();
		this.kbd.nextLine();
	}
	
	/**
	 * Set the choice of a player
	 * @param choice
	 * @param player
	 */
	public void choose(int choice, int player) {
		this.chosen[player] = choice - 1;
	}
	
	/**
	 * Start the game
	 */
	public void play() {
		System.out.println("Lets play!");
		// Ask to each player for their choice
		for (int player = 0; player < this.players; player++) {
			System.out.println(this.playerNames[player] + ", maak uw keuze:");
			System.out.println("[1] Rock");
			System.out.println("[2] Paper");
			System.out.println("[3] Scissor");
			this.choose(this.kbd.nextInt(), player);
			// This is needed to add an "\n" to the console, otherwise the program will continue after asking for something.
			this.kbd.nextLine();
			// TODO: clear the cmd line after a choice is made
		}
		// Set the choices for the npc players
		for (int npc = this.players; npc < this.npcPlayers + this.players; npc++) {
			this.choose(this.randomizer.nextInt(3)+1, npc);
		}
	}
	
	/**
	 * Check who has won the current round
	 */
	public void determinRoundWinner() {
		// Show the choices of each real player
		for (int player = 0; player < this.players; player++) {
			System.out.println(this.playerNames[player] + "'s keuze: " + this.options[this.chosen[player]]);
		}
		// Show the choices of each npc player
		for (int npc = this.players; npc < this.npcPlayers + this.players; npc++) {
			System.out.println("Npc" + npc + "'s keuze: " + this.options[this.chosen[npc]]);
		}
		// Set the amount of players (real and npc)
		int players = this.players + this.npcPlayers;
		// Create an array to store the tested players
		int[] tested = new int[players];
		// Check the choice of every player with every player, except himself
		for (int i = 0; i < players; i++) {
			for (int y = 0; y < players; y++) {
				/*
				 *  If it are 2 different players, and the player from the first for loop is not been tested
				 *  test the choice of that player
				 */
				if (y != i && !this.intInArray(tested, i)) {
					tested[i] = y;
					tested[y] = i;
					if ((this.chosen[y] > this.chosen[i]) || (this.chosen[i] == 2 && this.chosen[y] == 0)) {
						// y wins
						this.scores[y]++;
					} else if ((this.chosen[i] > this.chosen[y]) || (this.chosen[y] == 2 && this.chosen[i] == 0)) {
						// i wins
						this.scores[i]++;
					}
					// All other cases draw and no score is applied
				}
			}
		}
		
		boolean playOn = true;
		// Check if a player has reached the target score
		for (int player = 0; player < this.scores.length; player++) {
			if (this.scores[player] == (this.bestOf + 1) / 2) {
				playOn = false;
				break;
			}
		}
		
		// If there are no players with the target score, play further
		if (playOn) {
			this.startNewRound();
		} else {
			// If at least 1 player has reached the target score, end the game
			this.printScores();
			this.askForNewGame();
		}
	}

	/**
	 * Ask for a new game
	 */
	private void askForNewGame() {
		System.out.println("Would you like to play another game?");
		char answer = this.kbd.nextChar();
		if (answer == 'y') {
			this.startGame();
		} else {
			System.out.println("Thanks for playing.");
		}
	}

	/**
	 * Start the game
	 */
	public void startGame() {
		this.askForPlayerAmount();
		this.setPlayerNames();
		this.askForPlayRounds();
		this.startNewRound();
	}

	/**
	 * Print the scores of each player
	 */
	private void printScores() {
		for (int i = 0; i < this.playerNames.length; i++) {
			System.out.println(this.playerNames[i] + ": " + this.scores[i]);
		}
		for (int npc = this.players; npc < this.npcPlayers + this.players; npc++) {
			System.out.println("Npc" + npc + ": " + this.scores[npc]);
		}
	}

	/**
	 * Start a new round
	 */
	public void startNewRound() {
		this.play();
		this.determinRoundWinner();
	}
	
	/**
	 * Check if an int is already in the given int array
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	private boolean intInArray(int[] arr, int targetValue) {	
		for(int s: arr){
			if(s == targetValue)
				return true;
		}
		return false;
	}
}
