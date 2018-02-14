import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MoveHandler {

    private static List<Threat> blackThreats = new ArrayList<>();
    private static List<Threat> whiteThreats = new ArrayList<>();
    private static List<Coordinates> safePositions = new ArrayList<>();
    private static List<ChessPiece> allCurrentChesspieces = new ArrayList<>();
    private static List<Threat> potentialSaviour = new ArrayList<>();
    private static final int BLACKKING = 5;
    private static final int WHITEKING = 29;
    private static final int KINGSVALUE = 1000;

    private static MoveCoordinates safeStrikeMove;
    private static int toX;
    private static int toY;
    private static int fromX;
    private static int fromY;
    private static ChessPiece savedPiece;
    private static MoveCoordinates bestMove;

    private static PlayerTurn playerTurn = PlayerTurn.WHITE;

    public static void setPlayerTurn(PlayerTurn playerTurn) {
        MoveHandler.playerTurn = playerTurn;
    }

    public static PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

    public static Move pickMove() {
        safePositions = MoveCollection.getSafePositions();
        blackThreats = MoveCollection.getBlackThreats();
        whiteThreats = MoveCollection.getWhiteThreats();
        allCurrentChesspieces = MoveCollection.getAllCurrentChessPieces();

        switch (playerTurn) {
            case BLACK:
                if (blackThreats.size() > 0) {

                    Threat highestBlackThreat = blackThreats.get(0);

                    if (highestBlackThreat.getThreatenedPieceValue() < 9) {
                        // Obs kolla queenswap!!!!
                    } else {
                        if (whiteThreats.size() > 0) {

                            Threat highestWhiteThreat = whiteThreats.get(0);
                            if (highestBlackThreat.getThreatenedPieceValue() > highestWhiteThreat.getThreatenedPieceValue()) {
                                return move(highestBlackThreat, blackThreats, whiteThreats);
                            } else {
                                // metod för att räkna ut om det är värt att ta den vita mest värdefulla pjäsen
                                //return takeSafestHighestValuedPiece();
                            }
                        } else {
                            return move(highestBlackThreat, blackThreats, whiteThreats);
                        }

//                    int highestThreatenedPieceID = highestBlackThreat.getThreatenedPiece().id;
//                    String highestThreatColor = highestBlackThreat.getThreatenedPiece().color;
//                    createPotentialSaviourList(highestThreatenedPieceID, blackThreats, whiteThreats);
//
//                    Threat lowestTestThreatMove = getLowestTestThreatMove(highestBlackThreat, highestThreatColor);
//                    if(bestMove == null) {
//                        int fromX = lowestTestThreatMove.getThreat().currentXPosition;
//                        int fromY = lowestTestThreatMove.getThreat().currentYPosition;
//                        int toX = lowestTestThreatMove.getThreatenedPiece().currentXPosition;
//                        int toY = lowestTestThreatMove.getThreatenedPiece().currentYPosition;
//
//                        Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
//                        if (lowestThreatMove != null) {
//                            return lowestThreatMove;
//                        }
//                    }
//                    else{
//                        int fromX = bestMove.getFrom().getX();
//                        int fromY = bestMove.getFrom().getY();
//                        int toX = bestMove.getTo().getX();
//                        int toY = bestMove.getTo().getY();
//
//                        Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
//                        return lowestThreatMove;
//                    }
                    }
                } else {
                    // Metod för att välja en random pjäs att flytta till en säker position
                    return getRandomSafeMove("Black");

                }
            case WHITE:
                if (whiteThreats.size() > 0) {

                    Threat highestWhiteThreat = whiteThreats.get(0);

                    if (highestWhiteThreat.getThreatenedPieceValue() < 9) {
                        // Obs kolla queenswap!!!!
                    } else {
                        if (blackThreats.size() > 0) {

                            Threat highestBlackThreat = blackThreats.get(0);
                            if (highestWhiteThreat.getThreatenedPieceValue() > highestBlackThreat.getThreatenedPieceValue()) {
                                return move(highestWhiteThreat, whiteThreats, blackThreats);
                            } else {
                                // metod för att räkna ut om det är värt att ta den svarta mest värdefulla pjäsen
                                //return takeSafestHighestValuedPiece();
                            }
                        } else {
                            return move(highestWhiteThreat, whiteThreats, blackThreats);
                        }


//                    int highestThreatenedPieceID = highestThreat.getThreatenedPiece().id;
//                    String highestThreatColor = highestThreat.getThreatenedPiece().color;
//                    createPotentialSaviourList(highestThreatenedPieceID, whiteThreats, blackThreats);
//
//                    Threat lowestTestThreatMove = getLowestTestThreatMove(highestThreat, highestThreatColor);
//                    if(bestMove == null) {
//                        int fromX = lowestTestThreatMove.getThreat().currentXPosition;
//                        int fromY = lowestTestThreatMove.getThreat().currentYPosition;
//                        int toX = lowestTestThreatMove.getThreatenedPiece().currentXPosition;
//                        int toY = lowestTestThreatMove.getThreatenedPiece().currentYPosition;
//
//                        Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
//                        if (lowestThreatMove != null) {
//                            return lowestThreatMove;
//                        }
//                    }
//                    else{
//                        int fromX = bestMove.getFrom().getX();
//                        int fromY = bestMove.getFrom().getY();
//                        int toX = bestMove.getTo().getX();
//                        int toY = bestMove.getTo().getY();
//
//                        Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
//                        return lowestThreatMove;
//                    }
                    }
                } else {
                    return getRandomSafeMove("White");
                }
        }
        Move testMove = new Move("A2", "A3");
        return testMove;
    }

    private static Move getRandomSafeMove(String color) {
        Boolean run = true;
        while (run) {
            Random random = new Random();
            int indexRandomPiece = random.nextInt(allCurrentChesspieces.size());
            ChessPiece randomPiece = allCurrentChesspieces.get(indexRandomPiece);
            if (randomPiece.color == color) {
                List<MoveCoordinates> potentialMoves = randomPiece.getPotentialMoves();
                for (MoveCoordinates mc : potentialMoves) {
                    int moveX = mc.getTo().getX();
                    int moveY = mc.getTo().getY();
                    if (safeSpot(moveX, moveY)) {
                        Move move = new Move(randomPiece.currentYPosition, randomPiece.currentYPosition, moveX, moveY);
                        return move;
                    }
                }
            }
        }
        return null;
    }

    private static Move move(Threat highestThreat, List<Threat> currentPlayersThreatList, List<Threat> oponentsThreatList) {

        int highestThreatenedPieceID = highestThreat.getThreatenedPiece().id;
        String highestThreatColor = highestThreat.getThreatenedPiece().color;
        createPotentialSaviourList(highestThreatenedPieceID, currentPlayersThreatList, oponentsThreatList);

        Threat lowestTestThreatMove = getLowestTestThreatMove(highestThreat, highestThreatColor);
        if (bestMove == null) {
            int fromX = lowestTestThreatMove.getThreat().currentXPosition;
            int fromY = lowestTestThreatMove.getThreat().currentYPosition;
            int toX = lowestTestThreatMove.getThreatenedPiece().currentXPosition;
            int toY = lowestTestThreatMove.getThreatenedPiece().currentYPosition;

            Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
            if (lowestThreatMove != null) {
                return lowestThreatMove;
            }
        } else {
            int fromX = bestMove.getFrom().getX();
            int fromY = bestMove.getFrom().getY();
            int toX = bestMove.getTo().getX();
            int toY = bestMove.getTo().getY();

            Move lowestThreatMove = new Move(fromX, fromY, toX, toY);
            return lowestThreatMove;
        }
        return null;
    }

//    private static Move takeSafestHighestValuedPiece(){
//
//    }

    private static boolean check(int player) {

        if (player == 5) {
            return blackThreats.get(0).getThreatenedPieceValue() == 1000;
        } else {
            return whiteThreats.get(0).getThreatenedPieceValue() == 1000;
        }
    }

    private static void createPotentialSaviourList(int id, List<Threat> threatenedList, List<Threat> threatList) {

        for (Threat threat : threatenedList) {
            if (threat.getThreatenedPiece().id == id) {
                for (Threat threat2 : threatList) {
                    if (threat.getThreat().id == threat2.getThreatenedPiece().id) {
                        potentialSaviour.add(threat2);
                    }
                }
            }
        }
        potentialSaviour = MoveCollection.sortThreatList(potentialSaviour);
    }

    private static Threat getLowestTestThreatMove(Threat threat, String color) {

        // Lists with potential threats
        List<Threat> testList1 = new ArrayList<>();
        List<Threat> testList2 = new ArrayList<>();
        List<Threat> testList3 = new ArrayList<>();

        int lowestThreatValue1 = 1001;
        int lowestThreatValue2 = 1001;
        int lowestThreatValue3 = 1001;

        int lastIndex1 = -1;
        int lastIndex2 = -1;
        int lastIndex3 = -1;

        if (potentialSaviour.size() > 0) {
            // Testing every move that can take the threatening piece, and adding the highest threatScore after that move to a list
            for (Threat threatMove : potentialSaviour) {
                int fromX = threatMove.getThreat().currentXPosition;
                int fromY = threatMove.getThreat().currentYPosition;
                int toX = threatMove.getThreatenedPiece().currentXPosition;
                int toY = threatMove.getThreatenedPiece().currentYPosition;
                int id = threatMove.getThreat().id;

                //Setting up board for a testrun
                setBoard(id, fromX, fromY, toX, toY, false);
                MoveCollection.setTest(true);
                MoveCollection.createThreatList();
                MoveCollection.setTest(false);
                //Resetting board
                setBoard(id, toX, toY, fromX, fromY, true);

                if (color == "Black") {
                    List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                    if (testBlackThreatList.size() == 0) {
                        return threatMove;
                    } else {
                        testList1.add(testBlackThreatList.get(0));
                    }
                } else {
                    List<Threat> testWhiteThreatList = MoveCollection.getTestWhiteThreats();
                    if (testWhiteThreatList.size() == 0) {
                        return threatMove;
                    } else {
                        testList1.add(testWhiteThreatList.get(0));
                    }
                }
            }
        }

        // Fetching the lists with potential moves and strikes for the threatened piece
        List<MoveCoordinates> potentialMoves = threat.getThreatenedPiece().getPotentialMoves();
        List<MoveCoordinates> potentialStrikes = threat.getThreatenedPiece().getPotentialStrikes();


        // Creating testlists with threat outcomes and sorting all lists by the highest threat value
        if (potentialMoves.size() > 0) {
            testList2 = testPotentialMoves(potentialMoves, threat, color);
            testList2 = MoveCollection.sortThreatList(testList2);
            lastIndex2 = testList2.size() - 1;
            lowestThreatValue2 = testList2.get(lastIndex2).getThreatenedPieceValue();
        }
        if (potentialStrikes.size() > 0) {
            testList3 = testPotentialMoves(potentialStrikes, threat, color);
            testList3 = MoveCollection.sortThreatList(testList3);
            lastIndex3 = testList3.size() - 1;
            lowestThreatValue3 = testList3.get(lastIndex3).getThreatenedPieceValue();
        }
        if (testList1.size() > 0) {
            testList1 = MoveCollection.sortThreatList(testList1);
            lastIndex1 = testList1.size() - 1;
            lowestThreatValue1 = testList1.get(lastIndex1).getThreatenedPieceValue();
        }

        // Returning the lowest threatMove
        if (lowestThreatValue1 <= lowestThreatValue2 && lowestThreatValue1 <= lowestThreatValue3 && lowestThreatValue1 != 1001 && lastIndex1 != -1) {
            return testList1.get(lastIndex1);
        } else if (lowestThreatValue3 <= lowestThreatValue2 && lowestThreatValue3 != 1001 && lastIndex3 != -1) {
            return testList3.get(lastIndex3);
        } else if (lowestThreatValue2 != 1001 && lastIndex2 != -1) {
            return testList2.get(lastIndex2);
        }
        return null;
    }

    private static List<Threat> testPotentialMoves(List<MoveCoordinates> list, Threat threat, String color) {

        List<Threat> testList = new ArrayList<>();
        ChessPiece threatenedPiece = threat.getThreatenedPiece();
        int threatenedPieceCurrentXPosition = threatenedPiece.currentXPosition;
        int threatenedPieceCurrentYPosition = threatenedPiece.currentYPosition;
        int threatenedPieceID = threatenedPiece.id;

        for (MoveCoordinates mc : list) {
            int moveX = mc.getTo().getX();
            int moveY = mc.getTo().getY();
            if (safeSpot(moveX, moveY)) {
                setBoard(threatenedPieceID, threatenedPieceCurrentXPosition, threatenedPieceCurrentYPosition, moveX, moveY, false);
                MoveCollection.setTest(true);
                MoveCollection.createThreatList();
                MoveCollection.setTest(false);
                setBoard(threatenedPieceID, moveX, moveY, threatenedPieceCurrentXPosition, threatenedPieceCurrentYPosition, true);
            }

            if (color == "Black") {
                List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                if (testBlackThreatList.size() == 0) {
                    bestMove = mc;
                    break;
                } else {
                    testList.add(testBlackThreatList.get(0));
                }
            } else {
                List<Threat> testWhiteThreatList = MoveCollection.getTestWhiteThreats();
                if (testWhiteThreatList.size() == 0) {
                    bestMove = mc;
                    break;
                } else {
                    testList.add(testWhiteThreatList.get(0));
                }
            }
        }
        return testList;
    }

    private static boolean safeStrikePosition(Threat threatMove) {

        List<MoveCoordinates> potentialStrikes = threatMove.getThreatenedPiece().getPotentialStrikes();

        for (MoveCoordinates mc : potentialStrikes) {
            if (safeSpot(mc.getTo().getX(), mc.getTo().getY())) {
                safeStrikeMove = new MoveCoordinates(mc.getFrom(), mc.getTo());
                return true;
            }

        }
        return false;
    }

    private static void setBoard(int id, int fromX, int fromY, int toX, int toY, boolean reset) {

        outerloop:
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessPiece piece = Game.board[x][y];
                if (piece != null) {
                    if (piece.id == id) {
                        if (!reset) {
                            savedPiece = Game.board[toX][toY];
                        }
                        Game.board[toX][toY] = piece;
                        if (!reset) {
                            Game.board[fromX][fromY] = null;
                        } else {
                            Game.board[fromX][fromY] = savedPiece;
                        }
                        piece.tile.setX(toX);
                        piece.tile.setY(toY);
                        break outerloop;
                    }
                }
            }
        }
//        Stream.of(Game.board)
//                .flatMap(Stream::of)
//                .forEach(piece -> {
//                    if (piece != null) {
//                        if (piece.id == id) {
//                            if(!reset){
//                                savedPiece = Game.board[toX][toY];
//                            }
//                            Game.board[toX][toY] = piece;
//                            if(!reset) {
//                                Game.board[fromX][fromY] = null;
//                            }else{
//                                Game.board[fromX][fromY] = savedPiece;
//                            }
//                            piece.tile.setX(toX);
//                            piece.tile.setY(toY);
//
//                        }
//                    }
//                });
    }

    private static boolean safeSpot(int x, int y) {
        for (Coordinates coord : safePositions) {
            if (coord.getX() == x && coord.getY() == y) {
                return true;
            }
        }
        return false;
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
//}