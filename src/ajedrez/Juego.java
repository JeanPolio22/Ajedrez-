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

public class Juego {

    private Tablero tablero;
    private boolean turnoBlancas;
    private Pieza piezaSeleccionada;
    private PanelTablero panelTablero;

    public Juego() {
        tablero = new Tablero();
        turnoBlancas = true;
        piezaSeleccionada = null;
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
        // Piezas Negras (Fila 0)
        tablero.colocarPieza(new Torre(false, 0, 0), 0, 0);
        tablero.colocarPieza(new Caballo(false, 0, 1), 0, 1);
        tablero.colocarPieza(new Alfil(false, 0, 2), 0, 2);
        tablero.colocarPieza(new Reina(false, 0, 3), 0, 3);
        tablero.colocarPieza(new Rey(false, 0, 4), 0, 4);
        tablero.colocarPieza(new Alfil(false, 0, 5), 0, 5);
        tablero.colocarPieza(new Caballo(false, 0, 6), 0, 6);
        tablero.colocarPieza(new Torre(false, 0, 7), 0, 7);

        // Peones Negros (Fila 1)
        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(false, 1, c), 1, c);
        }

        // Piezas Blancas (Fila 7)
        tablero.colocarPieza(new Torre(true, 7, 0), 7, 0);
        tablero.colocarPieza(new Caballo(true, 7, 1), 7, 1);
        tablero.colocarPieza(new Alfil(true, 7, 2), 7, 2);
        tablero.colocarPieza(new Reina(true, 7, 3), 7, 3);
        tablero.colocarPieza(new Rey(true, 7, 4), 7, 4);
        tablero.colocarPieza(new Alfil(true, 7, 5), 7, 5);
        tablero.colocarPieza(new Caballo(true, 7, 6), 7, 6);
        tablero.colocarPieza(new Torre(true, 7, 7), 7, 7);

        // Peones Blancos (Fila 6)
        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(true, 6, c), 6, c);
        }
    }

    public Tablero getTablero(){
        return tablero;
    }

    public boolean seleccionarCasilla(int fila, int columna, PanelTablero panel) {
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
                for (int f = 0; f < 8; f++) {
                    for (int c = 0; c < 8; c++) {
                        if (piezaSeleccionada.movimientoValido(f, c, tablero)) {
                            // Verificamos si en la casilla destino hay un enemigo para pintar de rojo
                            boolean esCaptura = tablero.hayEnemigo(f, c, piezaSeleccionada.esBlanca());
                            panel.iluminarCasilla(f, c, esCaptura);
                        }
                    }
                }
            }
            return false;
        }

        if(piezaSeleccionada.movimientoValido(fila, columna, tablero)){
            tablero.moverPieza(
                    piezaSeleccionada.getFila(),
                    piezaSeleccionada.getColumna(),
                    fila,
                    columna
            );

            turnoBlancas = !turnoBlancas;
        }

        piezaSeleccionada = null;
        if (panel != null) {
            panel.restaurarColores();
        }

        return true;
    }
}