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

    public boggleBoard (int r, int c) {
        R = r; C = c;
        board = new String [R][C];
        openFile(); generateBoard();
        directions = new ArrayList <coord> ();
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) if(i != 0 || j != 0) directions.add(new coord(i, j));
        }
    }
    private static class coord {
        int r, c;
        public coord (int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    private void openFile () { //opens wordlist.txt file and transfers data into list
        try {
            File wordlist = new File ("wordlist.txt");
            BufferedReader br = new BufferedReader(new FileReader(wordlist));
            String in;

            while((in = br.readLine()) != null) wordList.add(in); //read in a word and store it into the list and stop when the end (null) is reached
        } catch (Exception E) {
            System.out.println("Failed to read wordlist.txt file. Make sure file is properly formatted and the folder");
        }
    }
    private void generateBoard () {
        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                String rand = Character.toString((char) ((int) (Math.random() * 26) + 65));
                board[r][c] = rand;
            }
        }
    }
    private boolean searchDir (int r, int c, int dirR, int dirC, String val) {
        if(val.length() == 1 && board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0)))) return true;
        if(board[r][c].equalsIgnoreCase(Character.toString(val.charAt(0))) && r + dirR < R && r + dirR >= 0 && c + dirC < C && c + dirC >= 0)
            return searchDir(r + dirR, c + dirC, dirR, dirC, val.substring(1));
        return false;
    }
    boolean isValid (String val){
        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++)
                for(coord dir : directions)
                    if(searchDir(r, c, dir.r, dir.c, val) && wordList.contains(val.toLowerCase()))  return true;
        }
        return false;
    }
    String [][] getBoard () {
        return board;
    }
}
