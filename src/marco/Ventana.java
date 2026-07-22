package marco;

import Tablero.PanelTablero;
import javax.swing.JFrame;

public class Ventana extends JFrame {

    public Ventana(boolean modoRobot) {
        setTitle("Ajedrez");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new PanelTablero(modoRobot));

        setVisible(true);
    }
}