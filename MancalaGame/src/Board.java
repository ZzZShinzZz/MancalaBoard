/**
 * Game Project (Mancala Board Game)
 * @author Anh Le
 * @version 1.6.0 12/03/21
 */
import java.awt.*;

/**
 * Mancala Game Board
 */
public interface Board {
	/**
	 * Draws the board
	 * @param g2 - the graphics context
	 */
	void drawBoard(Graphics2D g2);
	/**
	 * Draws the stones
	 * @param g - the graphics context
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param num - number of stones
	 * @param rangeX - x coordinate draw range
	 * @param rangeY - y coordinate draw range
	 */
	void drawStones(Graphics g, int x, int y, int num, int rangeX, int rangeY);
}