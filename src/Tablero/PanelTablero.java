package Tablero;

import Piezas.Pieza;
import ajedrez.Juego;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelTablero extends JPanel {

    private JButton[][] casillas;

    private Juego juego;

    public PanelTablero() {

        setLayout(new GridLayout(8,8));

        casillas = new JButton[8][8];

        juego = new Juego();

        crearTablero();

        actualizarTablero();

    }

    private void crearTablero() {

        Color claro = new Color(240,217,181);
        Color oscuro = new Color(181,136,99);

        for(int fila=0; fila<8; fila++) {

            for(int columna=0; columna<8; columna++) {

                JButton boton = new JButton();

                boton.setFocusable(false);

                boton.setBorder(BorderFactory.createEmptyBorder());

                boton.setFocusPainted(false);

                boton.setFont(new Font("Dialog", Font.PLAIN, 44));

                if((fila+columna)%2==0) {

                    boton.setBackground(claro);

                }
                else {

                    boton.setBackground(oscuro);

                }

                final int f = fila;
                final int c = columna;

                boton.addActionListener(e -> {

                    juego.seleccionarCasilla(f,c);

                });

                casillas[fila][columna] = boton;

                add(boton);

            }

        }

    }

    public void actualizarTablero() {

        for(int fila=0; fila<8; fila++) {

            for(int columna=0; columna<8; columna++) {

                Pieza pieza = juego.getTablero().getPieza(fila,columna);

                if(pieza == null) {

                    casillas[fila][columna].setText("");

                }
                else {

                    casillas[fila][columna].setText(pieza.getSimbolo());

                    if(pieza.esBlanca()) {

                        casillas[fila][columna].setForeground(Color.WHITE);

                    }
                    else {

                        casillas[fila][columna].setForeground(Color.BLACK);

                    }

                }

            }

        }

    }

    public JButton getCasilla(int fila,int columna) {

        return casillas[fila][columna];

    }

}