import java.util.*;
import java.util.stream.Collectors;

class MoveHandler {

    private static List<Threat> blackThreats = new ArrayList<>();
    private static List<Threat> whiteThreats = new ArrayList<>();
    private static List<Coordinates> safePositions = new ArrayList<>();
    private static List<ChessPiece> allCurrentChesspieces = new ArrayList<>();
    private static List<Threat> potentialSaviour = new ArrayList<>();
    private static final int BLACKKINGID = 5;
    private static final int WHITEKINGID = 29; //Jag lade till id i namnen för att vara lite tydligare
    private static final int KINGSVALUE = 1000;
    private static final int QUEENSVALUE = 9;

    private static ChessPiece savedPiece;
    private static MoveCoordinates bestMove;

    private static PlayerTurn playerTurn = PlayerTurn.BLACK;

    public static void setPlayerTurn(PlayerTurn playerTurn) {
        MoveHandler.playerTurn = playerTurn;
    }

    public static PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

    public static Move pickMove() {
        //TODO fråga Tore om  clear behövs här
        safePositions.clear();
        blackThreats.clear();
        whiteThreats.clear();
        allCurrentChesspieces.clear();
        safePositions = MoveCollection.getSafePositions();
        blackThreats = MoveCollection.getBlackThreats();
        whiteThreats = MoveCollection.getWhiteThreats();
        allCurrentChesspieces = MoveCollection.getAllCurrentChessPieces();

        switch (playerTurn) {
            case BLACK:
                // If one or more of our pieces is threatened
                // TODO: vi vill loopa detta tills hotet mot motståndaren är större än mot oss
                if (blackThreats.size() > 0) {

                    // We get the one with  the highest value
                    Threat highestBlackThreat = blackThreats.get(0);

                    // If it's not our king or queen we check to see if we can make a queenswap
                    if (highestBlackThreat.getThreatenedPieceValue() < QUEENSVALUE) {

                        // TODO: check if we can take opponents queen first
                        Move move = makeQueenSwap();
                        if (move != null) {
                            return move;
                        }
                    }
                    if (whiteThreats.size() > 0) {
                        // If we can take any of the opponents pieces we select the one with the highest value
                        Threat highestWhiteThreat = whiteThreats.get(0);
                        // If our highest valued threatened piece is more valuable then our opponents
                        // we try to save it
                        if (highestBlackThreat.getThreatenedPieceValue() > highestWhiteThreat.getThreatenedPieceValue()) {
                            Move m = savePiece(highestBlackThreat, blackThreats, whiteThreats);
                            if (m != null) {
                                return m;
                            }
                            // If we couldn't eliminate the threat or move our threatened piece we try to block it
                            else {
                                MoveCoordinates mc = blockThreat(highestBlackThreat);
                                if (mc != null) {
                                    Move move = new Move(mc.getFrom().getX(), mc.getFrom().getY(), mc.getTo().getX(), mc.getTo().getY());
                                    int test = 0;
                                    // Will return Check mate if we couldn't block and it'sour king that's threatened
                                    // Or it will return the blocking move
                                    return move;
                                }
                                // If we can't block it we try to take our opponents highest valued piece
                                return makeStrikeOrMove(whiteThreats);
                            }
                        } else {
                            makeStrikeOrMove(whiteThreats);
                        }
                    } else {
                        Move m = savePiece(highestBlackThreat, blackThreats, whiteThreats);
                        if (m != null) {
                            return m;
                        }
                        // If we couldn't eliminate the threat or move our threatened piece we try to block it
                        else {
                            MoveCoordinates mc = blockThreat(highestBlackThreat);
                            if (mc != null) {
                                Move move = new Move(mc.getFrom().getX(), mc.getFrom().getY(), mc.getTo().getX(), mc.getTo().getY());
                                int test = 0;
                                // Will return Check mate if we couldn't block and it'sour king that's threatened
                                // Or it will return the blocking move
                                return move;
                            }
                            // If we can't block it we try to take our opponents highest valued piece
                            return makeStrikeOrMove(whiteThreats);
                        }
                    }
                }
                return makeStrikeOrMove(whiteThreats);

            case WHITE:
                // If one or more of our pieces is threatened
                getRandomSafeMove("White");

                // TODO: vi vill loopa detta tills hotet mot motståndaren är större än mot oss
                if (whiteThreats.size() > 0) {

                    // We get the one with  the highest value
                    Threat highestWhiteThreat = whiteThreats.get(0);

                    // If it's not our king or queen we check to see if we can make a queenswap
                    if (highestWhiteThreat.getThreatenedPieceValue() < QUEENSVALUE) {

                        // TODO: check if we can take opponents queen first
                        Move move = makeQueenSwap();
                        if (move != null) {
                            return move;
                        }
                    }
                    if (whiteThreats.size() > 0) {
                        // If we can take any of the opponents pieces we select the one with the highest value
                        Threat highestBlackThreat = blackThreats.get(0);
                        // If our highest valued threatened piece is more valuable then our opponents
                        // we try to save it
                        if (highestWhiteThreat.getThreatenedPieceValue() > highestBlackThreat.getThreatenedPieceValue()) {
                            Move m = savePiece(highestWhiteThreat, whiteThreats, blackThreats);
                            if (m != null) {
                                return m;
                            }
                            // If we couldn't eliminate the threat or move our threatened piece we try to block it
                            else {
                                MoveCoordinates mc = blockThreat(highestWhiteThreat);
                                if (mc != null) {
                                    Move move = new Move(mc.getFrom().getX(), mc.getFrom().getY(), mc.getTo().getX(), mc.getTo().getY());
                                    int test = 0;
                                    // Will return Check mate if we couldn't block and it'sour king that's threatened
                                    // Or it will return the blocking move
                                    return move;
                                }
                                // If we can't block it we try to take our opponents highest valued piece
                                return makeStrikeOrMove(blackThreats);
                            }
                        } else {
                            makeStrikeOrMove(blackThreats);
                        }
                    } else {
                        Move m = savePiece(highestWhiteThreat, whiteThreats, blackThreats);
                        if (m != null) {
                            return m;
                        }
                        // If we couldn't eliminate the threat or move our threatened piece we try to block it
                        else {
                            MoveCoordinates mc = blockThreat(highestWhiteThreat);
                            if (mc != null) {
                                Move move = new Move(mc.getFrom().getX(), mc.getFrom().getY(), mc.getTo().getX(), mc.getTo().getY());
                                int test = 0;
                                // Will return Check mate if we couldn't block and it'sour king that's threatened
                                // Or it will return the blocking move
                                return move;
                            }
                            // If we can't block it we try to take our opponents highest valued piece
                            return makeStrikeOrMove(blackThreats);
                        }
                    }
                }
                return makeStrikeOrMove(blackThreats);
        }
        Move testMove = new Move("A2", "A3");
        return testMove;
    }

    private static Move makeStrikeOrMove(List<Threat> opponentsThreatlist) {

        Move move;

        for (int i = 0; i < opponentsThreatlist.size(); i++) {
            // gets the highest untested valued strike info
            ChessPiece opponentsPiece = opponentsThreatlist.get(i).getThreatenedPiece();
            int opX = opponentsPiece.currentXPosition;
            int opY = opponentsPiece.currentYPosition;

            ChessPiece attackPiece = opponentsThreatlist.get(i).getThreat();
            int attackPieceX = attackPiece.currentXPosition;
            int attackPieceY = attackPiece.currentYPosition;

            // If it's a safeSpot or the piece we take is more valuable than the piece we loose
            // then we try to make the move
            if (safeSpot(opX, opY) || attackPiece.value < opponentsPiece.value) {
                // Checkes to see if the move will result in check against us
                testRun(attackPiece.id, attackPieceX, attackPieceY, opX, opY);

                if (attackPiece.color == "Black") {
                    // If there is a threat after the move
                    List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                    move = resultInCheck(testBlackThreatList, attackPieceX, attackPieceY, opX, opY);
                } else {
                    List<Threat> testWhiteThreatsList = MoveCollection.getTestWhiteThreats();
                    move = resultInCheck(testWhiteThreatsList, attackPieceX, attackPieceY, opX, opY);
                }

                if (move != null) {
                    return move;
                }
            }
        }
        // If we can't find an attack that is worth making we do random safemove
        //TODO might be wrong color
        String color;

        if(playerTurn == playerTurn.BLACK){
            color = "White";
        }
        else{
            color = "Black";
        }

        if(opponentsThreatlist.size() > 0) {

            move = getRandomSafeMove(color);
            if (move != null) {
                return move;
            }


            // If we can't find a safespot we try to find a strike that will lead to the lowest threatresult against us
            move = getRandomLowThreatMove(color, true);
            if (move != null) {
                return move;
            }
        }
        // If we can't strike we do the move that will lead to the lowest threatresult against us.
        return getRandomLowThreatMove(color, false);
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Move resultInCheck(List<Threat> testThreatList, int attackPieceX, int attackPieceY, int opX, int opY) {
        if (testThreatList.size() != 0) {
            // Check if it's check
            int highestThreatenedPieceID = testThreatList.get(0).getThreatenedPiece().id;
            if (!check(highestThreatenedPieceID)) {
                Move attack = new Move(attackPieceX, attackPieceY, opX, opY);
                return attack;
            }
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static ChessPiece possibleQueenSwap() {

        for (ChessPiece piece : allCurrentChesspieces) {
            if (piece.isPossibleQueen()) {
                return piece;
            }
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Move makeQueenSwap() {
        ChessPiece queenswapPiece = possibleQueenSwap();

        if (queenswapPiece != null) {
            int moveX = queenswapPiece.currentXPosition;
            int moveY = queenswapPiece.currentYPosition;
            if (safeSpot(moveX, moveY)) {
                Move move = new Move(moveX, moveY, moveX, moveY);
                Game.board[moveX][moveY] = new Queen(moveX, moveY, queenswapPiece.color, queenswapPiece.id, 9);
                queenswapPiece.setPossibleQueen(false);
                return move;
            }
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    // Try to find a way to block our threat
    // TODO: This might not save the piece. Need to check if the theat is eliminated after.
    private static MoveCoordinates blockThreat(Threat threat) {

        int x1 = threat.getThreatenedPiece().currentXPosition;
        int y1 = threat.getThreatenedPiece().currentYPosition;
        int x2 = threat.getThreat().currentXPosition;
        int y2 = threat.getThreat().currentYPosition;
        int tempX;
        int tempY;
        String color = threat.getThreatenedPiece().color;
        List<Threat> highestPotentialThreats = new ArrayList<>();
        Map<Integer, MoveCoordinates> potentialBlockCoordinates = new HashMap<>();

        // Checkes to see how we should change the coordinates to look for tiles that is between our threatened
        // piece, and the threatening piece.
        if (x1 > x2) {
            tempX = x1--;
        } else {
            tempX = x1++;
        }
        if (y1 > y2) {
            tempY = y1--;
        } else {
            tempY = y1++;
        }

        for (ChessPiece piece : allCurrentChesspieces) {
            // Checkes all allies
            if (piece.color == color) {
                for (MoveCoordinates mc : piece.getPotentialMoves()) {
                    // if they have any possible move that will block the path
                    int moveX = mc.getTo().getX();
                    int moveY = mc.getTo().getY();
                    // checkes every tile to see if it's a match
                    while (x1 != x2) {
                        x1 = tempX;
                        y1 = tempY;
                        if (moveX == x1 && moveY == y1) {
                            // Kör testmove
                            testRun(piece.id, piece.currentXPosition, piece.currentYPosition, tempX, tempY);
                            if (piece.color == "Black") {
                                // returnerar threatlista
                                List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                                // if there still is any threat after blocking the main threat we add the
                                // highest valued one to our list
                                if (testBlackThreatList.size() != 0) {
                                    highestPotentialThreats.add(testBlackThreatList.get(0));
                                }
                                // If there no longer is any threat against us we make that move
                                else {
                                    //TODO: If there is no threat anymore we will choose this move to make
                                    // Don't forget to return it!
                                }
                            } else {
                                List<Threat> testWhiteThreatList = MoveCollection.getTestWhiteThreats();
                                // if there still is any threat after blocking the main threat we add the
                                // highest valued one to our list
                                if (testWhiteThreatList.size() != 0) {
                                    highestPotentialThreats.add(testWhiteThreatList.get(0));
                                }
                                // If there no longer is any threat against us we make that move
                                else {
                                    //TODO: If there is no threat anymore we will choose this move to make
                                    // Don't forget to return it!
                                }
                            }
                            // Saves the blocking piece id and the relevant move into a map
                            potentialBlockCoordinates.put(piece.id, mc);
                        }
                    }
                }
            }
        }
        if (highestPotentialThreats.size() != 0) {
            // Sorts our generated potential threat outcomes by value
            highestPotentialThreats = MoveCollection.sortThreatList(highestPotentialThreats);
            // Selects the option with the lowest threat value
            Threat bestBlockMoveOption = highestPotentialThreats.get(highestPotentialThreats.size() - 1);
            // Get the piece ID and the MoveCoordinates
            return potentialBlockCoordinates.get(bestBlockMoveOption.getThreat().id);
            // If we couldn't find a way to block the move it's check mate if the threatened piece is the king
        } else if (threat.getThreatenedPieceValue() == KINGSVALUE) {
            System.out.println("SchackMatt!!!!");
            MoveCoordinates checkMate = new MoveCoordinates(new Coordinates(-1, -1), new Coordinates(-2, -2));
            return checkMate;
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    // TODO: test this method
    private static Move getRandomSafeMove(String color) {

        Random random = new Random();
        // Creates a list with the pieces of relevant color
        List<ChessPiece> notTestedPieces = allCurrentChesspieces.stream()
                .filter(piece -> piece.color == color)
                .collect(Collectors.toList());

        while (notTestedPieces.size() > 0) {

            // Randomizes one of the pieces
            int indexRandomPiece = random.nextInt(notTestedPieces.size());
            ChessPiece randomPiece = notTestedPieces.get(indexRandomPiece);

            // Get the potential moves for that piece
            List<MoveCoordinates> potentialMoves = randomPiece.getPotentialMoves();

            // Then we look for a safe move to make
            for (MoveCoordinates mc : potentialMoves) {
                int moveX = mc.getTo().getX();
                int moveY = mc.getTo().getY();

                // If we find one we return it otherwise we keep looking
                if (safeSpot(moveX, moveY)) {
                    Move move = new Move(randomPiece.currentYPosition, randomPiece.currentYPosition, moveX, moveY);
                    return move;
                }
            }
            notTestedPieces.remove(indexRandomPiece);
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    // TODO: kommentera mera
    private static Move getRandomLowThreatMove(String color, Boolean strike) {

        Random random = new Random();
        List<Threat> highestTreatList = new ArrayList<>();
        Map<Integer, MoveCoordinates> currentBestMoveMap = new HashMap<>();

        // Creates a list with the pieces of relevant color
        List<ChessPiece> notTestedPieces = allCurrentChesspieces.stream()
                .filter(piece -> piece.color == color)
                .collect(Collectors.toList());

        while (notTestedPieces.size() > 0) {

            List<Threat> highestTreatsAfterStrikes = new ArrayList<>();
            List<MoveCoordinates> movesList;
            // Randomizes one of the pieces
            int indexRandomPiece = random.nextInt(notTestedPieces.size());
            ChessPiece randomPiece = notTestedPieces.get(indexRandomPiece);

            if (strike) {
                // Get the potential moves for that piece
                movesList = randomPiece.getPotentialStrikes();
            } else {
                movesList = randomPiece.getPotentialMoves();
            }

            MoveCoordinates currentBestMove = movesList.get(0);

            if (movesList.size() > 0) {
                // Then we look for a safe move to make
                for (MoveCoordinates mc : movesList) {

                    int fromX = mc.getFrom().getX();
                    int fromY = mc.getFrom().getX();
                    int toX = mc.getTo().getX();
                    int toY = mc.getTo().getY();

                    testRun(randomPiece.id, fromX, fromY, toX, toY);

                    if (color == "Black") {
                        List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                        if (testBlackThreatList.size() != 0) {
                            // Adds the highest threat this strike will generate
                            highestTreatsAfterStrikes.add(testBlackThreatList.get(0));
                        }

                    } else {
                        List<Threat> testWhiteThreatList = MoveCollection.getTestWhiteThreats();
                        if (testWhiteThreatList.size() != 0) {
                            // Adds the highest threat this strike will generate
                            highestTreatsAfterStrikes.add(testWhiteThreatList.get(0));
                        }
                    }
                    currentBestMove = mc;
                }

                highestTreatsAfterStrikes = MoveCollection.sortThreatList(highestTreatsAfterStrikes);
                int indexOfLowestThreat = highestTreatsAfterStrikes.size() - 1;

                // Adds the best alternative
                highestTreatList.add(highestTreatsAfterStrikes.get(indexOfLowestThreat));
                currentBestMoveMap.put(randomPiece.id, currentBestMove);
                notTestedPieces.remove(indexRandomPiece);
            }
        }
        if (highestTreatList.size() > 0) {
            highestTreatList = MoveCollection.sortThreatList(highestTreatList);
            int lowestThreatIndex = highestTreatList.size() - 1;
            int idOfBestPiece = highestTreatList.get(lowestThreatIndex).getThreatenedPiece().id;
            MoveCoordinates bestMC = currentBestMoveMap.get(idOfBestPiece);
            Move move = new Move(bestMC.getFrom().getX(), bestMC.getFrom().getY(), bestMC.getTo().getX(), bestMC.getTo().getY());
            if (highestTreatList.get(lowestThreatIndex).getThreatenedPieceValue() != KINGSVALUE) {
                return move;
            } else {
                System.out.println("SchackMatt!!!!");
                Move checkMateMoveSignal = new Move(-1, -1, -2, -2);
                return checkMateMoveSignal;
            }
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Move savePiece(Threat highestThreat, List<Threat> currentPlayersThreatList, List<Threat> opponentsThreatList) {

        int highestThreatenedPieceID = highestThreat.getThreatenedPiece().id;
        String highestThreatColor = highestThreat.getThreatenedPiece().color;

        createPotentialSaviourList(highestThreatenedPieceID, currentPlayersThreatList, opponentsThreatList);

        // Try to find the best possible move to make
        Threat lowestTestThreatMove = getLowestTestThreatMove(highestThreat, highestThreatColor);

        // If we didn't find a way to eliminate all threat we make the move that will result in the lowest
        // threat score against us
        if (bestMove == null) {
            int fromX = lowestTestThreatMove.getThreat().currentXPosition;
            int fromY = lowestTestThreatMove.getThreat().currentYPosition;
            int toX = lowestTestThreatMove.getThreatenedPiece().currentXPosition;
            int toY = lowestTestThreatMove.getThreatenedPiece().currentYPosition;

            Move lowestThreatMove = new Move(fromX, fromY, toX, toY);

            if (lowestThreatMove != null) {
                return lowestThreatMove;
            }
            // If we couldn't find any way of eliminating the threat against our piece, we check if it's check
            else {
                if (check(highestThreatenedPieceID)) {
                    // TODO: Method for Checkmate!
                } else {
                    //TODO: Method for finding the highest valued strike if there is one to make
                    // if there isn't one make a random move
                    // don't forget to return the moves
                    getRandomSafeMove(highestThreatColor);
                }
            }
            // If we decided on a best move inside our "getLowestTestThreatMove method we use it
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

    // ----------------------------------------------------------------------------------------------------- //

    private static boolean check(int pieceID) {

        // Looks to see if it's check at the moment
        if (pieceID == BLACKKINGID) {
            return blackThreats.get(0).getThreatenedPieceValue() == KINGSVALUE;
        } else {
            return whiteThreats.get(0).getThreatenedPieceValue() == KINGSVALUE;
        }
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static void createPotentialSaviourList(int highestThreatenedPieceID,
                                                   List<Threat> currentPlayersThreatList,
                                                   List<Threat> opponentsThreatList) {

        // Lookes for our piece with the highest current threat value
        for (Threat threat : currentPlayersThreatList) {
            if (threat.getThreatenedPiece().id == highestThreatenedPieceID) {
                // Then lookes for all the pieces that can eliminate that threat
                for (Threat threat2 : opponentsThreatList) {
                    if (threat.getThreat().id == threat2.getThreatenedPiece().id) {
                        potentialSaviour.add(threat2);
                    }
                }
            }
        }
        potentialSaviour = MoveCollection.sortThreatList(potentialSaviour);
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Threat getLowestTestThreatMove(Threat threat, String color) {

        // Lists with potential threats
        List<Threat> testList1 = new ArrayList<>();
        List<Threat> testList2 = new ArrayList<>();
        List<Threat> testList3 = new ArrayList<>();

        int lowestThreatValue1 = KINGSVALUE + 1;
        int lowestThreatValue2 = KINGSVALUE + 1;
        int lowestThreatValue3 = KINGSVALUE + 1;

        int lastIndex1 = -1;
        int lastIndex2 = -1;
        int lastIndex3 = -1;

        // If we have any piece that can take the piece who treaten us
        if (potentialSaviour.size() > 0) {

            for (Threat threatMove : potentialSaviour) {
                int fromX = threatMove.getThreat().currentXPosition;
                int fromY = threatMove.getThreat().currentYPosition;
                int toX = threatMove.getThreatenedPiece().currentXPosition;
                int toY = threatMove.getThreatenedPiece().currentYPosition;
                int id = threatMove.getThreat().id;

                // Testing every savePiece that can take the threatening piece
                testRun(id, fromX, fromY, toX, toY);

                // If we no longer have a threat against us we return that savePiece
                if (color == "Black") {
                    List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                    if (testBlackThreatList.size() == 0) {
                        return threatMove;
                    } else {
                        // If we still have any threat against us we add the highest threatscore
                        // to the list with all other highest threatscores from  our other testresults
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
        if (potentialStrikes.size() > 0) {
            testList3 = testPotentialMoves(potentialStrikes, threat, color);
            testList3 = MoveCollection.sortThreatList(testList3);
            if (testList3.size() > 0) {
                lastIndex3 = testList3.size() - 1;
                lowestThreatValue3 = testList3.get(lastIndex3).getThreatenedPieceValue();
            }
        }
        if (potentialMoves.size() > 0) {
            testList2 = testPotentialMoves(potentialMoves, threat, color);
            testList2 = MoveCollection.sortThreatList(testList2);
            if (testList2.size() > 0) {
                lastIndex2 = testList2.size() - 1;
                lowestThreatValue2 = testList2.get(lastIndex2).getThreatenedPieceValue();
            }
        }
        if (testList1.size() > 0) { // If statement not needed???
            testList1 = MoveCollection.sortThreatList(testList1);
            lastIndex1 = testList1.size() - 1;
            lowestThreatValue1 = testList1.get(lastIndex1).getThreatenedPieceValue();
        }

        // Returning the lowest threatMove
        if (lowestThreatValue1 <= lowestThreatValue2 && lowestThreatValue1 <= lowestThreatValue3
                && lowestThreatValue1 != KINGSVALUE + 1 && lastIndex1 != -1) {
            return testList1.get(lastIndex1);
        } else if (lowestThreatValue3 <= lowestThreatValue2 && lowestThreatValue3 != KINGSVALUE + 1 && lastIndex3 != -1) {
            return testList3.get(lastIndex3);
        } else if (lowestThreatValue2 != KINGSVALUE + 1 && lastIndex2 != -1) {
            return testList2.get(lastIndex2);
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static void testRun(int id, int fromX, int fromY, int toX, int toY) {

        //Setting up board for a testrun
        setBoard(id, fromX, fromY, toX, toY, false);
        MoveCollection.setTest(true);
        MoveCollection.createThreatList();
        MoveCollection.setTest(false);
        //Resetting board
        setBoard(id, toX, toY, fromX, fromY, true);

    }

    // ----------------------------------------------------------------------------------------------------- //

    private static List<Threat> testPotentialMoves(List<MoveCoordinates> list, Threat threat, String color) {

        List<Threat> testList = new ArrayList<>();
        ChessPiece threatenedPiece = threat.getThreatenedPiece();
        int threatenedPieceCurrentXPosition = threatenedPiece.currentXPosition;
        int threatenedPieceCurrentYPosition = threatenedPiece.currentYPosition;
        int threatenedPieceID = threatenedPiece.id;

        // Testing a threatened piece move or strike options to see what outcome they will have
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
                // If there is no threat against us after the tested savePiece or strike we deside it's our best option
                if (testBlackThreatList.size() == 0) {
                    bestMove = mc;
                    break;
                    // Otherwise we add the highest threat score to our test list
                } else {
                    testList.add(testBlackThreatList.get(0));
                }
            } else {
                // Same for white player
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

    // ----------------------------------------------------------------------------------------------------- //

    // Checkes if the threatened piece has a strike savePiece that is safe to make
//    private static boolean safeStrikePosition(Threat threatMove) {
//
//        List<MoveCoordinates> potentialStrikes = threatMove.getThreatenedPiece().getPotentialStrikes();
//
//        for (MoveCoordinates mc : potentialStrikes) {
//            if (safeSpot(mc.getTo().getX(), mc.getTo().getY())) {
//                safeStrikeMove = new MoveCoordinates(mc.getFrom(), mc.getTo());
//                return true;
//            }
//        }
//        return false;
//    }

    // ----------------------------------------------------------------------------------------------------- //

    private static void setBoard(int id, int fromX, int fromY, int toX, int toY, boolean reset) {

        outerloop:
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessPiece piece = Game.board[x][y];
                // Finds a piece and checkes if it has the ID we are looking for
                if (piece != null) {
                    if (piece.id == id) {
                        // If it's not a reset we save the piece we remove so we can put it back again
                        // after the testrun
                        if (!reset) {
                            savedPiece = Game.board[toX][toY];
                        }
                        // We make the savePiece
                        Game.board[toX][toY] = piece;
                        // And cleares the tile it moves from if it's not a reset
                        if (!reset) {
                            Game.board[fromX][fromY] = null;
                            // If it's a reset we put the original piece back
                        } else {
                            Game.board[fromX][fromY] = savedPiece;
                        }
                        // We change the objects internal coordinates
                        piece.tile.setX(toX);
                        piece.tile.setY(toY);
                        break outerloop;
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------- //

    // Checking if the coordinates are a safe spot
    private static boolean safeSpot(int x, int y) {
        for (Coordinates coord : safePositions) {
            if (coord.getX() == x && coord.getY() == y) {
                return true;
            }
        }
        return false;
    }
}