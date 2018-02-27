import java.util.List;

public class StrikeValues {

    public StrikeValues(List<Coordinate> potentialStrikes, List<ChessPiece> threatenedPieces){
        this.potentialStrikes = potentialStrikes;
        this.threatenedPieces = threatenedPieces;
    }

    private List<Coordinate> potentialStrikes;
    private List<ChessPiece> threatenedPieces;

    public List<Coordinate> getPotentialStrikes() {
        return potentialStrikes;
    }

    public List<ChessPiece> getThreatenedPieces() {
        return threatenedPieces;
    }

    public void setPotentialStrikes(List<Coordinate> potentialStrikes) {
        this.potentialStrikes = potentialStrikes;
    }

    public void setThreatenedPieces(List<ChessPiece> threatenedPieces) {
        this.threatenedPieces = threatenedPieces;
    }
}
