package interfaces;

import javax.swing.ImageIcon;
import game_pieces.GamePieceType;

public interface GamePieceInterface {

    public GamePieceType getType();

    public ImageIcon getIcon();

    public void setIcon(String imagePath);
}
