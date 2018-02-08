public class Knight extends ChessPiece implements IChessPieces {

    public Knight(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private final int FARUP = -2;
    private final int UP = -1;
    private final int FARDOWN = 2;
    private final int DOWN = 1;
    private final int FARLEFT = -2;
    private final int LEFT = -1;
    private final int FARRIGHT = 2;
    private final int RIGHT = 1;
    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;

    @Override
    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, LEFT, FARUP, FIRSTTURN);
        move(currentXPosition, currentYPosition, LEFT, FARDOWN, FIRSTTURN);
        move(currentXPosition, currentYPosition, FARLEFT, UP, FIRSTTURN);
        move(currentXPosition, currentYPosition, FARLEFT, DOWN, FIRSTTURN);
        move(currentXPosition, currentYPosition, RIGHT, FARUP, FIRSTTURN);
        move(currentXPosition, currentYPosition, RIGHT, FARDOWN, FIRSTTURN);
        move(currentXPosition, currentYPosition, FARRIGHT, UP, FIRSTTURN);
        move(currentXPosition, currentYPosition, FARRIGHT, DOWN, FIRSTTURN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondMove){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if(moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, STRIKE, NO_PAWN, firstOrSecondMove, safeSpotCheck);
        }
    }
}
