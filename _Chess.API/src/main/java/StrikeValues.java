import java.util.List;

public class StrikeValues {

    public StrikeValues(List<Coordinates> potentialStrikes, List<ChessPiece> threatenedPieces){
        this.potentialStrikes = potentialStrikes;
        this.threatenedPieces = threatenedPieces;
    }

    private List<Coordinates> potentialStrikes;
    private List<ChessPiece> threatenedPieces;

    public List<Coordinates> getPotentialStrikes() {
        return potentialStrikes;
    }

    public List<ChessPiece> getThreatenedPieces() {
        return threatenedPieces;
    }

    public void setPotentialStrikes(List<Coordinates> potentialStrikes) {
        this.potentialStrikes = potentialStrikes;
    }

    public void setThreatenedPieces(List<ChessPiece> threatenedPieces) {
        this.threatenedPieces = threatenedPieces;
    }
}
