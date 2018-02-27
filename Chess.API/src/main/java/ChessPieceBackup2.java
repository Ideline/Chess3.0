//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//class ChessPieceBackup2 {
//
//    protected Coordinate field;
//    protected String color;
//    protected int id;
//    protected int value;
//    private boolean possibleQueen = false;
//    private Map<String, Map<String, List<Coordinate>>> allPotentialMoves = new HashMap<>();
//
//
//    public ChessPieceBackup2(int x, int y, String color, int id, int value) {
//        this.field = new Coordinate(x, y);
//        this.color = color;
//        this.id = id;
//        this.value = value;
//    }
//
//    public void setAllPotentialmoves() {
//        setPotentialMoves();
//    }
//
//    public Map<String, Map<String, List<Coordinate>>> getAllPotentialMoves() {
//        setAllPotentialmoves();
//        return allPotentialMoves;
//    }
//
//    public void setPotentialMoves() {
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
//    public boolean checkMove(int x, int y, boolean strike, boolean pawn, boolean nextMove) {
//        if (pawn) {
//            if (tileIsEmpty(x, y) && !strike) {
//                addPotentialMoves(x, y, nextMove);
//            } else if (!tileIsEmpty(x, y) && strike) {
//                if (!sameColor(x, y)) {
//                    addPotentialStrikesAndMoves(x, y, nextMove);
//                }
//            }
//            return !nextMove;
//        } else if (!pawn) {
//            if (tileIsEmpty(x, y)) {
//                addPotentialMoves(x, y, nextMove);
//                return true;
//            } else if (!tileIsEmpty(x, y)) {
//                if (!sameColor(x, y)) {
//                    addPotentialStrikesAndMoves(x, y, nextMove);
//                    return false;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean moveOnBoard(int x, int y) {
//        return (y >= 0 && y < 8 && x >= 0 && x < 8);
//    }
//
//    public boolean sameColor(int x, int y) {
//        return (color == Game.board[x][y].color);
//    }
//
//    public boolean tileIsEmpty(int x, int y) {
//        return (Game.board[x][y] == null);
//    }
//
//
//    public void addPotentialStrikesAndMoves(int x, int y, boolean nextMove) {
//        if (nextMove) {
//            addOtherMove(x, y, color, "nextPotentialMoves");
//            addOtherMove(x, y, color, "nextPotentialStrikes");
//        } else {
//            addPotentialMove(x, y, color, "potentialMoves");
//            addPotentialMove(x, y, color, "potentialStrikes");
//        }
//    }
//
//    public void addPotentialMoves(int x, int y, boolean nextMove) {
//        if (nextMove) {
//            addOtherMove(x, y, color, "nextPotentialMoves");
//        } else {
//            addPotentialMove(x, y, color, "potentialMoves");
//        }
//    }
//
//    private void addPotentialMove(int x, int y, String color, String key) {
//
//        if (allPotentialMoves.get(color) == null) {
//            Map<String, List<Coordinate>> map = new HashMap();
//            List<Coordinate> list = new ArrayList<>();
//            map.put(key, list);
//            allPotentialMoves.put(color, map);
//            list.add(new Coordinate(x, y));
//        } else {
//            Map<String, List<Coordinate>> map = allPotentialMoves.get(color);
//            List<Coordinate> list = map.get(key);
//            list.add(new Coordinate(x, y));
//        }
//    }
//
//    private void addOtherMove(int x, int y, String color, String key){
//
//        if (allPotentialMoves.get(color).get(key) == null) {
//            Map<String, List<Coordinate>> map = allPotentialMoves.get(color);
//            List<Coordinate> list = new ArrayList<>();
//            map.put(key, list);
//            allPotentialMoves.put(color, map);
//            list.add(new Coordinate(x, y));
//        } else {
//            Map<String, List<Coordinate>> map = allPotentialMoves.get(color);
//            List<Coordinate> list = map.get(key);
//            list.add(new Coordinate(x, y));
//        }
//    }
//}
//
