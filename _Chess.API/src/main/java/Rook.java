public class Rook extends ChessPiece {

    public Rook(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int STAY = 0;
    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;

    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, STAY, UP, false);
        move(currentXPosition, currentYPosition, STAY, DOWN, false);
        move(currentXPosition, currentYPosition, LEFT, STAY, false);
        move(currentXPosition, currentYPosition, RIGHT, STAY, false);
    }

    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean nextTurn) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        while (moveOnBoard(moveX, moveY)) {
            if (!checkMove(moveX, moveY, STRIKE, NO_PAWN, nextTurn)) {
                break;
            } else {
                if(!nextTurn){
                    checkNextPotentialMoves(moveX, moveY);
                }
                moveX += rightLeftOrStay;
                moveY += upDownOrStay;
            }
        }
    }

    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, STAY, UP, true);
        move(moveX, moveY, STAY, DOWN, true);
        move(moveX, moveY, LEFT, STAY, true);
        move(moveX, moveY, RIGHT, STAY, true);
    }
}
