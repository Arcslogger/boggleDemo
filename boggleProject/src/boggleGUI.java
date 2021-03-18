import com.sun.xml.internal.ws.api.client.WSPortInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class boggleGUI extends JFrame implements ActionListener {

    static String [][] dice;
    static StringBuilder entered = new StringBuilder(36);

    JPanel uiPanel = new JPanel();
    static JFrame frame = new JFrame();

    public boggleGUI (boggleBoard board) {
        //just use a fucking jTextField man
        //not that deep
        frame.setTitle("Boggle Game");
        frame.setSize(800, 600);

        FlowLayout test = new FlowLayout();
        frame.setLayout(test);

        dice = board.getBoard();

        int ROW = dice.length, COL = dice[0].length;

        FlowLayout uiLayout = new FlowLayout();
        uiPanel.setLayout(uiLayout);

        JButton check = new JButton("check");
        check.addActionListener(e -> {
            System.out.println(entered.toString());
            System.out.println(board.isValid(entered.toString()));
            entered = new StringBuilder(36);
        });

        uiPanel.add(check);
        frame.add(uiPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        boggleBoard board = new boggleBoard ();
        board.generateBoard();
        new boggleGUI(board);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
