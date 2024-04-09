package aguero.code.backend.logic;

import aguero.code.frontend.Gui.Board;
import aguero.code.utils.Constants;

import java.awt.*;

public class Shape {
    private long beginTime;
    private boolean collision = false;
    private Board board;
    private    int x = 2, y = 0, normal = 600, fast = 50, deltax = 0, delayTime = normal;
    private int[][] coords;
    private Color color;
    public Shape(int[][] coords, Board board, Color color) {
        this.coords = coords;
        this.board = board;
        this.color = color;
    }

    public void reset(){
        this.x = 4;
        this.y = 0;
        collision = false;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }


    public void update(){
        if(collision){
            for (int row = 0; row < coords.length; row++){
                for(int col = 0; col < coords[0].length; col++){
                    if(coords[row][col] !=0){
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }

            checkLine();
            //SET CURREN SHAPE
            board.setCurrentShape();

            return;
        }

        //Movimiento vertical de piezas
        if(System.currentTimeMillis() - beginTime > delayTime){
            if(!(y + 1 + coords.length > Constants.BOARD_HEIGTH)){
                for(int row = 0; row < coords.length; row++){
                    for (int col = 0; col < coords[row].length; col++){
                        if(coords[row][col] != 0){
                            if(board.getBoard()[(y + 1 + row)][(x + deltax + col)] != null){
                                collision = true;
                            }
                        }
                    }
                }
                if (!collision){
                    y++;
                }
            }else {
                collision = true;
            }


            beginTime = System.currentTimeMillis();
        }
        boolean moveX = true;
        //Verificar el movimiendo horizontal
        if(!(x + deltax + coords[0].length > 10) && !(x + deltax < 0)){
            for (int row = 0; row < coords.length; row++){
                for (int col = 0; col < coords[row].length; col++){
                    if(coords[row][col] != 0){
                        if (board.getBoard()[y + row][x + deltax + col] != null){
                            moveX = false;
                        }
                    }
                }
            }
            if(moveX){
                x += deltax;
            }

        }
        deltax = 0;
    }

    public void rotateShape(){
        int[][] rotate = transposeMatrix(coords);
        reverseRows(rotate);
        //verificar que no se salga por los bordes al rotar
        if(((x + rotate[0].length) > Constants.BOARD_WIDTH) || ((y + rotate.length) > Constants.BOARD_HEIGTH)){
            return;
        }
        //verificar la colisión con otras figuras ya existentes
        for (int row = 0; row < rotate.length; row++){
            for (int col = 0; col < rotate[row].length; col++){
                if(rotate[row][col] != 0){
                    if (board.getBoard()[y + row][x + col] != null){
                        return;
                    }
                }
            }
        }
        coords = rotate;
    }

    private int[][] transposeMatrix(int[][] matrix){
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++){
            for (int col = 0; col < matrix[row].length; col++){
                temp[col][row] = matrix[row][col];
            }
        }
        return temp;
    }

    private void reverseRows(int matrix[][]){
        int middle = matrix.length / 2;
        for (int row = 0; row < middle; row++){
            int[] temp = matrix[row];
            matrix[row] = matrix[matrix.length - row - 1];
            matrix[matrix.length - row - 1] = temp;
        }
    }

    public void render(Graphics g){
        for (int row = 0; row < coords.length; row++){
            for (int col = 0; col < coords[0].length; col++){
                if (coords[row][col] != 0){
                    g.setColor(color);
                    g.fillRect(col * Constants.BLOCK_SIZE + x * Constants.BLOCK_SIZE, row * Constants.BLOCK_SIZE + y * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                }

            }
        }
    }

    public void paintBoard(Graphics g){
        // draw the shape
        g.setColor(Color.white);
        for (int row = 0; row < Constants.BOARD_HEIGTH; row ++){
            g.drawLine(0, Constants.BLOCK_SIZE * row, Constants.BLOCK_SIZE * Constants.BOARD_WIDTH, Constants.BLOCK_SIZE * row);
        }

        g.setColor(Color.white);
        for (int row = 0; row < Constants.BOARD_HEIGTH; row ++){
            g.drawLine(0, Constants.BLOCK_SIZE * row, Constants.BLOCK_SIZE * Constants.BOARD_WIDTH, Constants.BLOCK_SIZE * row);
        }

        g.setColor(Color.white);
        for (int col = 0; col <= Constants.BOARD_WIDTH; col ++){
            g.drawLine(col * Constants.BLOCK_SIZE, 0, col * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE * Constants.BOARD_HEIGTH);
        }


    }
    private void checkLine(){
        int bottomLine = board.getBoard().length - 1;
        for (int topLine = board.getBoard().length - 1; topLine > 0; topLine--){
            int count = 0;
            for (int col = 0; col < board.getBoard()[0].length; col++){
                if (board.getBoard()[topLine][col] != null){
                    count++;
                }
                board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
            }
            if (count < board.getBoard()[0].length){
                bottomLine--;
            }
        }
    }

    public void speedUp(){
        delayTime = fast;
    }

    public void speedDown(){
        delayTime = normal;
    }

    public void moveRight(){
        deltax = 1;
    }

    public void moveLeft(){
        deltax = -1;
    }

    public int[][] getCoords(){
        return coords;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
