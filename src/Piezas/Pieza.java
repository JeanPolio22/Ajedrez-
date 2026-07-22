package Piezas;

import Tablero.Tablero;

public abstract class Pieza {
    private boolean blanca;
    private int fila;
    private int columna;
    private boolean haMovido = false;

    public Pieza(boolean blanca, int fila, int columna) {
        this.blanca = blanca;
        this.fila = fila;
        this.columna = columna;
    }

    public boolean esBlanca() {
        return blanca;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean haMovido() {
        return haMovido;
    }

    public void setHaMovido(boolean haMovido) {
        this.haMovido = haMovido;
    }

    public abstract String getSimbolo();

    public abstract boolean movimientoValido(int filaDestino, int columnaDestino, Tablero tablero);
}