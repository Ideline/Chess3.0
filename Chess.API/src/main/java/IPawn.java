public interface IPawn {

    void setPotentialMoves();

    void firstMove();

    void move(int x, int y, int rightLeftOrStay, int upDownOrStay, boolean nextMove, boolean strikeOrMove);

    public void checkNextPotentialMoves(int x, int y);
}
