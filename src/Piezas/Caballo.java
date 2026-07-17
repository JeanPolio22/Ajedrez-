package Piezas;

import Tablero.Tablero;

public class Caballo extends Pieza {

    public Caballo(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        // Mantenemos tu símbolo original de caballo
        return "♞";
    }

    @Override
    public boolean movimientoValido(int filaDestino, int columnaDestino, Tablero tablero) {
        
        // 1. Validar que la posición esté dentro del tablero
        if (!tablero.posicionValida(filaDestino, columnaDestino)) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();

        // 2. Calcular la diferencia absoluta de filas y columnas
        int difFila = Math.abs(filaDestino - filaOrigen);
        int difColumna = Math.abs(columnaDestino - columnaOrigen);

        // 3. El caballo se mueve en "L": 2 casillas en una dirección y 1 en la otra
        boolean esMovimientoEnL = (difFila == 2 && difColumna == 1) || 
                                   (difFila == 1 && difColumna == 2);

        if (!esMovimientoEnL) {
            return false;
        }

        // 4. Validar destino: Debe estar vacío o tener un enemigo
        if (tablero.estaVacia(filaDestino, columnaDestino)) {
            return true;
        } else {
            // Usa tu método existente para verificar si es enemigo y evitar capturar piezas propias
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }
    }
}
