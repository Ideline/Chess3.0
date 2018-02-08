public class WhitePawn extends ChessPiece implements IPawn{

    public WhitePawn(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private int x = tile.getX();
    private int y = tile.getY();
    private final int UP = -1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int STAY = 0;
    private final boolean PAWN = true;
    private final boolean STRIKE = true;
    private final boolean MOVE = false;
    private final boolean FIRSTTURN = false;
    private final boolean SECONDTURN = true;

    @Override
    public void setPotentialMoves() {
        // Signals to swap the pawn for a queen
        if(super.tile.getY() == 0){
            setPossibleQueen(true);
        }
        else {
            // If it's the pawns first move
            if (super.tile.getY() == 6) {
                firstMove();
            }
            // And all moves
//            nextMove(super.tile.getX(), super.tile.getY() - 1, false);
//            strikeRight(super.tile.getX() + 1, super.tile.getY() - 1, false);
//            strikeLeft(super.tile.getX() - 1, super.tile.getY() - 1, false);
            move(x, y, STAY, UP, FIRSTTURN, MOVE);
            move(x, y, LEFT, UP, FIRSTTURN,  STRIKE);
            move(x, y, RIGHT, UP, FIRSTTURN, STRIKE);
        }
    }

    // If the first tile is empty we check the next
    @Override
    public void firstMove() {
        int moveY = y + UP;
        int moveX = x;
        if(tileIsEmpty(moveX, moveY)){
            moveY = y + UP;
            // When we have checked are first potential move we check the follow up move
            if( checkMove(moveX, moveY, MOVE, PAWN, FIRSTTURN, safeSpotCheck)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    public void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondMove, boolean strikeOrMove) {
        int moveX = x + rightLeftOrStay;
        int moveY = y + upDownOrStay;

        if(moveOnBoard(moveX, moveY)){
            if(checkMove(moveX, moveY,strikeOrMove, PAWN, firstOrSecondMove, safeSpotCheck)) {
                checkNextPotentialMoves(moveX, moveY);
            }
        }
    }

    public void checkNextPotentialMoves(int moveX, int moveY){
        move(moveX, moveY, STAY, UP, SECONDTURN, MOVE);
        move(moveX, moveY, LEFT, UP, SECONDTURN,  STRIKE);
        move(moveX, moveY, RIGHT, UP, SECONDTURN, STRIKE);
    }

//    @Override
//    public void nextMove(int x, int y, boolean nextMove) {
//        if(moveOnBoard(x, y)){
//            if(checkMove(x, y,false, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }
//
//    @Override
//    public void strikeRight(int x, int y, boolean nextMove) {
//        if(moveOnBoard(x, y)){
//            if(checkMove(x, y, true, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }
//
//    @Override
//    public void strikeLeft(int x, int y, boolean nextMove) {
//        if(moveOnBoard(x, y)) {
//            if(checkMove(x, y, true, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }
}
