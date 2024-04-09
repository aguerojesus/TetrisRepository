package aguero.code.frontend.Gui;

import aguero.code.utils.Constants;
import aguero.code.utils.ImageLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Portate extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private BufferedImage instructions;
    private Timer timer;
    private WindowGame windowGame;
    private JFrame frame;


    public Portate(JFrame frame) {

        this.frame = frame;
        instructions = ImageLoader.loadImage("/arrow.png"); // Cargar la imagen "arrow.png"

        timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();

        setFocusable(true); // Para que JPanel pueda recibir eventos del teclado
        addKeyListener(this); // Agregar el KeyListener a este JPanel
    }

    public void setWindowGame(WindowGame windowGame) {
        this.windowGame = windowGame;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // Dibujar la imagen "arrow.png" si se ha cargado correctamente
        if (instructions != null) {
            g.drawImage(instructions, Constants.WIDTH / 2 - instructions.getWidth() / 2,
                    Constants.HEIGHT / 2 - instructions.getHeight() / 2, null);
            g.setColor(Color.white);
            Font fuente2 = new Font("Roboto", Font.BOLD, 16);
            g.setFont(fuente2);
            g.drawString("Press SPACE To Play", 90, 550);
        } else {
            // Si la imagen no se cargó correctamente, mostrar un mensaje de error
            g.setColor(Color.RED);
            g.drawString("Error: Failed to load image", 100, Constants.HEIGHT / 2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            windowGame = new WindowGame();
            this.setVisible(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
