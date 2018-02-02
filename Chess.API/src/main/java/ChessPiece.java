import com.sun.management.GarbageCollectionNotificationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChessPiece {

    protected Coordinates field;
    protected String color;
    protected int id;
    private List<Coordinates> potentialMoves = new ArrayList<>();
    private List<Coordinates> potentialStrikes = new ArrayList<>();

    public ChessPiece(int x, int y, String color, int id){
        this.field = new Coordinates(x, y);
        this.color = color;
        this.id = id;
    }

    public void setPotentialMoves(){

    }

    public List<Coordinates> getPotentialMoves() {
        potentialMoves = new ArrayList<>();
        setPotentialMoves();
        return potentialMoves;
    }

    public List<Coordinates> getPotentialStrikes() {
        potentialStrikes = new ArrayList<>();
        return potentialStrikes;
    }

    public void addPotentialMove(int x, int y){
        potentialMoves.add(new Coordinates(x, y));
    }

    public void addPotentialStrike(int x, int y){
        potentialStrikes.add(new Coordinates(x, y));
    }
    public boolean checkMove(int x, int y, boolean strike, boolean pawn){
        if(pawn){
            if (isFieldEmpty(x, y) && !strike) {
                addPotentialMove(x, y);
            }
            else if(!isFieldEmpty(x, y) && strike){
                if(!isSameColor(x, y)){
                    addPotentialMove(x, y);
                    addPotentialStrike(x, y);
                }
            }
        }
        else if(!pawn){
            if (isFieldEmpty(x, y)) {
                addPotentialMove(x, y);
                return true;
            }
            else if(!isFieldEmpty(x, y)){
                if(!isSameColor(x, y)){
                    addPotentialMove(x, y);
                    addPotentialStrike(x, y);
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isMoveOnBoard(int x, int y){
        if(y >= 0 && y < 8 && x >= 0 && x < 8){
            return true;
        }
        return false;
    }

    public boolean isSameColor(int x, int y){
        if(color == Game.board[x][y].color){
            return true;
        }
        return false;
    }

    public boolean isFieldEmpty(int x, int y){
        if (Game.board[x][y] == null) {
            return true;
        }
        return false;
    }
}
