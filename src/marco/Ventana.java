package marco;

import Piezas.Pieza;
import Tablero.PanelTablero;
import ajedrez.Juego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Ventana extends JFrame {

    private PanelTablero panelTablero;
    private Juego juego;
    private boolean modoRobot;
    
    private JLabel lblRelojBlancas;
    private JLabel lblRelojNegras;
    private JLabel lblTextoNegras;
    private Thread hiloRelojBlanco;
    private Thread hiloRelojNegro;
    private RelojJugador tareaRelojBlanco;
    private RelojJugador tareaRelojNegro;

    private JPanel panelCapturadasNegras; 
    private JPanel panelCapturadasBlancas; 

    public Ventana(boolean modoRobot) {
        this.modoRobot = modoRobot;
        setTitle("Ajedrez");
        setSize(800, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        setIconImage(crearIconoAjedrez());

        // Panel superior para los relojes
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(40, 44, 52));
        panelSuperior.setPreferredSize(new Dimension(800, 45));
        panelSuperior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 8));
        
        lblRelojBlancas = new JLabel("Tiempo: 05:00", SwingConstants.CENTER);
        lblRelojBlancas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRelojBlancas.setForeground(Color.WHITE);

        lblRelojNegras = new JLabel("Tiempo: 05:00", SwingConstants.CENTER);
        lblRelojNegras.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRelojNegras.setForeground(Color.WHITE);

        lblTextoNegras = new JLabel("<html><font color='white'><b>| Negras:</b></font></html>");

        panelSuperior.add(new JLabel("<html><font color='white'><b>Blancas:</b></font></html>"));
        panelSuperior.add(lblRelojBlancas);
        
        // Si estamos en modo robot, ocultamos el reloj y el texto de las negras
        if (!modoRobot) {
            panelSuperior.add(lblTextoNegras);
            panelSuperior.add(lblRelojNegras);
        }
        
        add(panelSuperior, BorderLayout.NORTH);

        // Panel Izquierdo: Piezas capturadas por las blancas
        panelCapturadasNegras = new JPanel();
        panelCapturadasNegras.setPreferredSize(new Dimension(95, 600));
        panelCapturadasNegras.setBackground(new Color(230, 233, 239));
        panelCapturadasNegras.setBorder(BorderFactory.createLineBorder(new Color(180, 185, 195), 1));
        panelCapturadasNegras.setLayout(new BoxLayout(panelCapturadasNegras, BoxLayout.Y_AXIS));
        add(panelCapturadasNegras, BorderLayout.WEST);

        // Tablero central
        panelTablero = new PanelTablero(modoRobot);
        add(panelTablero, BorderLayout.CENTER);

        // Panel Derecho: Piezas capturadas por las negras
        panelCapturadasBlancas = new JPanel();
        panelCapturadasBlancas.setPreferredSize(new Dimension(95, 600));
        panelCapturadasBlancas.setBackground(new Color(80, 85, 95));
        panelCapturadasBlancas.setBorder(BorderFactory.createLineBorder(new Color(60, 65, 75), 1));
        panelCapturadasBlancas.setLayout(new BoxLayout(panelCapturadasBlancas, BoxLayout.Y_AXIS));
        add(panelCapturadasBlancas, BorderLayout.EAST);

        juego = panelTablero.getJuego();
        juego.setVentanaPrincipal(this);

        iniciarRelojBlancas();
    }

    public void cambiarTurnoRelojes(boolean esTurnoBlancas) {
        if (modoRobot) {
            // Contra el robot, el reloj de las negras no corre ni se muestra
            if (esTurnoBlancas) {
                iniciarRelojBlancas();
            } else {
                detenerRelojBlancas();
            }
        } else {
            // Modo Jugador vs Jugador normal
            if (esTurnoBlancas) {
                detenerRelojNegras();
                iniciarRelojBlancas();
            } else {
                detenerRelojBlancas();
                iniciarRelojNegras();
            }
        }
    }

    public void iniciarRelojBlancas() {
        if (hiloRelojBlanco == null || !hiloRelojBlanco.isAlive()) {
            int segundos = obtenerSegundosDesdeLabel(lblRelojBlancas);
            tareaRelojBlanco = new RelojJugador(lblRelojBlancas, segundos, true, this);
            hiloRelojBlanco = new Thread(tareaRelojBlanco);
            hiloRelojBlanco.start();
        }
    }

    public void iniciarRelojNegras() {
        if (!modoRobot && (hiloRelojNegro == null || !hiloRelojNegro.isAlive())) {
            int segundos = obtenerSegundosDesdeLabel(lblRelojNegras);
            tareaRelojNegro = new RelojJugador(lblRelojNegras, segundos, false, this);
            hiloRelojNegro = new Thread(tareaRelojNegro);
            hiloRelojNegro.start();
        }
    }

    public void detenerRelojBlancas() {
        if (tareaRelojBlanco != null) {
            tareaRelojBlanco.detener();
        }
    }

    public void detenerRelojNegras() {
        if (tareaRelojNegro != null) {
            tareaRelojNegro.detener();
        }
    }

    public void detenerTodosLosRelojes() {
        detenerRelojBlancas();
        detenerRelojNegras();
    }

    public void terminarPorTiempo(boolean esBlancoPerdedor) {
        detenerTodosLosRelojes();
        String ganador = esBlancoPerdedor ? "Negras (por tiempo)" : "Blancas (por tiempo)";
        JOptionPane.showMessageDialog(this, "¡Se acabó el tiempo! Ganan las " + ganador, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
    }

    private int obtenerSegundosDesdeLabel(JLabel lbl) {
        try {
            String texto = lbl.getText().replace("Tiempo: ", "");
            String[] partes = texto.split(":");
            int min = Integer.parseInt(partes[0]);
            int seg = Integer.parseInt(partes[1]);
            return (min * 60) + seg;
        } catch (Exception e) {
            return 300; 
        }
    }

    public void actualizarPanelesCapturadas(List<Pieza> capturadasBlancas, List<Pieza> capturadasNegras) {
        panelCapturadasNegras.removeAll();
        for (Pieza p : capturadasBlancas) {
            JLabelConContorno lblPieza = new JLabelConContorno(p.getSimbolo(), false);
            lblPieza.setFont(new Font("Dialog", Font.PLAIN, 32));
            lblPieza.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panelCapturadasNegras.add(lblPieza);
        }
        panelCapturadasNegras.revalidate();
        panelCapturadasNegras.repaint();

        panelCapturadasBlancas.removeAll();
        for (Pieza p : capturadasNegras) {
            JLabelConContorno lblPieza = new JLabelConContorno(p.getSimbolo(), true);
            lblPieza.setFont(new Font("Dialog", Font.PLAIN, 32));
            lblPieza.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panelCapturadasBlancas.add(lblPieza);
        }
        panelCapturadasBlancas.revalidate();
        panelCapturadasBlancas.repaint();
    }

    private Image crearIconoAjedrez() {
        BufferedImage imagen = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(41, 128, 185));
        g2.fillOval(2, 2, 28, 28);
        g2.setColor(Color.WHITE);
        g2.fillRect(9, 13, 14, 14);
        g2.fillRect(8, 10, 16, 4);
        g2.fillRect(8, 27, 16, 3);
        g2.dispose();
        return imagen;
    }
}

class JLabelConContorno extends JLabel {
    private boolean esBlanca;

    public JLabelConContorno(String texto, boolean esBlanca) {
        super(texto, SwingConstants.CENTER);
        this.esBlanca = esBlanca;
    }

    @Override
    protected void paintComponent(Graphics g) {
        String texto = getText();
        if (texto != null && !texto.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(getFont());

            int x = (getWidth() - g2.getFontMetrics().stringWidth(texto)) / 2;
            int y = (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;

            if (esBlanca) {
                g2.setColor(new Color(30, 30, 30));
                g2.drawString(texto, x - 1, y);
                g2.drawString(texto, x + 1, y);
                g2.drawString(texto, x, y - 1);
                g2.drawString(texto, x, y + 1);
                g2.setColor(Color.WHITE);
                g2.drawString(texto, x, y);
            } else {
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