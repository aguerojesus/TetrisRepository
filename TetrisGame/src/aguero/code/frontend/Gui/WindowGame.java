package aguero.code.frontend.Gui;

import aguero.code.utils.Constants;

import javax.swing.*;

public class WindowGame {

    private JFrame window;
    private Board board;

    private Portate portate;
    public WindowGame() {

        this.window = new JFrame("Tetris Game");
        this.window.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.setLocationRelativeTo(null);

        board = new Board();
        window.add(board);
        window.addKeyListener(board);

        this.window.setVisible(true);
    }
}
