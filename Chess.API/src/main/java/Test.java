import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    private static Map<String, Map<Integer, Map<String, List<Coordinates>>>> allPotentialMoves = new HashMap<>();
    private static Map<String, List<ChessPiece>> threatenedPieces = new HashMap<>();
    private static Map<String, List<ChessPiece>> rankedThreats = new HashMap<>();
    private static List<Coordinates> test = new ArrayList<>();
    private static List<Coordinates> safePositions = new ArrayList<>();

    public static Map<String, Map<Integer, Map<String, List<Coordinates>>>> createAllPotentialMovesMap() {

        allPotentialMoves.clear();


        Stream.of(Game.board)
                .flatMap(Stream::of)
                .forEach(piece -> {
                    if (piece != null) {
                        Map<Integer, Map<String, List<Coordinates>>> map = piece.getAllPotentialMoves();
                        if (map.size() != 0) {
                            if (allPotentialMoves.get(piece.color) == null) {
                                allPotentialMoves.put(piece.color, map);
                            } else {
                                allPotentialMoves.get(piece.color).put(piece.id, map.get(piece.id));
                            }
                        }
                    }
                });

        createThreatenedPiecesMap();
        rankThreats();
        createSafePositionsList();
        return allPotentialMoves;

        // fel safecoordinater (3, 0), (3, 7), (5, 0), (5, 7) kungen!
    }

    private static void createThreatenedPiecesMap() {

        Stream.of(Game.board)
                .flatMap(Stream::of)
                .forEach(piece -> {
                    if (piece != null && allPotentialMoves.get(piece.color).containsKey(piece.id)) {
                        List<Coordinates> potentialStrikes = allPotentialMoves.get(piece.color).get(piece.id).get("potentialStrikes");
                        if (potentialStrikes != null) {
                            potentialStrikes.forEach(xy -> {
                                int x = xy.getX();
                                int y = xy.getY();
                                ChessPiece threatenedPiece = Game.board[x][y];

                                if (threatenedPieces.get(threatenedPiece.color) == null) {
                                    List<ChessPiece> pieces = new ArrayList<>();
                                    threatenedPieces.put(threatenedPiece.color, pieces);
                                    pieces.add(threatenedPiece);
                                } else {
                                    List<ChessPiece> pieces = threatenedPieces.get(threatenedPiece.color);
                                    pieces.add(threatenedPiece);
                                }
                            });
                        }
                    }
                });
    }

    private static void rankThreats() {

        rankedThreats = threatenedPieces.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                list -> list.getValue().stream()
                        .sorted(Comparator.comparing(ChessPiece::getValue).reversed())
                        .collect(Collectors.toList())));
    }

    private static void createSafePositionsList() {
        fillSafePositionList();
        Stream.of(Game.board)
                .flatMap(Stream::of)
                .forEach(piece -> {
                    if (piece != null) {
                        List<Coordinates> unsafePositions = piece.getUnsafePositions();

                        for (Coordinates up : unsafePositions) {
                            safePositions.removeIf(sp -> sp.getX() == up.getX() && sp.getY() == up.getY());
                        }
                    }
                });
    }

    private static void fillSafePositionList() {
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                safePositions.add(new Coordinates(a, b));
            }
        }
    }
}
