public class Player {
    public String name;

    public Player(String name) {
        this.name = name;
    }

    public static void changePlayerTurn(){

        if (MoveHandler.getPlayerTurn() == PlayerTurn.WHITE) {
            MoveHandler.setPlayerTurn(PlayerTurn.BLACK);
        } else {
            MoveHandler.setPlayerTurn(PlayerTurn.WHITE);
        }
    }
}
