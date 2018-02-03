import java.util.*;
import java.util.Random;

public class MoveHandler {

    private static Map<Integer, List<Coordinates>> allPotentialMoves = new HashMap<Integer, List<Coordinates>>();
    private static Map<Integer, Map<String, List<Coordinates>>> allNextPotentialMoves = new HashMap<>();
    private static Map<String, List<Coordinates>> nextPotentialMoves = new HashMap<>();
    private static Map<String, List<ChessPiece>> chessPieceList = new HashMap<String, List<ChessPiece>>();
    private static List<ChessPiece> blackPieces = new ArrayList<>();
    private static List<ChessPiece> whitePieces = new ArrayList<>();
    private static Random r = new Random();
    private static Move fakeReturnMove = new Move("A2", "A3");
    private static List<Coordinates> potentialMoves = new ArrayList<Coordinates>();


    public static Move getMove(boolean black) {

        // Creates a list with all current pieces
        createChessPieceList();

        // Picks a random chesspiece
        if (black) {
            boolean run = true;
            while (run) {
                int index = r.nextInt(chessPieceList.get("Black").size());
                ChessPiece c = chessPieceList.get("Black").get(index);

                // Checks if chosen piece has any moves and is a working piece then randomizes one of it's moves
                if(allPotentialMoves.get(c.id) != null) {
                    run = false;
                    return chooseMove(c);
                }
            }
//            int index = r.nextInt(chessPieceList.get("Black").size());
//            return chooseMove(chessPieceList.get("Black").get(index));
        } else {
            boolean run = true;
            while (run) {
                int index = r.nextInt(chessPieceList.get("White").size());
                ChessPiece c = chessPieceList.get("White").get(index);
                if(allPotentialMoves.get(c.id) != null) {
                    run = false;
                    return chooseMove(c);
                }
            }
//            int index = r.nextInt(chessPieceList.get("White").size());
//            return chooseMove(chessPieceList.get("White").get(index));
        }
        return fakeReturnMove;
    }

    private static void createChessPieceList() {

        allPotentialMoves.clear();
        chessPieceList.clear();
        blackPieces.clear();
        whitePieces.clear();
        potentialMoves.clear();
        nextPotentialMoves.clear();
        allNextPotentialMoves.clear();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece c = Game.board[x][y];
                if (c != null) {
                    if (c.color == "White") {
                        whitePieces.add(c);
                        potentialMoves = c.getPotentialMoves();
                        nextPotentialMoves = c.getAllPotentialmoves();
                        if(potentialMoves != null && potentialMoves.size() > 0) {
                            allPotentialMoves.put(c.id, potentialMoves);
                        }
                        if(nextPotentialMoves.get("potentialMoves") != null && nextPotentialMoves.get("potentialMoves").size() > 0){
                            allNextPotentialMoves.put(c.id, c.getAllPotentialmoves());
                        }
                    } else {
                        blackPieces.add(c);
                        potentialMoves = c.getPotentialMoves();
                        nextPotentialMoves = c.getAllPotentialmoves();
                        if(potentialMoves != null && potentialMoves.size() > 0) {
                            allPotentialMoves.put(c.id, potentialMoves);
                        }
                        if(nextPotentialMoves.get("potentialMoves") != null && nextPotentialMoves.get("potentialMoves").size() > 0){
                            allNextPotentialMoves.put(c.id, c.getAllPotentialmoves());
                        }
                    }
                }
            }
        }
        chessPieceList.put("White", whitePieces);
        chessPieceList.put("Black", blackPieces);
    }

    // Picks a random move for chosen piece
    private static Move chooseMove(ChessPiece c) {

        int bound = allPotentialMoves.get(c.id).size();
        int index = r.nextInt(bound);

        int moveFromX = c.field.getX();
        int moveFromY = c.field.getY();
        int moveToX = allPotentialMoves.get(c.id).get(index).getX();
        int moveToY = allPotentialMoves.get(c.id).get(index).getY();

        Move m = new Move(moveFromX, moveFromY, moveToX, moveToY);
        updateGameBoard(c, moveFromX, moveFromY, moveToX, moveToY);
        return m;
    }

    private static void updateGameBoard(ChessPiece c, int moveFromX, int moveFromY, int moveToX, int moveToY){

        Game.board[moveToX][moveToY] = c;
        c.field.setX(moveToX);
        c.field.setY(moveToY);
        Game.board[moveFromX][moveFromY] = null;

    }

}
