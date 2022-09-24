/**
 * The TextView of the Connect Four game
 * Outputs and receives input from the user
 *
 * @author s1808795
 */
public final class TextView
{
	/**
	 * Constructor
	 */
	public TextView() {}
	/**
	 * Displays game settings.
	 * @param model an object of class Model containing the state of the game
	 */
	public final void displayGameSettingsMessage(Model model) {
		int nrRows = model.getNrRows();
		int nrCols = model.getNrCols();
		int winCon = model.getWinCon();
		System.out.printf("[GAME SETTINGS: Board size = %d * %d, Connect %d to win]\n", nrRows, nrCols, winCon);
	}
	/**
	 * Displays a message notifying the user that the settings they entered are invalid.
	 */
	public final void displayGameSettingsInvalid() {
		System.out.println("The number of pieces to connect must be larger than 1, and the dimensions of the " +
				"board must be larger or equal to the number of pieces to connect, please try again.");
	}
	/**
	 * Displays commands available to the user at the start of the game.
	 */
	public final void displayStartCommands() {
		System.out.println("COMMANDS:\n0 Change Game Settings\n1 Load Game\n2 Play Connect 4 Against NPC\n3 Play Against Human");
	}
	/**
	 * Displays file not found message.
	 */
	public final void displayFileNotFound() {
		System.out.println("Save not found, make sure you have saved a game.");
	}
	/**
	 * Ask the user for number of rows.
	 * @return int value representing the number of rows
	 */
	public final int askForRows() {
		System.out.print("New number of rows: ");
		return InputUtil.readIntFromUser();
	}
	/**
	 * Ask the user for number of columns.
	 * @return int value representing the number of columns
	 */
	public final int askForColumns() {
		System.out.print("New number of columns: ");
		return InputUtil.readIntFromUser();
	}
	/**
	 * Ask the user for number of pieces to connect for a win.
	 * @return int value representing the number of pieces to connect for a win
	 */
	public final int askForWinCondition() {
		System.out.print("New number of pieces to connect: ");
		return InputUtil.readIntFromUser();
	}
	/**
	 * Display message announcing new game has started.
	 */
	public final void displayNewGameMessage()
	{
		System.out.println("---- NEW GAME STARTED ----");
	}
	/**
	 * Displays input instructions.
	 * @param model an object of class Model containing the state of the game
	 */
	public final void displayInputInstructions(Model model) {
		int nrCols = model.getNrCols();
		System.out.printf("Please enter a valid free column number between 1 and %s. " +
				"(Enter 0 to open the commands list.)\n", nrCols);
	}
	/**
	 * Asks the user for a move.
	 * @param model an object of class Model containing the state of the game
	 * @return int value representing the user move
	 */
	public final int askForMove(Model model) {
		int player = model.getPlayer();
		System.out.printf("Player %s: ",player);
		return InputUtil.readIntFromUser();
	}
	/**
	 * Tells the user their move was invalid and to try again.
	 * @param model an object of class Model containing the state of the game
	 * @param move int value representing the user move
	 */
	public final void displayMoveInvalid(Model model, int move) {
		int nrCols = model.getNrCols();
		System.out.printf("%s is not a valid free column number between 1 and %s, please try again.\n", move, nrCols);
	}
	/**
	 * Display command available to the user in-game.
	 */
	public final void displayInGameMenuCommands() {
		System.out.println("COMMANDS:\n0 Return to Game\n1 New Game\n2 Save Game\n3 Surrender\n4 Quit");
	}
	/**
	 * Asks for a command from the user.
	 * @param model an object of class Model containing the state of the game
	 * @return int value representing the user command
	 */
	public final int askForCommand(Model model) {
		int player = model.getPlayer();
		System.out.printf("Player %s : ", player);
		return InputUtil.readIntFromUser();
	}
	/**
	 * Display command is not valid.
	 * @param command int value representing the command the user gave
	 */
	public final void displayCommandInvalid(int command) {
		System.out.printf("%s is not a valid command, please enter a command between 0 and 3.\n", command);
	}
	/**
	 * show the move the NPC made
	 * @param move int value representing the npc move
	 */
	public final void displayNPCMove(int move) {
		System.out.printf("Player 2: %s\n", move);
	}
	/**
	 * Displays the board
	 * @param model an object of class Model containing the state of the game
	 */
	public final void displayBoard(Model model)	{
		int nrRows = model.getNrRows();
		int nrCols = model.getNrCols();
		int[][] board = model.getBoard();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < nrCols; i++) {
			sb.append(" [").append(i + 1).append(']');
		}
		sb.append('\n');
		String rowDivider = "-".repeat(4 * nrCols + 1);
		for (int i = 0; i < nrRows; i++) {
			sb.append(rowDivider);
			sb.append('\n');
			for (int j = 0; j<nrCols ; j++) {
				sb.append("| ").append(board[i][j]).append(' ');
			}
			sb.append('|');
			sb.append('\n');
		}
		sb.append(rowDivider);
		System.out.println(sb);
	}
	/**
	 * Displays the in message
	 * @param model an object of class Model containing the state of the game
	 */
	public final void displayWinMessage(Model model) {
		int player = model.getPlayer();
		int winCon = model.getWinCon();
		System.out.printf("Player %s wins by achieving connect %s!\n", player, winCon);
	}
	/**
	 * Displays surrender message.
	 * @param model an object of class Model containing the state of the game
	 */
	public final void displaySurrenderMessage(Model model) {
		int player = model.getPlayer();
		int otherPlayer = 1;
		if (player == 1){
			otherPlayer = 2;
		}
		System.out.printf("Player %s has surrendered. Player %s wins!\n", player, otherPlayer);
	}
	/**
	 * Displays board is full
	 */
	public final void displayBoardFullMessage() {
		System.out.println("The board is now full, there are no more valid moves to be made, it is a draw.");
	}
	/**
	 * Asks for a new move from user
	 * @return int value representing the new user move
	 */
	public final int askForNewGame() {
		System.out.println("Enter 0 to start a new game or any other integer to quit:");
		return InputUtil.readIntFromUser();
	}
}
