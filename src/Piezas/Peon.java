package Piezas;

import Tablero.Tablero;

public class Peon extends Pieza {

    public Peon(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        return "♟";
    }

    @Override
    public boolean movimientoValido(int filaDestino, int columnaDestino, Tablero tablero) {
        if (!tablero.posicionValida(filaDestino, columnaDestino)) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();
        
        int direccion = esBlanca() ? -1 : 1;
        int filaInicial = esBlanca() ? 6 : 1;

        int difFila = filaDestino - filaOrigen;
        int difColumna = columnaDestino - columnaOrigen;

        // 1. Movimiento hacia adelante de una casilla
        if (difColumna == 0 && difFila == direccion) {
            return tablero.estaVacia(filaDestino, columnaDestino);
        }

        // 2. Movimiento hacia adelante de dos casillas (Solo si está en su fila inicial y no se ha movido)
        if (difColumna == 0 && difFila == (2 * direccion)) {
            if (filaOrigen == filaInicial && !haMovido()) {
                int filaIntermedia = filaOrigen + direccion;
                return tablero.estaVacia(filaIntermedia, columnaOrigen) && tablero.estaVacia(filaDestino, columnaDestino);
            }
        }

        // 3. Captura en diagonal
        if (Math.abs(difColumna) == 1 && difFila == direccion) {
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }

        return false;
    }
}