import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class boggleWindow extends JFrame {

    private static JFrame frame; //frame where everything is laid out on
    private static JPanel uiPanel, boardPanel;
    private static JTextField input;
    private static JButton check;

    public boggleWindow(boggleBoard board) {

        String [][] map = board.getBoard();

        frame = new JFrame("Boggle Game"); //initializing window and configuring properties
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(0, 2));
        uiPanel = new JPanel(new BorderLayout());
        boardPanel = new JPanel(); //new GridLayout(map.length, map[0].length)

        input = new JTextField(""); //textbox for entering queries

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(board.isValid(input.getText()));
                input.setText("");
            }
        });

        //boardPanel.add(new boardDice());
        boardPanel.add(new boardDice(250, 100, map.length, map[0].length, map));
        uiPanel.add(input);

//        for(int i = 0; i < 6; i++) {
//            for(int j = 0; j < 6; j++) {
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }

        frame.getContentPane().add(uiPanel);
        frame.getContentPane().add(boardPanel);


        frame.setVisible(true);
    }

    private static class boardDice extends JComponent {
        int x, y, rowNum, colNum;
        String [][] labels;
        boardDice (int x, int y, int rowNum, int colNum, String [][] labels) {
            setPreferredSize(frame.getSize());
            this.x = x;
            this.y = y;
            this.rowNum = rowNum;
            this.colNum = colNum;
            this.labels = labels;
        }
        @Override
        public void paintComponent(Graphics g) {
            for(int r = 0; r < rowNum; r++) {
                for(int c = 0; c < colNum; c++) {
                    g.drawRoundRect(x + 50*r, y + 50*c, 50, 50, 10, 10);
                    Font font = new Font("Helvetica", Font.BOLD, 18);
                    g.setFont(font);
                    g.drawString(labels[r][c], x + 50*r + 20, y + 50*c + 30);
                }
            }
        }
    }


    public static void main(String[] args) {
        boggleBoard board = new boggleBoard(6, 6);
        new boggleWindow(board);
    }

}
