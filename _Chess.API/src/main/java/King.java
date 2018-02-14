public class King extends ChessPiece implements IChessPieces{

    public King(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int STAY = 0;
    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;

    @Override
    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, STAY, UP);
        move(currentXPosition, currentYPosition, STAY, DOWN);
        move(currentXPosition, currentYPosition, LEFT, STAY);
        move(currentXPosition, currentYPosition, LEFT, UP);
        move(currentXPosition, currentYPosition, LEFT, DOWN);
        move(currentXPosition, currentYPosition, RIGHT, STAY);
        move(currentXPosition, currentYPosition, RIGHT, UP);
        move(currentXPosition, currentYPosition, RIGHT, DOWN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if (moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, STRIKE, NO_PAWN, false);
            if(secondTurn){
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, STAY, UP);
        secondTurn = true;
        move(moveX, moveY, STAY, DOWN);
        secondTurn = true;
        move(moveX, moveY, RIGHT, STAY);
        secondTurn = true;
        move(moveX, moveY, RIGHT, UP);
        secondTurn = true;
        move(moveX, moveY, RIGHT, DOWN);
        secondTurn = true;
        move(moveX, moveY, LEFT, STAY);
        secondTurn = true;
        move(moveX, moveY, LEFT, UP);
        secondTurn = true;
        move(moveX, moveY, LEFT, DOWN);
        secondTurn = false;
    }
}
