public class boggleGUI {

    public static void main(String[] args) {

        boggleBoard board = new boggleBoard();
        board.generateBoard();
        String [][] test = board.getBoard();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                System.out.print(test[i][j] + " ");
            }
            System.out.println();
        }
//            for(int i = 0; i < 6; i++) {
//                for(int j = 0; j < 6; j++) {
//                    boolean [][] vis = new boolean[board.length][board[i].length];
//                    String s = "";
//                    dfs(i, j, s, vis);
//                }
//            }
//            System.out.println(valid.toString());
    }

}
