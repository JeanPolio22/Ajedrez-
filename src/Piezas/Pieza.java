package Piezas;

import Tablero.Tablero;

public abstract class Pieza {

    // Determina si la pieza es blanca o negra.
    private boolean blanca;

    // Posición actual de la pieza.
    private int fila;
    private int columna;

    // Indica si la pieza fue capturada.
    private boolean capturada;
    
    // NUEVO: Indica si la pieza ya se ha movido (vital para el Enroque).
    private boolean haMovido; 

    // Constructor.
    public Pieza(boolean blanca, int fila, int columna) {
        this.blanca = blanca;
        this.fila = fila;
        this.columna = columna;
        this.capturada = false;
        this.haMovido = false; // Comienza en false
    }

    // Indica si la pieza es blanca.
    public boolean esBlanca() {
        return blanca;
    }

    // Modifica el color de la pieza.
    public void setBlanca(boolean blanca) {
        this.blanca = blanca;
    }

    // Obtiene la fila.
    public int getFila() {
        return fila;
    }

    // Modifica la fila.
    public void setFila(int fila) {
        this.fila = fila;
    }

    // Obtiene la columna.
    public int getColumna() {
        return columna;
    }

    // Modifica la columna.
    public void setColumna(int columna) {
        this.columna = columna;
    }

    // Comprueba si la pieza fue capturada.
    public boolean estaCapturada() {
        return capturada;
    }

    // Modifica el estado de captura.
    public void setCapturada(boolean capturada) {
        this.capturada = capturada;
    }

    // NUEVO: Verifica si la pieza se ha movido.
    public boolean haMovido() {
        return haMovido;
    }

    // NUEVO: Modifica el estado de movimiento.
    public void setHaMovido(boolean haMovido) {
        this.haMovido = haMovido;
    }

    // Mueve la pieza.
    public void mover(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.haMovido = true; // Al usar este método, la pieza registra que se movió
    }

    // Devuelve el emoji de la pieza.
    public abstract String getSimbolo();

    // Cada pieza implementa su propia lógica de movimiento.
    public abstract boolean movimientoValido(int filaDestino,
                                             int columnaDestino,
                                             Tablero tablero);

}