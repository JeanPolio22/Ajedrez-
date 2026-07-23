package ajedrez;

import marco.Ventana;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Ajedrez - Menú Principal");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Asignamos un icono de ajedrez generado por código para la ventana
        setIconImage(crearIconoAjedrez());

        // Panel principal con diseño moderno y un fondo elegante
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 247, 250));
        add(panelPrincipal);

        // Título principal con mejor tipografía y color
        JLabel lblTitulo = new JLabel("Bienvenido al Ajedrez", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(40, 44, 52));
        lblTitulo.setBounds(40, 30, 370, 40);
        panelPrincipal.add(lblTitulo);

        // Subtítulo descriptivo
        JLabel lblSubtitulo = new JLabel("Por favor, seleccione su modo de juego:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 110, 120));
        lblSubtitulo.setBounds(40, 80, 370, 25);
        panelPrincipal.add(lblSubtitulo);

        // Botón 1: Humano vs Humano
        JButton btnHumano = new JButton("Humano vs Humano");
        estilizarBoton(btnHumano, new Color(52, 152, 219));
        btnHumano.setBounds(75, 135, 285, 48);
        btnHumano.addActionListener(e -> {
            Ventana ventana = new Ventana(false);
            ventana.setVisible(true);
            dispose(); // Cierra el menú principal
        });
        panelPrincipal.add(btnHumano);

        // Botón 2: Robot vs Humano
        JButton btnRobot = new JButton("Robot vs Humano");
        estilizarBoton(btnRobot, new Color(46, 204, 113));
        btnRobot.setBounds(75, 200, 285, 48);
        btnRobot.addActionListener(e -> {
            Ventana ventana = new Ventana(true);
            ventana.setVisible(true);
            dispose(); // Cierra el menú principal
        });
        panelPrincipal.add(btnRobot);
    }

    // Método para aplicar estilos modernos a los botones
    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 10, 10, 10));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    // Método que dibuja un icono de una torre de ajedrez para usarlo en la ventana
    private java.awt.Image crearIconoAjedrez() {
        BufferedImage imagen = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo circular azulado para el icono
        g2.setColor(new Color(41, 128, 185));
        g2.fillOval(2, 2, 28, 28);

        // Dibujamos una torre en blanco dentro del icono
        g2.setColor(Color.WHITE);
        g2.fillRect(9, 13, 14, 14); // Cuerpo de la torre
        g2.fillRect(8, 10, 16, 4);  // Almenas superiores
        g2.fillRect(8, 27, 16, 3);  // Base
        
        g2.dispose();
        return imagen;
    }
}