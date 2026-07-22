package Piezas;

import Tablero.Tablero;

public class Rey extends Pieza {

    public Rey(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        return "♚"; // Símbolo de rey
    }

    @Override
    public boolean movimientoValido(int filaDestino, int columnaDestino, Tablero tablero) {
        if (!tablero.posicionValida(filaDestino, columnaDestino)) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();

        int difFila = Math.abs(filaDestino - filaOrigen);
        int difColumna = Math.abs(columnaDestino - columnaOrigen);

        // El rey se mueve máximo 1 casilla en cualquier dirección
        if (difFila > 1 || difColumna > 1) {
            return false;
        }

        // No puede quedarse en el mismo lugar
        if (difFila == 0 && difColumna == 0) {
            return false;
        }

        // Validar destino (vacío o enemigo)
        if (tablero.estaVacia(filaDestino, columnaDestino)) {
            return true;
        } else {
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }
    }
}