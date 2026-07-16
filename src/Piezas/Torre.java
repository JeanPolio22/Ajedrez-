package Piezas;

import Tablero.Tablero;

public class Torre extends Pieza {

    public Torre(boolean blanca, int fila, int columna) {

        super(blanca, fila, columna);

    }

    @Override
    public String getSimbolo() {

        return esBlanca() ? "♖" : "♜";

    }

    @Override
    public boolean movimientoValido(int filaDestino,
                                    int columnaDestino,
                                    Tablero tablero) {

        //La posición debe existir.
        if (!tablero.posicionValida(filaDestino, columnaDestino)) {

            return false;

        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();

        //No puede quedarse en la misma casilla.
        if (filaOrigen == filaDestino &&
            columnaOrigen == columnaDestino) {

            return false;

        }

        //Debe moverse solo en fila o columna.
        if (filaOrigen != filaDestino &&
            columnaOrigen != columnaDestino) {

            return false;

        }

        //Movimiento horizontal.
        if (filaOrigen == filaDestino) {

            int paso;

            if (columnaDestino > columnaOrigen) {

                paso = 1;

            } else {

                paso = -1;

            }

            for (int c = columnaOrigen + paso;
                 c != columnaDestino;
                 c += paso) {

                if (!tablero.estaVacia(filaOrigen, c)) {

                    return false;

                }

            }

        }

        //Movimiento vertical.
        else {

            int paso;

            if (filaDestino > filaOrigen) {

                paso = 1;

            } else {

                paso = -1;

            }

            for (int f = filaOrigen + paso;
                 f != filaDestino;
                 f += paso) {

                if (!tablero.estaVacia(f, columnaOrigen)) {

                    return false;

                }

            }

        }

        //Destino vacío.
        if (tablero.estaVacia(filaDestino, columnaDestino)) {

            return true;

        }

        //Destino ocupado por enemigo.
        return tablero.hayEnemigo(
                filaDestino,
                columnaDestino,
                esBlanca());

    }

}