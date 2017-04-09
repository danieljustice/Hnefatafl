package game_pieces;

public class EmptyPiece extends GamePiece {

    public EmptyPiece() {
        super("src/Assets/empty.png");
    }

    public EmptyPiece(String imagePath) {
        super(imagePath);
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.EMPTY;
    }
}
