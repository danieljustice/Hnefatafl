package game_pieces;

public class AttackerPiece extends GamePiece {

    public AttackerPiece() {
        this("src/Assets/First Axe.png");
    }

    public AttackerPiece(String imagePath) {
        super(imagePath);
        _icon.setDescription(GamePieceType.ATTACKER.toString());
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.ATTACKER;
    }
}
