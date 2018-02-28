public class Queen extends ChessPiece implements IChessPieces {

    public Queen(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;
    private final int VALUE = 9;

    @Override
    public int getValue() {
        return VALUE;
    }

    @Override
    public void setPotentialMoves() {

        if(!safeSpotCheck) {
            clearLists();
        }

        move(currentXPosition, currentYPosition, LEFT, STAY);
        move(currentXPosition, currentYPosition, LEFT, UP);
        move(currentXPosition, currentYPosition, LEFT, DOWN);
        move(currentXPosition, currentYPosition, RIGHT, STAY);
        move(currentXPosition, currentYPosition, RIGHT, UP);
        move(currentXPosition, currentYPosition, RIGHT, DOWN);
        move(currentXPosition, currentYPosition, STAY, UP);
        move(currentXPosition, currentYPosition, STAY, DOWN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        while(moveOnBoard(moveX, moveY)){
            if(!checkMove(moveX, moveY, STRIKE, NO_PAWN, false)) {
                break;
            }
            else{
                moveX += rightLeftOrStay;
                moveY += upDownOrStay;
                secondTurn = false;
            }
        }
    }

    @Override
    public void checkNextPotentialMoves(int moveX, int moveY){};
}
