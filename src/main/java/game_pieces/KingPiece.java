package game_pieces;

public class KingPiece extends GamePiece {

    public KingPiece() {
        super("src/Assets/Crown.PNG");
    }

    public KingPiece(String imagePath) {
        super(imagePath);
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.KING;
    }
}
