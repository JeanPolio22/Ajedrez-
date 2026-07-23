package ajedrez;

import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Peon;
import Piezas.Pieza;
import Piezas.Reina;
import Piezas.Rey;
import Piezas.Torre;
import Tablero.PanelTablero;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import marco.Ventana;

public class Juego {

    private Tablero tablero;
    private boolean turnoBlancas;
    private Pieza piezaSeleccionada;
    private PanelTablero panelTablero;
    private boolean juegoTerminado;

    // Listas para almacenar las piezas capturadas
    private List<Pieza> capturadasPorBlancas;
    private List<Pieza> capturadasPorNegras;
    private Ventana ventanaPrincipal;

    public Juego() {
        tablero = new Tablero();
        turnoBlancas = true;
        piezaSeleccionada = null;
        juegoTerminado = false;
        capturadasPorBlancas = new ArrayList<>();
        capturadasPorNegras = new ArrayList<>();
        colocarPiezas();
    }

    public Juego(PanelTablero panelTablero) {
        this();
        this.panelTablero = panelTablero;
    }

    public void setPanelTablero(PanelTablero panelTablero) {
        this.panelTablero = panelTablero;
    }

    public void setVentanaPrincipal(Ventana ventana) {
        this.ventanaPrincipal = ventana;
    }

    public boolean isTurnoBlancas() {
        return turnoBlancas;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    private void colocarPiezas() {
        tablero.colocarPieza(new Torre(false, 0, 0), 0, 0);
        tablero.colocarPieza(new Caballo(false, 0, 1), 0, 1);
        tablero.colocarPieza(new Alfil(false, 0, 2), 0, 2);
        tablero.colocarPieza(new Reina(false, 0, 3), 0, 3);
        tablero.colocarPieza(new Rey(false, 0, 4), 0, 4);
        tablero.colocarPieza(new Alfil(false, 0, 5), 0, 5);
        tablero.colocarPieza(new Caballo(false, 0, 6), 0, 6);
        tablero.colocarPieza(new Torre(false, 0, 7), 0, 7);

        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(false, 1, c), 1, c);
        }

        tablero.colocarPieza(new Torre(true, 7, 0), 7, 0);
        tablero.colocarPieza(new Caballo(true, 7, 1), 7, 1);
        tablero.colocarPieza(new Alfil(true, 7, 2), 7, 2);
        tablero.colocarPieza(new Reina(true, 7, 3), 7, 3);
        tablero.colocarPieza(new Rey(true, 7, 4), 7, 4);
        tablero.colocarPieza(new Alfil(true, 7, 5), 7, 5);
        tablero.colocarPieza(new Caballo(true, 7, 6), 7, 6);
        tablero.colocarPieza(new Torre(true, 7, 7), 7, 7);

        for(int c = 0; c < 8; c++){
            tablero.colocarPieza(new Peon(true, 6, c), 6, c);
        }
    }

    public Tablero getTablero(){
        return tablero;
    }

    public boolean estaEnJaque(boolean esBlanca) {
        int[] posRey = tablero.buscarRey(esBlanca);
        if (posRey == null) return false;
        return tablero.esCasillaAtacada(posRey[0], posRey[1], !esBlanca);
    }

    public boolean esMaterialInsuficiente() {
        int contadorPiezas = 0;
        boolean hayOtrasPiezas = false;

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = tablero.getPieza(f, c);
                if (p != null) {
                    contadorPiezas++;
                    if (!(p instanceof Rey)) {
                        hayOtrasPiezas = true;
                    }
                }
            }
        }

        return (contadorPiezas == 2 && !hayOtrasPiezas);
    }

    public boolean tieneMovimientosLegales(boolean esBlanca) {
        for (int fOrigen = 0; fOrigen < 8; fOrigen++) {
            for (int cOrigen = 0; cOrigen < 8; cOrigen++) {
                Pieza p = tablero.getPieza(fOrigen, cOrigen);
                if (p != null && p.esBlanca() == esBlanca) {
                    for (int fDestino = 0; fDestino < 8; fDestino++) {
                        for (int cDestino = 0; cDestino < 8; cDestino++) {
                            if (p.movimientoValido(fDestino, cDestino, tablero)) {
                                Pieza piezaCapturada = tablero.getPieza(fDestino, cDestino);
                                int origenF = p.getFila();
                                int origenC = p.getColumna();

                                tablero.moverPieza(origenF, origenC, fDestino, cDestino, false);
                                boolean sigueEnJaque = estaEnJaque(esBlanca);
                                
                                tablero.moverPieza(fDestino, cDestino, origenF, origenC, false);
                                if (piezaCapturada != null) {
                                    tablero.colocarPieza(piezaCapturada, fDestino, cDestino);
                                }

                                if (!sigueEnJaque) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void registrarCaptura(Pieza piezaCapturada, boolean capturoBlanca) {
        if (piezaCapturada != null) {
            if (capturoBlanca) {
                capturadasPorBlancas.add(piezaCapturada);
            } else {
                capturadasPorNegras.add(piezaCapturada);
            }
            if (ventanaPrincipal != null) {
                ventanaPrincipal.actualizarPanelesCapturadas(capturadasPorBlancas, capturadasPorNegras);
            }
        }
    }

    private void verificarPromocion(Pieza pieza, int filaDestino, int columnaDestino, PanelTablero panel) {
        if (pieza instanceof Peon) {
            if ((pieza.esBlanca() && filaDestino == 0) || (!pieza.esBlanca() && filaDestino == 7)) {
                boolean esBlanca = pieza.esBlanca();
                Pieza nuevaPieza;

                if (!esBlanca) {
                    nuevaPieza = new Reina(esBlanca, filaDestino, columnaDestino);
                } else {
                    String[] opciones = {"Reina", "Torre", "Alfil", "Caballo"};
                    
                    int seleccion = JOptionPane.showOptionDialog(
                            panel,
                            "¡Tu peón ha llegado al final! Elige la pieza para la promoción:",
                            "Promoción de Peón",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[0]
                    );

                    switch (seleccion) {
                        case 1:
                            nuevaPieza = new Torre(esBlanca, filaDestino, columnaDestino);
                            break;
                        case 2:
                            nuevaPieza = new Alfil(esBlanca, filaDestino, columnaDestino);
                            break;
                        case 3:
                            nuevaPieza = new Caballo(esBlanca, filaDestino, columnaDestino);
                            break;
                        case 0:
                        default:
                            nuevaPieza = new Reina(esBlanca, filaDestino, columnaDestino);
                            break;
                    }
                }

                tablero.colocarPieza(nuevaPieza, filaDestino, columnaDestino);
            }
        }
    }

    public void ejecutarTurnoRobot(PanelTablero panel) {
        if (juegoTerminado || turnoBlancas) return;

        int mejorValorPuntuacion = -9999;
        int[] mejorMovimiento = null; 
        Pieza piezaMovidaSeleccionada = null;

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = tablero.getPieza(f, c);
                if (p != null && !p.esBlanca()) {
                    for (int fd = 0; fd < 8; fd++) {
                        for (int cd = 0; cd < 8; cd++) {
                            if (p.movimientoValido(fd, cd, tablero)) {
                                
                                Pieza destinoTemp = tablero.getPieza(fd, cd);
                                int origF = p.getFila();
                                int origC = p.getColumna();

                                tablero.moverPieza(origF, origC, fd, cd, false);
                                boolean reyExpuesto = estaEnJaque(false);
                                tablero.moverPieza(fd, cd, origF, origC, false);
                                if (destinoTemp != null) {
                                    tablero.colocarPieza(destinoTemp, fd, cd);
                                }

                                if (!reyExpuesto) {
                                    int puntuacionMovimiento = 0;

                                    if (destinoTemp != null) {
                                        puntuacionMovimiento += 10; 
                                    }

                                    puntuacionMovimiento += fd; 

                                    if (puntuacionMovimiento > mejorValorPuntuacion) {
                                        mejorValorPuntuacion = puntuacionMovimiento;
                                        mejorMovimiento = new int[]{origF, origC, fd, cd};
                                        piezaMovidaSeleccionada = p;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (mejorMovimiento != null) {
            int origF = mejorMovimiento[0];
            int origC = mejorMovimiento[1];
            int fd = mejorMovimiento[2];
            int cd = mejorMovimiento[3];

            Pieza piezaCapturada = tablero.getPieza(fd, cd);
            tablero.moverPieza(origF, origC, fd, cd);
            
            registrarCaptura(piezaCapturada, false); 
            verificarPromocion(piezaMovidaSeleccionada, fd, cd, panel);
            
            if (esMaterialInsuficiente()) {
                juegoTerminado = true;
                if (ventanaPrincipal != null) {
                    ventanaPrincipal.detenerTodosLosRelojes();
                }
                JOptionPane.showMessageDialog(panel, "¡Empate por material insuficiente (Rey vs Rey)!", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            turnoBlancas = true;
            if (ventanaPrincipal != null) {
                ventanaPrincipal.cambiarTurnoRelojes(turnoBlancas);
            }

            if (estaEnJaque(true)) {
                if (!tieneMovimientosLegales(true)) {
                    juegoTerminado = true;
                    if (ventanaPrincipal != null) {
                        ventanaPrincipal.detenerTodosLosRelojes();
                    }
                    JOptionPane.showMessageDialog(panel, "¡Jaque Mate! El robot ha ganado.", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public boolean seleccionarCasilla(int fila, int columna, PanelTablero panel) {
        if (juegoTerminado) return false;

        Pieza pieza = tablero.getPieza(fila, columna);

        if(piezaSeleccionada == null){
            if(pieza == null){
                return false;
            }

            if(pieza.esBlanca() != turnoBlancas){
                return false;
            }

            piezaSeleccionada = pieza;

            if (panel != null) {
                panel.restaurarColores();
                
                if (estaEnJaque(turnoBlancas)) {
                    int[] posRey = tablero.buscarRey(turnoBlancas);
                    if (posRey != null) {
                        panel.iluminarJaque(posRey[0], posRey[1]);
                    }
                }

                for (int f = 0; f < 8; f++) {
                    for (int c = 0; c < 8; c++) {
                        if (piezaSeleccionada.movimientoValido(f, c, tablero)) {
                            Pieza destinoTemp = tablero.getPieza(f, c);
                            int origF = piezaSeleccionada.getFila();
                            int origC = piezaSeleccionada.getColumna();

                            tablero.moverPieza(origF, origC, f, c, false);
                            boolean reyExpuesto = estaEnJaque(turnoBlancas);
                            tablero.moverPieza(f, c, origF, origC, false);
                            if (destinoTemp != null) {
                                tablero.colocarPieza(destinoTemp, f, c);
                            }

                            if (!reyExpuesto) {
                                boolean esCaptura = tablero.hayEnemigo(f, c, piezaSeleccionada.esBlanca());
                                panel.iluminarCasilla(f, c, esCaptura);
                            }
                        }
                    }
                }
            }
            return false;
        }

        if(piezaSeleccionada.movimientoValido(fila, columna, tablero)){
            Pieza destinoTemp = tablero.getPieza(fila, columna);
            int origF = piezaSeleccionada.getFila();
            int origC = piezaSeleccionada.getColumna();

            tablero.moverPieza(origF, origC, fila, columna, false);
            boolean reyExpuesto = estaEnJaque(turnoBlancas);
            tablero.moverPieza(fila, columna, origF, origC, false);
            if (destinoTemp != null) {
                tablero.colocarPieza(destinoTemp, fila, columna);
            }

            if (!reyExpuesto) {
                Pieza piezaCapturada = tablero.getPieza(fila, columna);
                boolean turnoActualBlanco = turnoBlancas;

                tablero.moverPieza(origF, origC, fila, columna);
                
                registrarCaptura(piezaCapturada, turnoActualBlanco);
                verificarPromocion(piezaSeleccionada, fila, columna, panel);
                
                if (esMaterialInsuficiente()) {
                    juegoTerminado = true;
                    if (ventanaPrincipal != null) {
                        ventanaPrincipal.detenerTodosLosRelojes();
                    }
                    panel.restaurarColores();
                    JOptionPane.showMessageDialog(panel, "¡Empate por material insuficiente (Rey vs Rey)!", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                    piezaSeleccionada = null;
                    return true;
                }

                boolean oponenteEsBlanco = !turnoBlancas;
                turnoBlancas = !turnoBlancas;

                // Cambiamos el turno del reloj de ajedrez
                if (ventanaPrincipal != null) {
                    ventanaPrincipal.cambiarTurnoRelojes(turnoBlancas);
                }

                if (estaEnJaque(oponenteEsBlanco)) {
                    if (!tieneMovimientosLegales(oponenteEsBlanco)) {
                        juegoTerminado = true;
                        if (ventanaPrincipal != null) {
                            ventanaPrincipal.detenerTodosLosRelojes();
                        }
                        String ganador = oponenteEsBlanco ? "Negras" : "Blancas";
                        panel.restaurarColores();
                        int[] posRey = tablero.buscarRey(oponenteEsBlanco);
                        if (posRey != null) {
                            panel.iluminarJaque(posRey[0], posRey[1]);
                        }
                        JOptionPane.showMessageDialog(panel, "¡Jaque Mate! El juego ha terminado. Ganaron las " + ganador + ".", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                        piezaSeleccionada = null;
                        return true;
                    }
                }
            }
        }

        piezaSeleccionada = null;
        if (panel != null && !juegoTerminado) {
            panel.restaurarColores();
            if (estaEnJaque(turnoBlancas)) {
                int[] posRey = tablero.buscarRey(turnoBlancas);
                if (posRey != null) {
                    panel.iluminarJaque(posRey[0], posRey[1]);
                }
            }
        }

        return true;
    }
}