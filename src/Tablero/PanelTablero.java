package Tablero;

import Piezas.Pieza;
import ajedrez.Juego;
import ajedrez.MotorRobot;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelTablero extends JPanel {

    private JButton[][] casillas;
    private Juego juego;
    private boolean modoRobot;

    private final Color colorClaro = new Color(240, 217, 181);
    private final Color colorOscuro = new Color(181, 136, 99);

    public PanelTablero(boolean modoRobot) {
        this.modoRobot = modoRobot;
        setLayout(new GridLayout(8, 8));
        casillas = new JButton[8][8];
        juego = new Juego();

        crearTablero();
        actualizarTablero();
    }

    private void crearTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                BotonCasilla boton = new BotonCasilla();

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
                    if (modoRobot && !juego.isTurnoBlancas()) {
                        return;
                    }

                    juego.seleccionarCasilla(f, c, this);
                    actualizarTablero();

                    if (modoRobot && !juego.isJuegoTerminado() && !juego.isTurnoBlancas()) {
                        MotorRobot motor = new MotorRobot(this, juego);
                        Thread hiloRobot = new Thread(motor);
                        hiloRobot.start();
                    }
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
                        casillas[fila][columna].setForeground(new Color(20, 20, 20));
                    }
                }
            }
        }
    }

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

    public void iluminarJaque(int fila, int columna) {
        casillas[fila][columna].setBackground(new Color(255, 69, 0));
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

    // Subclase para aplicar contorno a piezas blancas (borde negro) y negras (borde blanco)
    private class BotonCasilla extends JButton {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            String texto = getText();
            if (texto != null && !texto.isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());

                int x = (getWidth() - g2.getFontMetrics().stringWidth(texto)) / 2;
                int y = (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;

                Color colorOriginal = getForeground();

                if (colorOriginal.equals(Color.WHITE)) {
                    // Contorno negro para piezas blancas
                    g2.setColor(new Color(30, 30, 30));
                    g2.drawString(texto, x - 1, y);
                    g2.drawString(texto, x + 1, y);
                    g2.drawString(texto, x, y - 1);
                    g2.drawString(texto, x, y + 1);

                    g2.setColor(Color.WHITE);
                    g2.drawString(texto, x, y);
                } else {
                    // Contorno blanco para piezas negras
                    g2.setColor(new Color(240, 240, 240));
                    g2.drawString(texto, x - 1, y);
                    g2.drawString(texto, x + 1, y);
                    g2.drawString(texto, x, y - 1);
                    g2.drawString(texto, x, y + 1);

                    g2.setColor(new Color(20, 20, 20));
                    g2.drawString(texto, x, y);
                }

                g2.dispose();
            }
        }
    }
}