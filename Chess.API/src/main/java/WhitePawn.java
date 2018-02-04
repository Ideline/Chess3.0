public class WhitePawn extends ChessPiece implements IPawn{

    public WhitePawn(int x, int y, String color, int id) {
        super(x, y, color, id);
    }


    @Override
    public void setPotentialMoves() {
        // Swaps the pawn for a queen. Need to fix so that it works in the frontend aswell and that it counts as a move
        if(super.field.getY() == 0){
            setPossibleQueen(true);
        }
        else {
            if (super.field.getY() == 6) {
                firstMove(super.field.getX(), super.field.getY() - 1);
            }
            nextMove(super.field.getX(), super.field.getY() - 1);
            strikeRight(super.field.getX() + 1, super.field.getY() - 1);
            strikeLeft(super.field.getX() - 1, super.field.getY() - 1);
        }
    }

    @Override
    public void firstMove(int x, int y) {
        if(isMoveOnBoard(x, y) && isFieldEmpty(x, y)){
            y--;
            checkMove(x, y, false, true);
            checkNextPotentialMoves(x, y);
        }
    }

    @Override
    public void nextMove(int x, int y) {
        if(isMoveOnBoard(x, y)){
            checkMove(x, y,false, true);
            checkNextPotentialMoves(x, y);
        }
    }

    @Override
    public void strikeRight(int x, int y) {
        if(isMoveOnBoard(x, y)){
            if(checkMove(x, y, true, true)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    @Override
    public void strikeLeft(int x, int y) {
        if(isMoveOnBoard(x, y)) {
            if(checkMove(x, y, true, true)) {
                checkNextPotentialMoves(x, y);
            }
        }
    }

    public void checkNextPotentialMoves(int x, int y){
        nextMoveNextMove(x, y - 1);
        nextMoveStrikeLeft(x - 1, y - 1);
        nextMoveStrikeRight(x + 1, y - 1);
    }

    public void nextMoveNextMove(int x, int y){
        if(isMoveOnBoard(x, y)){
            checkNextMove(x, y, false, true);
        }
    }

    public void nextMoveStrikeRight(int x, int y){
        if(isMoveOnBoard(x, y)){
            checkNextMove(x, y, true, true);
        }
    }

    public void nextMoveStrikeLeft(int x, int y){
        if(isMoveOnBoard(x, y)) {
            checkNextMove(x, y, true, true);

        }
    }
}
