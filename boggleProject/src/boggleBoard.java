/*==============================================================================
 Boggle Demo
 Wilbur Zhang
 March 26 2021
 Java - version 1.8.0

 GUI class contains all back-end logic for the boggle demo. When an instance is created, a new board is generated with given dimensions
 and can be used to check if any word is valid english/exists on the board.

 List of global variables:
 R - integer representing number of rows of the board
 C - integer representing number of columns of the board
 wordlist - hashset containing list of all valid english words
 board - 2d string array containing values of all dice on current board
 directions - arraylist of coord objects containing all possible directions to search for a word
================================================================================
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *  Refer to above for class description and global variable list
 *  @author Wilbur Zhang
 *  @version 1.0
 *  @since 2021-03-26
 */
public class boggleBoard {

    private static int R, C;
    private static HashSet<String> wordList = new HashSet <String> (110000);
    private static String [][] board;
    private static ArrayList <coord> directions;
    /**
     *  Represents a pair of numbers, one showing row movements and one showing column movements
     *  Used to describe all possible directions when searching.
     */
    private static class coord {
        int r, c;
        /**
         * Creates a coordinate pair with a specified row and column value.
         * @param r integer representing row value
         * @param c integer representing column value
         */
        public coord (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    /**
     *  Constructor initializes static level variables and creates a game board.
     *  @param r    integer representing number of rows on the board
     *  @param c    integer representing number of columns on the board
     */
    public boggleBoard (int r, int c) {
        R = r; C = c;
        board = new String [R][C];
        openFile(); generateBoard();
        directions = new ArrayList <coord> ();
        generateDir();
    }
    /**
     *  Generates all possible directions to search for a word. Each direction has a set of values for the row and col:
     *  -1  = up(row)/left(col)
     *  1   = down(row)/right(col)
     *  0   = no movement
     *
     *  List of Local Variables:
     *  i   loops through all directions for row </type int>
     *  j   loops through all directions for col </type int>
     */
    private void generateDir() {
        for(int i = -1; i < 2; i++) { //all directions for row
            for(int j = -1; j < 2; j++) //all directions for col
                if(i != 0 || j != 0) directions.add(new coord(i, j)); //adds direction as long as its not 0, 0 (which results in no movement)
        }
    }
    /**
     *  Reads each entry in the file of valid english words and transfers them into a hashset of String objects.
     *
     *  List of Local Variables:
     *  br  used to read in file </type BufferedReader>
     *  in  current string read by buffered reader </type String>
     *  E   exception thrown when file cannot be read </type Exception>
     */
    private void openFile () { //opens wordlist.txt file and transfers data into list
        try {
            BufferedReader br = new BufferedReader(new BufferedReader (new InputStreamReader(boggleBoard.class.getResourceAsStream("wordlist.txt"))));
            String in;
            while((in = br.readLine()) != null) wordList.add(in); //read in a word and store it into the list and stop when the end (null) is reached
        } catch (Exception E) {
            System.out.println("Failed to read wordlist.txt file. Make sure file is properly formatted and in the same folder as this program");
        }
    }
    /**
     *  Selects a random latin character for each of the 36 dice on the grid. RNG is weighted to favour more commonly used words.
     *  Some vowels are not weighted heavily due to their infrequent use.
     *  Refer to: www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html for most commonly used characters
     *
     *  List of Local Variables:
     *  bank    Contains all characters that can be generated. Duplicate chars are more commonly used thus have higher weight </type String>
     *  rand    A value from a random index within the bank </type String>
     */
    void generateBoard () {
        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                String bank = "EEARIOTNSLCUDPABCDEFGHIJKLMNOPQRSTUVQXYZ";
                String rand = Character.toString(bank.charAt((int) (Math.random() * bank.length()))); //gets a character from bank from a RNG'd index
                board[r][c] = rand; //set current dice on boggle board to this character
            }
        }
    }
    /**
     *  Recursively searches whether a queried word can be created from the characters on the board at a certain starting position & direction.
     *  @param r    integer representing row index of current character being inspected
     *  @param c    integer representing col index of current character being inspected
     *  @param dirR integer representing direction of the next row searched (-1 = up, 1 = down, 0 = no movement)
     *  @param dirC integer representing direction of the next col searched (-1 = up, 1 = down, 0 = no movement)
     *  @param val  string representing the word currently being searched for
     *  @return     boolean indicating whether the word can be created or not from the position & direction (true = possible & vice versa)
     */
    private boolean searchDir (int r, int c, int dirR, int dirC, String val) {
        //base case: last character is reached and matches grid
        if(val.length() == 1 && board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0)))) return true;
        //recursive case: check the next character if the current character matches grid and we aren't at an edge/corner
        if(board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0))) && r + dirR < R && r + dirR >= 0 && c + dirC < C && c + dirC >= 0)
            return searchDir(r + dirR, c + dirC, dirR, dirC, val.substring(1));
        //if none of those criteria are met, then our word cannot be created using our current position/direction
        return false;
    }
    /**
     *  Searches whether a queried word can be created from any position and direction. Checks every possible position/direction combination
     *  and calls the recursive search to find whether the word can be created from that combination.
     *
     *  List of Local Variables:
     *  r   loops through every row of the board </type int>
     *  c   loops through every column of the board </type int>
     *  dir loops through every possible direction direction (UDLR + diagonals) </type int>
     *  @param val  string representing the word currently being searched for
     *  @return     boolean indicating whether the word can be created or not (true = possible & vice versa)
     */
    boolean onBoard (String val){
        for(int r = 0; r < R; r++)
            for(int c = 0; c < C; c++)
                for(coord dir : directions)
                    if(searchDir(r, c, dir.r, dir.c, val)) return true; //we found the word at the current location & direction
        return false; //if all combinations fail, then query cannot be created using our boggle grid
    }
    /**
     *  Checks if a queried String is a valid english word.
     *  @param val  string representing the word currently being searched for
     *  @return     boolean indicating whether the word is english (true = valid & vise versa)
     */
    boolean isValid(String val) {
        return wordList.contains(val);
    }
    /**
     *  Returns the values of all 36 dice.
     *  @return  2D array containing all 36 dice values
     */
    String [][] getBoard () {
        return board;
    }
}
