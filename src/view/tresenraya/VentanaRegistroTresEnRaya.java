package view.tresenraya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import control.ControlApp;
import control.ControlTresEnRaya;

public class VentanaRegistroTresEnRaya extends JFrame {

    ControlApp controlApp = new ControlApp();

    public VentanaRegistroTresEnRaya(ControlApp controlApp) {
        this.controlApp = controlApp;
        setTitle("Registro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        Font fuente = new Font("Limelight", Font.BOLD, 14);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(245, 245, 220)); // beige
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        getContentPane().setBackground(new Color(245, 245, 220)); // beige

        // ── Título ───────────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("TRES EN RAYA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(180, 100, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelCentral.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // ── Labels jugadores ─────────────────────────────────────────────────
        JLabel label1 = new JLabel("Jugador 1", SwingConstants.CENTER);
        label1.setFont(fuente);
        label1.setForeground(new Color(150, 100, 50));
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(label1, gbc);

        JLabel label2 = new JLabel("Jugador 2", SwingConstants.CENTER);
        label2.setFont(fuente);
        label2.setForeground(new Color(150, 100, 50));
        gbc.gridx = 1; gbc.gridy = 1;
        panelCentral.add(label2, gbc);

        // ── Campos J1 ────────────────────────────────────────────────────────
        JTextField cajaUsuarioJ1 = new JTextField("Nombre de usuario");
        cajaUsuarioJ1.setFont(fuente);
        cajaUsuarioJ1.setForeground(Color.GRAY);
        cajaUsuarioJ1.setBackground(new Color(255, 255, 240));
        cajaUsuarioJ1.setPreferredSize(new Dimension(200, 30));
        cajaUsuarioJ1.setText(controlApp.getNombreUsuarioActual());
        gbc.gridx = 0; gbc.gridy = 2;
        panelCentral.add(cajaUsuarioJ1, gbc);

        JPasswordField cajaContraseñaJ1 = new JPasswordField("Nombre de usuario");
        cajaContraseñaJ1.setFont(fuente);
        cajaContraseñaJ1.setForeground(Color.GRAY);
        cajaContraseñaJ1.setBackground(new Color(255, 255, 240));
        cajaContraseñaJ1.setPreferredSize(new Dimension(200, 30));
        cajaContraseñaJ1.setText(controlApp.getContraseñaActual());
        gbc.gridx = 0; gbc.gridy = 3;
        panelCentral.add(cajaContraseñaJ1, gbc);

        // ── Campos J2 ────────────────────────────────────────────────────────
        JTextField cajaUsuarioJ2 = new JTextField("Nombre de usuario");
        cajaUsuarioJ2.setFont(fuente);
        cajaUsuarioJ2.setForeground(Color.GRAY);
        cajaUsuarioJ2.setBackground(new Color(255, 255, 240));
        cajaUsuarioJ2.setPreferredSize(new Dimension(200, 30));
        cajaUsuarioJ2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (cajaUsuarioJ2.getText().equals("Nombre de usuario")) {
                    cajaUsuarioJ2.setText("");
                    cajaUsuarioJ2.setForeground(new Color(80, 50, 20));
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (cajaUsuarioJ2.getText().isEmpty()) {
                    cajaUsuarioJ2.setText("Nombre de usuario");
                    cajaUsuarioJ2.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 2;
        panelCentral.add(cajaUsuarioJ2, gbc);

        JPasswordField cajaContraseñaJ2 = new JPasswordField("Contraseña");
        cajaContraseñaJ2.setFont(fuente);
        cajaContraseñaJ2.setForeground(Color.GRAY);
        cajaContraseñaJ2.setBackground(new Color(255, 255, 240));
        cajaContraseñaJ2.setEchoChar((char) 0);
        cajaContraseñaJ2.setPreferredSize(new Dimension(200, 30));
        cajaContraseñaJ2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(cajaContraseñaJ2.getPassword()).equals("Contraseña")) {
                    cajaContraseñaJ2.setText("");
                    cajaContraseñaJ2.setForeground(new Color(80, 50, 20));
                    cajaContraseñaJ2.setEchoChar('•');
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(cajaContraseñaJ2.getPassword()).isEmpty()) {
                    cajaContraseñaJ2.setText("Contraseña");
                    cajaContraseñaJ2.setForeground(Color.GRAY);
                    cajaContraseñaJ2.setEchoChar((char) 0);
                }
            }
        });
        gbc.gridx = 1; gbc.gridy = 3;
        panelCentral.add(cajaContraseñaJ2, gbc);

        // ── Botón ────────────────────────────────────────────────────────────
        JButton btnAñadirJugador = new JButton("Añadir jugadores");
        btnAñadirJugador.setFont(fuente);
        btnAñadirJugador.setPreferredSize(new Dimension(200, 30));
        btnAñadirJugador.setBackground(new Color(180, 100, 0));
        btnAñadirJugador.setForeground(Color.WHITE);
        btnAñadirJugador.setOpaque(true);
        btnAñadirJugador.setBorderPainted(false);
        gbc.gridx = 1; gbc.gridy = 4;
        panelCentral.add(btnAñadirJugador, gbc);

        add(panelCentral, BorderLayout.CENTER);

        btnAñadirJugador.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ControlTresEnRaya controlTresEnRaya = new ControlTresEnRaya();

                boolean ok = controlTresEnRaya.añadirJugadores(
                    cajaUsuarioJ1.getText(),
                    new String(cajaContraseñaJ1.getPassword()),
                    cajaUsuarioJ2.getText(),
                    new String(cajaContraseñaJ2.getPassword())
                );

                if (ok) {
                    controlTresEnRaya.crearPartida();
                    dispose();
                }
            }
        });
    }
}