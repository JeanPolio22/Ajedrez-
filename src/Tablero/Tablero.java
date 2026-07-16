package Tablero;

import Piezas.Pieza;

public class Tablero {

    //Matriz que almacenará las piezas del juego.
    private Pieza[][] tablero;


    //Constructor.
    public Tablero() {

        tablero = new Pieza[8][8];

    }


    //Devuelve la pieza que se encuentra en una posición.
    public Pieza getPieza(int fila, int columna) {

        return tablero[fila][columna];

    }


    //Coloca una pieza en el tablero.
    public void colocarPieza(Pieza pieza, int fila, int columna) {

        tablero[fila][columna] = pieza;

    }


    //Elimina una pieza del tablero.
    public void eliminarPieza(int fila, int columna) {

        tablero[fila][columna] = null;

    }


    //Permite mover una pieza dentro del tablero.
    public void moverPieza(int filaOrigen,
                           int columnaOrigen,
                           int filaDestino,
                           int columnaDestino) {

        Pieza pieza = tablero[filaOrigen][columnaOrigen];

        tablero[filaDestino][columnaDestino] = pieza;

        tablero[filaOrigen][columnaOrigen] = null;


        //Actualizamos la nueva posición de la pieza.
        if (pieza != null) {

            pieza.setFila(filaDestino);
            pieza.setColumna(columnaDestino);

        }

    }


    //Verifica si una casilla está vacía.
    public boolean estaVacia(int fila, int columna) {

        return tablero[fila][columna] == null;

    }
    
    //Verifica si una posición pertenece al tablero.
public boolean posicionValida(int fila, int columna) {

    return fila >= 0 && fila < 8 &&
           columna >= 0 && columna < 8;

}


//Devuelve true si en esa casilla hay una pieza enemiga.
public boolean hayEnemigo(int fila,
                          int columna,
                          boolean blanca) {

    if (estaVacia(fila, columna)) {

        return false;

    }

    return tablero[fila][columna].esBlanca() != blanca;

}
}