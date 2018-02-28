public class BlackPawn extends ChessPiece implements IPawn{

    public BlackPawn(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    private final int STARTPOSITION = 1;
    private final int OPPONENTS_NEST = 7;
    private final boolean PAWN = true;
    private final boolean STRIKE = true;
    private final boolean MOVE = false;
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;
    private final int VALUE = 1;

    @Override
    public int getValue() {
        return VALUE;
    }

    @Override
    public void setPotentialMoves() {

        if(!safeSpotCheck) {
            clearLists();
        }

        if(currentYPosition == OPPONENTS_NEST){
            setPossibleQueen(true); // Signals to swap the pawn for a queen
        }
        else {
            if (currentYPosition == STARTPOSITION) {
                firstMove();
            }
            move(currentXPosition, currentYPosition, STAY, DOWN, FIRSTTURN, MOVE);
            move(currentXPosition, currentYPosition, LEFT, DOWN, FIRSTTURN,  STRIKE);
            move(currentXPosition, currentYPosition, RIGHT, DOWN, FIRSTTURN, STRIKE);
        }
    }

    @Override
    public void firstMove() {
        int moveY = currentYPosition + DOWN;
        int moveX = currentXPosition;
        if(tileIsEmpty(moveX, moveY)){
            moveY += DOWN;
            // When we have checked if it's open to move two steps we try the move
            if( checkMove(moveX, moveY, MOVE, PAWN, FIRSTTURN)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondMove, boolean strikeOrMove) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;

        if(moveOnBoard(moveX, moveY)){
            if(checkMove(moveX, moveY,strikeOrMove, PAWN, firstOrSecondMove)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, STAY, DOWN, SECONDTURN, MOVE);
        move(moveX, moveY, LEFT, DOWN, SECONDTURN,  STRIKE);
        move(moveX, moveY, RIGHT, DOWN, SECONDTURN, STRIKE);
    }
}
