public interface IPawn {

    void setPotentialMoves();

    void firstMove(int x, int y, boolean nextMove);

    void nextMove(int x, int y, boolean nextMove);

    void strikeRight(int x, int y, boolean nextMove);

    void strikeLeft(int x, int y, boolean nextMove);
}
