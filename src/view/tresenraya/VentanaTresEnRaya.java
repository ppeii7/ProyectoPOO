package view.tresenraya;

import control.ControlTresEnRaya;
import model.Jugador;
import model.TresEnRaya;

import javax.swing.*;
import java.awt.*;

public class VentanaTresEnRaya extends JFrame {

    private TresEnRaya modelo;
    private ControlTresEnRaya control;

    private JButton[] botones = new JButton[9];
    private JLabel lblTurno;

    public VentanaTresEnRaya(Jugador j1, Jugador j2, ControlTresEnRaya control) {
        this.modelo  = new TresEnRaya(j1, j2);
        this.control = control;

        setTitle("Tres en Raya — " + j1.getUsername() + " vs " + j2.getUsername());
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // ── Panel superior ───────────────────────────────────────────────────
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(230, 220, 190)); // beige oscuro
        panelNorte.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("TRES EN RAYA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(180, 100, 0)); // naranja oscuro

        lblTurno = new JLabel(
            "Turno: " + modelo.getJ1().getUsername() + " (X)",
            SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.PLAIN, 15));
        lblTurno.setForeground(new Color(80, 50, 20)); // marrón

        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(lblTurno,  BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // ── Tablero ──────────────────────────────────────────────────────────
        JPanel panelTablero = new JPanel(new GridLayout(3, 3, 6, 6));
        panelTablero.setBackground(new Color(180, 100, 0)); // naranja oscuro de separador
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            int indice = i;
            botones[i] = new JButton("");
            botones[i].setFont(new Font("Arial", Font.BOLD, 52));
            botones[i].setBackground(new Color(255, 255, 240)); // crema
            botones[i].setForeground(new Color(80, 50, 20));
            botones[i].setFocusPainted(false);
            botones[i].setBorder(BorderFactory.createLineBorder(new Color(180, 100, 0), 2));
            botones[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            botones[i].addActionListener(e -> manejarClick(indice));
            panelTablero.add(botones[i]);
        }
        add(panelTablero, BorderLayout.CENTER);

        // ── Panel inferior ───────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.setBackground(new Color(230, 220, 190)); // beige oscuro
        panelSur.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 13));
        btnReiniciar.setBackground(new Color(180, 100, 0)); // naranja oscuro
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setOpaque(true);
        btnReiniciar.setBorderPainted(false);
        btnReiniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReiniciar.addActionListener(e -> reiniciar());

        panelSur.add(btnReiniciar);
        add(panelSur, BorderLayout.SOUTH);
    }

    // ── Lógica del click ─────────────────────────────────────────────────────
    public void manejarClick(int indice) {
        if (!modelo.colocarFicha(indice)) return;

        boolean esJ1 = modelo.isTurnoJ1();
        botones[indice].setText(esJ1 ? "X" : "O");
        botones[indice].setForeground(esJ1
            ? new Color(100, 149, 210)  // azul pastel para X
            : new Color(200, 100, 100)); // rojo pastel para O

        if (modelo.hayGanador()) {
            int[] combo = modelo.getCombinacionGanadora();
            botones[combo[0]].setBackground(new Color(120, 180, 120)); // verde pastel
            botones[combo[1]].setBackground(new Color(120, 180, 120));
            botones[combo[2]].setBackground(new Color(120, 180, 120));
            JOptionPane.showMessageDialog(this,
                "¡" + modelo.getGanador().getUsername() + " ha ganado!",
                "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            control.setGanador(modelo.getGanador());
            control.guardarDatosPartidas();
            reiniciar();
            return;
        }

        if (modelo.tableroLleno()) {
            JOptionPane.showMessageDialog(this, "¡Empate!",
                "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            control.setGanador(null);
            control.guardarDatosPartidas();
            reiniciar();
            return;
        }

        modelo.cambiarTurno();
        lblTurno.setText("Turno: " + modelo.getJugadorActual().getUsername()
            + (modelo.isTurnoJ1() ? " (X)" : " (O)"));
    }

    // ── Reiniciar ────────────────────────────────────────────────────────────
    private void reiniciar() {
        modelo.reiniciar();
        for (JButton b : botones) {
            b.setText("");
            b.setBackground(new Color(255, 255, 240)); // crema
        }
        lblTurno.setText("Turno: " + modelo.getJ1().getUsername() + " (X)");
    }
}