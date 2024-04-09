package aguero.code.frontend.Gui;

import aguero.code.backend.logic.Shape;
import aguero.code.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board extends JPanel implements KeyListener {
    private Timer loop;
    private int state = Constants.STATE_GAME_PLAY;
    private  int delay = Constants.FPS / 1000;
    public static Color[][] board = new Color[Constants.BOARD_HEIGTH][Constants.BOARD_WIDTH];
    private Shape currentShape;

    private Random random;
    private Color[] colors = {
            Color.decode("#ed1c24"),
            Color.decode("#ff7f27"),
            Color.decode("#fff200"),
            Color.decode("#22b14c"),
            Color.decode("#00a2e8"),
            Color.decode("#a34984"),
            Color.decode("#3f48cc"),
    };

    private Shape[] shapes = new Shape[7];
    public Board() {

        random = new Random();

        shapes[0] = new Shape(new int[][]{
                {1,1,1,1}}, this, colors[0]);
        shapes[1] = new Shape(new int[][]{
                {1,1,1},{0,1,0}}, this, colors[1]);
        shapes[2] = new Shape(new int[][]{
                {1,1,1},{1,0,0}}, this, colors[2]);
        shapes[3] = new Shape(new int[][]{
                {1,1,1},{0,0,1}}, this, colors[3]);
        shapes[4] = new Shape(new int[][]{
                {0,1,1},{1,1,0}}, this, colors[4]);
        shapes[5] = new Shape(new int[][]{
                {1,1,0},{0,0,1}}, this, colors[5]);
        shapes[6] = new Shape(new int[][]{
                {1,1},{1,1}}, this, colors[6]);

        currentShape = shapes[6];

        loop = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                update();

                repaint();
            }
        });
        loop.start();
    }

    private void update(){
        if(state == Constants.STATE_GAME_PLAY){
            currentShape.update();
        }

    }

    public Color[][] getBoard(){
        return board;
    }

    public void setCurrentShape(){
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
        checkOverGame();
    }

    private void checkOverGame(){
        int[][] coords = currentShape.getCoords();
        for(int row = 0; row < coords.length; row++){
            for (int col = 0; col < coords[row].length; col++){
                if (coords[row][col] != 0) {
                    if(board[row + currentShape.getY()][col + currentShape.getX()] != null){
                        state = Constants.STATE_GAME_OVER;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        //rellena el panel con color gris
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        currentShape.render(g);



        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[0].length; col++){

                if (board[row][col] != null){
                    g.setColor(board[row][col]);
                    g.fillRect(col * Constants.BLOCK_SIZE, row * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                }
            }
        }

        currentShape.paintBoard(g);

        if(state == Constants.STATE_GAME_OVER){
            g.setColor(Color.white);
            Font fuente = new Font("Roboto", Font.BOLD, 36);
            g.setFont(fuente);
            g.drawString("GAME OVER", 50, 300);
        }
        if(state == Constants.STATE_GAME_PAUSE){
            g.setColor(Color.white);
            Font fuente = new Font("Roboto", Font.BOLD, 36);
            g.setFont(fuente);
            g.drawString("GAME PAUSE", 50, 300);
            g.setColor(Color.white);
            Font fuente2 = new Font("Roboto", Font.BOLD, 16);
            g.setFont(fuente2);
            g.drawString("Press Enter To Play", 90, 350);
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            currentShape.speedUp();
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            currentShape.moveRight();
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            currentShape.moveLeft();
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
            currentShape.rotateShape();
        }

        if (state == Constants.STATE_GAME_OVER){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                for (int row = 0; row < board.length; row++){
                    for (int col = 0; col < board[0].length; col++){
                        board[row][col] = null;
                    }
                }
                setCurrentShape();
                state = Constants.STATE_GAME_PLAY;
            }
        }


            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if (state == Constants.STATE_GAME_PLAY){
                    state = Constants.STATE_GAME_PAUSE;
                }else if(state == Constants.STATE_GAME_PAUSE){
                    state = Constants.STATE_GAME_PLAY;
                }
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            currentShape.speedDown();
        }
    }
}
