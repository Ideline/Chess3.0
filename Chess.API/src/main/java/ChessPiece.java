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
    private List<Coordinates> nextPotentialMoves = new ArrayList<>();
    private List<Coordinates> nextPotentialStrikes = new ArrayList<>();
    private Map<String, List<Coordinates>> allPotentialmoves = new HashMap<>();

    public ChessPiece(int x, int y, String color, int id){
        this.field = new Coordinates(x, y);
        this.color = color;
        this.id = id;
    }

    public void setPotentialMoves(){

    }

    public void setAllPotentialmoves() {
        this.allPotentialmoves.put("potentialMoves", potentialMoves);
        this.allPotentialmoves.put("potentialStrikes", potentialStrikes);
        this.allPotentialmoves.put("nextPotentialMoves", nextPotentialMoves);
        this.allPotentialmoves.put("nextPotentialStrikes", nextPotentialStrikes);
    }

    public Map<String, List<Coordinates>> getAllPotentialmoves() {
        return allPotentialmoves;
    }

    public List<Coordinates> getPotentialMoves() {
        potentialMoves = new ArrayList<>(); // Maybe .clear() instead?
        nextPotentialMoves = new ArrayList<>();
        potentialStrikes = new ArrayList<>();
        nextPotentialStrikes = new ArrayList<>();
        allPotentialmoves = new HashMap<>();
        setPotentialMoves();
        setAllPotentialmoves();
        return potentialMoves;
    }

    // Hmm???
//    public List<Coordinates> getPotentialStrikes() {
//        potentialStrikes = new ArrayList<>();
//        return potentialStrikes;
//    }

    public void addPotentialMove(int x, int y){
        potentialMoves.add(new Coordinates(x, y));
    }

    public void addPotentialStrike(int x, int y){
        potentialStrikes.add(new Coordinates(x, y));
    }

    public void addNextPotentialMove(int x, int y){
        nextPotentialMoves.add(new Coordinates(x, y));
    }

    public void addNextPotentialStrike(int x, int y){
        nextPotentialStrikes.add(new Coordinates(x, y));
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
                    return true;
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

    public boolean checkNextMove(int x, int y, boolean strike, boolean pawn){
        if(pawn) {
            if (isFieldEmpty(x, y) && !strike) {
                addNextPotentialMove(x, y);
            } else if (!isFieldEmpty(x, y) && strike) {
                if (!isSameColor(x, y)) {
                    addNextPotentialMove(x, y);
                    addNextPotentialStrike(x, y);
                }
            }
        }
        else if(!pawn){
            if (isFieldEmpty(x, y)) {
                addNextPotentialMove(x, y);
                return true;
            }
            else if(!isFieldEmpty(x, y)){
                if(!isSameColor(x, y)){
                    addNextPotentialMove(x, y);
                    addNextPotentialStrike(x, y);
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

    public void isMoveCheck(int x, int y){
        if(Game.board[x][y].id == 29 || Game.board[x][y].id == 5){

        }
    }
}
