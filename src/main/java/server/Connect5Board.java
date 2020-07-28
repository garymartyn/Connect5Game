package server;

import java.util.Arrays;

/**
 * Connect5Board Class
 * Contains all method and parameters used to interact with the board
 */
public class Connect5Board{

    private String[][] BOARD;
    private static final int NUMBER_OF_ROWS = 6;
    private static final int NUMBER_OF_COLUMNS = 9;
    // use an use an empty String as the initial square value to keep board shape consistent for the pint outs
    // to the Players
    private String EMPTY_SQUARE = " ";

    // keep cound of the number of disks on the board so we can tell if the board becomes full
    private int NUMBER_OF_DISKS_ON_BOARD = 0;
    // number of disks that need to connect to win the game
    private static final int WINNING_CONNECTION = 5;

    // Store row and column of last disk added to the board to help with checking if the board is won
    private int lastDiskRow = 0;
    private int lastDiskColumn = 0;
    private String lastDiskUsed = "";

    /**
     * Constructor
     */
    public Connect5Board(){
        setUp();
    }

    /**
     * This method setus up the a new board
     * @return returns the initialized board in the format of a 2D String array
     */
    public String[][] setUp(){
        BOARD = new String[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        initializeBoard();
        return BOARD;
    }

    /**
     * For each column of each row fill with the EMPTY_SQUARE value
     */
    public void initializeBoard(){
        for(String[] row : BOARD){
            Arrays.fill(row, EMPTY_SQUARE);
        }
    }

    /**
     * The Drop Disk method, this is used to drop a disk on the board if the
     * Column entered is available
     * @param column The column where the disk should be dropped
     * @param disk The Players disk which will be dropped on the board
     */
    public void dropDisk(int column, String disk){
        int normalizedColumn = (column-1);

        for(int rowId = (NUMBER_OF_ROWS-1); rowId >=0; rowId --){
            if(BOARD[rowId][normalizedColumn].equals(EMPTY_SQUARE)){
                BOARD[rowId][normalizedColumn] = disk;
                this.lastDiskUsed = disk;
                this.lastDiskColumn = normalizedColumn;
                this.lastDiskRow = rowId;
                NUMBER_OF_DISKS_ON_BOARD++;
                break;
            }
        }
    }

    /**
     * Perform a series of checks on the board to determine if the game is won
     * @return returns a boolean value of whether the game is won or not
     */
    public boolean checkGameWon(){
        return horizontalWinCheck() || verticalWinCheck() || diagonalWinCheck();
    }


    /**
     * Performs a check on the last row where a disk was dropped to see if there
     * are 5 consecutive disks matching the last disk added to the board
     * @return returns true if the game was won after checking horizontally
     */
    private boolean horizontalWinCheck(){

        String diskToCheck = lastDiskUsed;
        int connectedDiskCount = 0;

        for(String colVal : BOARD[lastDiskRow]){
            if (colVal.equals(diskToCheck)){
                connectedDiskCount++;
                if (connectedDiskCount == WINNING_CONNECTION){
                    return true;
                }
            }else {
                connectedDiskCount = 0;
            }
        }

        return false;
    }


    /**
     * Performs a check on the last column where a disk was dropped to see if there
     * are 5 consecutive disks matching the last disk added to the board
     * @return returns true if the game was won after checking Vertically
     */
    private boolean verticalWinCheck(){

        String diskToCheck = lastDiskUsed;
        int connectedDiskCount = 0;
        for (int rowIdx = NUMBER_OF_ROWS-1; rowIdx >=0; rowIdx --){

            if(diskToCheck.equals(BOARD[rowIdx][lastDiskColumn])){
                connectedDiskCount++;
                if(connectedDiskCount == WINNING_CONNECTION){
                    return true;
                }
            }else {
                connectedDiskCount = 0;
            }
        }

        return false;
    }


    /**
     * Perform a series of checks starting at the location of where the last disk dropped
     * We check in NorthEasterly (NE), SouthWesterly(SW),NorthWesternly(NW) & SouthEasternly(SE) directions.
     * The method performs a check to see if there are 5 disks in a row (depending on board position)
     * @return returns true if the game was solved in a diagonal direction
     */
    private boolean diagonalWinCheck(){

        String diskToCheck = lastDiskUsed;

        return (checkDiagonalNE(diskToCheck) || checkDiagonalSW(diskToCheck) || checkDiagonalNW(diskToCheck)  || checkDiagonalSE(diskToCheck));
    }

    /**
     * Check if the Game was solved in a NorthEasterly diagonal direction
     * @param diskToCheck The Player Disk to check reached the winning combination of connections
     * @return returns true if the game was solved in a NorthEasterly diagonal direction
     */
    private boolean checkDiagonalNE(String diskToCheck){
        int connectedDiskCount = 1; // start checking from last know location of player disk to start count from 1

        for (int i = 1; i < WINNING_CONNECTION ; i++){ // R- C+
            if ((lastDiskRow-i) >=0 && (lastDiskColumn+i) < NUMBER_OF_COLUMNS){
                if (diskToCheck.equals(BOARD[lastDiskRow-i][lastDiskColumn+i] )){
                    connectedDiskCount++;
                }
            }
        }

        return (connectedDiskCount == WINNING_CONNECTION);
    }

    /**
     * Check if the Game was solved in a SouthWesternly diagonal direction
     * @param diskToCheck The Player Disk to check reached the winning combination of connections
     * @return returns true if the game was solved in a SouthWesternly diagonal direction
     */
    private boolean checkDiagonalSW(String diskToCheck){
        int connectedDiskCount = 1; // start checking from last know location of player disk to start count from 1

        for (int i = 1; i < WINNING_CONNECTION ; i++){ // R+ C-
            if ((lastDiskRow+i) < NUMBER_OF_ROWS && (lastDiskColumn-i) >= 0){ // check if next position is on board
                if (diskToCheck.equals(BOARD[lastDiskRow+i][lastDiskColumn-i] )){
                    connectedDiskCount++;
                }
            }
        }

        return (connectedDiskCount == WINNING_CONNECTION);
    }

    /**
     * Check if the Game was solved in a NorthWesternly diagonal direction
     * @param diskToCheck The Player Disk to check reached the winning combination of connections
     * @return returns true if the game was solved in a NorthWesternly diagonal direction
     */
    private boolean checkDiagonalNW(String diskToCheck){
        int connectedDiskCount = 1; // start checking from last know location of player disk to start count from 1

        for (int i = 1; i < WINNING_CONNECTION ; i++){ // R- C-
            if ((lastDiskRow-i) >= 0 && (lastDiskColumn-i) >= 0){ // check if next position is on board
                if (diskToCheck.equals(BOARD[lastDiskRow-i][lastDiskColumn-i] )){
                    connectedDiskCount++;
                }
            }
        }

        return (connectedDiskCount == WINNING_CONNECTION);
    }

    /**
     * Check if the Game was solved in a SouthEasternly diagonal direction
     * @param diskToCheck The Player Disk to check reached the winning combination of connections
     * @return returns true if the game was solved in a SouthEasternly diagonal direction
     */
    private boolean checkDiagonalSE(String diskToCheck){
        int connectedDiskCount = 1; // start checking from last know location of player disk to start count from 1

        for (int i = 1; i < WINNING_CONNECTION ; i++){ // R+ C+
            if ((lastDiskRow+i) < NUMBER_OF_ROWS && (lastDiskColumn+i) < NUMBER_OF_COLUMNS){ // check if next position is on board
                if (diskToCheck.equals(BOARD[lastDiskRow+i][lastDiskColumn+i] )){
                    connectedDiskCount++;
                }
            }
        }

        return (connectedDiskCount == WINNING_CONNECTION);
    }

    /**
     * This method generates the board in a presentable fashion to be returned to the user
     * It is in the format (plus any player markers added)
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     *  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
     * @return Returns the current game board representation in String form
     */
    @Override
    public String toString(){
        String toReturn = "";
        int rowNumber = 1;
        toReturn += "\n";

        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            rowNumber++;
            for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
                toReturn += (" [" + BOARD[row][column] + "]");
            }
            toReturn += "\n";
        }
        toReturn += " ";
        for (int columnNumber = 1; columnNumber < NUMBER_OF_COLUMNS + 1; columnNumber++) {
            toReturn += (" " + (columnNumber) + "  ");
        }
        toReturn += "\n";

        return toReturn;
    }


    /* *** Getters & Setters *** */

    public void setLastDiskRow(int lastDiskRow) {
        lastDiskRow = lastDiskRow;
    }


    public void setLastDiskColumn(int lastDiskColumn) {
        lastDiskColumn = lastDiskColumn;
    }

    public int getNumberOfDisksOnBoard() {
        return this.NUMBER_OF_DISKS_ON_BOARD;
    }

    public int getTieCondition(){
        return (this.NUMBER_OF_COLUMNS * this.NUMBER_OF_ROWS);
    }


    public String getLastDiskUsed() {
        return this.lastDiskUsed;
    }

    public void setLastDiskUsed(String lastDiskUsed) {
        this.lastDiskUsed = lastDiskUsed;
    }


    public int getBoardHeight(){
        return this.NUMBER_OF_ROWS;
    }

    public int getBoardWidth(){
        return this.NUMBER_OF_COLUMNS;
    }

    public String[][] getBoard() {
        return BOARD;
    }

}
