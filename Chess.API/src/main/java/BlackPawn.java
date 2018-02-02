import java.util.ArrayList;
import java.util.List;

public class BlackPawn extends ChessPiece {

    public BlackPawn(int x, int y, String color, int id) {

        super(x, y, color, id);
    }


    public void moves() {
        if(super.field.getY() == 1){
            firstMove();
        }
        nextMove();
        strikeRight();
        strikeLeft();
    }

    public void firstMove(){
        int x = super.field.getX();
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y) && isFieldEmpty(x, y)){
            y++;
            checkMove(x, y, false, true);
        }
    }

    public void nextMove(){
        int x = super.field.getX();
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y,false, true);
        }
    }

    public void strikeRight(){
        int x = super.field.getX() + 1;
        int y = super.field.getY() + 1;
        if(isMoveOnBoard(x, y)){
            checkMove(x, y, true, true);
        }
    }

    public void strikeLeft(){
        int x = super.field.getX() - 1;
        int y = super.field.getY() + 1;
        checkMove(x, y, true, true);
    }
}
