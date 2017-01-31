import org.junit.Test;

import static junit.framework.Assert.*;

public class HnefataflTests {
    private Hnefatafl game = new Hnefatafl();

    @Test
    public void testBoardDraws(){
        // Test that the board draws without error
        assertTrue(game.drawBoard());
    }
}
