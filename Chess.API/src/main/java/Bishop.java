public class Bishop extends ChessPiece  {

    public Bishop(int x, int y, String color , int id, int value) {
        super(x, y,color, id, value);
    }

    private int x = tile.getX();
    private int y = tile.getY();
    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;

    public void setPotentialMoves() {
//        moveUpLeft();
//        moveUpRight();
//        moveDownLeft();
//        moveDownRight();
        move(x, y, LEFT, UP);
        move(x, y, LEFT, DOWN);
        move(x, y, RIGHT, UP);
        move(x, y, RIGHT, DOWN);
    }

    private void move(int x, int y, int rightLeftOrStay, int upDownOrStay){
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;
        while(moveOnBoard(moveX, moveY)){
            if(!checkMove(moveX, moveY, true, false, false, safeSpotCheck)) {
                break;
            }
            else{
                moveX += rightLeftOrStay;
                moveY += upDownOrStay;
            }
        }
    }

//    public void moveUpLeft(){
//
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() - 1;
//        while(moveOnBoard(x, y)){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck)) {
//                break;
//            }
//            x--;
//            y--;
//        }
//    }
//
//    public void moveUpRight(){
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() - 1;
//        while(moveOnBoard(x, y)){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck)) {
//                break;
//            }
//            x++;
//            y--;
//        }
//    }
//
//    public void moveDownLeft(){
//        int x = super.tile.getX() - 1;
//        int y = super.tile.getY() + 1;
//        while(moveOnBoard(x, y)){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck)) {
//                break;
//            }
//            x--;
//            y++;
//        }
//    }
//
//    public void moveDownRight(){
//        int x = super.tile.getX() + 1;
//        int y = super.tile.getY() + 1;
//        while(moveOnBoard(x, y)){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck)) {
//                break;
//            }
//            x++;
//            y++;
//        }
//    }
}
