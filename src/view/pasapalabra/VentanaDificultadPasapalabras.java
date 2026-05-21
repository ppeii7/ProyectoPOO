package view.pasapalabra;

import model.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class VentanaDificultadPasapalabras extends JFrame {

    public static final Color FONDO = new Color(245, 245, 220); // beige

    public static final Object[][] DIFICULTADES = {
        { "FÁCIL",   "😊", new Color(120, 180, 120), ".\\data\\RoscoFacil.txt"    },
        { "MEDIO",   "🤔", new Color(210, 170,  80), ".\\data\\RoscoMedio.txt"    },
        { "DIFÍCIL", "🔥", new Color(200, 100, 100), ".\\data\\RoscoDifícil.txt"  },
        { "DISNEY",  "✨", new Color(130, 110, 200), ".\\data\\RoscoDisney.txt"   }
    };

    private Jugador jugador;

    public VentanaDificultadPasapalabras(Jugador jugador) {
        this.jugador = jugador;

        setTitle("Elige dificultad — Pasapalabra");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        add(panelTitulo(),   BorderLayout.NORTH);
        add(panelOpciones(), BorderLayout.CENTER);
        add(panelVolver(),   BorderLayout.SOUTH);

        setSize(860, 420);
        setMinimumSize(new Dimension(680, 360));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(24, 40, 8, 40));

        JLabel titulo = new JLabel("PASAPALABRA", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 30));
        titulo.setForeground(new Color(180, 100, 0));

        JLabel sub = new JLabel("Selecciona la dificultad", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(150, 100, 50));

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    public JPanel panelOpciones() {
        JPanel p = new JPanel(new GridLayout(1, DIFICULTADES.length, 18, 0));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(20, 30, 10, 30));

        for (Object[] d : DIFICULTADES) {
            p.add(crearTarjeta(
                (String) d[0],
                (String) d[1],
                (Color)  d[2],
                (String) d[3]
            ));
        }
        return p;
    }

    public JPanel crearTarjeta(String nombre, String emoji, Color acento, String ruta) {

        JPanel carta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 240)); // crema
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 18, 18));
                g2.setColor(acento);
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 18, 18));
            }
        };
        carta.setOpaque(false);
        carta.setBorder(new EmptyBorder(18, 16, 16, 16));
        carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Emoji
        JLabel lblEmoji = new JLabel(emoji, SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        lblEmoji.setAlignmentX(CENTER_ALIGNMENT);

        // Nombre
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Courier New", Font.BOLD, 15));
        lblNombre.setForeground(new Color(80, 50, 20));
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        // Botón
        JButton btn = new JButton("▶  JUGAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? acento.brighter() : acento);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> iniciarJuego(ruta));

        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapBtn.setOpaque(false);
        wrapBtn.add(btn);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(Box.createVerticalGlue());
        contenido.add(lblEmoji);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(lblNombre);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(wrapBtn);
        contenido.add(Box.createVerticalGlue());

        carta.add(contenido, BorderLayout.CENTER);

        carta.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                carta.setBorder(new EmptyBorder(16, 14, 14, 14));
                carta.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                carta.setBorder(new EmptyBorder(18, 16, 16, 16));
                carta.repaint();
            }
            @Override public void mouseClicked(MouseEvent e) { iniciarJuego(ruta); }
        });

        return carta;
    }

    public JPanel panelVolver() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(0, 20, 10, 0));

        JButton btnVolver = new JButton("← Volver al menú");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVolver.setForeground(new Color(150, 100, 50));
        btnVolver.setBackground(new Color(230, 220, 190));
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setOpaque(true);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> dispose());

        p.add(btnVolver);
        return p;
    }

    public void iniciarJuego(String ruta) {
        dispose();
        VentanaPasapalabra.abrir(jugador, ruta);
    }
}