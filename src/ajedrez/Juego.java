package ajedrez;

import Piezas.Pieza;
import Tablero.Tablero;

public class Juego {

    // Tablero lógico del juego.
    private Tablero tablero;

    // Indica si es el turno de las blancas.
    private boolean turnoBlancas;

    // Pieza seleccionada por el jugador.
    private Pieza piezaSeleccionada;

    // Posición seleccionada.
    private int filaSeleccionada;
    private int columnaSeleccionada;


    // Constructor.
    public Juego() {

        tablero = new Tablero();

        turnoBlancas = true;

        piezaSeleccionada = null;

        filaSeleccionada = -1;
        columnaSeleccionada = -1;

    }


    // Devuelve el tablero.
    public Tablero getTablero() {

        return tablero;

    }


    // Devuelve el turno actual.
    public boolean esTurnoBlancas() {

        return turnoBlancas;

    }


    // Cambia el turno.
    public void cambiarTurno() {

        turnoBlancas = !turnoBlancas;

    }


    // Devuelve la pieza seleccionada.
    public Pieza getPiezaSeleccionada() {

        return piezaSeleccionada;

    }


    // Guarda la pieza seleccionada.
    public void setPiezaSeleccionada(Pieza pieza) {

        piezaSeleccionada = pieza;

    }


    // Guarda la casilla seleccionada.
    public void seleccionarCasilla(int fila, int columna) {

        filaSeleccionada = fila;
        columnaSeleccionada = columna;

        System.out.println("----------------------------");
        System.out.println("Fila seleccionada : " + fila);
        System.out.println("Columna seleccionada : " + columna);
        System.out.println("----------------------------");

    }


    // Obtiene la fila seleccionada.
    public int getFilaSeleccionada() {

        return filaSeleccionada;

    }


    // Obtiene la columna seleccionada.
    public int getColumnaSeleccionada() {

        return columnaSeleccionada;

    }


    // Reinicia la selección.
    public void limpiarSeleccion() {

        piezaSeleccionada = null;

        filaSeleccionada = -1;
        columnaSeleccionada = -1;

    }

}