package ajedrez;

import Tablero.PanelTablero;
import javax.swing.SwingUtilities;

public class MotorRobot implements Runnable {

    private PanelTablero panelTablero;
    private Juego juego;

    public MotorRobot(PanelTablero panelTablero, Juego juego) {
        this.panelTablero = panelTablero;
        this.juego = juego;
    }

    @Override
    public void run() {
        try {
            // Simulamos el tiempo de "pensamiento" del robot usando Thread.sleep() en hilo secundario
            Thread.sleep(800);
        } catch (InterruptedException ex) {
            System.out.println("El turno del robot fue interrumpido.");
        }

        // Actualizamos la interfaz gráfica y ejecutamos el movimiento del robot de forma segura con invokeLater()
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                juego.ejecutarTurnoRobot(panelTablero);
                panelTablero.actualizarTablero();
            }
        });
    }
}