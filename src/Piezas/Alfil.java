package Piezas;

import Tablero.Tablero;

public class Alfil extends Pieza {

    public Alfil(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        return "♝"; // Símbolo de alfil
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

        // El alfil se mueve en diagonal (ancho y alto iguales)
        if (difFila != difColumna) {
            return false;
        }

        // Validar que el camino esté despejado
        int pasoFila = (filaDestino > filaOrigen) ? 1 : -1;
        int pasoColumna = (columnaDestino > columnaOrigen) ? 1 : -1;

        int f = filaOrigen + pasoFila;
        int c = columnaOrigen + pasoColumna;

        while (f != filaDestino && c != columnaDestino) {
            if (!tablero.estaVacia(f, c)) {
                return false; // Hay una pieza bloqueando el paso
            }
            f += pasoFila;
            c += pasoColumna;
        }

        // Validar casilla destino (vacía o enemigo)
        if (tablero.estaVacia(filaDestino, columnaDestino)) {
            return true;
        } else {
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }
    }
}