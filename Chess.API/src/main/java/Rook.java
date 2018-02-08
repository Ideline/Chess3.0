public class Rook extends ChessPiece {

    public Rook(int x, int y, String color, int id, int value) {
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
//        moveDown();
//        moveUp();
//        moveLeft();
//        moveRight();
        move(x, y, STAY, UP);
        move(x, y, STAY, DOWN);
        move(x, y, LEFT, STAY);
        move(x, y, RIGHT, STAY);
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

//    public void moveDown(){
//        int x = super.tile.getX();
//        for(int y = super.tile.getY()-1; y >= 0; y-- ){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck))
//                break;
//        }
//    }
//
//    public void moveUp(){
//        int x = super.tile.getX();
//        for(int y = super.tile.getY()+1; y < 8; y++ ){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck))
//                break;
//        }
//    }
//
//    public void moveLeft(){
//        int y = super.tile.getY();
//        for(int x = super.tile.getX()-1; x >= 0; x-- ){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck))
//                break;
//        }
//    }
//
//    public void moveRight(){
//        int y = super.tile.getY();
//        for(int x = super.tile.getX()+1; x < 8; x++ ){
//            if(!checkMove(x, y, true, false, false, safeSpotCheck))
//                break;
//
//        }
//    }
}
