package Tablero;

import ajedrez.Juego;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelTablero extends JPanel {

    private JButton[][] casillas;

    // NUEVO
    private Juego juego;


    public PanelTablero() {

        setLayout(new GridLayout(8,8));

        casillas = new JButton[8][8];

        // Creamos el juego.
        juego = new Juego();

        crearTablero();

    }


    private void crearTablero() {

        Color claro = new Color(240,217,181);
        Color oscuro = new Color(181,136,99);


        for(int fila=0; fila<8; fila++) {

            for(int columna=0; columna<8; columna++) {

                JButton boton = new JButton();

                boton.setFocusable(false);

                boton.setBorder(
                        BorderFactory.createEmptyBorder());


                if((fila+columna)%2==0) {

                    boton.setBackground(claro);

                }

                else {

                    boton.setBackground(oscuro);

                }


                final int f = fila;
                final int c = columna;


                // NUEVO
                boton.addActionListener(e -> {

                    juego.seleccionarCasilla(f,c);

                });


                casillas[fila][columna]=boton;

                add(boton);

            }

        }

    }


    public JButton getCasilla(int fila,int columna) {

        return casillas[fila][columna];

    }

}