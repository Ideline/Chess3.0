import java.util.ArrayList;
import java.util.List;

public class BlackRook extends ChessPiece {

    public BlackRook(int x, int y, String color) {
        super(x, y, color);
    }

    private List<Coordinates> potentialMoves = new ArrayList<>();
    private List<Coordinates> potentialStrikes = new ArrayList<>();


    public List<Coordinates> getPotentialMoves() {
        potentialMoves = new ArrayList<>();
        setPotentialMoves();
        return potentialMoves;
    }

    public List<Coordinates> getPotentialStrikes() {
        potentialStrikes = new ArrayList<>();
        return potentialStrikes;
    }

    public void setPotentialMoves() {
        moveForward();
        moveBack();
        moveLeft();
        moveRight();
    }

    public void moveForward(){
        int x = super.field.getX();
        for(int y = super.field.getY()-1; y >= 0; y-- ){
            if(!checkMove(x, y))
                break;
        }
    }

    public void moveBack(){
        int x = super.field.getX();
        for(int y = super.field.getY()+1; y < 8; y++ ){
            if(!checkMove(x, y))
                break;
        }
    }

    public void moveLeft(){
        int y = super.field.getY();
        for(int x = super.field.getX()-1; x >= 0; x-- ){
            if(!checkMove(x, y))
                break;
        }
    }

    public void moveRight(){
        int y = super.field.getY();
        for(int x = super.field.getX()+1; x < 8; x++ ){
            if(!checkMove(x, y))
                break;

        }
    }

    public void addPotentialMove(int x, int y){
        potentialMoves.add(new Coordinates(x, y));
    }

    public void addPotentialStrike(int x, int y){
        potentialStrikes.add(new Coordinates(x, y));
    }

    public boolean isSameColor(int x, int y){
        if(super.color == Game.board[x][y].color){
            return true;
        }
        return false;
    }

    public boolean checkMove(int x, int y){
        if (isFieldEmpty(x, y)) {
            addPotentialMove(x, y);
            return true;
        } else if (!isSameColor(x, y)) {
            addPotentialMove(x, y);
            addPotentialStrike(x, y);
            return false;
        } else {
            return false;
        }
    }
}
