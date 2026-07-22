package ajedrez;

import Tablero.PanelTablero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame implements ActionListener {

    private JButton btnHumanoHumano;
    private JButton btnRobotHumano;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    public MenuPrincipal() {
        setTitle("Ajedrez - Menú Principal");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior para los textos
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 5, 5));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        lblTitulo = new JLabel("Bienvenido al Ajedrez", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        
        lblSubtitulo = new JLabel("Por favor seleccione su modo de juego:", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        panelNorte.add(lblTitulo);
        panelNorte.add(lblSubtitulo);
        add(panelNorte, BorderLayout.NORTH);

        // Panel central para los botones de selección
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        btnHumanoHumano = new JButton("Humano vs Humano");
        btnRobotHumano = new JButton("Robot vs Humano");

        btnHumanoHumano.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRobotHumano.setFont(new Font("Arial", Font.PLAIN, 14));

        btnHumanoHumano.addActionListener(this);
        btnRobotHumano.addActionListener(this);

        panelCentro.add(btnHumanoHumano);
        panelCentro.add(btnRobotHumano);
        add(panelCentro, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHumanoHumano) {
            iniciarPartida(false); // Modo normal
        } else if (e.getSource() == btnRobotHumano) {
            iniciarPartida(true);  // Modo con Robot
        }
    }

    private void iniciarPartida(boolean modoRobot) {
        this.dispose(); // Cierra el menú principal

        // Ventana del tablero de ajedrez
        JFrame ventanaJuego = new JFrame("Ajedrez");
        ventanaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PanelTablero panelTablero = new PanelTablero(modoRobot); // Le pasamos si juega contra el robot
        ventanaJuego.add(panelTablero);
        
        ventanaJuego.pack();
        ventanaJuego.setLocationRelativeTo(null);
        ventanaJuego.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}