import java.sql.Date;
import java.util.*;
import java.util.ArrayList;

import spark.Request;

public class Game {

    //Test
    public Game(){
        this.players = new ArrayList<Player>();
        this.running = false;
    }

    public static ChessPiece[][] board = new ChessPiece[8][8];
    private static boolean running;

    //private ChessPiece piece;
    private static ArrayList<Player> players;
    private static ChessPiece piece;

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static ChessPiece[][] start() {
        board = new ChessPiece[8][8];
        fillBoard();
        piece = board[0][0];
        running = true;
        return board;
    }

    private static void fillBoard() {
        //this.players = new ArrayList<Player>();
        board[0][0] = new BlackRook(0, 0, "Black");
        board[1][0] = null;//new BlackKnight(1, 0, "Black");
        board[2][0] = null;//new BlackBishop(2, 0, "Black");
        board[3][0] = new BlackQueen(3, 0, "Black");
        board[4][0] = new BlackKing(4, 0, "Black");
        board[5][0] = new BlackBishop(5, 0, "Black");
        board[6][0] = new BlackKnight(6, 0, "Black");
        board[7][0] = new BlackRook(7, 0, "Black");
        board[0][1] = null;

        for(int y = 1; y < 2; y++){
            for(int x = 1; x < 8; x++){
                board[x][y] = new BlackPawn(x, y, "Black");
            }
        }
        for(int y = 2; y < 6; y++){
            for(int x = 0; x < 8; x++){
                board[x][y] = null;
            }
        }
        for(int y = 6; y < 7; y++){
            for(int x = 0; x < 8; x++){
                board[x][y] = new WhitePawn(x, y, "White");
            }
        }
        board[0][7] = new WhiteRook(0, 7, "White");
        board[1][7] = new WhiteKnight(1, 7, "White");
        board[2][7] = new WhiteBishop(2, 7, "White");
        board[3][7] = new WhiteQueen(3, 7, "White");
        board[4][7] = new WhiteRook(4, 7, "White");
        board[5][7] = new WhiteBishop(5, 7, "White");
        board[6][7] = new WhiteKnight(6, 7, "White");
        board[7][7] = new WhiteRook(7, 7, "White");
    }


    public static Move getNextMove() {

        if(!running)
            Game.start();

        List<Coordinates> potentialMoves = piece.getPotentialMoves();

        Random r = new Random();
        int i = r.nextInt(potentialMoves.size());
        Coordinates xy = potentialMoves.get(i);

        int x1 = piece.field.getX();
        int y1 = piece.field.getY();

        int x2 = xy.getX();
        int y2 = xy.getY();

        Move move = new Move(x1, y1, x2, y2);
        board[x2][y2] = board[x1][y1];
        board[x1][y1] = null;
        piece = board[x2][y2];
        piece.field.setX(x2);
        piece.field.setY(y2);

        return move;
    }
}
