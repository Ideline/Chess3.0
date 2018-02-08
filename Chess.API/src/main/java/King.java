public class King extends ChessPiece {

    public King(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private int x = tile.getX();
    private int y = tile.getY();
    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int STAY = 0;

    public void setPotentialMoves() {
//        moveUp();
//        moveDown();
//        moveUpRight();
//        moveUpLeft();
//        moveDownRight();
//        moveDownLeft();
//        moveRight();
//        moveLeft();
        move(x, y, STAY, UP);
        move(x, y, STAY, DOWN);
        move(x, y, LEFT, STAY);
        move(x, y, LEFT, UP);
        move(x, y, LEFT, DOWN);
        move(x, y, RIGHT, STAY);
        move(x, y, RIGHT, UP);
        move(x, y, RIGHT, DOWN);
    }

    private void move(int x, int y, int rightLeftOrStay, int upDownOrStay) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        if (moveOnBoard(moveX, moveY)) {
            checkMove(moveX, moveY, true, false, false, safeSpotCheck);
        }
    }

//    private void moveRight() {
//        int x = super.tile.getX();
//        int y = super.tile.getY() - 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUp() {
//        int x = super.tile.getX();
//        int y = super.tile.getY() - 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDown() {
//        int x = super.tile.getX();
//        int y = super.tile.getY() + 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUpRight() {
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() - 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveUpLeft() {
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() - 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownLeft() {
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() + 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
//
//    private void moveDownRight() {
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() + 1;
//        if (moveOnBoard(x, y)) {
//            checkMove(x, y, true, false, false, safeSpotCheck);
//        }
//    }
}
