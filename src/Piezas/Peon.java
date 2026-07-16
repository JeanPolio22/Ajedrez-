package Piezas;

import Tablero.Tablero;

public class Peon extends Pieza {

    public Peon(boolean blanca, int fila, int columna) {

        super(blanca, fila, columna);

    }

    @Override
    public String getSimbolo() {

        // Usamos siempre el peón negro
        return "♟";

    }

    @Override
    public boolean movimientoValido(int filaDestino,
                                    int columnaDestino,
                                    Tablero tablero) {

        return false;

    }

}