package game_pieces;

public class DefenderPiece extends GamePiece {

    public DefenderPiece() {
        super("src/Assets/First Shield.png");
    }

    public DefenderPiece(String imagePath) {
        super(imagePath);
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.DEFENDER;
    }
}
