package Piezas;

import Tablero.Tablero;

public class Peon extends Pieza {

    //Constructor.
    public Peon(boolean blanca, int fila, int columna) {

        super(blanca, fila, columna);

    }

    @Override
    public String getSimbolo() {

        return "♟";

    }

    @Override
    public boolean movimientoValido(int filaDestino,
                                    int columnaDestino,
                                    Tablero tablero) {

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();

        // Debe permanecer en la misma columna para avanzar.
        if (columnaOrigen != columnaDestino) {

            return false;

        }

        // Dirección del movimiento.
        int direccion = esBlanca() ? -1 : 1;

        // Avanzar una casilla.
        if (filaDestino == filaOrigen + direccion) {

            return tablero.estaVacia(filaDestino, columnaDestino);

        }

        // Primer movimiento: puede avanzar dos casillas.
        if (esBlanca() && filaOrigen == 6 &&
                filaDestino == filaOrigen + (2 * direccion)) {

            return tablero.estaVacia(filaOrigen + direccion, columnaOrigen)
                    && tablero.estaVacia(filaDestino, columnaDestino);

        }

        if (!esBlanca() && filaOrigen == 1 &&
                filaDestino == filaOrigen + (2 * direccion)) {

            return tablero.estaVacia(filaOrigen + direccion, columnaOrigen)
                    && tablero.estaVacia(filaDestino, columnaDestino);

        }

        return false;

    }

}