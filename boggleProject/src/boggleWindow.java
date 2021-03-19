import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class boggleWindow extends JFrame {

    private static JFrame frame; //frame where everything is laid out on
    private static JPanel uiPanel, boardPanel, buttonPanel;
    private static JTextField input;
    private static JButton scramble, quit;
    private static JLabel title, isValid;

    public boggleWindow(boggleBoard board) {

        frame = new JFrame("Boggle Assignment"); //initializing window and configuring properties
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(0, 2));

        uiPanel = new JPanel(new BorderLayout(20, 20));
        boardPanel = new JPanel(new BorderLayout(20, 20)); //new GridLayout(map.length, map[0].length)
        buttonPanel = new JPanel(new FlowLayout());
        uiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 70));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

        input = new JTextField("Enter here (press enter to check word)"); //text box for entering queries
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = board.isValid(input.getText());
                isValid.setText(flag ? "Valid" : "Invalid");
                boardPanel.revalidate();
                System.out.println(flag);
                input.setText("");
            }
        });
        scramble = new JButton("Scramble board");
        scramble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.generateBoard();
                boardPanel.add(new boardDice(5, 5, board.getBoard()), BorderLayout.NORTH);
                input.setText("");
                isValid.setText("");
                boardPanel.revalidate();
            }
        });
        title = new JLabel("A Boggling Game");
        title.setFont(new Font("Helvetica", Font.BOLD, 32));
        isValid = new JLabel("");

        buttonPanel.add(scramble);
        boardPanel.add(new boardDice(5, 5, board.getBoard()), BorderLayout.NORTH);
        boardPanel.add(isValid, BorderLayout.CENTER);
        uiPanel.add(title, BorderLayout.NORTH);
        uiPanel.add(input, BorderLayout.CENTER);
        uiPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(uiPanel);
        frame.getContentPane().add(boardPanel);
        frame.setVisible(true);
    }

    private static class boardDice extends JComponent {
        int x, y, rowNum, colNum;
        String [][] labels;
        boardDice (int x, int y, String [][] labels) {
            setPreferredSize(new Dimension(310, 310));
            this.x = x;
            this.y = y;
            this.labels = labels;
            rowNum = labels.length;
            colNum = labels[0].length;
        }
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //blue border
            g2.setColor(new Color(50, 102, 168));
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(x-2, y-2, 304, 304, 15, 15);
            //set the font for the dice
            Font font = new Font("Helvetica", Font.BOLD, 18);
            g2.setFont(font);
            //paint each of the 36 dice
            g2.setStroke(new BasicStroke(1));
            for(int r = 0; r < rowNum; r++) {
                for(int c = 0; c < colNum; c++) {
                    //dice border
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRoundRect(x + 50*c, y + 50*r, 50, 50, 15, 15);
                    //fill dice area with white
                    g2.setColor(Color.white);
                    g2.fillRoundRect(x + 50*c + 3, y + 50*r + 3, 45, 45, 15, 15);
                    //draw character on dice
                    g2.setColor(Color.black);
                    g2.drawString(labels[r][c], x + 50*c + 19, y + 50*r + 30);
                }
            }
        }
    }


    public static void main(String[] args) {
        boggleBoard board = new boggleBoard(6, 6);
        new boggleWindow(board);
    }

}
