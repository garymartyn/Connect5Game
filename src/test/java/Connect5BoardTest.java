import org.junit.Before;
import org.junit.Test;
import server.Connect5Board;

import utils.Colour;
import static org.junit.Assert.assertEquals;

public class Connect5BoardTest {
    private Connect5Board board;

    private int numRows = 6;
    private int numColumns = 9;

    private String playerADisk = Colour.YELLOW + "X" + Colour.RESET;
    private String playerBDisk = Colour.RED + "O" + Colour.RESET;

    private String EMPTY_SQUARE = " ";

    @Before
    public void setup() {
        board = new Connect5Board();
        board.setUp();

    }

    @Test
    public void checkBoardSetUp() {

        String[][] testBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
        };

        assertEquals("its not true that the board has correct number of columns", 9, board.getBoardWidth());
        assertEquals("its not true that the board has correct number of rows", 6, board.getBoardHeight());


        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                assertEquals("its not true that values at position[" + i + "][" + j + "] match", testBoard[i][j], board.getBoard()[i][j]);
            }
        }
    }

    @Test
    public void testDropDisk() {

        board.dropDisk(1, playerADisk);
        assertEquals(playerADisk, board.getBoard()[5][0]);

        board.dropDisk(1, playerBDisk);
        assertEquals(playerBDisk, board.getBoard()[4][0]);
    }

    @Test
    public void checkColour() {

        String marker = Colour.YELLOW + "X" + Colour.RESET;

        board.dropDisk(1, playerADisk);
        assertEquals(marker, board.getBoard()[5][0]);
    }

    /** e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [X] [O] [X] [X] [X] [X] [X]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonHorizontal() {

        board.dropDisk(1, playerADisk);

        board.dropDisk(3, playerADisk);
        board.dropDisk(4, playerBDisk);

        board.dropDisk(5, playerADisk);
        board.dropDisk(6, playerADisk);
        board.dropDisk(7, playerADisk);
        board.dropDisk(8, playerADisk);
        board.dropDisk(9, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }

    /**
     * e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonVertical() {

        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }


    /** e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [X] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [X] [X] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [X] [X] [X] [ ] [ ] [ ] [ ]
     *  [ ] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonDiagonal_NE_Direction() {

        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);

        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);

        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);

        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);

        board.dropDisk(1, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }

    /** e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [X] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [X] [X] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [X] [X] [X] [ ] [ ] [ ] [ ]
     *  [ ] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonDiagonal_SW_Direction() {

        board.dropDisk(1, playerADisk);

        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);

        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);

        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);

        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);
        board.dropDisk(5, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }

    /** e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonDiagonal_NW_Direction() {

        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);

        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);

        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);

        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);

        board.dropDisk(5, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }

    /** e.g.
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [ ] [ ] [ ] [ ] [ ]
     *  [X] [X] [X] [X] [X] [ ] [ ] [ ] [ ]
     *   1   2   3   4   5   6   7   8   9
     */
    @Test
    public void checkGameWonDiagonal_SE_Direction() {

        board.dropDisk(5, playerADisk);

        board.dropDisk(4, playerADisk);
        board.dropDisk(4, playerADisk);

        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);
        board.dropDisk(3, playerADisk);

        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);
        board.dropDisk(2, playerADisk);

        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);
        board.dropDisk(1, playerADisk);

        assertEquals("the game was not won as expected", true, board.checkGameWon());
    }

}