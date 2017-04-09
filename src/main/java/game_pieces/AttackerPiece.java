package game_pieces;

public class AttackerPiece extends GamePiece {

    public AttackerPiece() {
        super("src/Assets/First Axe.png");
    }

    public AttackerPiece(String imagePath) {
        super(imagePath);
    }

    @Override
    public GamePieceType getType() {
        return GamePieceType.ATTACKER;
    }
}
