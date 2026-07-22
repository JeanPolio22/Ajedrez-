package Piezas;

import Tablero.Tablero;

public class Reina extends Pieza {

    public Reina(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        return "♛"; // Símbolo de reina
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

        // Es movimiento válido de reina si es como Torre (recto) o como Alfil (diagonal)
        boolean esMovimientoRecto = (filaOrigen == filaDestino || columnaOrigen == columnaDestino);
        boolean esMovimientoDiagonal = (difFila == difColumna);

        if (!esMovimientoRecto && !esMovimientoDiagonal) {
            return false;
        }

        // Validar camino despejado (sirve tanto para recto como para diagonal)
        int pasoFila = (filaDestino == filaOrigen) ? 0 : (filaDestino > filaOrigen ? 1 : -1);
        int pasoColumna = (columnaDestino == columnaOrigen) ? 0 : (columnaDestino > columnaOrigen ? 1 : -1);

        int f = filaOrigen + pasoFila;
        int c = columnaOrigen + pasoColumna;

        while (f != filaDestino || c != columnaDestino) {
            if (!tablero.estaVacia(f, c)) {
                return false;
            }
            f += pasoFila;
            c += pasoColumna;
        }

        // Validar casilla destino
        if (tablero.estaVacia(filaDestino, columnaDestino)) {
            return true;
        } else {
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }
    }
}