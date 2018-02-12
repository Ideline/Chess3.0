public interface IChessPieces {

    void setPotentialMoves();
    void move(int x, int y, int rightLeftOrStay, int upDownOrStay);
    void checkNextPotentialMoves(int moveX, int moveY);

}
