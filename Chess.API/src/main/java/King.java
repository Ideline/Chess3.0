public class King extends ChessPiece implements IChessPieces{

    public King(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;
    private final int VALUE = 1000;

    @Override
    public int getValue() {
        return VALUE;
    }

    @Override
    public void setPotentialMoves() {

        if(!safeSpotCheck) {
            clearLists();
        }

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
    public void move(int fromX, int fromY, int rightLeftOrStay, int upDownOrStay) {
        int moveX = fromX + rightLeftOrStay;
        int moveY = fromY + upDownOrStay;
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
