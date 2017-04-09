package game_pieces;

public class DefenderPiece extends GamePiece {

    public DefenderPiece() {
        this("src/Assets/First Shield.png");
    }

    public DefenderPiece(String imagePath) {
        super(imagePath);
        _icon.setDescription(GamePieceType.DEFENDER.toString());
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.DEFENDER;
    }
}
