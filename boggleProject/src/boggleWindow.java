import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class boggleWindow extends JFrame {

    static String [][] dice;

    JPanel uiPanel; //controls interactive components
    static JFrame frame; //frame where everything is laid out on

    static JTextField input;
    static JButton check;
    public boggleWindow(boggleBoard board) {
        frame = new JFrame("Boggle Game");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        input = new JTextField("");
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(board.isValid(input.getText()));
                input.setText("");
            }
        });

        frame.getContentPane().add(input);
        frame.setVisible(true);
        String [][] map = board.getBoard();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        boggleBoard board = new boggleBoard(6, 6);
        new boggleWindow(board);
    }

}
