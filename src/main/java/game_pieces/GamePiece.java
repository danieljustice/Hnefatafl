package game_pieces;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import org.omg.CORBA.SystemException;

public class GamePiece extends JButton implements interfaces.GamePieceInterface {
    protected ImageIcon _icon;

    public GamePiece(String imagePath) {
        setIcon(imagePath);
    }

    public void setIcon(String imagePath) {
        try {
            this._icon = new ImageIcon(ImageIO.read(new File(imagePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GamePieceType getType() {
        return GamePieceType.DEFAULT;
    }

    public ImageIcon getIcon() {
        return _icon;
    }
}
