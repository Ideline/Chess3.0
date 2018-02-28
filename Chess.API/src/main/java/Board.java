public class Board {

    public static ChessPiece[][] board;

    public Board() {
        this.board = new ChessPiece[8][8];
    }


    public static ChessPiece[][] getBoard() {
        return board;
    }

    public static void fillBoard() {

        board = new ChessPiece[8][8];
        //TODO: Gör snyggare metod för att fylla brädet och lägg in värdet i resp konstruktor

        board[0][0] = new Rook(0, 0, "Black", 1);
        board[1][0] = new Knight(1, 0, "Black", 2);
        board[2][0] = new Bishop(2, 0, "Black", 3);
        board[3][0] = new Queen(3, 0, "Black", 4);
        board[4][0] = new King(4, 0, "Black", 5);
        board[5][0] = new Bishop(5, 0, "Black", 6);
        board[6][0] = new Knight(6, 0, "Black", 7);
        board[7][0] = new Rook(7, 0, "Black", 8);

        int currentID = 9;
        int y = 1;

        for (int x = 0; x < 8; x++) {

            board[x][y] = new BlackPawn(x, y, "Black", currentID);
            currentID++;
        }

//        board[0][1] = new BlackPawn(0, 1, "Black", 9);
//        board[1][1] = new BlackPawn(1, 1, "Black", 10);
//        board[2][1] = new BlackPawn(2, 1, "Black", 11);
//        board[3][1] = new BlackPawn(3, 1, "Black", 12);
//        board[4][1] = new BlackPawn(4, 1, "Black", 13);
//        board[5][1] = new BlackPawn(5, 1, "Black", 14);
//        board[6][1] = new BlackPawn(6, 1, "Black", 15);
//        board[7][1] = new BlackPawn(7, 1, "Black", 16);

        for(int i = 0; i < 4; i++) {
            y++;
            for (int x = 0; x < 8; x++) {
                board[x][y] = null;
            }
        }

        y++;
        for (int x = 0; x < 8; x++) {

            board[x][y] = new WhitePawn(x, y, "White", currentID);
            currentID++;
        }

//        board[0][2] = null;
//        board[1][2] = null;
//        board[2][2] = null;
//        board[3][2] = null;
//        board[4][2] = null;
//        board[5][2] = null;
//        board[6][2] = null;
//        board[7][2] = null;
//
//        board[0][3] = null;
//        board[1][3] = null;
//        board[2][3] = null;
//        board[3][3] = null;
//        board[4][3] = null;
//        board[5][3] = null;
//        board[6][3] = null;
//        board[7][3] = null;
//
//        board[0][4] = null;
//        board[1][4] = null;
//        board[2][4] = null;
//        board[3][4] = null;
//        board[4][4] = null;
//        board[5][4] = null;
//        board[6][4] = null;
//        board[7][4] = null;
//
//        board[0][5] = null;
//        board[1][5] = null;
//        board[2][5] = null;
//        board[3][5] = null;
//        board[4][5] = null;
//        board[5][5] = null;
//        board[6][5] = null;
//        board[7][5] = null;
//
//        board[0][6] = new WhitePawn(0, 6, "White", 17);
//        board[1][6] = new WhitePawn(1, 6, "White", 18);
//        board[2][6] = new WhitePawn(2, 6, "White", 19);
//        board[3][6] = new WhitePawn(3, 6, "White", 20);
//        board[4][6] = new WhitePawn(4, 6, "White", 21);
//        board[5][6] = new WhitePawn(5, 6, "White", 22);
//        board[6][6] = new WhitePawn(6, 6, "White", 23);
//        board[7][6] = new WhitePawn(7, 6, "White", 24);

        board[0][7] = new Rook(0, 7, "White", 25);
        board[1][7] = new Knight(1, 7, "White", 26);
        board[2][7] = new Bishop(2, 7, "White", 27);
        board[3][7] = new Queen(3, 7, "White", 28);
        board[4][7] = new King(4, 7, "White", 29);
        board[5][7] = new Bishop(5, 7, "White", 30);
        board[6][7] = new Knight(6, 7, "White", 31);
        board[7][7] = new Rook(7, 7, "White", 32);
    }

    synchronized public static void updateBoard(Move move) {

        if (!move.getFrom().matches(move.getTo())) {
            String getFrom = move.getFrom();
            String getTo = move.getTo();
            String sx1 = getFrom.substring(0, 1);
            String sy1 = getFrom.substring(1);
            String sx2 = getTo.substring(0, 1);
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
}
