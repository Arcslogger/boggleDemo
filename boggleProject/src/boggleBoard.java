//an attempt to use DFS to find all possible valid words on a grid of randomly generated characters
//this test is ust

/*

PRE-COMPUTATION OF VALID WORDS (dfs x36)
for each of the 36 starting chars, perform DFSs

when the user chooses a word:
    keep track of all chars visited
    the new char is valid if:
        - char hasn't been visited
        - char is adjacent to at least one other char (check all adj in bound neighbors (3 min, 8 max)

once a full word is chosen:
    compare word against valid word list (use hashset for fastest .contains)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;

class boggleBoard {

    private static HashSet<String> valid = new HashSet<String>(), wordList = new HashSet <String> (110000);
    private static String [][] board = new String[6][6];

    public boggleBoard() {
        openFile();
    }
    void openFile () { //opens wordlist.txt file and transfers data into list
        try {
            File wordlist = new File ("wordlist.txt");
            BufferedReader br = new BufferedReader(new FileReader(wordlist));
            String in;

            while((in = br.readLine()) != null) wordList.add(in); //read in a word and store it into the list and stop when the end (null) is reached
        } catch (Exception E) {
            System.out.println("Failed to read wordlist.txt file. Make sure file is properly formatted and the folder");
        }
    }
    void generateBoard () {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                String rand = Character.toString((char) ((int) (Math.random() * 26) + 65));
                board[i][j] = rand;
            }
        }
        findValid();
    }
    void findValid () {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                boolean [][] vis = new boolean[board.length][board[i].length];
                String s = "";
                dfs(i, j, s, vis);
            }
        }
    }
    void dfs (int r, int c, String total, boolean [][] vis) {
        total += board[r][c];
        vis[r][c] = true;
        if(wordList.contains(total.toLowerCase())) valid.add(total);
        for(int i = Math.max(r - 1, 0); i < Math.min(r + 2, vis.length); i++) {
            for(int j = Math.max(c - 1, 0); j < Math.min(c + 2, vis[0].length); j++) {
                if(!vis[i][j]) dfs(i, j, total, vis);
            }
        }
    }
    boolean isValid (String s) {
        return valid.contains(s);
    }
    String [][] getBoard () {
        return board;
    }
}
