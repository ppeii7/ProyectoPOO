package view;

import control.ControlJuego;
import model.Jugador;
import view.pasapalabra.VentanaDificultadPasapalabras;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Menú principal de la aplicación.
 * Muestra una tarjeta por cada juego disponible.
 *
 * Al pulsar "JUGAR" en una tarjeta:
 *  - Pasapalabra → VentanaDificultadPasapalabras.abrir(jugador)
 *  - Snake       → controlJuego.abrirVentanaSnake()
 *  - Pong        → controlJuego.abrirVentanaPong()
 *
 * El patrón es idéntico al del botón "Iniciar sesión" en VentanaPrincipal:
 * el click del botón llama a un método del controlador, que monta el MVC
 * y abre la ventana.
 */
public class VentanaJuegos extends JFrame {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final Color FONDO       = new Color(12, 12, 40);
    private static final Color FONDO_CARTA = new Color(25, 25, 70);
    private static final Color TITULO_APP  = new Color(255, 220, 50);

    // ── Datos de cada tarjeta: { nombre, descripción, color acento, tipo } ───
    private static final Object[][] JUEGOS = {
        {
            "PASAPALABRA",
            "Recorre el rosco respondiendo\nuna pregunta por cada letra",
            new Color(30, 120, 220),
            "pasapalabra"
        },
        {
            "SNAKE",
            "Guía a la serpiente y come\ntoda la comida sin chocarte",
            new Color(30, 180, 60),
            "snake"
        },
        {
            "PONG",
            "Juega al clásico juego de\npelota contra la máquina",
            new Color(200, 60, 200),
            "pong"
        }
    };

    private final Jugador     jugador;
    private final ControlJuego controlJuego; // ← controlador que abre los juegos

    // ────────────────────────────────────────────────────────────────────────
    public VentanaJuegos(Jugador jugador) {
        this.jugador      = jugador;
        this.controlJuego = new ControlJuego(); // una instancia por ventana de menú

        setTitle("Menú de juegos — " + jugador.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        add(panelTitulo(), BorderLayout.NORTH);
        add(panelJuegos(), BorderLayout.CENTER);
        add(panelFooter(), BorderLayout.SOUTH);

        setSize(820, 560);
        setMinimumSize(new Dimension(600, 420));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Panel superior ───────────────────────────────────────────────────────
    private JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(28, 40, 10, 40));

        JLabel titulo = new JLabel("ARCADE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 38));
        titulo.setForeground(TITULO_APP);

        JLabel sub = new JLabel(
            "Bienvenido, " + jugador.getUsername() + " — elige un juego",
            SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setForeground(new Color(160, 160, 200));

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    // ── Panel central con tarjetas ───────────────────────────────────────────
    private JPanel panelJuegos() {
        JPanel p = new JPanel(new GridLayout(1, JUEGOS.length, 24, 0));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(24, 40, 24, 40));

        for (Object[] datos : JUEGOS) {
            p.add(crearTarjeta(
                (String) datos[0],
                (String) datos[1],
                (Color)  datos[2],
                (String) datos[3]
            ));
        }
        return p;
    }

    // ── Cada tarjeta individual ──────────────────────────────────────────────
    private JPanel crearTarjeta(String nombre, String descripcion,
                                Color acento,  String tipo) {

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
        carta.setBorder(new EmptyBorder(24, 20, 20, 20));
        carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icono circular con la inicial
        JPanel icono = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(acento);
                g2.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        icono.setPreferredSize(new Dimension(54, 54));
        icono.setOpaque(false);

        JLabel letraIcono = new JLabel(String.valueOf(nombre.charAt(0)),
                                       SwingConstants.CENTER);
        letraIcono.setFont(new Font("Arial", Font.BOLD, 26));
        letraIcono.setForeground(Color.WHITE);
        icono.setLayout(new BorderLayout());
        icono.add(letraIcono, BorderLayout.CENTER);

        JPanel wrapIcono = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapIcono.setOpaque(false);
        wrapIcono.add(icono);

        // Nombre
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        // Descripción
        JLabel lblDesc = new JLabel(
            "<html><center>" + descripcion.replace("\n", "<br>") + "</center></html>",
            SwingConstants.CENTER);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDesc.setForeground(new Color(160, 160, 200));

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
        btnJugar.setPreferredSize(new Dimension(140, 38));
        btnJugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ── Conexión del botón con el controlador ────────────────────────────
        // Igual que en VentanaPrincipal:
        //   btnLogin.addMouseListener(new MouseAdapter() {
        //       public void mouseClicked(MouseEvent e) { controlador.iniciarSesion(...); }
        //   });
        btnJugar.addActionListener(e -> abrirJuego(tipo));

        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapBtn.setOpaque(false);
        wrapBtn.add(btnJugar);

        // Montar contenido de la tarjeta
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(wrapIcono);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(lblNombre);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblDesc);
        contenido.add(Box.createVerticalGlue());
        contenido.add(wrapBtn);

        carta.add(contenido, BorderLayout.CENTER);

        // Hover sobre la tarjeta completa también abre el juego
        carta.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                carta.setBorder(new EmptyBorder(22, 18, 18, 18));
                carta.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                carta.setBorder(new EmptyBorder(24, 20, 20, 20));
                carta.repaint();
            }
            @Override public void mouseClicked(MouseEvent e) { abrirJuego(tipo); }
        });

        return carta;
    }

    // ── Pie de página ────────────────────────────────────────────────────────
    private JPanel panelFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(0, 0, 10, 20));

        JLabel lbl = new JLabel("Jugador: " + jugador.getUsername());
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(100, 100, 140));
        p.add(lbl);
        return p;
    }

    // ── Método central que delega en el controlador correspondiente ──────────
    /**
     * Delega la apertura del juego en ControlJuego o en el flujo de dificultad.
     *
     * Para añadir un juego nuevo:
     *  1. Añade su tarjeta en el array JUEGOS[]
     *  2. Añade un método abrirVentana<Juego>() en ControlJuego
     *  3. Añade el caso aquí
     */
    public void abrirJuego(String tipo) {
        switch (tipo) {
            case "pasapalabra":
                // Pasapalabra tiene pantalla de dificultad propia
                VentanaDificultadPasapalabras.abrir(jugador);
                break;

            case "snake":
                // Snake sigue el mismo patrón: controlador monta el MVC y abre la ventana
                controlJuego.abrirVentanaSnake();
                break;

            case "pong":
                controlJuego.abrirVentanaPong();
                break;

            default:
                JOptionPane.showMessageDialog(this,
                    "Juego no disponible todavía.",
                    "Próximamente", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}