public class Rook extends ChessPiece implements IChessPieces {

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
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;

    @Override
    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, STAY, UP, FIRSTTURN);
        move(currentXPosition, currentYPosition, STAY, DOWN, FIRSTTURN);
        move(currentXPosition, currentYPosition, LEFT, STAY, FIRSTTURN);
        move(currentXPosition, currentYPosition, RIGHT, STAY, FIRSTTURN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondMove) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        while (moveOnBoard(moveX, moveY)) {
            if (!checkMove(moveX, moveY, STRIKE, NO_PAWN, firstOrSecondMove, safeSpotCheck)) {
                break;
            } else {
                moveX += rightLeftOrStay;
                moveY += upDownOrStay;
            }
        }
    }
}
