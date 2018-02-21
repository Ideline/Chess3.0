import java.util.*;
import java.util.stream.Collectors;

class MoveHandler {

    public static List<Threat> blackThreats = new ArrayList<>();
    public static List<Threat> whiteThreats = new ArrayList<>();
    public static List<Coordinates> whiteSafePositions = new ArrayList<>();
    public static List<Coordinates> blackSafePositions = new ArrayList<>();
    public static List<ChessPiece> allCurrentChesspieces = new ArrayList<>();
    public static List<Threat> potentialSaviour = new ArrayList<>();
    private static final int KINGSVALUE = 1000;
    private static final int QUEENSVALUE = 9;
    private static final String BLACK = "Black";
    private static final String WHITE = "White";
    private static String currentColor;

    private static ChessPiece savedPiece;
    private static MoveCoordinates bestMove;

    private static PlayerTurn playerTurn = PlayerTurn.BLACK;

    public static void setPlayerTurn(PlayerTurn playerTurn) {
        MoveHandler.playerTurn = playerTurn;
    }

    public static PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

    public static void clearLists() {

        blackSafePositions.clear();
        whiteSafePositions.clear();
        blackThreats.clear();
        whiteThreats.clear();
        potentialSaviour.clear();
        allCurrentChesspieces.clear();
    }

    public static Move pickMove() {
        //TODO: Man skulle kunna kolla att om man har flera alternativ till moves så kikar man på
        // de olika hotade pjäserna man har efter de olika moven, och om nån av pjäserna står på en
        // icke safespot för motståndaren så föredrar man det draget.
        //TODO: Vi vill försöka schacka mer om det går

        whiteSafePositions = MoveCollection.getWhiteSafePositions();
        blackSafePositions = MoveCollection.getBlackSafePositions();
        blackThreats = MoveCollection.getBlackThreats();
        whiteThreats = MoveCollection.getWhiteThreats();
        allCurrentChesspieces = MoveCollection.getAllCurrentChessPieces();
        bestMove = null;

        List<Threat> currentThreatList = new ArrayList<>();
        List<Threat> opponentsThreatList = new ArrayList<>();
        Threat highestCurrentThreat;
        Threat opHighestThreat;

        switch (playerTurn) {
            case BLACK:
                currentColor = BLACK;
                currentThreatList = blackThreats.stream().collect(Collectors.toList());
                opponentsThreatList = whiteThreats.stream().collect(Collectors.toList());
                break;
            case WHITE:
                currentColor = WHITE;
                currentThreatList = whiteThreats.stream().collect(Collectors.toList());
                opponentsThreatList = blackThreats.stream().collect(Collectors.toList());
                break;
        }

        // If one or more of our pieces is threatened
        // TODO: vi vill loopa detta tills hotet mot motståndaren är större än mot oss
        if (currentThreatList.size() > 0) {

            // We get the one with the highest value
            highestCurrentThreat = currentThreatList.get(0);

            if (highestCurrentThreat.getThreatenedPieceValue() == KINGSVALUE) {
                if (!Game.checkFlag.isSignaledCheck()) {

                    Game.checkFlag.setSignaledCheck(true);
                    return check();
                } else {
                    Game.checkFlag.setSignaledCheck(false);
                }
            }

            // If it's not our king or queen we check to see if we can make a queenswap
            if (highestCurrentThreat.getThreatenedPieceValue() < QUEENSVALUE) {

                // TODO: check if we can take opponents queen first
                Move move = makeQueenSwap();
                if (move != null) {
                    return move;
                }
            }
            if (opponentsThreatList.size() > 0) {
                // If we can take any of the opponents pieces we select the one with the highest value
                opHighestThreat = opponentsThreatList.get(0);
                // If our highest valued threatened piece is more valuable then our opponents
                // we try to save it
                if (highestCurrentThreat.getThreatenedPieceValue() > opHighestThreat.getThreatenedPieceValue()) {
                    return moveStrategy(highestCurrentThreat, currentThreatList, opponentsThreatList);
                } else {
                    Move move = strike(opponentsThreatList);
                    if (move != null) {
                        return move;
                    }
                    return randomMove(currentColor, highestCurrentThreat.getThreatenedPieceValue());
                }
            } else {
                return moveStrategy(highestCurrentThreat, currentThreatList, opponentsThreatList);
            }
        }
        // TODO: check if we can take opponents queen first
        Move move = makeQueenSwap();
        if (move != null) {
            return move;
        }
        move = strike(opponentsThreatList);
        if (move != null) {
            return move;
        }
        return randomMove(currentColor, 0);
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Move makeQueenSwap() {
        ChessPiece queenSwapPiece = possibleQueenSwap();

        if (queenSwapPiece != null) {
            int moveX = queenSwapPiece.currentXPosition;
            int moveY = queenSwapPiece.currentYPosition;
            if (safeSpot(moveX, moveY, queenSwapPiece.color)) {
                Move move = new Move(moveX, moveY, moveX, moveY);
                Game.board[moveX][moveY] = new Queen(moveX, moveY, queenSwapPiece.color, queenSwapPiece.id, 9);
                queenSwapPiece.setPossibleQueen(false);
                return move;
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

    private static Move moveStrategy(Threat highestCurrentThreat,
                                     List<Threat> currentThreatList, List<Threat> opponentsThreatList) {
        Move m = savePiece(highestCurrentThreat, currentThreatList, opponentsThreatList);
        if (m != null) {
            return m;
        }
        // If we couldn't eliminate the threat or move our threatened piece we try to block it
        else {
            //TODO: denna if-satsen funkar inte fixa!
            if (highestCurrentThreat.getThreat().getClass() != Knight.class
                    && highestCurrentThreat.getThreat().getClass() != WhitePawn.class
                    && highestCurrentThreat.getThreat().getClass() != BlackPawn.class) {
                Move move = blockThreat(highestCurrentThreat);
                if (move != null) {
                    // Will return Check mate if we couldn't block and it'sour king that's threatened
                    // Or it will return the blocking move
                    return move;
                }
            } else {
                if (highestCurrentThreat.getThreatenedPieceValue() == KINGSVALUE) {
                    return checkMate();
                }
            }
            // If we can't block it we try to take our opponents highest valued piece
            m = strike(opponentsThreatList);
            if (m != null) {
                return m;
            }
            return randomMove(currentColor, highestCurrentThreat.getThreatenedPieceValue());

        }
    }

    // ---------------------------------------------------------------------------------------------------- //

    private static Move savePiece(Threat highestThreat, List<Threat> currentPlayersThreatList, List<Threat> opponentsThreatList) {

        int highestThreatenedPieceID = highestThreat.getThreatenedPiece().id;
        String highestThreatColor = highestThreat.getThreatenedPiece().color;

        createPotentialSaviourList(highestThreatenedPieceID, currentPlayersThreatList, opponentsThreatList);

        // Try to find the best possible move to make
        //Threat lowestTestThreatMove = getLowestTestThreatMove(highestThreat, highestThreatColor);
        MoveCoordinates lowestTestThreatMove = getLowestTestThreatMove(highestThreat, highestThreatColor);

        // If we didn't find a way to eliminate all threat we make the move that will result in the lowest
        // threat score against us

        if (bestMove == null && lowestTestThreatMove != null) {

            return createMove(lowestTestThreatMove);

            // If we decided on a best move inside our "getLowestTestThreatMove method we use it
        } else if (lowestTestThreatMove == null && bestMove != null) {

            return createMove(bestMove);

        } else {

            return null;
        }
    }

    // ---------------------------------------------------------------------------------------------------- //

    // Try to find a way to block our threat
    private static Move blockThreat(Threat threat) {

        int x1 = threat.getThreatenedPiece().currentXPosition;
        int y1 = threat.getThreatenedPiece().currentYPosition;
        int x2 = threat.getThreat().currentXPosition;
        int y2 = threat.getThreat().currentYPosition;

        // Gets all coordinates that will block our current threat
        List<Coordinates> blockCoordinates = new ArrayList<>(getBlockCoordinates(x1, y1, x2, y2));
        String color = threat.getThreatenedPiece().color;
        List<MoveOption> highestPotentialThreats = new ArrayList<>();
        List<Threat> testThreatList;


        for (ChessPiece piece : allCurrentChesspieces) {
            // Checkes all allies
            if (piece.color == color) {

                List<MoveCoordinates> potentialMovesCopy = new ArrayList<>(piece.getPotentialMoves());

                int i = 0;

                for (MoveCoordinates mc : potentialMovesCopy) {

                    int fromX = mc.getFrom().getX();
                    int fromY = mc.getFrom().getY();
                    int toX = mc.getTo().getX();
                    int toY = mc.getTo().getY();

                    for (Coordinates c : blockCoordinates) {
                        if (c.getX() == toX && c.getY() == toY) {

                            testRun(piece.id, piece.currentXPosition, piece.currentYPosition, toX, toY, true);

                            if (piece.color == BLACK) {
                                // returnerar threatlista
                                //List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                                testThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                            } else {
                                testThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                            }

                            // if there still is any threat after blocking the main threat we add the
                            // highest valued one to our list
                            if (testThreatList.size() > 0) {
                                int highestThreatValue = testThreatList.get(0).getThreatenedPieceValue();
                                highestPotentialThreats.add(new MoveOption(piece, mc, highestThreatValue));
                            }
                            // If there is no longer any threat, we will choose to make this move
                            else {
                                Move move = new Move(fromX, fromY, toX, toY);
                                return move;
                            }
                        }
                        break;
                    }
                }
            }
        }

        if (highestPotentialThreats.size() > 0) {
            // Sorts our generated potential threat outcomes by value
            highestPotentialThreats = highestPotentialThreats.stream()
                    .sorted(Comparator.comparing(moveOption -> moveOption.getThreatValue()))
                    .collect(Collectors.toList());


            // Selects the option with the lowest threat value
            MoveOption bestBlockMoveOption = highestPotentialThreats.get(0);
            int blockPieceValue = bestBlockMoveOption.getPiece().value;
            int x = bestBlockMoveOption.getMoveCoordinates().getTo().getX();
            int y = bestBlockMoveOption.getMoveCoordinates().getTo().getY();

            //TODO: VIktigt!!!! signalerar matt ibland även om vi kan blocka
            //TODO: fixa så att den inte hänger sig i en loop med samma drag hela tiden
            // Checks to see if it's worth blocking the threat
            if (bestBlockMoveOption.getThreatValue() < threat.getThreatenedPieceValue()
                    || (blockPieceValue < threat.getThreatsValue() && !safeSpot(x, y, threat.getThreat().color))) {

                MoveCoordinates mc = bestBlockMoveOption.getMoveCoordinates();

                return createMove(mc);
            }
            // If we couldn't find a way to block the move it's check mate if the threatened piece is the king
        } else if (threat.getThreatenedPieceValue() == KINGSVALUE) {

            System.out.println("SchackMatt!!!!");

            return checkMate();
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------- //

    private static List<Coordinates> getBlockCoordinates(int x1, int y1, int x2, int y2) {

        List<Coordinates> blockCoordinates = new ArrayList<>();
        ValueComparator vcX = new ValueComparator(x1, x2);
        ValueComparator vcY = new ValueComparator(y1, y2);

        if (x1 == x2) {

            for (int i = vcY.getMinValue() + 1; i < vcY.getMaxValue(); i++) {
                blockCoordinates.add(new Coordinates(x1, i));
            }
//            if (y1 > y2) {
//                for (int i = y2 + 1; i < y1; i++) {
//                    blockCoordinates.add(new Coordinates(x1, i));
//                }
//            } else {
//                for (int i = y1 + 1; i < y2; i++) {
//                    blockCoordinates.add(new Coordinates(x1, i));
//                }
//            }

        } else if (y1 == y2) {

            for (int i = vcX.getMinValue() + 1; i < vcX.getMaxValue(); i++) {
                blockCoordinates.add(new Coordinates(1, y1));
            }
//            if (x1 > x2) {
//                for (int i = x2 + 1; i < x1; i++) {
//                    blockCoordinates.add(new Coordinates(i, y1));
//                }
//            } else {
//                for (int i = x1 + 1; i < x2; i++) {
//                    blockCoordinates.add(new Coordinates(i, y1));
//                }
//            }
        } else {
            if (x1 > x2) {
                if (y1 > y2) {
                    while (x1 != x2 + 1 || y1 != y2 + 1) {
                        x1--;
                        y1--;
                        blockCoordinates.add(new Coordinates(x1, y1));
                    }
                } else if (y1 < y2) {
                    while (x1 != x2 + 1 || y1 != y2 - 1) {
                        x1--;
                        y1++;
                        blockCoordinates.add(new Coordinates(x1, y1));
                    }
                }
            } else if (x1 < x2) {
                if (y1 > y2) {
                    while (x1 != x2 - 1 || y1 != y2 + 1) {
                        x1++;
                        y1--;
                        blockCoordinates.add(new Coordinates(x1, y1));
                    }
                } else if (y1 < y2) {
                    while (x1 != x2 - 1 || y1 != y2 - 1) {
                        x1++;
                        y1++;
                        blockCoordinates.add(new Coordinates(x1, y1));
                    }
                }
            }
        }
        return blockCoordinates;
    }

    // ---------------------------------------------------------------------------------------------------- //

    private static Move createMove(MoveCoordinates mc) {

        int fromX = mc.getFrom().getX();
        int fromY = mc.getFrom().getY();
        int toX = mc.getTo().getX();
        int toY = mc.getTo().getY();

        Move move = new Move(fromX, fromY, toX, toY);
        return move;
    }

    // ---------------------------------------------------------------------------------------------------- //

    private static Move checkMate() {

        Move move = new Move(-1, -1, -2, 7);

        return move;
    }

    // ---------------------------------------------------------------------------------------------------- //

    private static Move strike(List<Threat> opponentsThreatList) {

        Move move;

        for (int i = 0; i < opponentsThreatList.size(); i++) {
            // gets the highest untested valued strike info
            ChessPiece opponentsPiece = opponentsThreatList.get(i).getThreatenedPiece();
            int opX = opponentsPiece.currentXPosition;
            int opY = opponentsPiece.currentYPosition;
            List<Threat> testThreatList;

            ChessPiece attackPiece = opponentsThreatList.get(i).getThreat();
            int attackPieceX = attackPiece.currentXPosition;
            int attackPieceY = attackPiece.currentYPosition;

            if (safeSpot(opX, opY, attackPiece.color) || attackPiece.value < opponentsPiece.value) {

                testRun(attackPiece.id, attackPieceX, attackPieceY, opX, opY, false);

                move = controlTestThreats(attackPiece, opponentsPiece, attackPieceX, attackPieceY, opX, opY);

                if (move != null) {
                    return move;
                }
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------- //

    private static Move backUp(String currentPlayerColor) {

        List<MoveOption> moveOptionsList = new ArrayList<>();
        List<MoveOption> allMoveOptionsList = new ArrayList<>();
        List<MoveOption> noThreatOptions = new ArrayList<>();

        for (ChessPiece piece : allCurrentChesspieces) {

            if (piece.color == currentPlayerColor) {

                List<MoveCoordinates> potentialMoves = new ArrayList<>(piece.getPotentialMoves());
                List<Threat> currentPlayerTestThreatList;
                List<Threat> opponentsTestThreatList;

                for (MoveCoordinates mc : potentialMoves) {

                    int fromX = mc.getFrom().getX();
                    int fromY = mc.getFrom().getY();
                    int toX = mc.getTo().getX();
                    int toY = mc.getTo().getY();

                    if (safeSpot(toX, toY, currentPlayerColor)) {

                        testRun(piece.id, fromX, fromY, toX, toY, false);

                        if (currentPlayerColor == BLACK) {
                            currentPlayerTestThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                            opponentsTestThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                        } else {
                            currentPlayerTestThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                            opponentsTestThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                        }

                        if (currentPlayerTestThreatList.size() > 0) {

                            int cpHighestThreatValue = currentPlayerTestThreatList.get(0).getThreatenedPieceValue();

                            if (opponentsTestThreatList.size() > 0) {

                                int opHighestThreatValue = opponentsTestThreatList.get(0).getThreatenedPieceValue();

                                if (cpHighestThreatValue < opHighestThreatValue) {
                                    moveOptionsList.add(new MoveOption(piece, mc, cpHighestThreatValue));
                                }
                            }

                            allMoveOptionsList.add(new MoveOption(piece, mc, cpHighestThreatValue));
                        } else {
                            if (opponentsTestThreatList.size() > 0) {
                                int opHighestThreatValue = opponentsTestThreatList.get(0).getThreatenedPieceValue();

                                noThreatOptions.add(new MoveOption(piece, mc, opHighestThreatValue));
                            } else {
                                noThreatOptions.add(new MoveOption(piece, mc, 0));
                            }
                        }
                    }
                }
            }
        }

        if (noThreatOptions.size() > 0) {

            noThreatOptions = noThreatOptions.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue).reversed())
                    .collect(Collectors.toList());

            int bestThreatValue = noThreatOptions.get(0).getThreatValue();

            List<MoveOption> bestMoveOptions;

            bestMoveOptions = noThreatOptions.stream()
                    .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                    .collect(Collectors.toList());

            Random r = new Random();
            int bound = bestMoveOptions.size();

            int index = r.nextInt(bound);
            MoveOption randomMoveOption = bestMoveOptions.get(index);

            return createMove(randomMoveOption.getMoveCoordinates());
        }

        if (moveOptionsList.size() > 0) {
            moveOptionsList = moveOptionsList.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue))
                    .collect(Collectors.toList());

            int bestThreatValue = moveOptionsList.get(0).getThreatValue();

            List<MoveOption> bestMoveOptions;

            bestMoveOptions = moveOptionsList.stream()
                    .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                    .collect(Collectors.toList());

            Random r = new Random();
            int bound = bestMoveOptions.size();

            int index = r.nextInt(bound);
            MoveOption randomMoveOption = bestMoveOptions.get(index);

            return createMove(randomMoveOption.getMoveCoordinates());
        }

        if (allMoveOptionsList.size() > 0) {
            allMoveOptionsList = allMoveOptionsList.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue))
                    .collect(Collectors.toList());

            int bestThreatValue = allMoveOptionsList.get(0).getThreatValue();

            List<MoveOption> bestMoveOptions;

            bestMoveOptions = allMoveOptionsList.stream()
                    .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                    .collect(Collectors.toList());

            Random r = new Random();
            int bound = bestMoveOptions.size();

            int index = r.nextInt(bound);
            MoveOption randomMoveOption = bestMoveOptions.get(index);


            return createMove(randomMoveOption.getMoveCoordinates());
        }
        return null;
    }


    private static Move randomMove(String currentPlayerColor, int currentHighestThreatValue) {

        List<MoveOption> moveOptionsList = new ArrayList<>();
        List<MoveOption> allMoveOptionsList = new ArrayList<>();
        List<MoveOption> noThreatOptions = new ArrayList<>();

        for (ChessPiece piece : allCurrentChesspieces) {

            if (piece.color == currentPlayerColor) {

                List<MoveCoordinates> potentialMoves = new ArrayList<>(piece.getPotentialMoves());
                List<Threat> currentPlayerTestThreatList;
                List<Threat> opponentsTestThreatList;

                for (MoveCoordinates mc : potentialMoves) {

                    int fromX = mc.getFrom().getX();
                    int fromY = mc.getFrom().getY();
                    int toX = mc.getTo().getX();
                    int toY = mc.getTo().getY();


                    testRun(piece.id, fromX, fromY, toX, toY, false);

                    if (currentPlayerColor == BLACK) {
                        currentPlayerTestThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                        opponentsTestThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                    } else {
                        currentPlayerTestThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                        opponentsTestThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                    }

                    if (currentPlayerTestThreatList.size() > 0) {

                        int cpHighestThreatValue = currentPlayerTestThreatList.get(0).getThreatenedPieceValue();

                        if (opponentsTestThreatList.size() > 0) {

                            int opHighestThreatValue = opponentsTestThreatList.get(0).getThreatenedPieceValue();

                            if (cpHighestThreatValue < opHighestThreatValue) {
                                moveOptionsList.add(new MoveOption(piece, mc, cpHighestThreatValue));
                            }
                        }

                        allMoveOptionsList.add(new MoveOption(piece, mc, cpHighestThreatValue));
                    } else {
                        if (opponentsTestThreatList.size() > 0) {
                            int opHighestThreatValue = opponentsTestThreatList.get(0).getThreatenedPieceValue();

                            noThreatOptions.add(new MoveOption(piece, mc, opHighestThreatValue));
                        } else {
                            noThreatOptions.add(new MoveOption(piece, mc, 0));
                        }
                    }
                }
            }
        }

        if (noThreatOptions.size() > 0) {

            noThreatOptions = noThreatOptions.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue).reversed())
                    .collect(Collectors.toList());

            int bestThreatValue = noThreatOptions.get(0).getThreatValue();

            List<MoveOption> bestMoveOptions;

            bestMoveOptions = noThreatOptions.stream()
                    .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                    .collect(Collectors.toList());

            Random r = new Random();
            int bound = bestMoveOptions.size();

            int index = r.nextInt(bound);
            MoveOption randomMoveOption = bestMoveOptions.get(index);

            return createMove(randomMoveOption.getMoveCoordinates());
        }

        if (moveOptionsList.size() > 0) {
            moveOptionsList = moveOptionsList.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue))
                    .collect(Collectors.toList());

            int bestThreatValue = moveOptionsList.get(0).getThreatValue();

            if(bestThreatValue < currentHighestThreatValue){

                List<MoveOption> bestMoveOptions;

                bestMoveOptions = moveOptionsList.stream()
                        .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                        .collect(Collectors.toList());

                Random r = new Random();
                int bound = bestMoveOptions.size();

                int index = r.nextInt(bound);
                MoveOption randomMoveOption = bestMoveOptions.get(index);

                return createMove(randomMoveOption.getMoveCoordinates());
            }
        }

        if (allMoveOptionsList.size() > 0) {
            allMoveOptionsList = allMoveOptionsList.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue))
                    .collect(Collectors.toList());

            int bestThreatValue = allMoveOptionsList.get(0).getThreatValue();

            List<MoveOption> bestMoveOptions;

            bestMoveOptions = allMoveOptionsList.stream()
                    .filter(moveOption -> moveOption.getThreatValue() == bestThreatValue)
                    .collect(Collectors.toList());

            Random r = new Random();
            int bound = bestMoveOptions.size();

            int index = r.nextInt(bound);
            MoveOption randomMoveOption = bestMoveOptions.get(index);


            return createMove(randomMoveOption.getMoveCoordinates());
        }
        return null;
    }


    // -------------------------------------------------------------------------------------------------------- //

    private static Move controlTestThreats(ChessPiece attackPiece, ChessPiece opponentsPiece, int attackPieceX,
                                           int attackPieceY, int opX, int opY) {

        Move move;

        List<Threat> testThreatList;

        if (attackPiece.color == BLACK) {

            testThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
        } else {
            testThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
        }

        if (testThreatList.size() > 0) {
            int highestThreatValue = testThreatList.get(0).getThreatenedPieceValue();
            if (highestThreatValue < opponentsPiece.value) {
                move = new Move(attackPieceX, attackPieceY, opX, opY);
                return move;
            }
        }
        // If there is no longer any threat, we will choose to make this move
        else {
            move = new Move(attackPieceX, attackPieceY, opX, opY);
            return move;
        }

        return null;
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

    private static MoveCoordinates getLowestTestThreatMove(Threat threat, String color) {

        // Lists with potential threats
        List<MoveOption> highestPotentialThreats1 = new ArrayList<>();
        List<MoveOption> highestPotentialThreats2;
        List<MoveOption> highestPotentialThreats3;

        MoveCoordinates lowestThreatMove1 = null;
        MoveCoordinates lowestThreatMove2 = null;
        MoveCoordinates lowestThreatMove3 = null;

        int lowestThreatValue1 = KINGSVALUE + 1;
        int lowestThreatValue2 = KINGSVALUE + 1;
        int lowestThreatValue3 = KINGSVALUE + 1;

        // If we have any piece that can take the piece who threatens us
        if (potentialSaviour.size() > 0) {

            for (Threat threatMove : potentialSaviour) {

                ChessPiece piece = threatMove.getThreat();
                int id = piece.id;
                int fromX = piece.currentXPosition;
                int fromY = piece.currentYPosition;
                int toX = threatMove.getThreatenedPiece().currentXPosition;
                int toY = threatMove.getThreatenedPiece().currentYPosition;

                MoveCoordinates mc = createMoveCoordinates(fromX, fromY, toX, toY);
                //MoveCoordinates mc = new MoveCoordinates(new Coordinates(fromX, fromY), new Coordinates(toX, toY));


                // Testing every saviourPiece that can take the threatening piece
                testRun(id, fromX, fromY, toX, toY, false);

                List<Threat> testThreatList;

                if (color == BLACK) {
                    testThreatList = new ArrayList<>(MoveCollection.getTestBlackThreats());
                } else {
                    testThreatList = new ArrayList<>(MoveCollection.getTestWhiteThreats());
                }

                //List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();

                // If we no longer have a threat against us we return that savePiece
                if (testThreatList.size() == 0) {
                    return mc;
                } else {
                    int highestThreatValue = testThreatList.get(0).getThreatenedPieceValue();

                    // If we still have any threat against us we add the highest threatScore
                    // to the list with all other highest threatScores from our other getSortedThreatList results
                    highestPotentialThreats1.add(new MoveOption(piece, mc, highestThreatValue));
                }
            }
        }

        // Fetching the lists with potential moves and strikes for the threatened piece
        List<MoveCoordinates> potentialMoves = new ArrayList<>(threat.getThreatenedPiece().getPotentialMoves());
        List<MoveCoordinates> potentialStrikes = new ArrayList<>(threat.getThreatenedPiece().getPotentialStrikes());

        // Creating testLists with threat outcomes and sorting all lists by the highest threat value
        if (potentialStrikes.size() > 0) {

            highestPotentialThreats3 = getSortedThreatList(potentialStrikes, threat, color);

            if (highestPotentialThreats3.size() > 0) {
                lowestThreatMove3 = highestPotentialThreats3.get(0).getMoveCoordinates();
                lowestThreatValue3 = highestPotentialThreats3.get(0).getThreatValue();
            } else {
                return null;
            }
        } else {
            int threatenedPieceValue = threat.getThreatenedPieceValue();
            int threatsValue = threat.getThreatsValue();
            int threatenedPieceX = threat.getThreatenedPiece().currentXPosition;
            int threatenedPieceY = threat.getThreatenedPiece().currentYPosition;
            String threatsColor = threat.getThreat().color;

            if (threatenedPieceValue < threatsValue && !safeSpot(threatenedPieceX, threatenedPieceY, threatsColor)) {
                return null;
            }
        }
        if (potentialMoves.size() > 0) {

            highestPotentialThreats2 = getSortedThreatList(potentialMoves, threat, color);

            if (highestPotentialThreats2.size() > 0) {
                lowestThreatMove2 = highestPotentialThreats2.get(0).getMoveCoordinates();
                lowestThreatValue2 = highestPotentialThreats2.get(0).getThreatValue();
            } else {
                return null;
            }
        }
        if (highestPotentialThreats1.size() > 0) {

            highestPotentialThreats1 = highestPotentialThreats1.stream()
                    .sorted(Comparator.comparing(MoveOption::getThreatValue))
                    .collect(Collectors.toList());


            lowestThreatMove1 = highestPotentialThreats1.get(0).getMoveCoordinates();
            lowestThreatValue1 = highestPotentialThreats1.get(0).getThreatValue();
        }

        // Returning the lowest threatMove
        if (lowestThreatValue1 <= lowestThreatValue2 && lowestThreatValue1 <= lowestThreatValue3
                && lowestThreatValue1 != KINGSVALUE && lowestThreatValue1 != KINGSVALUE + 1) {
            return lowestThreatMove1;

        } else if (lowestThreatValue3 <= lowestThreatValue2 && lowestThreatValue3 != KINGSVALUE && lowestThreatValue3 != KINGSVALUE + 1) {
            return lowestThreatMove3;

        } else if (lowestThreatValue2 != KINGSVALUE && lowestThreatValue2 != KINGSVALUE + 1) {
            return lowestThreatMove2;
        }
        return null;
    }

// ---------------------------------------------------------------------------------------------------------- //

    private static List<MoveOption> getSortedThreatList(List<MoveCoordinates> list, Threat threat, String color) {

        List<MoveOption> highestPotentialThreats = testPotentialMoves(list, threat, color);
        highestPotentialThreats = highestPotentialThreats.stream()
                .sorted(Comparator.comparing(MoveOption::getThreatValue))
                .collect(Collectors.toList());

        return highestPotentialThreats;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static MoveCoordinates createMoveCoordinates(int fromX, int fromY, int toX, int toY) {

        MoveCoordinates mc = new MoveCoordinates(new Coordinates(fromX, fromY), new Coordinates(toX, toY));

        return mc;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static void testRun(int id, int fromX, int fromY, int toX, int toY, boolean block) {

        //Setting up board for a testrun
        setBoard(id, fromX, fromY, toX, toY, false);
        MoveCollection.setTest(true);
        if (block) {
            MoveCollection.setBlock(true);
        }
        MoveCollection.createThreatList();
        MoveCollection.setTest(false);
        MoveCollection.setBlock(false);
        //Resetting board
        setBoard(id, toX, toY, fromX, fromY, true);

    }

    // ----------------------------------------------------------------------------------------------------- //

    private static List<MoveOption> testPotentialMoves(List<MoveCoordinates> list, Threat threat, String color) {

        List<MoveOption> testList = new ArrayList<>();
        ChessPiece threatenedPiece = threat.getThreatenedPiece();
        int threatenedPieceCurrentXPosition = threatenedPiece.currentXPosition;
        int threatenedPieceCurrentYPosition = threatenedPiece.currentYPosition;
        int threatenedPieceID = threatenedPiece.id;
        List<MoveCoordinates> listCopy = new ArrayList<>(list);

        // Testing a threatened piece move or strike options to see what outcome they will have
        for (MoveCoordinates mc : listCopy) {
            int moveX = mc.getTo().getX();
            int moveY = mc.getTo().getY();
            //if (safeSpot(moveX, moveY, threatenedPiece.color)) {
            setBoard(threatenedPieceID, threatenedPieceCurrentXPosition, threatenedPieceCurrentYPosition, moveX, moveY, false);
            MoveCollection.setTest(true);
            MoveCollection.createThreatList();
            MoveCollection.setTest(false);
            setBoard(threatenedPieceID, moveX, moveY, threatenedPieceCurrentXPosition, threatenedPieceCurrentYPosition, true);

            if (color.equals(BLACK)) {
                List<Threat> testBlackThreatList = MoveCollection.getTestBlackThreats();
                // If there is no threat against us after the tested savePiece or strike we deside it's our best option
                if (testBlackThreatList.size() == 0) {
                    bestMove = mc;
                    break;
                    // Otherwise we add the highest threat score to our testList
                } else {
                    testList.add(new MoveOption(threatenedPiece, mc, testBlackThreatList.get(0).getThreatenedPieceValue()));
                }
            } else {
                // Same for white player
                List<Threat> testWhiteThreatList = MoveCollection.getTestWhiteThreats();
                if (testWhiteThreatList.size() == 0) {
                    bestMove = mc;
                    break;
                } else {
                    testList.add(new MoveOption(threatenedPiece, mc, testWhiteThreatList.get(0).getThreatenedPieceValue()));
                }
            }
            //}
        }
        return testList;
    }

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
                        piece.setCurrentXPosition(toX);
                        piece.setCurrentYPosition(toY);
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
    private static boolean safeSpot(int x, int y, String color) {

        List<Coordinates> list;

        if (color == BLACK) {
            list = blackSafePositions.stream().collect(Collectors.toList());
        } else {
            list = whiteSafePositions.stream().collect(Collectors.toList());
        }

        for (Coordinates coord : list) {
            if (coord.getX() == x && coord.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // ----------------------------------------------------------------------------------------------------- //

    private static Move check() {
        if (currentColor == BLACK) {
            return new Move(0, 7, -1, 7);
        } else {
            return new Move(0, 6, -1, 7);
        }
    }
}