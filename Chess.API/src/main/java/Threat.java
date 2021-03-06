public class Threat {

    private ChessPiece threatenedPiece;
    private ChessPiece threat;
    private int threatenedPieceValue;
    private int threatsValue;

    public Threat(ChessPiece threatenedPiece,  ChessPiece threat){
        this.threatenedPiece = threatenedPiece;
        this.threat = threat;
        this.threatenedPieceValue = threatenedPiece.getValue();
        this.threatsValue = threat.getValue();
    }

    public ChessPiece getThreatenedPiece() {
        return threatenedPiece;
    }

    public ChessPiece getThreat() {
        return threat;
    }

    public int getThreatenedPieceValue() {
        return threatenedPieceValue;
    }

    public int getThreatsValue() {
        return threatsValue;
    }
}
