public class Knight extends ChessPiece {

    public Knight(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    public void setPotentialMoves() {
        moveUpLongLeft();
        moveUpLongRight();
        moveUpShortLeft();
        moveUpShortRight();
        moveDownLongLeft();
        moveDownLongRight();
        moveDownShortLeft();
        moveDownShortRight();
    }

    private void moveUpLongRight() {
        int x = super.field.getX() + 1;
        int y = super.field.getY() - 2;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveUpLongLeft() {
        int x = super.field.getX() - 1;
        int y = super.field.getY() - 2;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveUpShortLeft(){
        int x = super.field.getX() - 2;
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveUpShortRight(){
        int x = super.field.getX() + 2;
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveDownLongLeft(){
        int x = super.field.getX() - 1;
        int y = super.field.getY() + 2;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveDownLongRight(){
        int x = super.field.getX() + 1;
        int y = super.field.getY() + 2;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveDownShortLeft(){
        int x = super.field.getX() - 2;
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveDownShortRight(){
        int x = super.field.getX() + 2;
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }
}
