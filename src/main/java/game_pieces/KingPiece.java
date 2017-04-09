package game_pieces;

public class KingPiece extends GamePiece {

    public KingPiece() {
        this("src/Assets/Crown.PNG");
    }

    public KingPiece(String imagePath) {
        super(imagePath);
        _icon.setDescription(GamePieceType.KING.toString());
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.KING;
    }
}
