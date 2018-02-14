import java.util.ArrayList;
import java.util.List;

public class Move {

    public Move(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public Move(int x1, int y1, int x2, int y2){
        this.from = Character.toString(getCharacter(x1)) + Integer.toString(8-y1);
        this.to = Character.toString(getCharacter(x2)) + Integer.toString(8-y2);
    }

    private String from;
    private String to;
    private Move move;
    private static List<Move> moves = new ArrayList<>();

    public static List<Move> getMoves() {
        return moves;
    }

    public static void removeMove(){
        moves.remove(0);
    }

    public static Move getMove(){
        return moves.get(0);
    }

    public static void makePlaylist(){

        moves = new ArrayList<Move>();

        // Adolf Anderssen vs Lionel Adalbert Bagration Felix Kieseritzky London 21 juni 1851
        moves.add(new Move("E2", "E4"));
        moves.add(new Move("E7", "E5"));
        moves.add(new Move("F2", "F4"));
        moves.add(new Move("E5", "F4"));
        moves.add(new Move("F1", "C4"));
        moves.add(new Move("D8", "H4"));
        moves.add(new Move("E1", "F1"));
        moves.add(new Move("B7", "B5"));
        moves.add(new Move("C4", "B5"));
        moves.add(new Move("G8", "F6"));
        moves.add(new Move("G1", "F3"));
        moves.add(new Move("H4", "H6"));
        moves.add(new Move("D2", "D3"));
        moves.add(new Move("F6", "H5"));
        moves.add(new Move("F3", "H4"));
        moves.add(new Move("H6", "G5"));
        moves.add(new Move("H4", "F5"));
        moves.add(new Move("C7", "C6"));
        moves.add(new Move("G2", "G4"));
        moves.add(new Move("H5", "F6"));
        moves.add(new Move("H1", "G1"));
        moves.add(new Move("C6", "B5"));
        moves.add(new Move("H2", "H4"));
        moves.add(new Move("G5", "G6"));
        moves.add(new Move("H4", "H5"));
        moves.add(new Move("G6", "G5"));
        moves.add(new Move("D1", "F3"));
        moves.add(new Move("F6", "G8"));
        moves.add(new Move("C1", "F4"));
        moves.add(new Move("G5", "F6"));
        moves.add(new Move("B1", "C3"));
        moves.add(new Move("F8", "C5"));
        moves.add(new Move("C3", "D5"));
        moves.add(new Move("F6", "B2"));
        moves.add(new Move("F4", "D6"));
        moves.add(new Move("C5", "G1"));
        moves.add(new Move("E4", "E5"));
        moves.add(new Move("B2", "A1"));
        moves.add(new Move("F1", "E2"));
        moves.add(new Move("B8", "A6"));
        moves.add(new Move("F5", "G7"));
        moves.add(new Move("E8", "D8"));
        moves.add(new Move("F3", "F6"));
        moves.add(new Move("G8", "F6"));
        moves.add(new Move("D6", "E7"));
        moves.add(new Move("SS", "MM"));
    }

    private static char getCharacter(int i){
        switch (i){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
        }
        return 'z';
    }

}
