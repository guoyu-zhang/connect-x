/**
 * The Controller of the Connect Four game.
 * Controls the main data flow of the game by manipulating Model and NPC
 * based on upcoming requests from View and updating the View with new Model data.
 *
 * @author s1808795
 */
public final class Controller
{
	private final Model model;
	private final TextView view;
	private final NPC npc;
	/*    field     */
	private boolean playingNPC;
	private boolean hasQuit;
	private boolean isGameOver;

	/**
	 * Constructor
	 * @param model an object of class Model containing the state of the game
	 * @param view an object of class View containing the display methods of the game
	 * @param npc an object of class NPC containing NPC of the game
	 */
	public Controller(Model model, TextView view, NPC npc)	{
		this.model = model;
		this.view = view;
		this.npc = npc;
		playingNPC = false;
		hasQuit = false;
		isGameOver = false;
	}
	/**
	 * Begins the session
	 */
	public void startSession() {
		int move;

		view.displayGameSettingsMessage(model);
		view.displayStartCommands();
		processCommand(getCommand(3));
		isGameOver = model.isGameOver();
		view.displayBoard(model);
		view.displayNewGameMessage();
		while (!isGameOver) {
			move = getMove();
			if (move == 0) {
				view.displayInGameMenuCommands();
				processCommand(getCommand(4)+3);
				if (hasQuit) {break;}
			} else {
				processPlayerMove(move);
				view.displayBoard(model);
				isGameOver = model.isGameOver();
				getWinner();
				model.switchPlayer();
				if (isGameOver) {break;}
				if (playingNPC) {
					processNPCMove();
					view.displayBoard(model);
					isGameOver = model.isGameOver();
					getWinner();
					model.switchPlayer();
				}
			}
		}
		if (model.isBoardFull() && !model.isWinConMet()) { // !model.isWinConMet() as player is switched
			view.displayBoardFullMessage();
		}
		if (!hasQuit) {
			askForNewGame();
		}
	}
	/**
	 * Gets command from user and makes sure it is valid.
	 * @param numCommands the number of commands available
	 * @return a valid command of type int
	 */
	public int getCommand(int numCommands) {
		int command = view.askForCommand(model);
		while (command < 0 || command > numCommands) {
			view.displayCommandInvalid(command);
			command = view.askForCommand(model);
		}
		return command;
	}
	/**
	 * Performs an action based on the command it gets.
	 * @param command the command the user inputted
	 */
	public void processCommand(int command) {
		switch (command){
			case 0:
				processGameSettings();
				break;
			case 1:
				if (!model.fileExists()){
					view.displayFileNotFound();
					view.displayStartCommands();
					processCommand(getCommand(3));
				} else {
					loadGame();
				}
				break;
			case 2:
				playingNPC = true;
				break;
			case 3:
				break;
			case 4:
				model.resetState();
				startSession();
				break;
			case 5:
				saveGame();
				break;
			case 6:
				playerSurrender();
				isGameOver = true;
				break;
			case 7:
				hasQuit = true;
				break;
		}
	}
	/**
	 * Gets and sets new game settings then shows it to the user.
	 */
	public void processGameSettings() {
		int nrRows = view.askForRows();
		int nrCols = view.askForColumns();
		int winCon = view.askForWinCondition();
		while (!model.areSettingsValid(nrRows,nrCols,winCon)) {
			view.displayGameSettingsInvalid();
			nrRows = view.askForRows();
			nrCols = view.askForColumns();
			winCon = view.askForWinCondition();
		}
		model.changeGameSettings(nrRows,nrCols,winCon);
		view.displayGameSettingsMessage(model);
	}
	/**
	 * Read the state from file and sets it to current state.
	 */
	public void loadGame() {
		String state = model.readFromFile();
		model.convertStringToState(state);
	}
	/**
	 * Gets the state of the game and writes it to file.
	 */
	public void saveGame() {
		StringBuilder state = model.convertStateToString();
		model.writeToFile(state);
	}
	/**
	 * Changes the surrender state of the game.
	 */
	public void playerSurrender() {
		model.hasSurrendered();
		view.displaySurrenderMessage(model);
	}
	/**
	 * Gets a move from the user.
	 * @return An int value representing the user move.
	 */
	public int getMove() {
		view.displayInputInstructions(model);
		return view.askForMove(model);
	}
	/**
	 * Makes sure the move is valid and executes it.
	 * @param move An int value representing the user move
	 */
	public void processPlayerMove(int move) {
		move--; // Takes 1 away from move so that move now represents the index of a column.
		move = playerMoveValidation(move);
		model.makeMove(move);
	}
	/**
	 * Makes sure the move is valid.
	 * @param move An int value representing the user move
	 * @return An int value representing a valid user move.
	 */
	public int playerMoveValidation(int move) {
		while (!model.isMoveValid(move)) {
			view.displayMoveInvalid(model, move+1);
			move = view.askForMove(model);
			move--;
		}
		return move;
	}
	/**
	 * Gets and makes NPC move.
	 */
	public void processNPCMove() {
		int move = npc.bestMove(model);
		model.makeMove(move);
		view.displayNPCMove(move+1);
	}
	/**
	 * Gets the winner.
	 */
	public void getWinner() {
		if (model.isWinConMet()) {
			view.displayWinMessage(model);
		}
	}
	/**
	 * Starts a new game if the user want to.
	 */
	public void askForNewGame() {
		if (view.askForNewGame() == 0) {
			model.resetState();
			startSession();
		}
	}
}
