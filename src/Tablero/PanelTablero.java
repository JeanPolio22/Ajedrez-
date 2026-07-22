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

    private final Color colorClaro = new Color(240, 217, 181);
    private final Color colorOscuro = new Color(181, 136, 99);

    public PanelTablero() {
        setLayout(new GridLayout(8, 8));
        casillas = new JButton[8][8];
        juego = new Juego();

        crearTablero();
        actualizarTablero();
    }

    private void crearTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                JButton boton = new JButton();

                boton.setFocusable(false);
                boton.setFocusPainted(false);
                boton.setBorder(BorderFactory.createEmptyBorder());
                boton.setFont(new Font("Dialog", Font.PLAIN, 42));

                if ((fila + columna) % 2 == 0) {
                    boton.setBackground(colorClaro);
                } else {
                    boton.setBackground(colorOscuro);
                }

                final int f = fila;
                final int c = columna;

                boton.addActionListener(e -> {
                    juego.seleccionarCasilla(f, c, this);
                    actualizarTablero();
                });

                casillas[fila][columna] = boton;
                add(boton);
            }
        }
    }

    public void actualizarTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = juego.getTablero().getPieza(fila, columna);

                if (pieza == null) {
                    casillas[fila][columna].setText("");
                } else {
                    casillas[fila][columna].setText(pieza.getSimbolo());

                    if (pieza.esBlanca()) {
                        casillas[fila][columna].setForeground(Color.WHITE);
                    } else {
                        casillas[fila][columna].setForeground(Color.BLACK);
                    }
                }
            }
        }
    }

    // NUEVO: Recibe un indicador de captura (esCaptura = true pinta rojo, false pinta verde)
    public void iluminarCasilla(int fila, int columna, boolean esCaptura) {
        Color verdeClaro = new Color(170, 215, 125);
        Color verdeOscuro = new Color(135, 185, 90);
        
        Color rojoClaro = new Color(235, 120, 120);
        Color rojoOscuro = new Color(205, 90, 90);

        if (esCaptura) {
            if ((fila + columna) % 2 == 0) {
                casillas[fila][columna].setBackground(rojoClaro);
            } else {
                casillas[fila][columna].setBackground(rojoOscuro);
            }
        } else {
            if ((fila + columna) % 2 == 0) {
                casillas[fila][columna].setBackground(verdeClaro);
            } else {
                casillas[fila][columna].setBackground(verdeOscuro);
            }
        }
    }

    public void restaurarColores() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if ((fila + columna) % 2 == 0) {
                    casillas[fila][columna].setBackground(colorClaro);
                } else {
                    casillas[fila][columna].setBackground(colorOscuro);
                }
            }
        }
    }

    public JButton getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }
}