package be.my_portfolio.rock_paper_scissor_simple;

import java.util.Random;
import java.util.Scanner;

public class RPSS {

	private int playerChoice;
	private int botChoice;
	private Scanner kbd = new Scanner(System.in);
	private int playerScore = 0, botScore = 0;
	private String[] options = { "Papier", "Steen", "Schaar" };

	public void start() {
		while (!determineWinner()) {
			playNewRound();
		}

		if (determineWinner()) {
			printScore();
			if (askForNewGame()) {
				this.playerScore = 0;
				this.botScore = 0;
				start();
			}
		}

		this.kbd.close();
	}

	private boolean askForNewGame() {

		boolean wrongAnswer = true;
		char answer = '0';
		while (wrongAnswer) {
			System.out.println("Wil je nog een spelletje spelen?");
			answer = this.nextChar();

			if (answer == 'y' || answer == 'n') {
				wrongAnswer = false;
			}
		}

		return answer == 'y' ? true : false;
	}

	private void printScore() {
		String output;
		if (this.playerScore >= 2) {
			output = "Speler wint het spelletje met " + this.playerScore + " punten tegen " + this.botScore
					+ " punt(en)!";
		} else {
			output = "Computer wint het spelletje met " + this.botScore + " punten tegen " + this.playerScore
					+ " punt(en)!";
		}

		System.out.println(output);
	}

	private boolean determineWinner() {
		return this.playerScore >= 2 || this.botScore >= 2;
	}

	private void playNewRound() {
		setBotChoice();
		askForPlayerChoice();
		determineRoundWinner();
	}

	private void askForPlayerChoice() {
		boolean wrongNumberChoice = true;
		while (wrongNumberChoice) {
			System.out.println("Maak uw keuze:");
			System.out.println(" [1] Papier\n [2] Steen\n [3] Schaar");
			this.playerChoice = this.kbd.nextInt();
			this.kbd.hasNextLine();
			switch (this.playerChoice) {
			case 1:
			case 2:
			case 3:
				this.playerChoice--;
				wrongNumberChoice = false;
				break;
			default:
				System.out.println("Oh oh!! Je hebt een verkeerde keuze gemaakt.");
			}
		}
	}

	private void setBotChoice() {
		Random rand = new Random();
		this.botChoice = (rand.nextInt(3) + 1) - 1;
	}

	private void determineRoundWinner() {
		String winner = "Er is geen winnaar deze ronde!";
		switch (this.playerChoice) {
		case 0:
			if (this.botChoice == 1) {
				winner = setWinnerOutput("player");
				this.playerScore++;
			} else if (this.botChoice == 2) {
				winner = setWinnerOutput("bot");
				this.botScore++;
			}
			break;
		case 1:
			if (this.botChoice == 0) {
				winner = setWinnerOutput("bot");
				this.botScore++;
			} else if (this.botChoice == 2) {
				winner = setWinnerOutput("player");
				this.playerScore++;
			}
			break;
		case 2:
			if (this.botChoice == 0) {
				winner = setWinnerOutput("player");
				this.playerScore++;
			} else if (this.botChoice == 1) {
				winner = setWinnerOutput("bot");
				this.botScore++;
			}
		}

		System.out.println(winner);
		System.out.println("Tussenstand: \nSpeler: " + this.playerScore + "\nComputer: " + this.botScore);
		System.out.println("-----------------------------------------------------------");
	}

	private String setWinnerOutput(String winner) {
		if (winner == "player") {
			return "Speler wint! " + this.options[this.playerChoice] + " wint van " + this.options[this.botChoice];
		}

		return "Computer wint!" + this.options[this.botChoice] + " wint van " + this.options[this.playerChoice];
	}

	private char nextChar() {
		String line = this.kbd.next();
		this.kbd.nextLine();
		return line.charAt(0);
	}

}
