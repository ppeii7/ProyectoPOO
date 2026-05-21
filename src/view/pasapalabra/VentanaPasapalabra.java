package view.pasapalabra;

import control.ControladorPasapalabra;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Jugador;
import model.pasapalabra.Pasapalabra;
import model.pasapalabra.Preguntas;

public class VentanaPasapalabra extends JFrame {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final Color FONDO           = new Color(245, 245, 220); // beige
    private static final Color FONDO_PANEL     = new Color(255, 255, 240); // crema
    private static final Color COLOR_PENDIENTE = new Color(100, 149, 210); // azul pastel
    private static final Color COLOR_ACERTADA  = new Color(120, 180, 120); // verde pastel
    private static final Color COLOR_FALLADA   = new Color(200, 100, 100); // rojo pastel
    private static final Color COLOR_PASADA    = new Color(210, 170,  80); // naranja pastel

    private static final char[] LETRAS = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    private final PanelRosco panelRosco;
    private final JLabel     lblLetraActual;
    private final JLabel     lblPregunta;
    private final JTextField campRespuesta;
    private final JButton    btnResponder;
    private final JButton    btnPasapalabra;
    private final JButton    btnSalir;
    private final JLabel     lblAciertos;
    private final JLabel     lblFallos;

    public VentanaPasapalabra(Jugador jugador) {
        setTitle("Pasapalabra — " + jugador.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));

        // ── Panel izquierdo: rosco ───────────────────────────────────────────
        panelRosco = new PanelRosco();
        panelRosco.setPreferredSize(new Dimension(380, 380));
        panelRosco.setBackground(FONDO);

        // ── Panel derecho ────────────────────────────────────────────────────
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(FONDO);
        panelDerecho.setBorder(new EmptyBorder(20, 0, 20, 30));

        // Letra actual
        lblLetraActual = new JLabel("?", SwingConstants.CENTER);
        lblLetraActual.setFont(new Font("Georgia", Font.BOLD, 72));
        lblLetraActual.setForeground(new Color(180, 100, 0));
        lblLetraActual.setAlignmentX(CENTER_ALIGNMENT);

        // Pregunta
        lblPregunta = new JLabel("<html><center>—</center></html>", SwingConstants.CENTER);
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPregunta.setForeground(new Color(80, 50, 20));
        lblPregunta.setAlignmentX(CENTER_ALIGNMENT);
        lblPregunta.setMaximumSize(new Dimension(320, 120));

        // Campo respuesta
        campRespuesta = new JTextField();
        campRespuesta.setFont(new Font("Arial", Font.BOLD, 16));
        campRespuesta.setMaximumSize(new Dimension(300, 40));
        campRespuesta.setAlignmentX(CENTER_ALIGNMENT);
        campRespuesta.setHorizontalAlignment(JTextField.CENTER);
        campRespuesta.setBackground(new Color(255, 255, 240));
        campRespuesta.setForeground(new Color(80, 50, 20));
        campRespuesta.setCaretColor(new Color(180, 100, 0));
        campRespuesta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 100, 0), 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        campRespuesta.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) btnResponder.doClick();
            }
        });

        // Botones
        btnResponder   = crearBoton("✔  RESPONDER",   new Color(120, 180, 120));
        btnPasapalabra = crearBoton("↷  PASAPALABRA", new Color(100, 149, 210));
        btnSalir       = crearBoton("✕  SALIR",        new Color(200, 100, 100));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnResponder);
        panelBotones.add(btnPasapalabra);
        panelBotones.add(btnSalir);

        // Contadores
        lblAciertos = contadorLabel("Aciertos: 0", new Color(80, 150, 80));
        lblFallos   = contadorLabel("Fallos: 0",   new Color(180, 80, 80));

        JPanel panelContadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelContadores.setOpaque(false);
        panelContadores.add(lblAciertos);
        panelContadores.add(lblFallos);

        // Montar panel derecho
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(lblLetraActual);
        panelDerecho.add(Box.createVerticalStrut(14));
        panelDerecho.add(lblPregunta);
        panelDerecho.add(Box.createVerticalStrut(20));
        panelDerecho.add(campRespuesta);
        panelDerecho.add(Box.createVerticalStrut(14));
        panelDerecho.add(panelBotones);
        panelDerecho.add(Box.createVerticalStrut(20));
        panelDerecho.add(panelContadores);
        panelDerecho.add(Box.createVerticalGlue());

        // ── Montar ventana ───────────────────────────────────────────────────
        JPanel contenedor = new JPanel(new BorderLayout(0, 0));
        contenedor.setBackground(FONDO);
        contenedor.setBorder(new EmptyBorder(20, 20, 20, 0));
        contenedor.add(panelRosco,   BorderLayout.WEST);
        contenedor.add(panelDerecho, BorderLayout.CENTER);

        add(contenedor, BorderLayout.CENTER);

        setSize(820, 520);
        setMinimumSize(new Dimension(700, 460));
        setLocationRelativeTo(null);
    }

    // ── API pública para el controlador ─────────────────────────────────────

    public void actualizarRosco(Preguntas[] preguntas) {
        panelRosco.setPreguntas(preguntas);
        panelRosco.repaint();
    }

    public void mostrarPregunta(char letra, String enunciado) {
        lblLetraActual.setText(letra == ' ' ? "—" : String.valueOf(letra));
        lblPregunta.setText("<html><center>" + enunciado + "</center></html>");
    }

    public void actualizarContadores(int aciertos, int fallos) {
        lblAciertos.setText("Aciertos: " + aciertos);
        lblFallos.setText("Fallos: "   + fallos);
    }

    public String getRespuesta() {
        return campRespuesta.getText();
    }

    public void limpiarRespuesta() {
        campRespuesta.setText("");
        campRespuesta.requestFocusInWindow();
    }

    public void mostrarResultadoFinal(int aciertos, int fallos) {
        btnResponder.setEnabled(false);
        btnPasapalabra.setEnabled(false);

        String mensaje = String.format(
            "<html><center><b>¡Partida terminada!</b><br><br>" +
            "Aciertos: <b>%d</b><br>Fallos: <b>%d</b></center></html>",
            aciertos, fallos);

        JOptionPane.showMessageDialog(this, mensaje,
            "Resultado final", JOptionPane.INFORMATION_MESSAGE);
    }

    public JButton getBtnResponder()   { return btnResponder; }
    public JButton getBtnPasapalabra() { return btnPasapalabra; }
    public JButton getBtnSalir()       { return btnSalir; }

    // ── Helpers privados ─────────────────────────────────────────────────────
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? color.brighter() : color);
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
        btn.setPreferredSize(new Dimension(145, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel contadorLabel(String texto, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(color);
        return lbl;
    }

    // ── Método estático de apertura ──────────────────────────────────────────
    public static void abrir(Jugador jugador, String rutaFichero) {
        SwingUtilities.invokeLater(() -> {
            Pasapalabra        modelo = new Pasapalabra(jugador, rutaFichero);
            VentanaPasapalabra vista  = new VentanaPasapalabra(jugador);
            new ControladorPasapalabra(modelo, vista);
            vista.setVisible(true);
        });
    }

    public static void abrirConProgreso(Jugador jugador, String rutaFichero) {
        SwingUtilities.invokeLater(() -> {
            Pasapalabra modelo = new Pasapalabra(jugador, rutaFichero);
            modelo.cargarProgreso();
            VentanaPasapalabra vista = new VentanaPasapalabra(jugador);
            new ControladorPasapalabra(modelo, vista);
            vista.setVisible(true);
        });
    }

    // ── Panel del rosco ──────────────────────────────────────────────────────
    private static class PanelRosco extends JPanel {

        private Preguntas[] preguntas = new Preguntas[0];

        public void setPreguntas(Preguntas[] preguntas) {
            this.preguntas = preguntas;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth()  / 2;
            int cy = getHeight() / 2;
            int radio     = Math.min(cx, cy) - 20;
            int radioCirc = 18;
            int n = LETRAS.length;

            for (int i = 0; i < n; i++) {
                double angulo = Math.toRadians(-90.0 + (360.0 / n) * i);
                int x = (int) (cx + radio * Math.cos(angulo));
                int y = (int) (cy + radio * Math.sin(angulo));

                Color colorFondo = COLOR_PENDIENTE;
                if (preguntas != null) {
                    for (Preguntas p : preguntas) {
                        if (p != null && p.getLetra() == LETRAS[i]) {
                            colorFondo = switch (p.getEstado()) {
                                case ACERTADA -> COLOR_ACERTADA;
                                case FALLADA  -> COLOR_FALLADA;
                                case PASADA   -> COLOR_PASADA;
                                default       -> COLOR_PENDIENTE;
                            };
                            break;
                        }
                    }
                }

                g2.setColor(colorFondo);
                g2.fill(new Ellipse2D.Float(
                    x - radioCirc, y - radioCirc,
                    radioCirc * 2, radioCirc * 2));

                g2.setColor(new Color(180, 100, 0, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new Ellipse2D.Float(
                    x - radioCirc, y - radioCirc,
                    radioCirc * 2, radioCirc * 2));

                g2.setColor(new Color(80, 50, 20));
                g2.setFont(new Font("Arial", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                String letra = String.valueOf(LETRAS[i]);
                int tx = x - fm.stringWidth(letra) / 2;
                int ty = y + fm.getAscent() / 2 - 2;
                g2.drawString(letra, tx, ty);
            }
        }
    }
}