public class Knight extends ChessPiece {

    public Knight(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private int x = tile.getX();
    private int y = tile.getY();
    private final int FARUP = -2;
    private final int UP = -1;
    private final int FARDOWN = 2;
    private final int DOWN = 1;
    private final int FARLEFT = -2;
    private final int LEFT = -1;
    private final int FARRIGHT = 2;
    private final int RIGHT = 1;

    public void setPotentialMoves() {
//        moveUpLongLeft();
//        moveUpLongRight();
//        moveUpShortLeft();
//        moveUpShortRight();
//        moveDownLongLeft();
//        moveDownLongRight();
//        moveDownShortLeft();
//        moveDownShortRight();
        move(x, y, LEFT, FARUP);
        move(x, y, LEFT, FARDOWN);
        move(x, y, FARLEFT, UP);
        move(x, y, FARLEFT, DOWN);
        move(x, y, RIGHT, FARUP);
        move(x, y, RIGHT, FARDOWN);
        move(x, y, FARRIGHT, UP);
        move(x, y, FARRIGHT, DOWN);
    }

    private void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if(moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, true, false, false, safeSpotCheck);
        }
    }

//    private void moveUpLongRight() {
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() - 2;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUpLongLeft() {
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() - 2;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUpShortLeft(){
//        int x = super.tile.getX() - 2;
//        int y = super.tile.getY() - 1;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUpShortRight(){
//        int x = super.tile.getX() + 2;
//        int y = super.tile.getY() - 1;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownLongLeft(){
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() + 2;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownLongRight(){
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() + 2;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownShortLeft(){
//        int x = super.tile.getX() - 2;
//        int y = super.tile.getY() - 1;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownShortRight(){
//        int x = super.tile.getX() + 2;
//        int y = super.tile.getY() + 1;
//        if(moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
}
