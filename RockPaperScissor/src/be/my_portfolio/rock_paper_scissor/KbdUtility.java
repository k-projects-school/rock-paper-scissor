package be.my_portfolio.rock_paper_scissor;

import java.util.*;

public class KbdUtility {
	/**
	 * Get a new instance of the Scanner.
	 */
	private Scanner kbd = new Scanner(System.in);
	
	public int nextInt() {
		return this.kbd.nextInt();
	}
	
	public String nextLine() {
		return this.kbd.nextLine();
	}
	
	public char nextChar() {
		String line = this.kbd.next();
		this.kbd.nextLine();
		return line.charAt(0);
	}
	
	public void close() {
		this.kbd.close();
	}
}
