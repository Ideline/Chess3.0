public class Rook extends ChessPiece {

    public Rook(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

        private final int UP = -1;
        private final int DOWN = 1;
        private final int LEFT = -1;
        private final int RIGHT = 1;
        private final int STAY = 0;


    public void setPotentialMoves() {
        move(currentXPosition, currentYPosition, STAY, UP);
        move(currentXPosition, currentYPosition, STAY, DOWN);
        move(currentXPosition, currentYPosition, LEFT, STAY);
        move(currentXPosition, currentYPosition, RIGHT, STAY);
    }

    private void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        while(moveOnBoard(moveX, moveY)){
            if(!checkMove(moveX, moveY, true, false, false, safeSpotCheck)){
                break;
            }
            else{
                moveX += rightLeftOrStay;
                moveY += upDownOrStay;
            }
        }
    }
}
