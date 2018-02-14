public class Threat {

    private ChessPiece threatenedPiece;
    private ChessPiece threat;
    private int threatenedPieceValue;
    private int threatValue;

    public Threat(ChessPiece threatenedPiece,  ChessPiece threat){
        this.threatenedPiece = threatenedPiece;
        this.threat = threat;
        this.threatenedPieceValue = threatenedPiece.value;
        this.threatValue = threat.value;
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

    public int getThreatValue() {
        return threatValue;
    }
}
