package ajedrez;

import Piezas.Caballo;
import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.PanelTablero;
import Tablero.Tablero;
import java.awt.Color;

public class Juego {

    private Tablero tablero;
    private boolean turnoBlancas;
    private Pieza piezaSeleccionada;
    
    // Referencia opcional al panel para pintar los movimientos desde el juego
    private PanelTablero panelTablero;

    public Juego() {
        tablero = new Tablero();
        turnoBlancas = true;
        piezaSeleccionada = null;
        colocarPiezas();
    }

    // Constructor sobrecargado si quieres pasarle el panel visual
    public Juego(PanelTablero panelTablero) {
        this();
        this.panelTablero = panelTablero;
    }

    public void setPanelTablero(PanelTablero panelTablero) {
        this.panelTablero = panelTablero;
    }

    private void colocarPiezas() {
        // Torres negras
        tablero.colocarPieza(new Torre(false, 0, 0), 0, 0);
        tablero.colocarPieza(new Torre(false, 0, 7), 0, 7);

        // Caballos negros
        tablero.colocarPieza(new Caballo(false, 0, 1), 0, 1);
        tablero.colocarPieza(new Caballo(false, 0, 6), 0, 6);

        // Torres blancas
        tablero.colocarPieza(new Torre(true, 7, 0), 7, 0);
        tablero.colocarPieza(new Torre(true, 7, 7), 7, 7);

        // Caballos blancos
        tablero.colocarPieza(new Caballo(true, 7, 1), 7, 1);
        tablero.colocarPieza(new Caballo(true, 7, 6), 7, 6);

        // Peones negros
        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(false, 1, c), 1, c);
        }

        // Peones blancos
        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(true, 6, c), 6, c);
        }
    }

    public Tablero getTablero(){
        return tablero;
    }

    public boolean seleccionarCasilla(int fila, int columna, PanelTablero panel) {
        Pieza pieza = tablero.getPieza(fila, columna);

        // CASO 1: No hay pieza seleccionada todavía
        if(piezaSeleccionada == null){
            if(pieza == null){
                return false;
            }

            // Solo puede seleccionar piezas de su turno
            if(pieza.esBlanca() != turnoBlancas){
                return false;
            }

            piezaSeleccionada = pieza;

            // RECORRIDO PARA ILUMINAR: Buscamos casillas válidas para esta pieza
            if (panel != null) {
                panel.restaurarColores(); // Limpiamos luces anteriores
                for (int f = 0; f < 8; f++) {
                    for (int c = 0; c < 8; c++) {
                        if (piezaSeleccionada.movimientoValido(f, c, tablero)) {
                            // Pintamos de un verde claro los movimientos válidos
                            panel.iluminarCasilla(f, c, new Color(144, 238, 144)); 
                        }
                    }
                }
            }
            return false;
        }

        // CASO 2: Ya había una pieza seleccionada, intentamos mover
        if(piezaSeleccionada.movimientoValido(fila, columna, tablero)){
            tablero.moverPieza(
                    piezaSeleccionada.getFila(),
                    piezaSeleccionada.getColumna(),
                    fila,
                    columna
            );

            turnoBlancas = !turnoBlancas;
        }

        // Al terminar el turno (sea exitoso el movimiento o se haya deseleccionado), limpiamos luces
        piezaSeleccionada = null;
        if (panel != null) {
            panel.restaurarColores();
        }

        return true;
    }
}