import java.util.*;
import java.util.Random;

public class MoveHandler {

    private static Map<Integer, List<Coordinates>> allPotentialMoves = new HashMap<Integer, List<Coordinates>>();
    private static Map<String, List<ChessPiece>> chessPieceList = new HashMap<String, List<ChessPiece>>();
    private static List<ChessPiece> blackPieces = new ArrayList<>();
    private static List<ChessPiece> whitePieces = new ArrayList<>();
    private static Random r = new Random();


    public static Move getMove(boolean black) {

        // Creates a list with all current pieces
        createChessPieceList();

        // Picks a random chesspiece
        if (black) { // 17-24,  25 och 32, 1 och 8, 9-16
            boolean bajs = true;
            while (bajs) {
                int index = r.nextInt(chessPieceList.get("Black").size());
                ChessPiece c = chessPieceList.get("Black").get(index);
                if (c.id == 1 || c.id == 32 || (c.id > 7 && c.id < 26)) {
                    bajs = false;
                    return chooseMove(chessPieceList.get("Black").get(index));
                }
            }
//            int index = r.nextInt(chessPieceList.get("Black").size());
//            return chooseMove(chessPieceList.get("Black").get(index));
        } else {
            boolean bajs = true;
            while (bajs) {
                int index = r.nextInt(chessPieceList.get("White").size());
                ChessPiece c = chessPieceList.get("White").get(index);
                if (c.id == 1 || c.id == 32 || (c.id > 7 && c.id < 26)) {
                    bajs = false;
                    return chooseMove(chessPieceList.get("White").get(index));
                }
            }
//            int index = r.nextInt(chessPieceList.get("White").size());
//            return chooseMove(chessPieceList.get("White").get(index));
        }
        return chooseMove(chessPieceList.get("White").get(1));
    }

    private static void createChessPieceList() {

        allPotentialMoves.clear();
        chessPieceList.clear();
        blackPieces.clear();
        whitePieces.clear();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece c = Game.board[x][y];
                if (c != null) {
                    if (c.color == "White") {
                        whitePieces.add(c);
                        allPotentialMoves.put(c.id, c.getPotentialMoves());
                    } else {
                        blackPieces.add(c);
                        allPotentialMoves.put(c.id, c.getPotentialMoves());
                    }
                }
            }
        }
        chessPieceList.put("White", whitePieces);
        chessPieceList.put("Black", blackPieces);
    }

    // Picks a random move for chosen piece
    private static Move chooseMove(ChessPiece c) {

        int index = r.nextInt(allPotentialMoves.get(c.id).size());

        int moveFromX = c.field.getX();
        int moveFromY = c.field.getY();
        int moveToX = allPotentialMoves.get(c.id).get(index).getX();
        int moveToY = allPotentialMoves.get(c.id).get(index).getY();

        Move m = new Move(moveFromX, moveFromY, moveToX, moveToY);

        return m;
    }

}
