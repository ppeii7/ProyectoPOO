package view;

import control.ControlApp;
import control.ControlJuego;
import model.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class VentanaJuegos extends JFrame {

    private static final Color FONDO       = new Color(245, 245, 220); // beige igual que VentanaPrincipal
    private static final Color FONDO_CARTA = new Color(255, 255, 240); // crema suave
    private static final Color TITULO_APP  = new Color(180, 100, 0);   // naranja oscuro

    private static final Object[][] JUEGOS = {
        { "PASAPALABRA", new Color(100, 149, 210), "pasapalabra" }, // azul pastel
        { "SNAKE",       new Color(120, 180, 120), "snake"       }, // verde pastel
        { "PONG",        new Color(180, 120, 180), "pong"        }, // lila pastel
        { "TRES EN RAYA",new Color(210, 150,  80), "tres en raya"} // naranja pastel
    };

    private final Jugador      jugador;
    private final ControlJuego controlJuego;

    public VentanaJuegos(Jugador jugador, ControlApp controlApp) {
        this.jugador      = jugador;
        this.controlJuego = new ControlJuego(controlApp);

        setTitle("Menú de juegos — " + jugador.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        add(panelTitulo(), BorderLayout.NORTH);
        add(panelJuegos(), BorderLayout.CENTER);
        add(panelFooter(), BorderLayout.SOUTH);

        setSize(700, 620);
        setMinimumSize(new Dimension(600, 520));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(30, 40, 10, 40));

        JLabel titulo = new JLabel("ARCADE", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 42));
        titulo.setForeground(TITULO_APP);

        JLabel sub = new JLabel(
            "Bienvenido, " + jugador.getUsername() + " — elige un juego",
            SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(150, 100, 50));

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelJuegos() {
        JPanel p = new JPanel(new GridLayout(2, 2, 20, 20));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(24, 40, 24, 40));

        for (Object[] datos : JUEGOS) {
            p.add(crearTarjeta(
                (String) datos[0],
                (Color)  datos[1],
                (String) datos[2]
            ));
        }
        return p;
    }

    private JPanel crearTarjeta(String nombre, Color acento, String tipo) {

        JPanel carta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO_CARTA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(acento);
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 20, 20));
            }
        };
        carta.setOpaque(false);
        carta.setBorder(new EmptyBorder(20, 20, 20, 20));
        carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Nombre
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Courier New", Font.BOLD, 16));
        lblNombre.setForeground(new Color(80, 50, 20)); // marrón oscuro sobre crema

        // Botón Jugar
        JButton btnJugar = new JButton("▶  JUGAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? acento.brighter() : acento);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFont(new Font("Arial", Font.BOLD, 13));
        btnJugar.setContentAreaFilled(false);
        btnJugar.setBorderPainted(false);
        btnJugar.setFocusPainted(false);
        btnJugar.setPreferredSize(new Dimension(130, 36));
        btnJugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJugar.addActionListener(e -> abrirJuego(tipo));

        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapBtn.setOpaque(false);
        wrapBtn.add(btnJugar);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(Box.createVerticalGlue());
        contenido.add(lblNombre);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(wrapBtn);
        contenido.add(Box.createVerticalGlue());

        carta.add(contenido, BorderLayout.CENTER);

        carta.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                carta.setBorder(new EmptyBorder(18, 18, 18, 18));
                carta.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                carta.setBorder(new EmptyBorder(20, 20, 20, 20));
                carta.repaint();
            }
            @Override public void mouseClicked(MouseEvent e) { abrirJuego(tipo); }
        });

        return carta;
    }

    private JPanel panelFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(0, 0, 10, 20));

        JLabel lbl = new JLabel("Jugador: " + jugador.getUsername());
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(150, 100, 50));
        p.add(lbl);
        return p;
    }

    public void abrirJuego(String tipo) {
        switch (tipo) {
            case "pasapalabra":
                controlJuego.abrirVentanaPasapalabra(jugador);
                break;
            case "snake":
                controlJuego.abrirVentanaSnake();
                break;
            case "pong":
                controlJuego.abrirVentanaPong();
                break;
            case "tres en raya":
                controlJuego.abrirVentanaRegistroTresEnRaya();
                break;
            default:
                JOptionPane.showMessageDialog(this,
                    "Juego no disponible todavía.",
                    "Próximamente", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}