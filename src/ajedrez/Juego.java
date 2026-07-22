package ajedrez;

import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Reina;
import Piezas.Rey;
import Piezas.Torre;
import Tablero.PanelTablero;
import Tablero.Tablero;
import javax.swing.JOptionPane;

public class Juego {

    private Tablero tablero;
    private boolean turnoBlancas;
    private Pieza piezaSeleccionada;
    private PanelTablero panelTablero;
    private boolean juegoTerminado;

    public Juego() {
        tablero = new Tablero();
        turnoBlancas = true;
        piezaSeleccionada = null;
        juegoTerminado = false;
        colocarPiezas();
    }

    public Juego(PanelTablero panelTablero) {
        this();
        this.panelTablero = panelTablero;
    }

    public void setPanelTablero(PanelTablero panelTablero) {
        this.panelTablero = panelTablero;
    }

    private void colocarPiezas() {
        tablero.colocarPieza(new Torre(false, 0, 0), 0, 0);
        tablero.colocarPieza(new Caballo(false, 0, 1), 0, 1);
        tablero.colocarPieza(new Alfil(false, 0, 2), 0, 2);
        tablero.colocarPieza(new Reina(false, 0, 3), 0, 3);
        tablero.colocarPieza(new Rey(false, 0, 4), 0, 4);
        tablero.colocarPieza(new Alfil(false, 0, 5), 0, 5);
        tablero.colocarPieza(new Caballo(false, 0, 6), 0, 6);
        tablero.colocarPieza(new Torre(false, 0, 7), 0, 7);

        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(false, 1, c), 1, c);
        }

        tablero.colocarPieza(new Torre(true, 7, 0), 7, 0);
        tablero.colocarPieza(new Caballo(true, 7, 1), 7, 1);
        tablero.colocarPieza(new Alfil(true, 7, 2), 7, 2);
        tablero.colocarPieza(new Reina(true, 7, 3), 7, 3);
        tablero.colocarPieza(new Rey(true, 7, 4), 7, 4);
        tablero.colocarPieza(new Alfil(true, 7, 5), 7, 5);
        tablero.colocarPieza(new Caballo(true, 7, 6), 7, 6);
        tablero.colocarPieza(new Torre(true, 7, 7), 7, 7);

        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(true, 6, c), 6, c);
        }
    }

    public Tablero getTablero(){
        return tablero;
    }

    // Método para comprobar si el rey del color indicado está en jaque
    public boolean estaEnJaque(boolean esBlanca) {
        int[] posRey = tablero.buscarRey(esBlanca);
        if (posRey == null) return false;
        // Si el rey es blanco, el atacante es negro (!esBlanca)
        return tablero.esCasillaAtacada(posRey[0], posRey[1], !esBlanca);
    }

    // Comprueba si un bando tiene algún movimiento legal disponible para evitar el jaque mate
    public boolean tieneMovimientosLegales(boolean esBlanca) {
        for (int fOrigen = 0; fOrigen < 8; fOrigen++) {
            for (int cOrigen = 0; cOrigen < 8; cOrigen++) {
                Pieza p = tablero.getPieza(fOrigen, cOrigen);
                if (p != null && p.esBlanca() == esBlanca) {
                    for (int fDestino = 0; fDestino < 8; fDestino++) {
                        for (int cDestino = 0; cDestino < 8; cDestino++) {
                            if (p.movimientoValido(fDestino, cDestino, tablero)) {
                                // Simulamos el movimiento para ver si el rey queda a salvo
                                Pieza piezaCapturada = tablero.getPieza(fDestino, cDestino);
                                int origenF = p.getFila();
                                int origenC = p.getColumna();

                                tablero.moverPieza(origenF, origenC, fDestino, cDestino);
                                boolean sigueEnJaque = estaEnJaque(esBlanca);
                                // Revertimos simulación
                                tablero.moverPieza(fDestino, cDestino, origenF, origenC);
                                if (piezaCapturada != null) {
                                    tablero.colocarPieza(piezaCapturada, fDestino, cDestino);
                                }

                                if (!sigueEnJaque) {
                                    return true; // Encontró al menos un movimiento válido para salvarse
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean seleccionarCasilla(int fila, int columna, PanelTablero panel) {
        if (juegoTerminado) return false;

        Pieza pieza = tablero.getPieza(fila, columna);

        if(piezaSeleccionada == null){
            if(pieza == null){
                return false;
            }

            if(pieza.esBlanca() != turnoBlancas){
                return false;
            }

            piezaSeleccionada = pieza;

            if (panel != null) {
                panel.restaurarColores();
                
                // Si hay jaque, resaltamos la casilla del rey en peligro
                if (estaEnJaque(turnoBlancas)) {
                    int[] posRey = tablero.buscarRey(turnoBlancas);
                    if (posRey != null) {
                        panel.iluminarJaque(posRey[0], posRey[1]);
                    }
                }

                for (int f = 0; f < 8; f++) {
                    for (int c = 0; c < 8; c++) {
                        if (piezaSeleccionada.movimientoValido(f, c, tablero)) {
                            // Validar que el movimiento no deje o mantenga al propio rey en jaque
                            Pieza destinoTemp = tablero.getPieza(f, c);
                            int origF = piezaSeleccionada.getFila();
                            int origC = piezaSeleccionada.getColumna();

                            tablero.moverPieza(origF, origC, f, c);
                            boolean reyExpuesto = estaEnJaque(turnoBlancas);
                            tablero.moverPieza(f, c, origF, origC);
                            if (destinoTemp != null) {
                                tablero.colocarPieza(destinoTemp, f, c);
                            }

                            if (!reyExpuesto) {
                                boolean esCaptura = tablero.hayEnemigo(f, c, piezaSeleccionada.esBlanca());
                                panel.iluminarCasilla(f, c, esCaptura);
                            }
                        }
                    }
                }
            }
            return false;
        }

        // Intento de movimiento
        if(piezaSeleccionada.movimientoValido(fila, columna, tablero)){
            // Validar simulación de movimiento seguro
            Pieza destinoTemp = tablero.getPieza(fila, columna);
            int origF = piezaSeleccionada.getFila();
            int origC = piezaSeleccionada.getColumna();

            tablero.moverPieza(origF, origC, fila, columna);
            boolean reyExpuesto = estaEnJaque(turnoBlancas);
            tablero.moverPieza(fila, columna, origF, origC);
            if (destinoTemp != null) {
                tablero.colocarPieza(destinoTemp, fila, columna);
            }

            if (!reyExpuesto) {
                tablero.moverPieza(origF, origC, fila, columna);
                turnoBlancas = !turnoBlancas;

                // Comprobar si el turno siguiente deja al rival en jaque mate o jaque
                boolean oponenteEsBlanco = turnoBlancas;
                if (estaEnJaque(oponenteEsBlanco)) {
                    if (!tieneMovimientosLegales(oponenteEsBlanco)) {
                        juegoTerminado = true;
                        String ganador = oponenteEsBlanco ? "Negras" : "Blancas";
                        panel.restaurarColores();
                        int[] posRey = tablero.buscarRey(oponenteEsBlanco);
                        if (posRey != null) {
                            panel.iluminarJaque(posRey[0], posRey[1]);
                        }
                        JOptionPane.showMessageDialog(panel, "¡Jaque Mate! El juego ha terminado. Ganaron las " + ganador + ".", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }

        piezaSeleccionada = null;
        if (panel != null && !juegoTerminado) {
            panel.restaurarColores();
            if (estaEnJaque(turnoBlancas)) {
                int[] posRey = tablero.buscarRey(turnoBlancas);
                if (posRey != null) {
                    panel.iluminarJaque(posRey[0], posRey[1]);
                }
            }
        }

        return true;
    }
}