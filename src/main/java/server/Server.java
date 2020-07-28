package server;

import utils.Colour;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket serverSocket;

    private final String playerADisk = Colour.YELLOW + "X" + Colour.RESET;
    private final String playerBDisk = Colour.RED + "O" + Colour.RESET;

    private Player firstPlayer,secondPlayer;

    // boolean used to determine which players turn it is, if true its player A's turn
    private boolean playerATurn = true;

    private final String PLAYER_DISCONNECTED = "The other player got disconnected\nJoin again to find a new challenger!";
    private final String ENTER_NAME = "\n\nPlease Enter Name: ";
    private final String GAME_INSTRUCTIONS = "\n*** ****** ***\n"
            + "How to play:\n"
            + "\tEnter a number between 1 - 9 and your disk drop on that column\n"
            + "\tTo Win the Game you must try to connect 5 tokens\n"
            + "\tThis can be vertically, horizontally or diagonally\n"
            + "\tTo Quit the game type 'Quit;\n"
            + "\t\tGood Luck!!\n";
    private final String SHUTDOWN_MESSAGE = "\n\nThank you for playing Connect5\nWe hope to see you return soon\n";


    /**
     * Constructor
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(7080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        System.out.println("\nWaiting for players to join game\n");


        try{
            /*#### Accept the Two Client connections ###*/

            // Get the first Client/Player
            firstPlayer = new Player(serverSocket.accept(), playerADisk, 1);
            firstPlayer.sendMessage("Connected Successfully! \nWaiting for another player to join the game...");

            System.out.println("Player 1 connected");

            // Get the second Client/Player
            secondPlayer = new Player(serverSocket.accept(), playerBDisk, 2);
            System.out.println("Player 2 connected!");
            secondPlayer.sendMessage("Connected Successfully! \nStarting Game!");
            firstPlayer.sendMessage("Starting Game!");

            System.out.println("Setting up Game");
            System.out.println("Waiting for players to enter names");

            getPlayerNames();

            // Initiate the Connect5Board
            Connect5Board connect5Board = new Connect5Board();

            sendToPlayers("The Game is about to start");
            sendToPlayers(GAME_INSTRUCTIONS);
            sendToPlayers("\n" + connect5Board);


            /**
             * The Game loop
             * Continue to get entries until the game is won, the board is full
             * or a player disconnects from the game
             //             */
            while (!connect5Board.checkGameWon()) {

                if(playerATurn){
                    performTurn(connect5Board, firstPlayer, secondPlayer);
                } else{
                    performTurn(connect5Board, secondPlayer, firstPlayer);
                }

                // Perform a check to see if both players are still connected
                if (!firstPlayer.checkConnected() || !secondPlayer.checkConnected()){
                    System.out.println("A player has disconnected from the game");
                    break; // exit the game loop
                }

                // Check if the game board if full
                checkBoardFull(connect5Board);

                // swap turns & send updated board to players
                playerATurn = !playerATurn;
                sendToPlayers("\n\n\n" + connect5Board.toString());
            }

            if(!firstPlayer.getIsActive()){
                secondPlayer.sendMessage(PLAYER_DISCONNECTED);
            }
            if(!secondPlayer.getIsActive()){
                firstPlayer.sendMessage(PLAYER_DISCONNECTED);
            }

            // Determine the winner of the game
            endGame(connect5Board);


        }catch(IOException e){
            e.printStackTrace();
        }finally {
            // Close resources if they are still open
            try{
                firstPlayer.shutDown();
                secondPlayer.shutDown();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * Sends a message to both players connected
     * @param message
     */
    private void sendToPlayers(String message){
        firstPlayer.sendMessage(message);
        secondPlayer.sendMessage(message);
    }


    /**
     * Check if the game board is full of disks
     * If so the result if a Tie
     * @param board the Server.Server.GameBoardInterface
     */
    private void checkBoardFull(Connect5Board board){
        if(board.getNumberOfDisksOnBoard() >= board.getTieCondition()){
            sendToPlayers("\n\nResult is a Tie!!\n\n" + board.toString() + "\n\n");
            System.exit(0);
        }
    }

    /**
     *
     */
    private void getPlayerNames(){
        firstPlayer.sendMessage(ENTER_NAME);
        secondPlayer.sendMessage("Waiting for player 1 details");

        String playerAName = "";
        String playerBName = "";

        playerAName = firstPlayer.getPlayerInput();
        firstPlayer.sendMessage("Welcome to the game " + playerAName + " !!");

        secondPlayer.sendMessage(ENTER_NAME);
        playerBName = secondPlayer.getPlayerInput();
        firstPlayer.sendMessage("Waiting for player 2 Details");
        secondPlayer.sendMessage("Welcome to the game " + playerBName + " !!");

        firstPlayer.setName(playerAName);
        secondPlayer.setName(playerBName);

    }


    /**
     * Determine the winner of the Game and broadcast the message to both players
     * @param connect5Board the Connect5Board
     */
    private void endGame(Connect5Board connect5Board){
        // Find the winner of the game and send message to each players
        sendToPlayers("\n\n\n\n\n\n\n" + connect5Board.toString() + "\n\n");
        String winner = connect5Board.getLastDiskUsed().equals(firstPlayer.getDisk())  ? firstPlayer.getName() : secondPlayer.getName();
        sendToPlayers("Game Complete, Congratulations to the Winner : " + winner + "!!\n\n");
    }

    /**
     * If we detect a player wants to quit the game, notify each player and set the
     *  quitting players isActive variable to false
     * @param quitter the Player which has decided to quit the game by entering 'quit'
     * @param otherPlayer the other Player left playing the game
     */
    private void playerQuit(Player quitter, Player otherPlayer){

        quitter.sendMessage("You Have Chosen to Leave the Game\nPlay again soon!");
        quitter.setIsActive(false);
        otherPlayer.sendMessage("The Other player has left the game");
    }

    /**
     * This method broadcasts a shutdown message to each player and outputs to the
     * server console that the game will shut down, then Stops the game
     */
    private void shutdownGame(){
        sendToPlayers(SHUTDOWN_MESSAGE);
        System.out.println("The Game is about to shut down");
        System.exit(0);
    }


    /**
     * this method deals with the interactions of a Player dropping a disk on the board.
     * It handles aking the player for input, handling the input (quit or a column input)
     * @param connect5Board the Connect5Board
     * @param playingPlayer The player who is currently playing the game
     * @param waitingPlayer The other player who is waiting to make their move next
     * @throws IOException an IO Exception which could be generated from interacting with the ClientSocket(Player)
     */
    private void performTurn(Connect5Board connect5Board, Player playingPlayer, Player waitingPlayer) throws  IOException{
        int inputColumn = 1;
        boolean validInput = false;

        waitingPlayer.sendMessage("Waiting for player 1...");
        playingPlayer.sendMessage("It's your turn " + playingPlayer.getName() + ", please enter a column (1-9): ");

        while (!validInput) {
            try {

                String inputString = playingPlayer.getPlayerInput();
                if ("QUIT".equalsIgnoreCase(inputString.trim())){
                    playerQuit(playingPlayer, waitingPlayer);
                    shutdownGame();
                }
                inputColumn = Integer.parseInt(inputString.trim());
                if (inputColumn < 1 || inputColumn > 9 || !connect5Board.getBoard()[0][inputColumn - 1].equals(" ")){
                    throw new NumberFormatException();
                }
                validInput = true;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                playingPlayer.sendMessage("Invalid input\nTry again: ");

            }
        }
        connect5Board.dropDisk(inputColumn, playingPlayer.getDisk());
    }

    /**
     * Main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args){
        Server server = new Server();
        server.startGame();
    }


    // used for testing as I had some issues mocking the sockets & input,output streams
    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setFirstPlayer(Player player) {
        this.firstPlayer = player;
    }

    public void setSecondPlayer(Player player) {
        this.secondPlayer = player;
    }

    public Player getFirstPlayer() {
        return this.firstPlayer;
    }

    public Player getSecondPlayer() {
        return this.secondPlayer;
    }
}