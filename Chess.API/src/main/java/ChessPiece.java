import java.util.*;

class ChessPiece {

    protected final int FARUP = -2;
    protected final int FARDOWN = 2;
    protected final int FARLEFT = -2;
    protected final int FARRIGHT = 2;
    protected final int UP = -1;
    protected final int DOWN = 1;
    protected final int LEFT = -1;
    protected final int RIGHT = 1;
    protected final int STAY = 0;
    protected Coordinate tile;
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
    private List<Coordinate> unsafePositions = new ArrayList<>();
    private List<MoveCoordinates> potentialStrikesTest = new ArrayList<>();
    private boolean strikeTest = false;

    public ChessPiece(int x, int y, String color, int id, int value) {
        this.tile = new Coordinate(x, y);
        this.color = color;
        this.id = id;
        this.value = value;
        currentXPosition = tile.getX();
        currentYPosition = tile.getY();
    }

    public void setCurrentXPosition(int currentXPosition) {
        this.currentXPosition = currentXPosition;
    }

    public void setCurrentYPosition(int currentYPosition) {
        this.currentYPosition = currentYPosition;
    }

    public void setPotentialMoves() {
    }

    public void clearLists(){
        if(!strikeTest){
            potentialMoves.clear();
            potentialStrikes.clear();
            nextPotentialMoves.clear();
            nextPotentialStrikes.clear();
        }
    }

    public List<MoveCoordinates> getPotentialMoves() {
        if(potentialMoves.size() == 0) {
            setPotentialMoves();
        }
        return potentialMoves;
    }

    public List<MoveCoordinates> getPotentialStrikesTest() {
        potentialStrikesTest.clear();
        strikeTest = true;
        setPotentialMoves();
        strikeTest = false;
        return potentialStrikesTest;
    }

    public List<Coordinate> getUnsafePositions() {
        unsafePositions.clear();
        safeSpotCheck = true;
        setPotentialMoves();
        safeSpotCheck = false;
        return unsafePositions;
    }

    public int getValue() {
        return value;
    }

    public boolean isPossibleQueen() {
        return possibleQueen;
    }

    public void setPossibleQueen(boolean possible) {
        possibleQueen = possible;
    }

    public List<MoveCoordinates> getPotentialStrikes() {
        return potentialStrikes;
    }

    public boolean checkMove(int moveX, int moveY, boolean strike, boolean pawn, boolean nextTurn) {
        if (pawn) { //OBS OBS!! Testkör nextpotentialmoves bonde
            if(strikeTest && tileIsEmpty(moveX, moveY) && !strike && !nextTurn && MoveCollection.isBlock()){
                potentialStrikesTest.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition),
                        new Coordinate(moveX, moveY)));
                return !nextTurn;
            }
            else if (strikeTest && !tileIsEmpty(moveX, moveY) && !sameColor(moveX, moveY) && strike && !nextTurn && !MoveCollection.isBlock()) {
                potentialStrikesTest.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition),
                        new Coordinate(moveX, moveY)));
                return !nextTurn;
            }
            else if (safeSpotCheck && strike) {
                unsafePositions.add(new Coordinate(moveX, moveY));
            } else if (!safeSpotCheck) {
                if (tileIsEmpty(moveX, moveY) && !strike) {
                    if (!nextTurn) {
                        addPotentialMove(moveX, moveY);
                    } else {
                        addNextPotentialMove(moveX, moveY);
                    }
                    return !nextTurn;
                } else if (!tileIsEmpty(moveX, moveY) && strike && !sameColor(moveX, moveY)) {
                    if (!nextTurn) {
                        addPotentialStrike(moveX, moveY);
                    } else {
                        addNextPotentialStrike(moveX, moveY);
                    }
                    return !nextTurn;
                }
            }
        } else if (!pawn) {
            if (strikeTest && !tileIsEmpty(moveX, moveY) && !sameColor(moveX, moveY) && strike && !nextTurn && ! secondTurn) {
                potentialStrikesTest.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition),
                        new Coordinate(moveX, moveY)));
                return false;
            }
            else if(strikeTest && tileIsEmpty(moveX, moveY)){
                return true;
            }
            else if (safeSpotCheck) {
                if (tileIsEmpty(moveX, moveY) && !nextTurn) {
                    unsafePositions.add(new Coordinate(moveX, moveY));
                    return true; // signal for the piece to keep checking next tile
                } else if (!tileIsEmpty(moveX, moveY) && !nextTurn) {
                    unsafePositions.add(new Coordinate(moveX, moveY));
                    return false; // signaling for the piece it's the end of the line
                }
            } else if(!strikeTest) {
                if (tileIsEmpty(moveX, moveY) || (moveX == currentXPosition && moveY == currentYPosition)) {
                    if (!secondTurn) {
                        addPotentialMove(moveX, moveY);
                    } else {
                        addNextPotentialMove(moveX, moveY);
                    }
                    secondTurn = !secondTurn;
                    return true; // signal for the piece to keep checking next tile
                } else if (!tileIsEmpty(moveX, moveY) && !sameColor(moveX, moveY)) {
                    if (!secondTurn) {
                        addPotentialMove(moveX, moveY);
                        addPotentialStrike(moveX, moveY);
                    } else {
                        addNextPotentialMove(moveX, moveY);
                        addNextPotentialStrike(moveX, moveY);
                    }
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

    private void addPotentialStrikeTest(int moveX, int moveY) {
        potentialStrikesTest.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(moveX, moveY)));
    }

    private void addPotentialMove(int x, int y) {
        if(!potentialMoves.stream()
                .anyMatch(mc -> mc.getTo().getX() == x && mc.getTo().getY() == y)) {
            potentialMoves.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
        }
    }

    private void addPotentialStrike(int x, int y) {
        potentialMoves.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
        potentialStrikes.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
    }

    private void addNextPotentialMove(int x, int y) {
        nextPotentialMoves.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
    }

    private void addNextPotentialStrike(int x, int y) {
        nextPotentialMoves.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
        nextPotentialStrikes.add(new MoveCoordinates(new Coordinate(currentXPosition, currentYPosition), new Coordinate(x, y)));
    }
}