package ajedrez;

import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Tablero;

public class Juego {

    private Tablero tablero;

    private boolean turnoBlancas;

    private Pieza piezaSeleccionada;

    public Juego() {

        tablero = new Tablero();

        turnoBlancas = true;

        piezaSeleccionada = null;

        colocarPiezas();

    }

    private void colocarPiezas() {

        // Torres negras
        tablero.colocarPieza(new Torre(false,0,0),0,0);
        tablero.colocarPieza(new Torre(false,0,7),0,7);

        // Torres blancas
        tablero.colocarPieza(new Torre(true,7,0),7,0);
        tablero.colocarPieza(new Torre(true,7,7),7,7);

        // Peones negros
        for(int c=0;c<8;c++){

            tablero.colocarPieza(new Peon(false,1,c),1,c);

        }

        // Peones blancos
        for(int c=0;c<8;c++){

            tablero.colocarPieza(new Peon(true,6,c),6,c);

        }

    }

    public Tablero getTablero(){

        return tablero;

    }

    public boolean seleccionarCasilla(int fila,int columna){

        Pieza pieza = tablero.getPieza(fila,columna);

        // No hay pieza seleccionada todavía.
        if(piezaSeleccionada == null){

            if(pieza == null){

                return false;

            }

            // Solo puede seleccionar piezas de su turno.
            if(pieza.esBlanca() != turnoBlancas){

                return false;

            }

            piezaSeleccionada = pieza;

            return false;

        }

        // Intentar mover.
        if(piezaSeleccionada.movimientoValido(fila,columna,tablero)){

            tablero.moverPieza(

                    piezaSeleccionada.getFila(),
                    piezaSeleccionada.getColumna(),
                    fila,
                    columna

            );

            turnoBlancas = !turnoBlancas;

        }

        piezaSeleccionada = null;

        return true;

    }

}