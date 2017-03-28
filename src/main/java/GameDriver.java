import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GameDriver{
	//This class's sole purpose is to provide a main method to run the Hnefatafl game

	 /**
     * Main method
     */
    public static void main(String[] args) {
        HnefataflGame hGame = new HnefataflGame();
       
       //hgame - set up game
        JFrame frame = hGame.createGameFrame("Hnefatafl");
        JToolBar tool =  hGame.createToolBar();
        JPanel panel =  hGame.createBackground(backgroundImage);

        frame.add(tool);
        fram.add(panel);


        Hnefatafl game = new Hnefatafl();
        game.drawBoard();
    }
}