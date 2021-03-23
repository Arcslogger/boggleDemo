import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class boggleWindow extends JFrame {

    private static JFrame frame; //frame where everything is laid out on
    private static JPanel uiPanel, boardPanel, buttonPanel, headerPanel, footerPanel;
    private static JTextField input;
    private static JButton scramble, quit;
    private static JLabel title, subTitle, isValid, enterRules, resultRules;

    public boggleWindow(boggleBoard board) throws IOException, FontFormatException {

        frame = new JFrame("Boggle Assignment"); //initializing window and configuring properties
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        uiPanel = new JPanel(new BorderLayout(0, 20));
        boardPanel = new JPanel(new BorderLayout(0, 0)); //new GridLayout(map.length, map[0].length)
        buttonPanel = new JPanel(new FlowLayout());
        headerPanel = new JPanel(new GridLayout(2, 1));
        footerPanel = new JPanel(new FlowLayout());

        uiPanel.setBorder(BorderFactory.createEmptyBorder(100, 45, 150, 10));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        input = new JTextField(); //text box for entering queries
        input.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = input.getText();
                boolean onBoard = board.onBoard(s), isEnglish = board.isValid(s);
                isValid.setText(onBoard ? "Possible" : "Impossible");
                isValid.setForeground((isEnglish) ? Color.GREEN : Color.red);
                boardPanel.revalidate();
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
        quit = new JButton("Exit demo");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("User has terminated program.");
                System.exit(0);
            }
        });

        title = new JLabel("Wib's Mind Boggling Boggle Game");
        title.setFont(new Font ("Sans Serif", Font.BOLD, 32));
        subTitle = new JLabel(" (◕‿◕✿)");
        subTitle.setFont(new Font("Sans Serif", Font.BOLD, 16));
        subTitle.setForeground(Color.darkGray);
        enterRules = new JLabel("Enter queries into search box below. Press enter to check query.");
        enterRules.setFont(new Font("Sans Serif", Font.ITALIC, 12));
        enterRules.setForeground(Color.lightGray);
        resultRules = new JLabel("<html><body style=\"text-align:center;\">Possible/Impossible: Valid/Invalid combination of dice respectively" +
                                        "<br>Green/Red: Valid/Invalid english word respectively<br></body></html>");
        resultRules.setHorizontalAlignment(JTextField.CENTER);
        enterRules.setFont(new Font("Sans Serif", Font.ITALIC, 12));
        resultRules.setForeground(Color.darkGray);
        isValid = new JLabel("");
        isValid.setHorizontalAlignment(JTextField.CENTER);
        isValid.setFont(new Font("Sans Serif", Font.BOLD, 24));

        buttonPanel.add(scramble);
        buttonPanel.add(quit);
        boardPanel.add(new boardDice(5, 5, board.getBoard()), BorderLayout.NORTH);
        boardPanel.add(isValid, BorderLayout.CENTER);
        uiPanel.add(enterRules, BorderLayout.NORTH);
        uiPanel.add(input, BorderLayout.CENTER);
        uiPanel.add(buttonPanel, BorderLayout.SOUTH);
        headerPanel.add(title);
        headerPanel.add(subTitle);
        footerPanel.add(resultRules);

        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(uiPanel, BorderLayout.LINE_START);
        frame.getContentPane().add(boardPanel, BorderLayout.LINE_END);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
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
    public static void main(String[] args) throws IOException, FontFormatException {
        boggleBoard board = new boggleBoard(6, 6);
        new boggleWindow(board);
    }
}
