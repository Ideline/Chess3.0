public interface IChessPieces {

    void setPotentialMoves();
    void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean firstOrSecondMove);

}
