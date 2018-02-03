public class King extends ChessPiece {

    public King(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    public void setPotentialMoves() {
        moveUp();
        moveDown();
        moveUpRight();
        moveUpLeft();
        moveDownRight();
        moveDownLeft();
    }

    private void moveUp() {
        int x = super.field.getX();
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y,true, false);
        }
    }

    private void moveDown() {
        int x = super.field.getX();
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y,true, false);
        }
    }

    private void moveUpRight() {
        int x = super.field.getX() + 1;
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y, true, false);
        }
    }

    private void moveUpLeft() {
        int x = super.field.getX() - 1;
        int y = super.field.getY() - 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y, true, false);
        }
    }

    private void moveDownLeft() {
        int x = super.field.getX() - 1;
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }

    private void moveDownRight() {
        int x = super.field.getX() + 1;
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)) {
            checkMove(x, y, true, false);
        }
    }
}
