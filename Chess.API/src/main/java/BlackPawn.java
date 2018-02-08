public class BlackPawn extends ChessPiece implements IPawn{

    public BlackPawn(int x, int y, String color, int id, int value) {
        super(x, y, color, id, value);
    }

    private int x = tile.getX();
    private int y = tile.getY();
    private final int DOWN = 1;
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
        if(super.tile.getY() == 7){
            setPossibleQueen(true);
        }
        else {
            // If it's the pawns first move
            if (super.tile.getY() == 1) {
                firstMove();
            }
            // And all moves
//            nextMove(super.tile.getX(), super.tile.getY() + 1, false);
//            strikeRight(super.tile.getX() + 1, super.tile.getY() + 1, false);
//            strikeLeft(super.tile.getX() - 1, super.tile.getY() + 1, false);
            move(x, y, STAY, DOWN, FIRSTTURN, MOVE);
            move(x, y, LEFT, DOWN, FIRSTTURN,  STRIKE);
            move(x, y, RIGHT, DOWN, FIRSTTURN, STRIKE);
        }
    }

    // If the first tile is empty we check the next
    @Override
    public void firstMove() {
        int moveY = y + DOWN;
        int moveX = x;
        if(tileIsEmpty(moveX, moveY)){
            moveY = y + DOWN;
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

    public void checkNextPotentialMoves(int x, int y){
        move(x, y, STAY, DOWN, SECONDTURN, MOVE);
        move(x, y, LEFT, DOWN, SECONDTURN,  STRIKE);
        move(x, y, RIGHT, DOWN, SECONDTURN, STRIKE);
    }

//    @Override
//    public void nextMove(int x, int y, boolean nextMove){
//        if(moveOnBoard(x, y)){
//            if(checkMove(x, y, false, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }
//
//    @Override
//    public void strikeRight(int x, int y, boolean nextMove){
//        if(moveOnBoard(x, y)){
//            if(checkMove(x, y, true, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }
//
//    @Override
//    public void strikeLeft(int x, int y, boolean nextMove){
//        if(moveOnBoard(x, y)) {
//            if(checkMove(x, y, true, true, nextMove, safeSpotCheck)) {
//                checkNextPotentialMoves(x, y);
//            }
//        }
//    }

//    public void checkNextPotentialMoves(int x, int y){
//        nextMove(x, y + 1, true);
//        strikeLeft(x - 1, y + 1, true);
//        strikeRight(x + 1, y + 1, true);
//    }
}
