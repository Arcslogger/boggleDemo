import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class boggleBoard {

    private static int R, C;
    private static HashSet<String> wordList = new HashSet <String> (110000);
    private static String [][] board;
    private static ArrayList <coord> directions;

    private static class coord {
        int r, c;
        public coord (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    public boggleBoard (int r, int c) {
        R = r; C = c;
        board = new String [R][C];
        openFile(); generateBoard();
        directions = new ArrayList <coord> ();
        //generates a list of all directions to go in
        for(int i = -1; i < 2; i++) { //all possible row movements (-1 = up, 1 = down, 0 = no movement)
            for(int j = -1; j < 2; j++) //all possible col movements
                if(i != 0 || j != 0) directions.add(new coord(i, j)); //adds direction as long as its not 0, 0 (which results in no movement)
        }
    }
    private void openFile () { //opens wordlist.txt file and transfers data into list
        try {
            File wordlist = new File ("wordlist.txt"); //get file from path
            BufferedReader br = new BufferedReader(new FileReader(wordlist)); //create a buffered reader to read in the file
            String in; //current string read by buffered reader
            while((in = br.readLine()) != null) wordList.add(in); //read in a word and store it into the list and stop when the end (null) is reached
        } catch (Exception E) { //runs when an error occurs attempting to read file
            System.out.println("Failed to read wordlist.txt file. Make sure file is properly formatted and in the folder");
        }
    }
    void generateBoard () {
        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                //https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html
                //most commonly used letters are duplicated, with E having 3 instances as the most common letter
                String bank = "EEARIOTNSLCUDPABCDEFGHIJKLMNOPQRSTUVQXYZ";
                String rand = Character.toString(bank.charAt((int) (Math.random() * bank.length()))); //gets a character from bank from a RNG'd index
                board[r][c] = rand; //set current dice on boggle board to this character
            }
        }
    }
    private boolean searchDir (int r, int c, int dirR, int dirC, String val) {
        //base case: last character is reached and matches grid
        if(val.length() == 1 && board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0)))) return true;
        //recursive case: check the next character if the current character matches grid and we aren't at an edge/corner
        if(board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0))) && r + dirR < R && r + dirR >= 0 && c + dirC < C && c + dirC >= 0)
            return searchDir(r + dirR, c + dirC, dirR, dirC, val.substring(1));
        //if none of those criteria are met, then our word cannot be created using our current position/direction
        return false;
    }
    boolean onBoard (String val){
        for(int r = 0; r < R; r++) //loop through every row
            for(int c = 0; c < C; c++) //loop through ever column
                for(coord dir : directions) //try out every possible direction (UDLR + diagonals)
                    if(searchDir(r, c, dir.r, dir.c, val)) return true; //we found the word at the current location & direction
        return false; //if all combinations fail, then query cannot be created using our boggle grid
    }
    boolean isValid(String val) {
        return wordList.contains(val);
    }
    String [][] getBoard () {
        return board;
    }
}
