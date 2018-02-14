public class Board {

    private ChessPiece[][] board;

    public Board() {
        this.board = new ChessPiece[8][8];
    }


    public ChessPiece[][] getBoard() {
        return board;
    }

    public void print(){
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                System.out.println(board[x][y]);
            }
        }
    }

    //    private ArrayList<Player> players;
//
//    public ArrayList<Player> getPlayers() {
//        return players;
//    }
//
//    public void addPlayer(Player player) {
//        this.players.add(player);
//    }
}
