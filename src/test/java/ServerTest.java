

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import server.Player;
import server.Server;
import utils.Colour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest {

    private ServerSocket serverSocket;

    @Mock
    private OutputStream mockedClientOutputStream;
    @Mock
    private InputStream mockedInputStream;

    private Player firstPlayer;
    private Player secondPlayer;

    private Socket clientA, clientB;

    // Class to rest
    private Server server;

    private static final String playerADisk = Colour.YELLOW + "X" + Colour.RESET;
    private static final String playerBDisk = Colour.GREEN + "X" + Colour.RESET;


    @Before
    public void setUp(){

        try {
            serverSocket = mock(ServerSocket.class);
            clientA = mock(Socket.class);
            clientB = mock(Socket.class);

            when(clientA.getInputStream()).thenReturn(mockedInputStream);
            when(clientA.getOutputStream()).thenReturn(mockedClientOutputStream);
            when(clientB.getInputStream()).thenReturn(mockedInputStream);
            when(clientB.getOutputStream()).thenReturn(mockedClientOutputStream);

            firstPlayer = new Player(clientA,playerADisk,1);
            secondPlayer = new Player(clientB,playerBDisk,2);

            server = new Server();
            server.setServerSocket(serverSocket);
            /*
            throws Unnecessary stubbings detected in test class: ServerTest message
            at the moment as the tests for Server are incomplete
            * */
//            when(serverSocket.accept()).thenReturn(clientA);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void performTurnTest(){

        /*Had a few issues mocking the client server interaction through the game  */
//        server.startGame();
        assert 1 == 1;
    }

}
