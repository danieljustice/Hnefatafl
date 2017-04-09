package game_pieces;

public class EmptyPiece extends GamePiece {

    public EmptyPiece() {
        this("src/Assets/empty.png");
    }

    public EmptyPiece(String imagePath) {
        super(imagePath);
        _icon.setDescription(GamePieceType.EMPTY.toString());
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.EMPTY;
    }
}
