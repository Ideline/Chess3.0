import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoveCollection {

    private static List<Coordinate> whiteSafePositions = new ArrayList<>();
    private static List<Coordinate> blackSafePositions = new ArrayList<>();
    private static List<ChessPiece> allCurrentChessPieces = new ArrayList<>();
    private static List<ChessPiece> allCurrentTestChessPieces = new ArrayList<>();
    private static List<Threat> blackThreats = new ArrayList<>();
    private static List<Threat> whiteThreats = new ArrayList<>();
    private static List<Threat> testBlackThreats = new ArrayList<>();
    private static List<Threat> testWhiteThreats = new ArrayList<>();
    private static boolean test = false;
    private static boolean block = false;

    public static void setBlock(boolean block) {
        MoveCollection.block = block;
    }

    public static boolean isBlock() {
        return block;
    }

    public static void setTest(boolean test) {
        MoveCollection.test = test;
    }

    public static List<Threat> getBlackThreats() {
        return blackThreats;
    }

    public static List<Threat> getWhiteThreats() {
        return whiteThreats;
    }

    public static List<Coordinate> getWhiteSafePositions() {
        return whiteSafePositions;
    }

    public static List<Coordinate> getBlackSafePositions() {
        return blackSafePositions;
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

    public static void createCurrentChessPieceList() {

        if(!test){
            allCurrentChessPieces.clear();
        }
        else{
            allCurrentTestChessPieces.clear();
        }

        Stream.of(Game.board)
                .flatMap(Stream::of)
                .forEach(piece -> {
                    if (piece != null) {
                        if(!test) {
                            piece.setPotentialMoves();
                        }
                        if (test) {
                            allCurrentTestChessPieces.add(piece);
                        } else {
                            allCurrentChessPieces.add(piece);
                        }
                    }
                });

        if (!test) {
            createThreatList();
            createSafePositionsLists();
        }
    }

    public static void createThreatList() {

        if(!test){
            whiteThreats.clear();
            blackThreats.clear();
        }
        else{
            testBlackThreats.clear();
            testWhiteThreats.clear();
        }

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
                strikeList = piece.getPotentialStrikes();
            }
            if (strikeList.size() != 0) {
                for (MoveCoordinates mc : strikeList) {
                    int threatenedXCoordinate = mc.getTo().getX();
                    int threatenedYCoordinate = mc.getTo().getY();

                    for (ChessPiece piece2 : list) {
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

    private static void createSafePositionsLists() {
        whiteSafePositions.clear();
        blackSafePositions.clear();
        fillSafePositionList();

        for (ChessPiece piece : allCurrentChessPieces) {
            List<Coordinate> unsafePositions = piece.getUnsafePositions();

            unsafePositions.forEach(up -> {
                whiteSafePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY() && piece.color == "Black");
            });

            unsafePositions.forEach(up -> {
                blackSafePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY() && piece.color == "White");
            });
        }
    }

    private static void fillSafePositionList() {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                whiteSafePositions.add(new Coordinate(a, b));
                blackSafePositions.add(new Coordinate(a, b));
            }
        }
    }
}
