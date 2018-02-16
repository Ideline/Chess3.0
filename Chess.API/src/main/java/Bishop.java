public class Bishop extends ChessPiece implements IChessPieces {

    public Bishop(int x, int y, String color , int id, int value) {
        super(x, y,color, id, value);
    }

    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;

    @Override
    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, LEFT, UP);
        move(currentXPosition, currentYPosition, LEFT, DOWN);
        move(currentXPosition, currentYPosition, RIGHT, UP);
        move(currentXPosition, currentYPosition, RIGHT, DOWN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
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
