package Tablero;

import java.awt.*;

import javax.swing.*;


public class PanelTablero extends JPanel {

    private JButton[][] casillas;

    public PanelTablero() {

        setLayout(new GridLayout(8,8));

        casillas = new JButton[8][8];

        crearTablero();

    }

    private void crearTablero() {

        Color claro = new Color(240,217,181);
        Color oscuro = new Color(181,136,99);
        for(int fila=0; fila<8; fila++) {

            for(int columna=0; columna<8; columna++) {

                JButton boton = new JButton();

                boton.setFocusable(false);
                boton.setBorder(BorderFactory.createEmptyBorder());

                if((fila + columna) % 2 == 0)
                    boton.setBackground(claro);
                else
                    boton.setBackground(oscuro);

                casillas[fila][columna] = boton;

                add(boton);

            }

        }

    }

}