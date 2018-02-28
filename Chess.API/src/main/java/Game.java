import java.util.ArrayList;

public class Game {

    public Game(){
        this.players = new ArrayList<Player>();
        this.running = false;
    }

    private static ArrayList<Player> players;
    public static ChessPiece piece;
    public static ChessPiece[][] board;
    public static CheckFlag checkFlag;
    private static boolean running;

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static ChessPiece[][] start() {
        if(running){
            return board;
        }
        running = true;
        Board.fillBoard();
        board = Board.getBoard();
        checkFlag = new CheckFlag();

        Move.makePlaylist();
        return board;
    }

    public synchronized static Move getNextMove() {

        if(!running)
            Game.start();

        MoveCollection.createCurrentChessPieceList();
        Move move = MoveHandler.pickMove();

        if(move.getTo().matches("S1") || move.getTo().matches("M1")){
            return move;
        }

        MoveHandler.clearLists();
        Board.updateBoard(move);
        Player.changePlayerTurn();

        System.out.println(move.getFrom() + move.getTo());

        return move;
    }
}
