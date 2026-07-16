package Piezas;

public class Pieza {

    //Determina si la pieza es blanca o negra.
    private boolean blanca;

    //Posición de la pieza dentro del tablero.
    private int fila;
    private int columna;

    //Constructor.
    public Pieza(boolean blanca, int fila, int columna) {
        this.blanca = blanca;
        this.fila = fila;
        this.columna = columna;
    }

    //Indica si la pieza es blanca.
    public boolean esBlanca() {
        return blanca;
    }

    //Permite cambiar el color de la pieza.
    public void setBlanca(boolean blanca) {
        this.blanca = blanca;
    }

    //Obtiene la fila donde se encuentra la pieza.
    public int getFila() {
        return fila;
    }

    //Modifica la fila.
    public void setFila(int fila) {
        this.fila = fila;
    }


    //Obtiene la columna donde se encuentra la pieza.
    public int getColumna() {
        return columna;
    }


    //Modifica la columna.
    public void setColumna(int columna) {

        this.columna = columna;

    }

}