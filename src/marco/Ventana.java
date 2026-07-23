package marco;

import Tablero.PanelTablero;
import ajedrez.Juego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Ventana extends JFrame {

    private PanelTablero panelTablero;
    private Juego juego;
    private boolean modoRobot;

    public Ventana(boolean modoRobot) {
        this.modoRobot = modoRobot;
        setTitle("Ajedrez");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Asignamos el icono con el símbolo de ajedrez generado por código
        setIconImage(crearIconoAjedrez());

        panelTablero = new PanelTablero(modoRobot);
        add(panelTablero, BorderLayout.CENTER);

        juego = new Juego(panelTablero);
    }

    // Método que dibuja la torre de ajedrez para la barra de título
    private Image crearIconoAjedrez() {
        BufferedImage imagen = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo circular azulado para el icono
        g2.setColor(new Color(41, 128, 185));
        g2.fillOval(2, 2, 28, 28);

        // Dibujamos la torre de ajedrez en blanco
        g2.setColor(Color.WHITE);
        g2.fillRect(9, 13, 14, 14); // Cuerpo
        g2.fillRect(8, 10, 16, 4);  // Almenas superiores
        g2.fillRect(8, 27, 16, 3);  // Base
        
        g2.dispose();
        return imagen;
    }
}