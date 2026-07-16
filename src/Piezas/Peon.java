package Piezas;

import Tablero.Tablero;

public class Peon extends Pieza {

    //Constructor.
    public Peon(boolean blanca, int fila, int columna) {

        super(blanca, fila, columna);

    }

    //Devuelve el símbolo del peón.
    @Override
    public String getSimbolo() {

        return esBlanca() ? "♙" : "♟";

    }

    @Override
    public boolean movimientoValido(int filaDestino,
                                    int columnaDestino,
                                    Tablero tablero) {

        // Se implementará más adelante.
        return false;

    }

}