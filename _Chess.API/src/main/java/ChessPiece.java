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
    protected boolean secondTurn = false;
    private List<MoveCoordinates> potentialMoves = new ArrayList<>();
    private List<MoveCoordinates> potentialStrikes = new ArrayList<>();
    private List<MoveCoordinates> nextPotentialMoves = new ArrayList<>();
    private List<MoveCoordinates> nextPotentialStrikes = new ArrayList<>();
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

    public void setPotentialMoves() {

    }

    public boolean isPossibleQueen() {
        return possibleQueen;
    }

    public void setPossibleQueen(boolean possible) {
        possibleQueen = possible;
    }

    public List<MoveCoordinates> getPotentialMoves() {
        setPotentialMoves();
        return potentialMoves;
    }

    public List<MoveCoordinates> getPotentialStrikes() {
        return potentialStrikes;
    }

    public List<MoveCoordinates> getNextPotentialMoves() {
        return nextPotentialMoves;
    }

    public List<MoveCoordinates> getNextPotentialStrikes() {
        return nextPotentialStrikes;
    }

//    public Map<Integer, Map<String, List<Coordinates>>> getMapAllPotentialMoves(){
//        return allPotentialMoves;
//    }

    public boolean checkMove(int moveX, int moveY, boolean strike, boolean pawn, boolean nextTurn) {
        if (pawn) {
            if (safeSpotCheck && strike) {
                unsafePositions.add(new Coordinates(moveX, moveY));
            } else if(!safeSpotCheck){
                if (tileIsEmpty(moveX, moveY) && !strike) {
                    addPotentialMove(moveX, moveY);
                    //addPotentialMoves(moveX, moveY, nextTurn);
                    return !nextTurn;
                } else if (!tileIsEmpty(moveX, moveY) && strike && !sameColor(moveX, moveY)) {
                    addPotentialMove(moveX, moveY);
                    addPotentialStrike(moveX, moveY);
//                    addNextPotentialMove(moveX, moveY);
//                    addPotentialStrike(moveX, moveY);
//                    addPotentialStrikesAndMoves(moveX, moveY, nextTurn);
                    return !nextTurn;
                }
            }
        } else if (!pawn) {
            if (safeSpotCheck) {
                if (tileIsEmpty(moveX, moveY) && !nextTurn) {
                    unsafePositions.add(new Coordinates(moveX, moveY));
                    return true; // signal for the piece to keep checking next tile
                } else if (!tileIsEmpty(moveX, moveY) && !nextTurn) {
                    unsafePositions.add(new Coordinates(moveX, moveY));
                    return false; // signaling for the piece it's the end of the line
                }
            } else {
                if (tileIsEmpty(moveX, moveY) || (moveX == currentXPosition && moveY == currentYPosition)) {
                    if(!secondTurn){
                        addPotentialMove(moveX, moveY);
                    }
                    else{
                        addNextPotentialMove(moveX, moveY);
                    }
//                    addPotentialMoves(moveX, moveY, nextTurn);
                    secondTurn = !secondTurn;
                    return true; // signal for the piece to keep checking next tile
                } else if (!tileIsEmpty(moveX, moveY) && !sameColor(moveX, moveY)) {
                    if(!secondTurn){
                        addPotentialMove(moveX, moveY);
                        addPotentialStrike(moveX, moveY);
                    }
                    else{
                        addNextPotentialMove(moveX, moveY);
                        addNextPotentialStrike(moveX, moveY);
                    }
//                    addPotentialStrikesAndMoves(moveX, moveY, nextTurn);
                    secondTurn = !secondTurn;
                    return false; // signal for the piece it's the end of the line
                }
                secondTurn = false;
            }
        }
        return false; // signal for the piece it's the end of the line
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

    private void addPotentialMove(int x, int y){
        potentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
    }

    private void addPotentialStrike(int x, int y){
        potentialStrikes.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
    }

    private void addNextPotentialMove(int x, int y){
        nextPotentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
    }

    private void addNextPotentialStrike(int x, int y){
        nextPotentialStrikes.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
    }

//    private void addPotentialMove(int x, int y, boolean nextTurn, boolean strike){
//        if(!nextTurn && !strike) {
//            potentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//        }
//        else if(!nextTurn && strike){
//            potentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//            potentialStrikes.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//        }
//        else if(nextTurn && !strike){
//            nextPotentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//        }
//        else{
//            nextPotentialMoves.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//            nextPotentialStrikes.add(new MoveCoordinates(new Coordinates(currentXPosition, currentYPosition), new Coordinates(x, y)));
//        }
//    }

//    public void addPotentialStrikesAndMoves(int x, int y, boolean nextTurn) {
//        if (nextTurn || secondTurn) {
//            addOtherMove(x, y, "nextPotentialMoves");
//            addOtherMove(x, y, "nextPotentialStrikes");
//        } else {
//            addPotentialMove(x, y, "potentialMoves");
//            addOtherMove(x, y, "potentialStrikes");
//        }
//    }
//
//    public void addPotentialMoves(int x, int y, boolean nextTurn) {
//        if (nextTurn || secondTurn) {
//            addOtherMove(x, y, "nextPotentialMoves");
//        } else {
//            addPotentialMove(x, y, "potentialMoves");
//        }
//    }


    // Should add condition unique
//    private void addPotentialMove(int x, int y, String key) {
//

//        if (allPotentialMoves.get(id) == null) {
//
//            Map<String, List<Coordinates>> map = new HashMap();
//            List<Coordinates> list = new ArrayList<>();
//            map.put(key, list);
//            allPotentialMoves.put(id, map);
//            list.add(new Coordinates(x, y));
//        } else {
//            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
//            List<Coordinates> list = map.get(key);
//            list.add(new Coordinates(x, y));
//        }
//    }

    // Should add condition unique
//    private void addOtherMove(int x, int y, String key) {
//
//        if (allPotentialMoves.get(id).get(key) == null) {
//            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
//            List<Coordinates> list = new ArrayList<>();
//            map.put(key, list);
//            allPotentialMoves.put(id, map);
//            list.add(new Coordinates(x, y));
//        } else {
//            Map<String, List<Coordinates>> map = allPotentialMoves.get(id);
//            List<Coordinates> list = map.get(key);
//            if(!list.stream().anyMatch(coordinate -> coordinate.getX() == x && coordinate.getY() == y)){
//                list.add(new Coordinates(x, y));
//            }
//        }
//    }

    public List<Coordinates> getUnsafePositions() {
        unsafePositions.clear();
        safeSpotCheck = true;
        setPotentialMoves();
        safeSpotCheck = false;
        return unsafePositions;
    }

    public int getValue() {
        return value;
    }

    public void setAllPotentialmoves() {
        setPotentialMoves();
    }

//    public Map<Integer, Map<String, List<Coordinates>>> getAllPotentialMoves() {
//        allPotentialMoves.clear();
//        setAllPotentialmoves();
//        if(allPotentialMoves.size() > 0){
//            int i = 0;
//        }
//        return allPotentialMoves;
//    }


}