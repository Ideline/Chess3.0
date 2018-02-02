import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(int x, int y, String color, int id) {
        super(x, y, color, id);
    }

    public void moves() {
        moveDown();
        moveUp();
        moveLeft();
        moveRight();
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
