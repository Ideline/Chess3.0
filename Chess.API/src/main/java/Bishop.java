public class Bishop extends ChessPiece  {

    public Bishop(int x, int y, String color , int id) {
        super(x, y,color, id);
    }



    public void setPotentialMoves() {
        moveUpLeft();
        moveUpRight();
        moveDownLeft();
        moveDownRight();
    }

    public void moveUpLeft(){

        int x = super.field.getX() - 1;
        int y = super.field.getY() - 1;
        while(x >= 0 && x < 8 && y >= 0 && y < 8){
            if(!checkMove(x, y, true, false)) {
                break;
            }
            x--;
            y--;
        }
    }

    public void moveUpRight(){
        int x = super.field.getX() + 1;
        int y = super.field.getY() - 1;
        while(x >= 0 && x < 8 && y >= 0 && y < 8){
            if(!checkMove(x, y, true, false)) {
                break;
            }
            x++;
            y--;
        }
    }

    public void moveDownLeft(){
        int x = super.field.getX() - 1;
        int y = super.field.getY() + 1;
        while(x >= 0 && x < 8 && y >= 0 && y < 8){
            if(!checkMove(x, y, true, false)) {
                break;
            }
            x--;
            y++;
        }
    }

    public void moveDownRight(){
        int x = super.field.getX() + 1;
        int y = super.field.getY() + 1;
        while(x >= 0 && x < 8 && y >= 0 && y < 8){
            if(!checkMove(x, y, true, false)) {
                break;
            }
            x++;
            y--;
        }
    }
}
