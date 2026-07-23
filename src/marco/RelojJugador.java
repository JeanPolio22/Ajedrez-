package marco;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RelojJugador implements Runnable {

    private JLabel lblReloj;
    private int segundosRestantes;
    private boolean esBlanco;
    private Ventana ventana;
    private volatile boolean activo = true;

    public RelojJugador(JLabel lblReloj, int segundosIniciales, boolean esBlanco, Ventana ventana) {
        this.lblReloj = lblReloj;
        this.segundosRestantes = segundosIniciales;
        this.esBlanco = esBlanco;
        this.ventana = ventana;
    }

    @Override
    public void run() {
        while (activo && segundosRestantes > 0) {
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                break;
            }

            if (!activo) break;

            segundosRestantes--;

            final int minutos = segundosRestantes / 60;
            final int segundos = segundosRestantes % 60;

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    lblReloj.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
                }
            });
        }

        if (activo && segundosRestantes <= 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ventana.terminarPorTiempo(esBlanco);
                }
            });
        }
    }

    public void detener() {
        activo = false;
    }
}