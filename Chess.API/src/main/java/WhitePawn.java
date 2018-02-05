public class WhitePawn extends ChessPiece implements IPawn{

    public WhitePawn(int x, int y, String color, int id) {
        super(x, y, color, id);
    }


    @Override
    public void setPotentialMoves() {
        // Signals to swap the pawn for a queen
        if(super.field.getY() == 0){
            setPossibleQueen(true);
        }
        else {
            // If it's the pawns first move
            if (super.field.getY() == 6) {
                firstMove(super.field.getX(), super.field.getY() - 1, false);
            }
            // And all moves
            nextMove(super.field.getX(), super.field.getY() - 1, false);
            strikeRight(super.field.getX() + 1, super.field.getY() - 1, false);
            strikeLeft(super.field.getX() - 1, super.field.getY() - 1, false);
        }
    }

    // If the first field is empty we check the next
    @Override
    public void firstMove(int x, int y, boolean nextMove) {
        if(isFieldEmpty(x, y)){
            y--;
            // When we have checked are first potential move we check the follow up move
            if( checkMove(x, y, false, true, nextMove)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    @Override
    public void nextMove(int x, int y, boolean nextMove) {
        if(isMoveOnBoard(x, y)){
            if(checkMove(x, y,false, true, nextMove)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    @Override
    public void strikeRight(int x, int y, boolean nextMove) {
        if(isMoveOnBoard(x, y)){
            if(checkMove(x, y, true, true, nextMove)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    @Override
    public void strikeLeft(int x, int y, boolean nextMove) {
        if(isMoveOnBoard(x, y)) {
            if(checkMove(x, y, true, true, nextMove)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    public void checkNextPotentialMoves(int x, int y){
        nextMove(x, y - 1, true);
        strikeLeft(x - 1, y - 1, true);
        strikeRight(x + 1, y - 1, true);
    }
}
