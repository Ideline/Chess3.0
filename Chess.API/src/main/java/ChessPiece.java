import com.sun.management.GarbageCollectionNotificationInfo;

import java.util.List;

class ChessPiece {

    protected Coordinates field;
    protected String color;

    public ChessPiece(int x, int y, String color){
        this.field = new Coordinates(x, y);
        this.color = color;
    }

    public boolean isFieldEmpty(int x, int y){
        if (Game.board[x][y] == null) {
            return true;
        }
        return false;
    }

    public List<Coordinates> getPotentialMoves() {
        return null;
    }

    public void setPotentialMoves(){

    }
}
