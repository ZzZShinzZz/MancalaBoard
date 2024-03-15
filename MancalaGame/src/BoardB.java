/**
 * Game Project (Mancala Board Game)
 * @author Anh Le
 * @version 1.6.0 12/03/21
 */
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Mancala Game Board style 2
 */
public class BoardB extends JPanel implements Board {
	/**
	 * Read in the Board style image
	 */
	public BoardB() {
		try {
            image = ImageIO.read(new File("./src/images/SimpleBoard1.png"));
            stone = ImageIO.read(new File("./src/images/stone1.png"));
        }
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
	}

	/**
	 * Draws the board
	 * @param g2 - the graphics context
	 */
	public void drawBoard(Graphics2D g2) {
        g2.drawImage(image, 0, 50, null);
	}
	
	/**
	 * Draws the stones
	 * @param g - the graphics context
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param num - number of stones
	 * @param rangeX - x coordinate draw range
	 * @param rangeY - y coordinate draw range
	 */
	public void drawStones(Graphics g, int x, int y, int num, int rangeX, int rangeY) {
		for (int i=0; i<num; i++) {
			int wid = 0;
			int len = 0;
			wid = (int)(Math.random()*rangeX);
			len = (int)(Math.random()*rangeY);
			g.drawImage(stone, x+wid, y+len, null);
		}
	}
	
	private Image image;
	private Image stone;
}
