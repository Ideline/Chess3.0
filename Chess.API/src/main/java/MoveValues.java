import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveValues {

    private static Map<Integer, List<Coordinates>> allPotentialStrikes = new HashMap<>();
    private static Map<String, List<ChessPiece>> chessPieceMap = new HashMap<String, List<ChessPiece>>();
    private static Map<String, List<ChessPiece>> threatenedPieces = new HashMap<>();

    public static void setAllNextPotentialMoves(Map<Integer, List<Coordinates>> allPotentialStrikes) {
        allPotentialStrikes = allPotentialStrikes;
    }

    public static void setChessPieceMap(Map<String, List<ChessPiece>> chessPieceList) {
        MoveValues.chessPieceMap = chessPieceList;
        createValueList();
    }

    public static void createValueList(){

        for(ChessPiece piece : chessPieceMap.get("Black")){

            if(allPotentialStrikes.containsKey(piece.id)){
                for(Coordinates xy : allPotentialStrikes.get(piece.id)){
                    int x = xy.getX();
                    int y = xy.getY();

                    if(threatenedPieces.get("White") == null){
                        List<ChessPiece> pieces = new ArrayList<>();
                        threatenedPieces.put("White", pieces);
                        pieces.add(Game.board[x][y]);
                    }
                    else {
                        List<ChessPiece> pieces = threatenedPieces.get("White");
                        pieces.add(Game.board[x][y]);
                    }
                }

            }
        }
        for(ChessPiece piece : chessPieceMap.get("White")){

            if(allPotentialStrikes.containsKey(piece.id)){
                for(Coordinates xy : allPotentialStrikes.get(piece.id)){
                    int x = xy.getX();
                    int y = xy.getY();

                    if(threatenedPieces.get("Black") == null){
                        List<ChessPiece> pieces = new ArrayList<>();
                        threatenedPieces.put("Black", pieces);
                        pieces.add(Game.board[x][y]);
                    }
                    else {
                        List<ChessPiece> pieces = threatenedPieces.get("White");
                        pieces.add(Game.board[x][y]);
                    }
                }

            }
        }
        int tjolahopp = 0;
    }

}
