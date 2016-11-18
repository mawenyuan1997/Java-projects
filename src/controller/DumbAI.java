package controller;

import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;
import model.Board;
/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {

	public DumbAI(Player me) {
		super(me);
	}

	protected @Override Location nextMove(Game g) {
		// Note: Calling delay here will make the CLUI work a little more
		// nicely when competing different AIs against each other.
		delay();
		Board b = g.getBoard();
		for(int i = 0 ; i < Board.NUM_ROWS ; i++) {
			for(int j = 0 ; j < Board.NUM_COLS ; j++) {
				if (b.get(i, j) == null)
					return new Location(i, j);
			}
		}
		return null;
	}
}
