import java.util.*;

class ChessPiece {

    protected Coordinates tile;
    protected String color;
    protected int id;
    protected int value;
    protected int currentXPosition;
    protected int currentYPosition;
    private boolean possibleQueen = false;
    protected boolean safeSpotCheck = false;
    private List<Coordinates> unsafePositions = new ArrayList<>();
    private Map<Integer, Map<String, List<Coordinates>>> allPotentialMoves = new HashMap<>();

    public ChessPiece(int x, int y, String color, int id, int value) {
        this.tile = new Coordinates(x, y);
        this.color = color;
        this.id = id;
        this.value = value;
        currentXPosition = tile.getX();
        currentYPosition = tile.getY();
    }

    public int getValue() {
        return value;
    }

    public void setAllPotentialmoves() {
        setPotentialMoves();
    }

    public Map<Integer, Map<String, List<Coordinates>>> getAllPotentialMoves() {
        setAllPotentialmoves();
        return allPotentialMoves;
    }

    public void setPotentialMoves() {

    }

    public boolean isPossibleQueen() {
        return possibleQueen;
    }

    public void setPossibleQueen(boolean possible) {
        possibleQueen = possible;
    }

    public boolean checkMove(int x, int y, boolean strike, boolean pawn, boolean nextMove, boolean safeSpotCheck) {
        if (pawn) {
            if(safeSpotCheck && strike){
                if(tileIsEmpty(x, y)){
                    unsafePositions.add(new Coordinates(x, y));
                }
                else if(!tileIsEmpty(x, y)){
                    unsafePositions.add(new Coordinates(x, y));
                }            }
            else {
                if (!safeSpotCheck && tileIsEmpty(x, y) && !strike) {
                    addPotentialMoves(x, y, nextMove);
                    return !nextMove;
                } else if (!tileIsEmpty(x, y) && strike && !sameColor(x, y)) {
                    addPotentialStrikesAndMoves(x, y, nextMove);
                    return !nextMove;
                }
            }
        } else if (!pawn) {
            if(safeSpotCheck){
                if(tileIsEmpty(x, y)){
                    unsafePositions.add(new Coordinates(x, y));
                    return true;
                }
                else if(!tileIsEmpty(x, y)){
                    unsafePositions.add(new Coordinates(x, y));
                    return false;
                }
            }
            else {
                if (tileIsEmpty(x, y)) {
                    addPotentialMoves(x, y, nextMove);
                    return true;
                } else if (!tileIsEmpty(x, y) && !sameColor(x, y)) {
                    addPotentialStrikesAndMoves(x, y, nextMove);
                    return false;
                }
            }
        }
        return false;
    }

    public boolean moveOnBoard(int x, int y) {
        return (y >= 0 && y < 8 && x >= 0 && x < 8);
    }

    public boolean sameColor(int x, int y) {
        return (color == Game.board[x][y].color);
    }

    public boolean tileIsEmpty(int x, int y) {
        return (Game.board[x][y] == null);
    }


    public void addPotentialStrikesAndMoves(int x, int y, boolean nextMove) {
        if (nextMove) {
            addOtherMove(x, y, "nextPotentialMoves");
            addOtherMove(x, y, "nextPotentialStrikes");
        } else {
            addPotentialMove(x, y, "potentialMoves");
            addOtherMove(x, y, "potentialStrikes");
        }
    }

    public void addPotentialMoves(int x, int y, boolean nextMove) {
        if (nextMove) {
            addOtherMove(x, y, "nextPotentialMoves");
        } else {
            addPotentialMove(x, y, "potentialMoves");
        }
    }

    private void addPotentialMove(int x, int y, String key) {

        if (allPotentialMoves.get(id) == null) {
            Map<String, List<Coordinates>> map = new HashMap();
            List<Coordinates> list = new ArrayList<>();
            map.put(key, list);
            allPotentialMoves.put(id, map);
            list.add(new Coordinates(x, y));
        } else {
            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
            List<Coordinates> list = map.get(key);
            list.add(new Coordinates(x, y));
        }
    }

    private void addOtherMove(int x, int y, String key) {

        if (allPotentialMoves.get(id).get(key) == null) {
            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
            List<Coordinates> list = new ArrayList<>();
            map.put(key, list);
            allPotentialMoves.put(id, map);
            list.add(new Coordinates(x, y));
        } else {
            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
            List<Coordinates> list = map.get(key);
            list.add(new Coordinates(x, y));
        }
    }

    public List<Coordinates> getUnsafePositions(){
        safeSpotCheck = true;
        setPotentialMoves();
        safeSpotCheck = false;
        return unsafePositions;
    }
}
