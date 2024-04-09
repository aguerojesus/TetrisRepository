package aguero.code.backend.main;

import aguero.code.frontend.Gui.Portate;
import aguero.code.frontend.Gui.WindowGame;
import aguero.code.utils.Constants;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //WindowGame windowMain = new WindowGame();
        JFrame frame = new JFrame("Tetris Game"); // Crear un JFrame con el título "Tetris Game"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establecer la operación de cierre
        frame.setSize(Constants.WIDTH, Constants.HEIGHT); // Establecer el tamaño de la ventana
        frame.setResizable(false); // Evitar que la ventana sea redimensionable

        Portate portate = new Portate(frame); // Crear una instancia de Portate
        frame.add(portate); // Agregar Portate al JFrame

        frame.setVisible(true); // Hacer visible el JFrame

    }
}