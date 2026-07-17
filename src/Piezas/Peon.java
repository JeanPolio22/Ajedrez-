package Piezas;

import Tablero.Tablero;

public class Peon extends Pieza {

    // Constructor.
    public Peon(boolean blanca, int fila, int columna) {
        super(blanca, fila, columna);
    }

    @Override
    public String getSimbolo() {
        // Mantenemos tu símbolo original para evitar problemas de visualización
        return "♟";
    }

    @Override
    public boolean movimientoValido(int filaDestino, int columnaDestino, Tablero tablero) {
        
        if (!tablero.posicionValida(filaDestino, columnaDestino)) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();
        
        int direccion = esBlanca() ? -1 : 1;

        // 1. MOVIMIENTO HACIA ADELANTE (Sin capturar)
        if (columnaOrigen == columnaDestino) {
            if (filaDestino == filaOrigen + direccion) {
                return tablero.estaVacia(filaDestino, columnaDestino);
            }
            
            if (!haMovido()) {
                if (filaDestino == filaOrigen + (2 * direccion)) {
                    return tablero.estaVacia(filaOrigen + direccion, columnaOrigen)
                            && tablero.estaVacia(filaDestino, columnaDestino);
                }
            }
        }

        // 2. CAPTURA EN DIAGONAL
        if (Math.abs(columnaDestino - columnaOrigen) == 1 && filaDestino == filaOrigen + direccion) {
            return tablero.hayEnemigo(filaDestino, columnaDestino, esBlanca());
        }

        return false;
    }
}