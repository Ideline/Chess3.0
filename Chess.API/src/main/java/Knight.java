public class Knight extends ChessPiece implements IChessPieces {

    public Knight(int x, int y, String color, int id) {
        super(x, y, color, id);
    }


    private final boolean NO_PAWN = false;
    private final boolean STRIKE = true;
    private final int VALUE = 3;

    @Override
    public int getValue() {
        return VALUE;
    }

    @Override
    public void setPotentialMoves() {

        if(!safeSpotCheck) {
            clearLists();
        }

        move(currentXPosition, currentYPosition, LEFT, FARUP);
        move(currentXPosition, currentYPosition, LEFT, FARDOWN);
        move(currentXPosition, currentYPosition, FARLEFT, UP);
        move(currentXPosition, currentYPosition, FARLEFT, DOWN);
        move(currentXPosition, currentYPosition, RIGHT, FARUP);
        move(currentXPosition, currentYPosition, RIGHT, FARDOWN);
        move(currentXPosition, currentYPosition, FARRIGHT, UP);
        move(currentXPosition, currentYPosition, FARRIGHT, DOWN);
    }

    @Override
    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if(moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, STRIKE, NO_PAWN, false);
            if(secondTurn){
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    @Override
    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, LEFT, FARUP);
        secondTurn = true;
        move(moveX, moveY, LEFT, FARDOWN);
        secondTurn = true;
        move(moveX, moveY, FARLEFT, UP);
        secondTurn = true;
        move(moveX, moveY, FARLEFT, DOWN);
        secondTurn = true;
        move(moveX, moveY, RIGHT, FARUP);
        secondTurn = true;
        move(moveX, moveY, RIGHT, FARDOWN);
        secondTurn = true;
        move(moveX, moveY, FARRIGHT, UP);
        secondTurn = true;
        move(moveX, moveY, FARRIGHT, DOWN);
        secondTurn = false;
    }
}
