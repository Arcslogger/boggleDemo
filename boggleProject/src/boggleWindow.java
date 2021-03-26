/*==============================================================================
 Boggle Demo
 Wilbur Zhang
 March 26 2021
 Java - version 1.8.0

 Class containing all front-end layout/formatting. When created, it creates a window based off of the current board

 List of global variables:
 frame - JFrame where everything is laid out
 uiPanel - JPanel containing all intractable swing components; left side of the window
 boardPanel - JPanel containing the boggle board along with outputted results; right side of the window
 buttonPanel - JPanel containing all the button components; bottom of uiPanel
 headerPanel - JPanel containing the title and subtitle; top of window
 footerPanel - JPanel containing some user prompts; bottom of window
 input - JTextField used by user to enter queries
 scramble - button used to generate a new board
 quit - JButton used to exit the program
 title - JLabel containing the title text of the demo
 subTitle - JLabel containing some user prompts below the main title
 isValid - JLabel used to output whether the word entered is valid english/on the board
 enterRules - JLabel explaining how to enter a query
 resultRules - JLabel explaining how to interpret code output
================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 *  Refer to above for class description and global variable list
 *  @author Wilbur Zhang
 *  @version 1.0
 *  @since 2021-03-25
 */

public class boggleWindow extends JFrame {

    private static JFrame frame;
    private static JPanel uiPanel, boardPanel, buttonPanel, headerPanel, footerPanel;
    private static JTextField input;
    private static JButton scramble, quit;
    private static JLabel title, subTitle, isValid, enterRules, resultRules;

    public boggleWindow(boggleBoard board) throws IOException, FontFormatException {
        //initializing window and configuring properties
        frame = new JFrame("Boggle Assignment");
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        //initializing panels and configuring properties
        uiPanel = new JPanel(new BorderLayout(0, 20));
        boardPanel = new JPanel(new BorderLayout(0, 0));
        buttonPanel = new JPanel(new FlowLayout());
        headerPanel = new JPanel(new GridLayout(2, 1));
        footerPanel = new JPanel(new FlowLayout());
        //padding and margins for each panel
        uiPanel.setBorder(BorderFactory.createEmptyBorder(100, 45, 150, 10));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 5, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        //initializing panel components and configuring properties
        input = new JTextField();
        input.setFont(new Font("Helvetica", Font.PLAIN, 16));
        input.addActionListener(new ActionListener() {
            /**
             *  Gets user query from input field and checks whether it's English & on the board.
             *  Paints the appropriate for each situation and resets the input field.
             *
             *  List of Local Variables:
             *  s   value of the input entered by user </type String>
             *
             *  @param e ActionEvent created when user presses Enter
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = input.getText();
                boolean onBoard = board.onBoard(s), isEnglish = board.isValid(s);
                isValid.setText(onBoard ? "Possible" : "Impossible");
                isValid.setForeground((isEnglish) ? Color.GREEN : Color.red);
                input.setText("");
                boardPanel.revalidate();
            }
        });
        scramble = new JButton("Scramble board");

        scramble.addActionListener(new ActionListener() {
            /**
             *  Generates a new board layout when the scramble button is pressed. Resets input/ui and refreshes the panel.
             *  @param e ActionEvent created when user presses scramble button.
             */
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
            /**
             *  Terminates program when quit button is pressed.
             *  @param e ActionEvent created when user presses scramble button.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("User has terminated program.");
                System.exit(0);
            }
        });
        //initializing text components for user prompts
        title = new JLabel("Wib's Mind Boggling Boggle Game");
        title.setFont(new Font ("Helvetica", Font.BOLD, 32));
        subTitle = new JLabel("<html>Find words that exist either vertically, horizontally, or diagonally<br>All characters need to be in the same direction<html>");
        subTitle.setFont(new Font("Helvetica", Font.BOLD, 16));
        subTitle.setForeground(Color.darkGray);
        enterRules = new JLabel("Enter queries into search box below. Press enter to check query.");
        enterRules.setFont(new Font("Helvetica", Font.ITALIC, 12));
        enterRules.setForeground(Color.lightGray);
        resultRules = new JLabel("<html><body style=\"text-align:center;\">Possible/Impossible: Valid/Invalid combination of dice respectively<br>Green/Red: Valid/Invalid english word respectively<br></body></html>");
        enterRules.setForeground(Color.lightGray);
        resultRules.setHorizontalAlignment(JTextField.CENTER);
        resultRules.setForeground(Color.darkGray);
        isValid = new JLabel("");
        isValid.setHorizontalAlignment(JTextField.CENTER);
        isValid.setFont(new Font("Helvetica", Font.BOLD, 24));
        //adding components to panels
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
        //adding panels to frame
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(uiPanel, BorderLayout.LINE_START);
        frame.getContentPane().add(boardPanel, BorderLayout.LINE_END);
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
        //finally, show panel to user
        frame.setVisible(true);
    }
    /**
     *  A graphical representation of the board as a grid of dice with labels on top.
     */
    private static class boardDice extends JComponent {
        int x, y, rowNum, colNum;
        String [][] labels;
        /**
         * Class constructor creates a board starting at top left corner at specified coordinates.
         * @param x         x-coordinate of top left corner
         * @param y         y-coordinate of top left corner
         * @param labels    values to be shown on the face of all 36 dice
         */
        boardDice (int x, int y, String [][] labels) {
            setPreferredSize(new Dimension(310, 310)); //set size of board component. Slightly bigger than the true board size to be safe
            this.x = x;
            this.y = y;
            this.labels = labels;
            rowNum = labels.length;
            colNum = labels[0].length;
        }
        /**
         *  Paints the die at the specified coordinates and labels them with the values of the current board.
         *  @param g used to paint all the sections of the board
         */
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //blue border
            g2.setColor(new Color(50, 102, 168));
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(x-2, y-2, 304, 304, 15, 15);
            //set the font for the dice
            g2.setFont(new Font("Helvetica", Font.BOLD, 18));
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
    /**
     * Executes the app by creating the board logic and GUI interface.
     *
     * List of Local Variables:
     * board    contains all back end data and processing for the current instance of the program </type boggleBoard>
     *
     * @param args                  supplied command line arguments as a string array of objects
     * @throws IOException          if wordlist file can't be found or if improperly formatted
     * @throws FontFormatException  if a font created can't be accepted
     */
    public static void main(String[] args) throws IOException, FontFormatException {
        boggleBoard board = new boggleBoard(6, 6); //create a new board with 6 rows and 6 columns
        new boggleWindow(board); //create game window using the board
    }
}
