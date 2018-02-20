public class MoveOption {
    private ChessPiece piece;
    private MoveCoordinates mc;
    private int threatValue;


    public MoveOption(ChessPiece piece, MoveCoordinates mc, int threatValue){
        this.piece = piece;
        this.mc = mc;
        this.threatValue = threatValue;
    }

    public void setPiece(ChessPiece piece){
        this.piece = piece;
    }

    public ChessPiece getPiece(){
        return piece;
    }

    public void setMoveCoordinates (MoveCoordinates mc){
        this.mc = mc;
    }

    public MoveCoordinates getMoveCoordinates() {
        return mc;
    }
    public void setThreatValue(int threatValue){
        this.threatValue = threatValue;
    }

    public int getThreatValue(){
        return threatValue;
    }
}
