/**
 * The NPC of the Connect Four game
 * Calculates the next best move to be made by NPC
 *
 * @author s1808795
 */
public final class NPC {
    /*    constant variable     */
    public static final int NPC_PLAYER = 2;
    /*    field     */
    private final int player = NPC_PLAYER;
    /**
     * constructor
     */
    public NPC() {}
    /**
     * Calculates the best move for the NPC.
     * @param model an object of class Model containing the state of the game
     * @return int value representing the NPC move
     */
    public int bestMove(Model model){
        int nrRows = model.getNrRows();
        int nrCols = model.getNrCols();
        int winCon = model.getWinCon();
        int[][] boardCopy;
        int value = -10000;
        int score;
        int column = 0;

        for (int j = 0; j< nrCols; j++){
            boardCopy = makeCopy(model);
            if (boardCopy[0][j] == 0){
                int row = nrRows-1;
                while (boardCopy[row][j] != 0){
                    row--;
                }
                boardCopy[row][j] = player;
                score = evaluateBoard(nrRows,nrCols,boardCopy,winCon);
                if (score > value){
                    value = score;
                    column = j;
                }
            }
        }
        return column;
    }
    /**
     * Gives the board a score.
     * @param nrRows int value representing the number of rows
     * @param nrCols int value representing the number of columns
     * @param board  int[][] value representing the board state
     * @param winCon int value representing the number of pieces to connect for a win
     * @return int value representing the score of the board
     */
    public int evaluateBoard(int nrRows, int nrCols, int[][] board, int winCon){
        int score = 0;
        int[] window = new int[winCon];
        // get 1*4 window horizontal
        for (int i = 0; i < nrRows; i++){
            for (int j = 0; j < nrCols-(winCon-1); j++){
                for (int k = 0; k < winCon; k++){
                    window[k] = board[i][j+k];
                    score += calculateScore(window);
                }
            }
        }
        // get 4*1 window vertical
        for (int j = 0; j < nrCols; j++){
            for (int i = 0; i < nrRows-(winCon-1); i++){
                for (int k = 0; k < winCon; k++){
                    window[k] = board[i+k][j];
                    score += calculateScore(window);
                }
            }
        }
        // negative slope diagonal check
        for (int i = 0; i < nrRows-(winCon-1); i++) {
            for (int j = 0; j < nrCols -(winCon-1); j++) {
                for (int k = 0; k < winCon; k++) {
                    window[k] = board[i+k][j+k];
                    score += calculateScore(window);

                }
            }
        }
        // positive slope diagonal check
        for (int i = 3; i < nrRows; i++){
            for (int j = 0; j < nrCols-(winCon-1); j++){
                for (int k = 0; k < winCon; k++) {
                    window[k] = board[i-k][j+k];
                    score += calculateScore(window);

                }
            }
        }
        return score;
    }
    /**
     * Calculates the score of a four piece section.
     * @param window int[] value representing four pieces
     * @return int value representing the score of the four pieces
     */
    public int calculateScore(int[] window) {
        int score = 0;
        int emptyCounter = 0;
        int playerCounter = 0;
        int otherPlayerCounter = 0;
        int otherPlayer = 1;

        for (int piece: window) {
            if (piece == player) {
                playerCounter++;
            } else if (piece == 0) {
                emptyCounter++;
            } else if (piece == otherPlayer) {
                otherPlayerCounter++;
            }
        }
        if (playerCounter == 4) {
            score += 100;
        } else if (playerCounter == 3 && emptyCounter == 1) {
            score += 50;
        } else if (playerCounter == 2 && emptyCounter == 2) {
            score += 10;
        }
        if (otherPlayerCounter == 3 && emptyCounter == 1) {
            score -= 90;
        } else if (otherPlayerCounter == 2 && emptyCounter == 2) {
            score -= 40;
        }

        return score;
    }
    /**
     * Makes a deep copy of the board state from model
     * @param model an object of class Model containing the state of the game
     * @return int[][] value representing a copy of the board state of the game
     */
    public int[][] makeCopy(Model model) {
        int[][] board = model.getBoard();
        int nrRows = model.getNrRows();
        int nrCols = model.getNrCols();
        int[][] boardCopy = new int[nrRows][nrCols];

        for (int i = 0; i <nrRows; i++) {
            for (int j = 0; j < nrCols; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }
}
