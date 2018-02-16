public class WhitePawn extends ChessPiece implements IPawn{

    public WhitePawn(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }


    private final int STARTPOSITION = 6;
    private final int OPPONENTS_NEST = 0;
    private final boolean PAWN = true;
    private final boolean STRIKE = true;
    private final boolean MOVE = false;
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;

    @Override
    public void setPotentialMoves() {
        // Signals to swap the pawn for a queen
        if(currentYPosition == OPPONENTS_NEST){
            setPossibleQueen(true); // Signals to swap the pawn for a queen
        }
        else {
            if (currentYPosition == STARTPOSITION) {
                firstMove();
            }
            move(currentXPosition, currentYPosition, STAY, UP, FIRSTTURN, MOVE);
            move(currentXPosition, currentYPosition, LEFT, UP, FIRSTTURN,  STRIKE);
            move(currentXPosition, currentYPosition, RIGHT, UP, FIRSTTURN, STRIKE);
        }
    }

    @Override
    public void firstMove() {
        int moveY = currentYPosition + UP;
        int moveX = currentXPosition;
        if(tileIsEmpty(moveX, moveY)){
            moveY += UP;
            // When we have checked if it's open to move two steps we try the move
            if( checkMove(moveX, moveY, MOVE, PAWN, FIRSTTURN)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondTurn, boolean strikeOrMove) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;

        if(moveOnBoard(moveX, moveY)){
            if(checkMove(moveX, moveY,strikeOrMove, PAWN, firstOrSecondTurn)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, STAY, UP, SECONDTURN, MOVE);
        move(moveX, moveY, LEFT, UP, SECONDTURN,  STRIKE);
        move(moveX, moveY, RIGHT, UP, SECONDTURN, STRIKE);
    }
}
