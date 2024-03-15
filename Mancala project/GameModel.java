/**
* Game Project (Mancala Board Game)
* @author Anh Le
* @version 1.6.0 12/03/21
*/

import java.util.ArrayList;
import javax.swing.event.*;

/**
 * A GameModel class that is used as a MODEL in MVC architecture.
 */
public class GameModel {
	/**
	 * A default Constructor to initialize the underlying data
	 * Set first player is A, Game Board contains 14 bits, number of undo button is 3 times each turn
	 */
	public GameModel() {
		data = new int[14];
		turn = 'A';
		pre_turn = 'A';
		aUndoNum = 3;
		isOver = false;
		listeners = new ArrayList<ChangeListener>();
	}

	/**
	 * Initialization the game
	 * @param num - the number of stones per pit
	 * precondition: 
	 * postcondition: 
	 */
	public void init(int num) {
		turn = 'A';
		pre_turn = 'A';
		aUndoNum = 3;
		isOver = false;
		
		for (int i = 0; i < 14; i++) {
			if (i == 6 || i == 13) {
				data[i] = 0;
				continue;
			}
			data[i] = num;
		}
		updateUI();
	}

	/**
	 * Get the current state of underlying model (game)
	 * @return the clone underlying data structure of the model
	 */
	public int[] getData() {
		return data.clone();
	}

	/**
	 * Determines which player's turn at the moment
	 * @return return a character: either A or B to notify which player's turn
	 */
	public char getTurn() {
		return turn;
	}

	/**
	 * Determines which the last player just made the turn
	 * @return return a character : either A or B to notify which player's turn previously
	 */
	public char getPreTurn() {
		return pre_turn;
	}


	/**
	 * Move the stones across the pits which depends on each special situation
	 * @param pos - the position of the pit selected by the player
	 * precondition:
	 * postcondition: all of the stone in a pit that was selected should be distributed across pits one-by-one by counter-clockwise
	 */
	public void move(int pos) {
		if(data[pos]!=0) {
			if (turn == 'A')
				bUndoNum = 3;
			else
				aUndoNum = 3;
			
			pre_data = data.clone();
			pre_turn = turn;
	
			int stones = data[pos];
			int cur_pos = pos;
			int i = 0;
			while (i < stones) {
				cur_pos = (cur_pos + 1) % 14;
				if (turn == 'A' && cur_pos == 13) {
					cur_pos = (cur_pos + 1) % 14;
				}
				if (turn == 'B' && cur_pos == 6) {
					cur_pos = (cur_pos + 1) % 14;
				}
	
				data[cur_pos]++;
				i++;
			}
			data[pos] = 0;
	
			// the last stone drop in own Mancala, get a free turn
			if (turn == 'A' && cur_pos == 6 || turn == 'B' && cur_pos == 13) {
				pre_turn = turn;

				updateUI();
				return;
			}

			// the last stone drop in an empty pit on your side
			// take that stone and all of your opponents stones that are in the opposite pit
			if (turn == 'A')
				if (0 <= cur_pos && cur_pos <= 5 && data[cur_pos] == 1 && data[12 - cur_pos] > 0) {
					data[6] += data[cur_pos] + data[12 - cur_pos];
					data[cur_pos] = 0;
					data[12 - cur_pos] = 0;
				}
			if (turn == 'B')
				if (7 <= cur_pos && cur_pos <= 12 && data[cur_pos] == 1 && data[12 - cur_pos] > 0) {
					data[13] += data[cur_pos] + data[12 - cur_pos];
					data[cur_pos] = 0;
					data[12 - cur_pos] = 0;
				}
	
			turn = (turn == 'A') ? 'B' : 'A';
	
			for (ChangeListener l : listeners) {
				l.stateChanged(new ChangeEvent(this));
			}
		}	
	}
	
	/** 
	 * Undo move that a player has just made
	 * precondition: another player has not made the move yet.
	 * postcondition: the last move is now reversed and player can make another move.
	 */
	public void undo() {
		if (aUndoNum > 0 && bUndoNum > 0 && data != pre_data) {
			data = pre_data;
			turn = pre_turn;
			
			for (ChangeListener l : listeners) {
				l.stateChanged(new ChangeEvent(this));
			}
			
			if (turn == 'A')
				aUndoNum--;
			else
				bUndoNum--;
		}	
	}

	/**
	 * return the total number of undo button that has been pressed in one turn of PLayer A
	 * @return number of time that undo button has been pressed
	 */
	public int getAUndoNum() {
		return aUndoNum;
	}
	/**
	 * return the total number of undo button that has been pressed in one turn of Player B
	 * @return number of time that undo button has been pressed
	 */
	public int getBUndoNum() {
		return bUndoNum;
	}
	
	/** 
	 * Check if the game is over
	 * precondition: 
	 * postcondition: 
	 */
	public boolean isOver() {
		boolean isOverA = true;
		for (int i = 0; i < 6; i++)
			if (data[i] != 0) {
				isOverA = false;
				break;
			}

		boolean isOverB = true;
		for (int i = 7; i < 13; i++)
			if (data[i] != 0) {
				isOverB = false;
				break;
			}

		return isOverA || isOverB;
	}

	/** 
	 * Calculate the score and get the winner
	 * precondition: 
	 * postcondition: 
	 */
	public String gameOver() {
		for (int i = 0; i < 6; i++) {
			data[6] += data[i];
			data[i] = 0;
		}

		for (int i = 7; i < 13; i++) {
			data[13] += data[i];
			data[i] = 0;
		}
		
		if (data[6] > data[13])
			return "Winner: Player A";
		else if(data[6] < data[13])
			return "Winner: Player B";
		else
			return "Draw";		
	}

	/**
	 * Update UI
	 * precondition: 
	 * postcondition: 
	 */
	public void updateUI() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Attach a listener to the Model
	 * @param c the listener
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	// Field of GameModel class //
	private char turn;
	private char pre_turn;
	private int[] data;
	private int[] pre_data;
	private int aUndoNum;
	private int bUndoNum;
	private boolean isOver;
	private ArrayList<ChangeListener> listeners;
}
