import static org.junit.Assert.*;
import javax.swing.JButton;
import org.junit.Test;
import org.junit.Before;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GameLogicTest{
    private Hnefatafl h;
	private GameLogic g;
    private static final int GAMEWIDTH = 11;
    private static final int GAMEHEIGHT = 11;

    @Before
    public void setUp(){
        h = new Hnefatafl();
        g = new GameLogic(GAMEWIDTH, GAMEHEIGHT);
    }

    //This test checks to see the method kingIsAtEdge return true if every button is a King
    @Test
    public void kingIsEverywhere(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    if((i == 0 && (j > 2 && j < 8)) || (i == 1 && (j == 5))){
                        //top black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if((j == 0 && (i > 2 && i < 8)) || (i == 5 && (j == 1))){
                        //left black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if((j == 10 && (i > 2 && i < 8)) || (i == 5 && (j == 9))){
                        //right black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if((i == 10 && (j > 2 && j < 8)) || (i == 9 && (j == 5))){
                        //bottom black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if(i == 5 && j == 5){
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if((i == 3 && j == 5) || (i == 4 && (j > 3 && j <7)) || (i == 5 && (j > 2 && j <8)) || (i == 6 && (j > 3 && j <7)) || (i == 7 && j == 5)){
                        //center white pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(kingIcon);

                    }
                }
            }
        }
        assertTrue(g.kingIsAtEdge(_buttons));
    }



    //This test checks to see the method kingIsAtEdge will return true if the whole top row is kings
    @Test
    public void kingIsAtTop(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    if((i == 0 )){
                        //top black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }

                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(emptyImageIcon);

                    }
                }
            }
        }
        assertTrue(g.kingIsAtEdge(_buttons));
    }

    //This test checks to see the method kingIsAtEdge will return true if the whole bottom row is kings
    @Test
    public void kingIsAtBot(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    if((i == 10 )){
                        //top black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }

                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(emptyImageIcon);

                    }
                }
            }
        }
        assertTrue(g.kingIsAtEdge(_buttons));
    }

    //This test checks to see the method kingIsAtEdge will return true if the whole left column is kings
    @Test
    public void kingIsAtLeft(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    if((j == 0 )){
                        //top black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }

                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(emptyImageIcon);

                    }
                }
            }
        }
        assertTrue(g.kingIsAtEdge(_buttons));
    }

    //This test checks to see the method kingIsAtEdge will return true if the whole right column is kings
    @Test
    public void kingIsAtRight(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    if((j == 10 )){
                        //top black pieces
                        _buttons[i][j] = new JButton(kingIcon);
                    }

                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(emptyImageIcon);

                    }
                }
            }
        }
        assertTrue(g.kingIsAtEdge(_buttons));
    }

    //This test checks to see the method kingIsAtEdge will return false if the whole right column is kings
    @Test
    public void kingIsNotOnEdge(){
        JButton[][] _buttons = new JButton[GAMEWIDTH][GAMEHEIGHT];

        ImageIcon defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");
        
        ImageIcon axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");
        
        ImageIcon kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");
        
        ImageIcon emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        for(int i=0; i<GAMEWIDTH; i++){
            for (int j=0; j<GAMEHEIGHT; j++) {
                if(_buttons[i][j] == null) {
                    // Make a new button in the array location with text "_"
                    _buttons[i][j] = new JButton(emptyImageIcon);                    
                }
            }
        }
        assertTrue(!g.kingIsAtEdge(_buttons));
    }
}