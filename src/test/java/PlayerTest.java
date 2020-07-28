import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import server.Player;
import utils.Colour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    private Socket client;

    private BufferedReader inputStream;
    private PrintWriter outputStream;

    // Need to mock the Socket input and output streams
    @Mock
    private OutputStream mockedClientOutputStream;
    @Mock
    private InputStream mockedInputStream;

    // Service to test
    private Player player;

    private static final String playerDisk = Colour.YELLOW + "X" + Colour.RESET;

    @Before
    public void setup() {
        try {

            client = mock(Socket.class);

            when(client.getInputStream()).thenReturn(mockedInputStream);
            when(client.getOutputStream()).thenReturn(mockedClientOutputStream);

            inputStream = org.mockito.Mockito.mock(BufferedReader.class);
            outputStream = new PrintWriter(client.getOutputStream(),true);

            player = new Player(client,playerDisk,1);

            player.setInputStream(inputStream);
            player.setOutputStream(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkPlayerSetUp() {

        assertEquals(player.getPlayerID(),1);
        assertEquals(player.getIsActive(),true);
        assertEquals(player.getDisk(),playerDisk);

        player.setName("Example");
        assertEquals("Example",player.getName());
    }

    @Test
    public void checkPlayerInput() {

        try {
            when(inputStream.readLine()).thenReturn("5");
            assertEquals("5", player.getPlayerInput());

        } catch (IOException e) {
            assertEquals("IOException was not returned",1,2);
        }

    }

    @Test
    public void checkShutDown() {
        try {

            assertEquals(true, player.getIsActive());
            player.shutDown();
            assertEquals(false, player.getIsActive());

        } catch (IOException e) {
            assertEquals("IOException was not returned",1,2);
        }
    }

}
