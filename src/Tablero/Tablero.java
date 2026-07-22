package Tablero;

import Piezas.Pieza;

public class Tablero {

    private Pieza[][] casillas;

    public Tablero() {
        casillas = new Pieza[8][8];
    }

    public void colocarPieza(Pieza pieza, int fila, int columna) {
        if (posicionValida(fila, columna)) {
            casillas[fila][columna] = pieza;
            if (pieza != null) {
                pieza.setFila(fila);
                pieza.setColumna(columna);
            }
        }
    }

    public Pieza getPieza(int fila, int columna) {
        if (posicionValida(fila, columna)) {
            return casillas[fila][columna];
        }
        return null;
    }

    // Movimiento real del jugador (actualiza haMovido a true)
    public void moverPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        moverPieza(filaOrigen, columnaOrigen, filaDestino, columnaDestino, true);
    }

    // Movimiento de prueba (NO altera haMovido)
    public void moverPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino, boolean actualizarEstado) {
        if (posicionValida(filaOrigen, columnaOrigen) && posicionValida(filaDestino, columnaDestino)) {
            Pieza pieza = casillas[filaOrigen][columnaOrigen];
            casillas[filaDestino][columnaDestino] = pieza;
            casillas[filaOrigen][columnaOrigen] = null;

            if (pieza != null) {
                pieza.setFila(filaDestino);
                pieza.setColumna(columnaDestino);
                if (actualizarEstado) {
                    pieza.setHaMovido(true);
                }
            }
        }
    }

    public boolean posicionValida(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }

    public boolean estaVacia(int fila, int columna) {
        return getPieza(fila, columna) == null;
    }

    public boolean hayEnemigo(int fila, int columna, boolean esBlanca) {
        Pieza pieza = getPieza(fila, columna);
        if (pieza == null) {
            return false;
        }
        return pieza.esBlanca() != esBlanca;
    }

    public int[] buscarRey(boolean esBlanca) {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = getPieza(f, c);
                if (p != null && p instanceof Piezas.Rey && p.esBlanca() == esBlanca) {
                    return new int[]{f, c};
                }
            }
        }
        return null;
    }

    public boolean esCasillaAtacada(int fila, int columna, boolean atacanteEsBlanco) {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = getPieza(f, c);
                if (p != null && p.esBlanca() == atacanteEsBlanco) {
                    if (p.movimientoValido(fila, columna, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}