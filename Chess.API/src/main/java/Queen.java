public class Queen extends ChessPiece {

    public Queen(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    public void setPotentialMoves() {
        moveUpLeft();
        moveUpRight();
        moveDownLeft();
        moveDownRight();
        moveUp();
        moveDown();
        moveRight();
        moveLeft();
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

    public void moveDown(){
        int x = super.field.getX();
        for(int y = super.field.getY()-1; y >= 0; y-- ){
            if(!checkMove(x, y, true, false))
                break;
        }
    }

    public void moveUp(){
        int x = super.field.getX();
        for(int y = super.field.getY()+1; y < 8; y++ ){
            if(!checkMove(x, y, true, false))
                break;
        }
    }

    public void moveLeft(){
        int y = super.field.getY();
        for(int x = super.field.getX()-1; x >= 0; x-- ){
            if(!checkMove(x, y, true, false))
                break;
        }
    }

    public void moveRight(){
        int y = super.field.getY();
        for(int x = super.field.getX()+1; x < 8; x++ ){
            if(!checkMove(x, y, true, false))
                break;

        }
    }
}
