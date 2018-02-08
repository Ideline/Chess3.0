public class Knight extends ChessPiece {

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

    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, LEFT, FARUP);
        move(currentXPosition, currentYPosition, LEFT, FARDOWN);
        move(currentXPosition, currentYPosition, FARLEFT, UP);
        move(currentXPosition, currentYPosition, FARLEFT, DOWN);
        move(currentXPosition, currentYPosition, RIGHT, FARUP);
        move(currentXPosition, currentYPosition, RIGHT, FARDOWN);
        move(currentXPosition, currentYPosition, FARRIGHT, UP);
        move(currentXPosition, currentYPosition, FARRIGHT, DOWN);
    }

    private void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if(moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, true, false, false, safeSpotCheck);
        }
    }
}
