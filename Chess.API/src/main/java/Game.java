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
    private static boolean black = true;

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static ChessPiece[][] start() {
        if(running){
            return board;
        }
        running = true;
        board = new ChessPiece[8][8];
        checkFlag = new CheckFlag();

        fillBoard();

        Move.makePlaylist();
        return board;
    }

    private static void fillBoard() {

        //TODO: Gör snyggare metod för att fylla brädet och lägg in värdet i resp konstruktor

        board[0][0] = new Rook(0, 0, "Black", 1, 5);
        board[1][0] = new Knight(1, 0, "Black", 2, 3);
        board[2][0] = new Bishop(2, 0, "Black", 3,  3);
        board[3][0] = new Queen(3, 0, "Black", 4, 9);
        board[4][0] = new King(4, 0, "Black", 5 , 1000);
        board[5][0] = new Bishop(5, 0, "Black", 6, 3);
        board[6][0] = new Knight(6, 0, "Black", 7, 3);
        board[7][0] = new Rook(7, 0, "Black", 8, 5);

        board[0][1] = new BlackPawn(0, 1, "Black", 9, 1);
        board[1][1] = new BlackPawn(1, 1, "Black" , 10, 1);
        board[2][1] = new BlackPawn(2, 1, "Black", 11, 1);
        board[3][1] = new BlackPawn(3, 1, "Black", 12, 1);
        board[4][1] = new BlackPawn(4, 1, "Black", 13, 1);
        board[5][1] = new BlackPawn(5, 1, "Black", 14, 1);
        board[6][1] = new BlackPawn(6, 1, "Black", 15, 1);
        board[7][1] = new BlackPawn(7, 1, "Black", 16, 1);

        board[0][2] = null;
        board[1][2] = null;
        board[2][2] = null;
        board[3][2] = null;
        board[4][2] = null;
        board[5][2] = null;
        board[6][2] = null;
        board[7][2] = null;

        board[0][3] = null;
        board[1][3] = null;
        board[2][3] = null;
        board[3][3] = null;
        board[4][3] = null;
        board[5][3] = null;
        board[6][3] = null;
        board[7][3] = null;

        board[0][4] = null;
        board[1][4] = null;
        board[2][4] = null;
        board[3][4] = null;
        board[4][4] = null;
        board[5][4] = null;
        board[6][4] = null;
        board[7][4] = null;

        board[0][5] = null;
        board[1][5] = null;
        board[2][5] = null;
        board[3][5] = null;
        board[4][5] = null;
        board[5][5] = null;
        board[6][5] = null;
        board[7][5] = null;

        board[0][6] = new WhitePawn(0, 6, "White", 17, 1);
        board[1][6] = new WhitePawn(1, 6, "White" ,18, 1);
        board[2][6] = new WhitePawn(2, 6, "White", 19, 1);
        board[3][6] = new WhitePawn(3, 6, "White", 20, 1);
        board[4][6] = new WhitePawn(4, 6, "White", 21, 1);
        board[5][6] = new WhitePawn(5, 6, "White", 22, 1);
        board[6][6] = new WhitePawn(6, 6, "White", 23, 1);
        board[7][6] = new WhitePawn(7, 6, "White", 24, 1);

        board[0][7] = new Rook(0, 7, "White", 25, 5);
        board[1][7] = new Knight(1, 7, "White", 26, 3);
        board[2][7] = new Bishop(2, 7, "White", 27, 3);
        board[3][7] = new Queen(3, 7, "White", 28, 9);
        board[4][7] = new King(4, 7, "White", 29, 1000);
        board[5][7] = new Bishop(5, 7, "White", 30, 3);
        board[6][7] = new Knight(6, 7, "White", 31, 3);
        board[7][7] = new Rook(7, 7, "White", 32, 5);
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
        updateBoard(move);
        changePlayerTurn();

        System.out.println(move.getFrom() + move.getTo());

        return move;
    }

    synchronized public static void updateBoard(Move move){

        if(!move.getFrom().matches(move.getTo())){
            String getFrom = move.getFrom();
            String getTo = move.getTo();
            String sx1 = getFrom.substring(0,1);
            String sy1 = getFrom.substring(1);
            String sx2 = getTo.substring(0,1);
            String sy2 = getTo.substring(1);

            int x1 = Move.getX(sx1);
            int y1 = Move.getY(sy1);
            int x2 = Move.getX(sx2);
            int y2 = Move.getY(sy2);

            Game.board[x2][y2] = Game.board[x1][y1];
            Game.board[x1][y1] = null;
            Game.piece = Game.board[x2][y2];
            Game.piece.setCurrentXPosition(x2);
            Game.piece.setCurrentYPosition(y2);
            Game.piece.tile.setX(x2);
            Game.piece.tile.setY(y2);
        }
    }

    public static void changePlayerTurn(){

        if (MoveHandler.getPlayerTurn() == PlayerTurn.WHITE) {
            MoveHandler.setPlayerTurn(PlayerTurn.BLACK);
        } else {
            MoveHandler.setPlayerTurn(PlayerTurn.WHITE);
        }
    }
}
