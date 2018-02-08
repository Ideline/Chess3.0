//import com.sun.management.GarbageCollectionNotificationInfo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//class ChessPieceBackup {
//
//    protected Coordinates field;
//    protected String color;
//    protected int id;
//    private boolean possibleQueen = false;
//    protected int value;
//    private List<Coordinates> potentialMoves = new ArrayList<>();
//    private List<Coordinates> potentialStrikes = new ArrayList<>();
//    private List<Coordinates> nextPotentialMoves = new ArrayList<>();
//    private List<Coordinates> nextPotentialStrikes = new ArrayList<>();
//    private Map<String, List<Coordinates>> allPotentialmoves = new HashMap<>();
//
//    public ChessPieceBackup(int x, int y, String color, int id, int value){
//        this.field = new Coordinates(x, y);
//        this.color = color;
//        this.id = id;
//        this.value = value;
//
//    }
//
//    public List<Coordinates> getPotentialStrikes() {
//        return potentialStrikes;
//    }
//
//    public void setPotentialMoves(){
//
//    }
//
//    public boolean isPossibleQueen() {
//        return possibleQueen;
//    }
//
//    public void setPossibleQueen(boolean possible) {
//        possibleQueen = possible;
//    }
//
//    public void setAllPotentialmoves() {
//        this.allPotentialmoves.put("potentialMoves", potentialMoves);
//        this.allPotentialmoves.put("potentialStrikes", potentialStrikes);
//        this.allPotentialmoves.put("nextPotentialMoves", nextPotentialMoves);
//        this.allPotentialmoves.put("nextPotentialStrikes", nextPotentialStrikes);
//    }
//
//    public Map<String, List<Coordinates>> getAllPotentialMoves() {
//        return allPotentialmoves;
//    }
//
//    public List<Coordinates> getPotentialMoves() {
//        potentialMoves = new ArrayList<>(); // Maybe .clear() instead?
//        nextPotentialMoves = new ArrayList<>();
//        potentialStrikes = new ArrayList<>();
//        nextPotentialStrikes = new ArrayList<>();
//        allPotentialmoves = new HashMap<>();
//        setPotentialMoves();
//        //setAllPotentialmoves();
//        return potentialMoves;
//    }
//
//    public boolean checkMove(int x, int y, boolean strike, boolean pawn, boolean nextMove){
//        if(pawn){
//            if (isFieldEmpty(x, y) && !strike) {
//                if(nextMove){
//                    addNextPotentialMove(x, y);
//                }
//                else {
//                    addPotentialMove(x, y);
//                }
//            }
//            else if(!isFieldEmpty(x, y) && strike){
//                if(!isSameColor(x, y)){
//                    canStrike(nextMove, x, y);
//                }
//            }
//            return !nextMove;
//        }
//        else if(!pawn){
//            if (isFieldEmpty(x, y)) {
//                if(nextMove){
//                    addNextPotentialMove(x, y);
//                }
//                else {
//                    addPotentialMove(x, y);
//                }
//                return true;
//            }
//            else if(!isFieldEmpty(x, y)){
//                if(!isSameColor(x, y)){
//                    canStrike(nextMove, x, y);
//                    return false;
//                }
//            }
//        }
//        return false;
//    }
//
//    public void canStrike(boolean nextMove, int x, int y){
//        if(nextMove){
//            addNextPotentialMove(x, y);
//            addNextPotentialStrike(x, y);
//        }
//        else {
//            addPotentialMove(x, y);
//            addPotentialStrike(x, y);
//        }
//    }
//
//    public boolean moveOnBoard(int x, int y){
//        return (y >= 0 && y < 8 && x >= 0 && x < 8);
//    }
//
//    public boolean isSameColor(int x, int y){
//        return (color == Game.board[x][y].color);
//    }
//
//    public boolean isFieldEmpty(int x, int y){
//        return (Game.board[x][y] == null);
//    }
//
//    public void addPotentialMove(int x, int y){
//        potentialMoves.add(new Coordinates(x, y));
//    }
//
//    public void addPotentialStrike(int x, int y){
//        potentialStrikes.add(new Coordinates(x, y));
//    }
//
//    public void addNextPotentialMove(int x, int y){
//        nextPotentialMoves.add(new Coordinates(x, y));
//    }
//
//    public void addNextPotentialStrike(int x, int y){
//        nextPotentialStrikes.add(new Coordinates(x, y));
//    }
//}
