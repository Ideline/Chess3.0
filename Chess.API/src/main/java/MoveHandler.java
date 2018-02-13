import java.util.ArrayList;
import java.util.List;

class MoveHandler{

    private static List<Threat> blackThreats = new ArrayList<>();
    private static List<Threat> whiteThreats = new ArrayList<>();

    public static PlayerTurn playerTurn = PlayerTurn.BLACK;

    public static Move pickMove(){

        blackThreats = MoveCollection.getBlackThreats();
        whiteThreats = MoveCollection.getWhiteThreats();

        switch (playerTurn){
            case BLACK:
                if(blackThreats.size() != 0) {
                    if(check("Black")) {
                        if(takeThreat("Black")){

                        }
                        Move move = new Move("CH", "CH");
                        return move;
                    }
                }
                break;
            case WHITE:
                if(whiteThreats.size() != 0) {
                    if(check("White")){
                        Move move = new Move("CH", "CH");
                        return move;
                    }
                }
                break;
        }
        Move testMove = new Move("A2", "A3");
        return testMove;
    }

    private static boolean check(String player){

        if(player == "Black"){
            return blackThreats.get(0).getThreatenedPieceValue() == 1000;
        }
        else {
            return whiteThreats.get(0).getThreatenedPieceValue() == 1000;
        }
    }

    private static boolean takeThreat(String color){
        if(color == "Black"){
            for(Threat threat : blackThreats){
                if(threat.getThreatenedPieceValue() == 1000){
                    whiteThreats.stream()
                            .an
                }
            }
        }
    }



//    private static boolean takeThreat(String player){
//
//        List<Coordinates> potentialStrikes = new ArrayList<>();
//
//        potentialStrikes = MoveCollection.getAllPotentialMoves().get(player).get().get("")

//        MoveCollection.getAllPotentialMoves().get(player).entrySet().stream()
//                .forEach(id -> { id.
//
//                });
    //}
}