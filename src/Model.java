import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * The Model of the Connect Four game.
 * Stores the state of the game and has logic to update the data
 *
 * @author s1808795
 */
public final class Model
{
	// ===========================================================================
	// ================================ CONSTANTS ================================
	// ===========================================================================
	public static final int DEFAULT_NR_ROWS = 6; //
	public static final int DEFAULT_NR_COLS = 7;
	public static final int DEFAULT_WIN_CON = 4;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	// ========================================================================
	// ================================ FIELDS ================================
	// ========================================================================
	private int nrRows;
	private int nrCols;
	private int winCon;
	private int[][] board;
	private boolean hasSurrendered;
	private int player;

	// =============================================================================
	// ================================ CONSTRUCTOR ================================
	// =============================================================================
	public Model() {
		nrRows = DEFAULT_NR_ROWS;
		nrCols = DEFAULT_NR_COLS;
		winCon = DEFAULT_WIN_CON;
		board = new int[nrRows][nrCols];
		hasSurrendered = false;
		player = PLAYER_1;
	}
	// ====================================================================================
	// ================================ MODEL INTERACTIONS ================================
	// ====================================================================================
	/**
	 * Input validation for the settings passed in.
	 * @param nrRows int value representing number of rows
	 * @param nrCols int value representing number of columns
	 * @param winCon int value representing the number of pieces to connect for a win
	 * @return boolean value representing whether the settings are valid
	 */
	public boolean areSettingsValid(int nrRows, int nrCols, int winCon) {
		return (winCon > 1 && nrRows >= winCon && nrCols >= winCon);
	}
	/**
	 * Changes the state of the game with the new values passed in.
	 * @param nrRows int value representing number of rows
	 * @param nrCols int value representing number of columns
	 * @param winCon int value representing the number of pieces to connect for a win
	 */
	public void changeGameSettings(int nrRows, int nrCols, int winCon) {
		this.nrRows = nrRows;
		this.nrCols = nrCols;
		board = new int[nrRows][nrCols];
		this.winCon = winCon;
	}
	/**
	 * Input validation for user move.
	 * @param move int value representing user move
	 * @return boolean value representing whether the move is valid
	 */
	public boolean isMoveValid(int move) {
		return move < nrCols && move > -1 && board[0][move] == 0;
	}
	/**
	 * Makes move for the current player.
	 * @param move int value representing user move
	 */
	public void makeMove(int move) {
		int row = nrRows-1;
		while (board[row][move] != 0){
			row--;
		}
		board[row][move] = player;
	}
	/**
	 * Switches to the next player.
	 */
	public void switchPlayer() {
		switch (player){
			case PLAYER_1:
				player = PLAYER_2;
				break;
			case PLAYER_2:
				player = PLAYER_1;
				break;
		}
	}
	/**
	 * checks if the game is over.
	 * @return boolean value representing whether the game is over
	 */
	public boolean isGameOver(){
		return isBoardFull() || hasSurrendered || isWinConMet();
	}
	/**
	 * Changes the state of hasSurrendered.
	 */
	public void hasSurrendered(){
		hasSurrendered = true;
	}
	/**
	 * Checks if the board is full.
	 * @return boolean value representing whether the board is full
	 */
	public boolean isBoardFull() {
		for (int i = 0; i < nrRows; i++) {
			for (int j = 0; j < nrCols; j++) {
				if (board[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Checks if a win condition has been met.
	 * @return boolean value representing whether a win condition has been met
	 */
	public boolean isWinConMet() {
		//horizontal check
		for (int i = 0; i < nrRows; i++) {
			int counter = 0;
			for (int j = 0; j < nrCols; j++) {
				if (board[i][j] == player) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == winCon) {
					return true;
				}
			}
		}
		//vertical check
		for (int j = 0; j < nrCols; j++) {
			int counter = 0;
			for (int i = 0; i < nrRows; i++) {
				if (board[i][j] == player) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == winCon) {
					return true;
				}
			}
		}
		// negative slope diagonal check
		for (int i = 0; i < nrRows-(winCon-1); i++) {
			for (int j = 0; j < nrCols -(winCon-1); j++) {
				int counter = 0;
				for (int k = 0; k < winCon; k++) {
					if (board[i + k][j + k] == player) {
						counter++;
					} else {
						counter = 0;
					}
					if (counter == winCon) {
						return true;
					}
				}
			}
		}
		// positive slope diagonal check
		for (int i = 3; i < nrRows; i++) {
			for (int j = 0; j < nrCols-(winCon-1); j++) {
				int counter = 0;
				for (int k = 0; k <winCon; k++) {
					if (board[i-k][j+k] == player) {
						counter++;
					} else {
						counter = 0;
					}
					if (counter == winCon) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Resets the state of the board to default values.
	 */
	public void resetState() {
		nrRows = DEFAULT_NR_ROWS;
		nrCols = DEFAULT_NR_COLS;
		winCon = DEFAULT_WIN_CON;
		board = new int[nrRows][nrCols];
		hasSurrendered = false;
		player = PLAYER_1;
	}
	/**
	 * Converts the state of the game to a string.
	 * @return Stringbuilder value representing the state of the game
	 */
	public StringBuilder convertStateToString() {
		StringBuilder sb = new StringBuilder();
		//convert board state
		for (int i = 0; i < nrRows; i++) {
			for (int j = 0; j < nrCols; j++) {
				sb.append(board[i][j]);
			}
		}
		sb.append("-");
		sb.append(nrRows).append("-");
		sb.append(nrCols).append("-");
		sb.append(winCon).append("-");
		sb.append(player);
		return sb;
	}
	/**
	 * Gets new state of game from a string.
	 * @param state String value representing the state of the game
	 */
	public void convertStringToState(String state) {
		String[] stateSections = state.split("-");
		String board = stateSections[0];
		nrRows = Integer.parseInt(stateSections[1]);
		nrCols = Integer.parseInt(stateSections[2]);
		winCon = Integer.parseInt(stateSections[3]);
		player = Integer.parseInt(stateSections[4]);

		int k = 0;
		for (int i = 0; i < nrRows; i++) {
			for (int j = 0; j < nrCols; j++) {
				this.board[i][j] = Integer.parseInt(board.charAt(k)+"");
				k++;
			}
		}
	}
	/**
	 * Writes the state of the game to a file.
	 * @param state StringBuilder value representing the state of the game to be saved
	 */
	public void writeToFile(StringBuilder state) {
		try {
			FileWriter myWriter = new FileWriter("gameState.txt");
			myWriter.write(String.valueOf(state));
			myWriter.close();
			System.out.println("Game Saved.");
		} catch (IOException e) {
			System.out.println("A problem occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the file at the specified directory exists.
	 * @return boolean value representing whether a file exists
	 */
	public boolean fileExists() {
		File file = new File(System.getProperty("user.dir") + "\\gameState.txt");
		if (file.exists()) {return true;}
		return false;
	}
	/**
	 * Reads the game state from file to a string.
	 * @return String value representing the state of a saved game
	 */
	public String readFromFile() {
		String state = "";
		try {
			File file = new File("gameState.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				state = scanner.nextLine();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return state;
	}
	// =========================================================================
	// ================================ GETTERS ================================
	// =========================================================================
	public int getNrRows() {return nrRows;}
	public int getNrCols() {return nrCols;}
	public int getWinCon() {return winCon;}
	public int[][] getBoard() {return board;}
	public int getPlayer() {return player;}
}
