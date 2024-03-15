/**
 * Game Project (Mancala Board Game)
 * @author Anh Le
 * @version 1.6.0 12/03/21
 */


/**
 * A class to run the application (game)
 */
public class GameTester {
	/**
	 * 
	 * 
	 */
	public static void main(String[] args) {
		final GameModel model = new GameModel();
		GameFrame frame = new GameFrame(model);
		model.attach(frame);
	}
}