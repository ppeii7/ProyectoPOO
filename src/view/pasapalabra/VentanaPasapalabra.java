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

/**
 * Vista del juego Pasapalabra.
 *
 * Muestra:
 *  - Rosco circular con las 27 letras y su estado (colores)
 *  - Pregunta actual
 *  - Campo de respuesta + botones (Responder / Pasapalabra / Salir)
 *  - Contadores de aciertos y fallos
 *
 * Sigue el mismo patrón que VentanaSnake: es un JPanel sin lógica,
 * expone getters/métodos para que el controlador la actualice.
 *
 * Se monta dentro de un JFrame creado por el método estático abrir().
 */
public class VentanaPasapalabra extends JFrame {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final Color FONDO        = new Color(12, 12, 40);
    private static final Color FONDO_PANEL  = new Color(20, 20, 60);
    private static final Color COLOR_PENDIENTE = new Color(30, 100, 210);
    private static final Color COLOR_ACERTADA  = new Color(30, 180, 60);
    private static final Color COLOR_FALLADA   = new Color(200, 40, 40);
    private static final Color COLOR_PASADA    = new Color(200, 160, 0);

    // ── Letras del rosco (español: incluye Ñ) ────────────────────────────────
    private static final char[] LETRAS = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    // ── Componentes que el controlador necesita actualizar ───────────────────
    private final PanelRosco panelRosco;
    private final JLabel     lblLetraActual;
    private final JLabel     lblPregunta;
    private final JTextField campRespuesta;
    private final JButton    btnResponder;
    private final JButton    btnPasapalabra;
    private final JButton    btnSalir;
    private final JLabel     lblAciertos;
    private final JLabel     lblFallos;

    // ────────────────────────────────────────────────────────────────────────
    public VentanaPasapalabra(Jugador jugador) {
        setTitle("Pasapalabra — " + jugador.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));

        // ── Panel izquierdo: rosco ───────────────────────────────────────────
        panelRosco = new PanelRosco();
        panelRosco.setPreferredSize(new Dimension(380, 380));
        panelRosco.setBackground(FONDO);

        // ── Panel derecho: pregunta + respuesta ──────────────────────────────
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(FONDO);
        panelDerecho.setBorder(new EmptyBorder(20, 0, 20, 30));

        // Letra actual grande
        lblLetraActual = new JLabel("?", SwingConstants.CENTER);
        lblLetraActual.setFont(new Font("Arial", Font.BOLD, 72));
        lblLetraActual.setForeground(COLOR_PENDIENTE);
        lblLetraActual.setAlignmentX(CENTER_ALIGNMENT);

        // Enunciado de la pregunta
        lblPregunta = new JLabel("<html><center>—</center></html>", SwingConstants.CENTER);
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPregunta.setForeground(new Color(210, 210, 255));
        lblPregunta.setAlignmentX(CENTER_ALIGNMENT);
        lblPregunta.setMaximumSize(new Dimension(320, 120));

        // Campo de respuesta
        campRespuesta = new JTextField();
        campRespuesta.setFont(new Font("Arial", Font.BOLD, 16));
        campRespuesta.setMaximumSize(new Dimension(300, 40));
        campRespuesta.setAlignmentX(CENTER_ALIGNMENT);
        campRespuesta.setHorizontalAlignment(JTextField.CENTER);
        campRespuesta.setBackground(new Color(30, 30, 70));
        campRespuesta.setForeground(Color.WHITE);
        campRespuesta.setCaretColor(Color.WHITE);
        campRespuesta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 130), 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        // Enter también responde
        campRespuesta.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) btnResponder.doClick();
            }
        });

        // Botones
        btnResponder   = crearBoton("✔  RESPONDER",   new Color(30, 180, 60));
        btnPasapalabra = crearBoton("↷  PASAPALABRA", new Color(30, 100, 210));
        btnSalir       = crearBoton("✕  SALIR",        new Color(120, 40, 40));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnResponder);
        panelBotones.add(btnPasapalabra);
        panelBotones.add(btnSalir);

        // Contadores
        lblAciertos = contadorLabel("Aciertos: 0", COLOR_ACERTADA);
        lblFallos   = contadorLabel("Fallos: 0",   COLOR_FALLADA);

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

    /** Actualiza los colores del rosco según el estado de cada pregunta. */
    public void actualizarRosco(Preguntas[] preguntas) {
        panelRosco.setPreguntas(preguntas);
        panelRosco.repaint();
    }

    /** Muestra la letra y el enunciado de la pregunta actual. */
    public void mostrarPregunta(char letra, String enunciado) {
        lblLetraActual.setText(letra == ' ' ? "—" : String.valueOf(letra));
        lblPregunta.setText("<html><center>" + enunciado + "</center></html>");
    }

    /** Actualiza los contadores de aciertos y fallos. */
    public void actualizarContadores(int aciertos, int fallos) {
        lblAciertos.setText("Aciertos: " + aciertos);
        lblFallos.setText("Fallos: "   + fallos);
    }

    /** Devuelve el texto del campo de respuesta. */
    public String getRespuesta() {
        return campRespuesta.getText();
    }

    /** Borra el campo de respuesta y le devuelve el foco. */
    public void limpiarRespuesta() {
        campRespuesta.setText("");
        campRespuesta.requestFocusInWindow();
    }

    /** Muestra un diálogo con el resultado final de la partida. */
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

    // ── Getters de botones (para que el controlador los conecte) ─────────────
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

    // ── Método estático de apertura (mismo patrón que VentanaDificultad) ─────
    /**
     * Crea el modelo, la vista y el controlador, y muestra la ventana.
     * Llamado desde VentanaDificultadPasapalabras.iniciarJuego(ruta).
     */
    public static void abrir(Jugador jugador, String rutaFichero) {
        SwingUtilities.invokeLater(() -> {
            Pasapalabra       modelo = new Pasapalabra(jugador, rutaFichero);
            VentanaPasapalabra vista  = new VentanaPasapalabra(jugador);
            new ControladorPasapalabra(modelo, vista);   // conecta modelo ↔ vista
            vista.setVisible(true);
        });
    }

    /**
 * Carga una partida previamente guardada para el jugador y la muestra.
 * Llamado desde ControlJuego cuando el usuario elige continuar.
 */
public static void abrirConProgreso(Jugador jugador, String rutaFichero) {
    SwingUtilities.invokeLater(() -> {
        Pasapalabra modelo = new Pasapalabra(jugador, rutaFichero);
        modelo.cargarProgreso();                        // ← restaura estados
        VentanaPasapalabra vista = new VentanaPasapalabra(jugador);
        new ControladorPasapalabra(modelo, vista);
        vista.setVisible(true);
    });
}

    // ══════════════════════════════════════════════════════════════════════════
    // Panel interno que pinta el rosco circular
    // ══════════════════════════════════════════════════════════════════════════
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
            int radioCirc = 18; // radio de cada círculo de letra

            int n = LETRAS.length; // 27 letras

            for (int i = 0; i < n; i++) {
                // Ángulo: empieza arriba (-90°) y va en sentido horario
                double angulo = Math.toRadians(-90.0 + (360.0 / n) * i);
                int x = (int) (cx + radio * Math.cos(angulo));
                int y = (int) (cy + radio * Math.sin(angulo));

                // Buscar estado de esta letra
                Color colorFondo = COLOR_PENDIENTE; // por defecto
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

                // Dibujar círculo
                g2.setColor(colorFondo);
                g2.fill(new Ellipse2D.Float(
                    x - radioCirc, y - radioCirc,
                    radioCirc * 2, radioCirc * 2));

                // Borde oscuro
                g2.setColor(new Color(0, 0, 0, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new Ellipse2D.Float(
                    x - radioCirc, y - radioCirc,
                    radioCirc * 2, radioCirc * 2));

                // Letra
                g2.setColor(Color.WHITE);
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