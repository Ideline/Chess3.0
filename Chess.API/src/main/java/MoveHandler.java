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
    private static Move queenSwapMove;
    private static List<Coordinates> potentialMoves = new ArrayList<Coordinates>();


    public static Move getMove(boolean black) {

        // Creates a list with all current pieces
        boolean endTurn = createCollections();

        if(endTurn){
            return queenSwapMove;
        }
        else {
            // Picks a random chesspiece
            if (black) {

                boolean run = true;
                while (run) {
                    int index = r.nextInt(chessPieceList.get("Black").size());
                    ChessPiece c = chessPieceList.get("Black").get(index);

                    // Checks if chosen piece has any moves and is a working piece then randomizes one of it's moves
                    if (allPotentialMoves.get(c.id) != null) {
                        run = false;
                        return chooseMove(c);
                    }
                }

            } else {

                boolean run = true;
                while (run) {
                    int index = r.nextInt(chessPieceList.get("White").size());
                    ChessPiece c = chessPieceList.get("White").get(index);
                    if (allPotentialMoves.get(c.id) != null) {
                        run = false;
                        return chooseMove(c);
                    }
                }
            }
        }
        return fakeReturnMove;
    }

    private static boolean createCollections() {

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

                    if(c.isPossibleQueen()){
                        //c.setPossibleQueen(false);
                        int a = c.field.getX();
                        int b = c.field.getY();
                        Game.board[a][b] = new Queen(a, b, c.color, c.id);
                        queenSwapMove = new Move(a, b, a, b);
                        return true;
                    }
                    else {
                        if (c.color == "White") {
                            whitePieces.add(c);
                            createAllPotentialMovesMap(c);
                        } else {
                            blackPieces.add(c);
                            createAllPotentialMovesMap(c);
                        }
                    }
                }
            }
        }
        chessPieceList.put("White", whitePieces);
        chessPieceList.put("Black", blackPieces);
        return false;
    }

    private static void createAllPotentialMovesMap(ChessPiece c){
        potentialMoves = c.getPotentialMoves();
        nextPotentialMoves = c.getAllPotentialmoves();
        if (potentialMoves != null && potentialMoves.size() > 0) {
            allPotentialMoves.put(c.id, potentialMoves);
        }
        if (nextPotentialMoves.get("potentialMoves") != null && nextPotentialMoves.get("potentialMoves").size() > 0) {
            allNextPotentialMoves.put(c.id, c.getAllPotentialmoves());
        }
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

    private static void updateGameBoard(ChessPiece c, int moveFromX, int moveFromY, int moveToX, int moveToY) {

        Game.board[moveToX][moveToY] = c;
        c.field.setX(moveToX);
        c.field.setY(moveToY);
        Game.board[moveFromX][moveFromY] = null;

    }

}
