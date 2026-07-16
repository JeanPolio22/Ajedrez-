
package Piezas;

import Tablero.Tablero;

public class Torre extends Pieza {

    public Torre(boolean blanca, int fila, int columna) {

        super(blanca, fila, columna);

    }

    @Override
    public String getSimbolo() {

        // Usamos siempre la torre negra
        return "♜";

    }

    @Override
    public boolean movimientoValido(int filaDestino,
                                    int columnaDestino,
                                    Tablero tablero) {

        if (!tablero.posicionValida(filaDestino, columnaDestino)) {

            return false;

        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();

        if (filaOrigen == filaDestino &&
            columnaOrigen == columnaDestino) {

            return false;

        }

        if (filaOrigen != filaDestino &&
            columnaOrigen != columnaDestino) {

            return false;

        }

        if (filaOrigen == filaDestino) {

            int paso = columnaDestino > columnaOrigen ? 1 : -1;

            for (int c = columnaOrigen + paso; c != columnaDestino; c += paso) {

                if (!tablero.estaVacia(filaOrigen, c)) {

                    return false;

                }

            }

        } else {

            int paso = filaDestino > filaOrigen ? 1 : -1;

            for (int f = filaOrigen + paso; f != filaDestino; f += paso) {

                if (!tablero.estaVacia(f, columnaOrigen)) {

                    return false;

                }

            }

        }

        if (tablero.estaVacia(filaDestino, columnaDestino)) {

            return true;

        }

        return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());

    }

}