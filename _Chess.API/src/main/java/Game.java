import java.util.ArrayList;

public class Game {

    //MoveCollection
    public Game(){
        this.players = new ArrayList<Player>();
        this.running = false;
    }

    public static ChessPiece[][] board; //= new ChessPiece[8][8];
    private static boolean running;

    //private ChessPiece piece;
    private static ArrayList<Player> players;
    private static ChessPiece piece;
    private static boolean black = true;

    public static boolean isBlack() {
        return black;
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static ChessPiece[][] start() {
        board = new ChessPiece[8][8];
        fillBoard();
        //piece = board[3][1];
        running = true;
        Move.makePlaylist();
        return board;
    }

    private static void fillBoard() {
        //this.players = new ArrayList<Player>();
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

        board[0][2] = null;//new Rook(0, 2, "Black", 1, 5);//null;
        board[1][2] = null;
        board[2][2] = null;//new Queen(2, 2, "Black", 4, 9);//null;
        board[3][2] = new Knight(3, 2, "White", 26, 3);//null;
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
        board[4][4] = null;//new Queen(4, 4, "White", 28, 9);//null;
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
        board[1][7] = null;//new Knight(1, 7, "White", 26, 3);
        board[2][7] = new Bishop(2, 7, "White", 27, 3);
        board[3][7] = new Queen(3, 7, "White", 28, 9);
        board[4][7] = new King(4, 7, "White", 29, 1000);
        board[5][7] = new Bishop(5, 7, "White", 30, 3);
        board[6][7] = new Knight(6, 7, "White", 31, 3);
        board[7][7] = new Rook(7, 7, "White", 32, 5);
    }


    public static Move getNextMove() {
        black = !black;
        if(!running)
            Game.start();


// Random
//        List<Coordinates> potentialMoves = piece.getPotentialMoves();
//
//        Random r = new Random();
//        int i = r.nextInt(potentialMoves.size());
//        Coordinates xy = potentialMoves.get(i);
//
//        int x1 = piece.tile.getX();
//        int y1 = piece.tile.getY();
//
//        int x2 = xy.getX();
//        int y2 = xy.getY();
//
//        Move move = new Move(x1, y1, x2, y2);
//        board[x2][y2] = board[x1][y1];
//        board[x1][y1] = null;
//        piece = board[x2][y2];
//        piece.tile.setX(x2);
//        piece.tile.setY(y2);
// 2
// 3       Map<String, Map<Integer, Map<String, List<Coordinates>>>> testMap = MoveCollection.createAllPotentialMovesMap();
//
//        //Move test = new Move("A1", "A2");
//        Move move = Move.getMove();
//        Move.removeMove();
//
//  3      MoveHandler.pickMove();
        MoveCollection.createCurrentChessPieceList();
        return MoveHandler.pickMove();//test;//MoveHandler.getMove(black);
    }
}
