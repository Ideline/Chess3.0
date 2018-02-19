import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoveCollection {

    //    private static Map<String, Map<Integer, Map<String, List<Coordinates>>>> allPotentialMoves = new HashMap<>();
    private static Map<String, List<ChessPiece>> threatenedPieces = new HashMap<>();
    private static Map<String, List<ChessPiece>> rankedThreats = new HashMap<>();
    //private static List<Coordinates> test = new ArrayList<>();
    private static List<Coordinates> safePositions = new ArrayList<>();
    private static List<ChessPiece> allCurrentChessPieces = new ArrayList<>();
    private static List<ChessPiece> allCurrentTestChessPieces = new ArrayList<>();
    private static List<Threat> blackThreats = new ArrayList<>();
    private static List<Threat> whiteThreats = new ArrayList<>();
    private static List<Threat> testBlackThreats = new ArrayList<>();
    private static List<Threat> testWhiteThreats = new ArrayList<>();
    private static boolean test = false;

    public static void setTest(boolean test) {
        MoveCollection.test = test;
    }

    public static List<Threat> getBlackThreats() {
        return blackThreats;
    }

    public static List<Threat> getWhiteThreats() {
        return whiteThreats;
    }

    public static List<Coordinates> getSafePositions() {
        return safePositions;
    }

    public static List<ChessPiece> getAllCurrentChessPieces() {
        return allCurrentChessPieces;
    }

    public static List<Threat> getTestBlackThreats() {
        return testBlackThreats;
    }

    public static List<Threat> getTestWhiteThreats() {
        return testWhiteThreats;
    }
    //
//    public static Map<String, List<ChessPiece>> getRankedThreats() {
//        return rankedThreats;
//    }

//    public static Map<String, Map<Integer, Map<String, List<Coordinates>>>> getAllPotentialMoves() {
//        return allPotentialMoves;
//    }

    public static void createCurrentChessPieceList() {

        allCurrentChessPieces.clear();
        allCurrentTestChessPieces.clear();

        Stream.of(Game.board)
                .flatMap(Stream::of)
                .forEach(piece -> {
                    if (piece != null) {
                        piece.setPotentialMoves();
                        if (test) {
                            allCurrentTestChessPieces.add(piece);
                        } else {
                            allCurrentChessPieces.add(piece);
                        }

                    }
                });

        if (!test) {
            createThreatList();
            createSafePositionsList();
        }
    }

    public static void createThreatList() {

        whiteThreats.clear();
        blackThreats.clear();
        testBlackThreats.clear();
        testWhiteThreats.clear();

        List<ChessPiece> list;

        if (test) {
            createCurrentChessPieceList();
            list = allCurrentTestChessPieces;
        } else {
            list = allCurrentChessPieces;
        }

        List<MoveCoordinates> strikeList;

        for (ChessPiece piece : list) {
            if (test) {
                strikeList = piece.getPotentialStrikesTest();
            } else {
                if(piece.id == 32){
                    int test = 0;
                }
                strikeList = piece.getPotentialStrikes();
            }
            if (strikeList.size() != 0) {
                for (MoveCoordinates mc : strikeList) {
                    int threatenedXCoordinate = mc.getTo().getX();
                    int threatenedYCoordinate = mc.getTo().getY();

                    for (ChessPiece piece2 : allCurrentChessPieces) {
                        if (threatenedXCoordinate == piece2.currentXPosition && threatenedYCoordinate == piece2.currentYPosition) {
                            if (piece2.color == "Black") {
                                if (test) {
                                    testBlackThreats.add(new Threat(piece2, piece));
                                } else {
                                    blackThreats.add(new Threat(piece2, piece));
                                }
                            } else {
                                if (test) {
                                    testWhiteThreats.add(new Threat(piece2, piece));
                                } else {
                                    whiteThreats.add(new Threat(piece2, piece));
                                }
                            }

                        }
                    }
                }
            }
        }

        blackThreats = sortThreatList(blackThreats);
        whiteThreats = sortThreatList(whiteThreats);
        testBlackThreats = sortThreatList(testBlackThreats);
        testWhiteThreats = sortThreatList(testWhiteThreats);

    }

    public static List<Threat> sortThreatList(List<Threat> threatList) {
        threatList = threatList.stream()
                .sorted(Comparator.comparing(Threat::getThreatenedPieceValue).reversed())
                .collect(Collectors.toList());

        return threatList;
    }

//    public static Map<String, Map<Integer, Map<String, List<Coordinates>>>> createAllPotentialMovesMap() {
//
//        allPotentialMoves.clear();
//
//
//        Stream.of(Game.board)
//                .flatMap(Stream::of)
//                .forEach(piece -> {
//                    if (piece != null) {
//                        Map<Integer, Map<String, List<Coordinates>>> map = piece.getAllPotentialMoves();
//                        if (map.size() != 0) {
//                            if (allPotentialMoves.get(piece.color) == null) {
//                                allPotentialMoves.put(piece.color, map);
//                            } else {
//                                allPotentialMoves.get(piece.color).put(piece.id, map.get(piece.id));
//                            }
//                        }
//                    }
//                });
//
//        createThreatenedPiecesMap();
//        rankThreats();
//        createSafePositionsList();
//        return allPotentialMoves;
//    }

    //    private static void createThreatenedPiecesMap() {
//
//        Stream.of(Game.board)
//                .flatMap(Stream::of)
//                .forEach(piece -> {
//                    if (piece != null && allPotentialMoves.get(piece.color).containsKey(piece.id)) {
//                        List<Coordinates> potentialStrikes = allPotentialMoves.get(piece.color).get(piece.id).get("potentialStrikes");
//                        if (potentialStrikes != null) {
//                            potentialStrikes.forEach(xy -> {
//                                int x = xy.getX();
//                                int y = xy.getY();
//                                ChessPiece threatenedPiece = Game.board[x][y];
//
//                                if (threatenedPieces.get(threatenedPiece.color) == null) {
//                                    List<ChessPiece> pieces = new ArrayList<>();
//                                    threatenedPieces.put(threatenedPiece.color, pieces);
//                                    pieces.add(threatenedPiece);
//                                } else {
//                                    List<ChessPiece> pieces = threatenedPieces.get(threatenedPiece.color);
//                                    pieces.add(threatenedPiece);
//                                }
//                            });
//                        }
//                    }
//                });
//    }
//
//    private static void rankThreats() {
//
//        rankedThreats = threatenedPieces.entrySet().stream().collect(Collectors.toMap(
//                Map.Entry::getKey,
//                list -> list.getValue().stream()
//                        .sorted(Comparator.comparing(ChessPiece::getValue).reversed())
//                        .collect(Collectors.toList())));
//    }
//
    private static void createSafePositionsList() {
        safePositions.clear();
        fillSafePositionList();

        for (ChessPiece piece : allCurrentChessPieces) {
            List<Coordinates> unsafePositions = piece.getUnsafePositions();

            unsafePositions.forEach(up -> {
                safePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY());
            });

//            for (Coordinates up : unsafePositions) {
//                safePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY());
//            }
        }
//        Stream.of(Game.board)
//                .flatMap(Stream::of)
//                .forEach(piece -> {
//                    if (piece != null) {
//                        List<Coordinates> unsafePositions = piece.getUnsafePositions();
//
//                        for (Coordinates up : unsafePositions) {
//                            safePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY());
//                        }
//                    }
//                });
    }

    private static void fillSafePositionList() {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                safePositions.add(new Coordinates(a, b));
            }
        }
    }
}