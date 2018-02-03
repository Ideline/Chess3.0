public interface IPawn {

    void setPotentialMoves();

    void firstMove(int x, int y);

    void nextMove(int x, int y);

    void strikeRight(int x, int y);

    void strikeLeft(int x, int y);
}
