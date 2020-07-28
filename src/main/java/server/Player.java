package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {

    private Socket clint;
    private String name;
    private String disk;

    // Either player 1 or 2
    private int playerID;

    private BufferedReader inputStream;
    private PrintWriter outputStream;

    // Boolean which is used to determine if the client/player is still active
    private boolean isActive;

    /**
     * Constructor
     * @param client the Client Socket
     * @param disk The Disk of the Player
     * @param pID The ID of the Player
     * @throws IOException
     */
    public Player(Socket client, String disk, int pID) throws IOException {

        this.clint =  client;

        inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outputStream = new PrintWriter(client.getOutputStream(),true);
        this.disk = disk;
        this.playerID = pID;
        this.isActive = true;
    }

    /**
     *
     * @param message the String message which the server will send to the Client
     */
    public void sendMessage(String message){
        outputStream.println(message);
    }

    /**
     * Request a String input from the Client
     * @return Returns the Input returned by the Client
     */
    public String getPlayerInput(){
        String input;

        try{
            input = inputStream.readLine();
            return input;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "ERROR_GETTING_USER_INPUT";
    }


    /**
     * Perform a check to determine if the clint is still connected
     * @return returns a boolean value of whether the client is connected or not
     */
    public boolean checkConnected(){
        return this.clint.isConnected();
    }

    /**
     * This method Shuts down resources of the Client and sets isActive to false
     * @throws IOException
     */
    public void shutDown() throws IOException{

        this.isActive = false;
        inputStream.close();
        outputStream.close();
    }

    // *** Getters & Setters ***
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisk() {
        return disk;
    }
    public void setIsActive(Boolean status) {
        this.isActive = status;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public int getPlayerID() { return playerID;   }

    // below were added to help test the class
    public void setInputStream(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }
}
