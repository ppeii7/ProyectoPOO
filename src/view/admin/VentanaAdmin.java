package view.admin;

import control.ControlAdmin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de administración.
 * Muestra el historial completo de partidas de Tres en Raya en una tabla
 * y un ranking lateral de jugadores con más victorias.
 * Mismo estilo visual (fondo oscuro, amarillo, blanco) que el resto del proyecto.
 */
public class VentanaAdmin extends JFrame {

    // ── Colores (idénticos al resto del proyecto) ────────────────────────────
    private static final Color FONDO        = new Color(12, 12, 40);
    private static final Color FONDO_PANEL  = new Color(25, 25, 70);
    private static final Color FONDO_TABLA  = new Color(20, 20, 55);
    private static final Color TITULO_COLOR = new Color(255, 220, 50);
    private static final Color TEXTO_COLOR  = new Color(210, 210, 255);
    private static final Color VERDE        = new Color(30, 180, 60);
    private static final Color AZUL         = new Color(30, 120, 220);
    private static final Color ROJO         = new Color(200, 40, 40);

    private final ControlAdmin controlAdmin;

    // ────────────────────────────────────────────────────────────────────────
    public VentanaAdmin() {
        controlAdmin = new ControlAdmin();

        setTitle("Panel de Administración — Tres en Raya");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(0, 0));

        add(panelTitulo(),    BorderLayout.NORTH);
        add(panelResumen(),   BorderLayout.CENTER);
    }

    // ── Panel superior con título ────────────────────────────────────────────
    private JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(22, 30, 10, 30));

        JLabel titulo = new JLabel("PANEL DE ADMINISTRACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(TITULO_COLOR);

        JLabel sub = new JLabel(
            "Estadísticas e historial — Tres en Raya",
            SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setForeground(TEXTO_COLOR);

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    // ── Panel central: historial (izq) + ranking (der) ──────────────────────
    private JPanel panelResumen() {
        JPanel p = new JPanel(new BorderLayout(14, 0));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(10, 20, 20, 20));

        p.add(panelHistorial(), BorderLayout.CENTER);
        p.add(panelRanking(),   BorderLayout.EAST);
        return p;
    }

    // ── Tabla de historial ───────────────────────────────────────────────────
    private JPanel panelHistorial() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(FONDO_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AZUL, 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        // -- Título del panel
        JLabel lbl = new JLabel("Historial de partidas", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(AZUL.brighter());

        // -- Modelo de tabla
        String[] columnas = {"Nº Partida", "Jugador 1", "Jugador 2", "Ganador", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int      total    = controlAdmin.getTotal();
        int[]    nums     = controlAdmin.getNPartidas();
        String[] j1s      = controlAdmin.getJugador1s();
        String[] j2s      = controlAdmin.getJugador2s();
        String[] gans     = controlAdmin.getGanadores();
        String[] fechas   = controlAdmin.getFechas();

        for (int i = 0; i < total; i++) {
            String ganador = (gans[i] == null || gans[i].equalsIgnoreCase("null") || gans[i].isEmpty())
                             ? "— Empate —" : gans[i];
            modelo.addRow(new Object[]{nums[i], j1s[i], j2s[i], ganador, fechas[i]});
        }

        JTable tabla = new JTable(modelo);
        tabla.setBackground(FONDO_TABLA);
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(50, 50, 100));
        tabla.setSelectionBackground(new Color(50, 80, 160));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setShowVerticalLines(true);
        tabla.setShowHorizontalLines(true);

        // Cabecera
        tabla.getTableHeader().setBackground(new Color(15, 15, 50));
        tabla.getTableHeader().setForeground(TITULO_COLOR);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setReorderingAllowed(false);

        // Centrar todo
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        center.setBackground(FONDO_TABLA);
        center.setForeground(Color.WHITE);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(130);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(FONDO_TABLA);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 90), 1));

        // -- Pie con totales
        int empates = controlAdmin.getNumEmpates();
        int victorias = total - empates;
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pie.setOpaque(false);
        pie.add(etiquetaPie("Total partidas: " + total,    TEXTO_COLOR));
        pie.add(etiquetaPie("Con ganador: "   + victorias, VERDE));
        pie.add(etiquetaPie("Empates: "        + empates,  new Color(200, 160, 0)));

        p.add(lbl,    BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(pie,    BorderLayout.SOUTH);
        return p;
    }

    private JLabel etiquetaPie(String texto, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setForeground(color);
        return l;
    }

    // ── Panel de ranking lateral ─────────────────────────────────────────────
    private JPanel panelRanking() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(FONDO_PANEL);
        p.setPreferredSize(new Dimension(220, 0));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE, 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titulo = new JLabel("Ranking de victorias", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setForeground(VERDE.brighter());

        // Obtener datos del controlador
        Object[] ranking   = controlAdmin.getRanking();
        String[] jugadores = (String[]) ranking[0];
        int[]    victorias = (int[])    ranking[1];

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(FONDO_PANEL);

        if (jugadores.length == 0) {
            JLabel vacio = new JLabel("<html><center>Sin partidas<br>registradas aún</center></html>",
                                      SwingConstants.CENTER);
            vacio.setForeground(TEXTO_COLOR);
            vacio.setFont(new Font("Arial", Font.ITALIC, 13));
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            lista.add(Box.createVerticalGlue());
            lista.add(vacio);
            lista.add(Box.createVerticalGlue());
        } else {
            // Medallas para el podio
            String[] medallas    = {"🥇", "🥈", "🥉"};
            Color[]  medalColors = {
                new Color(255, 200, 50),
                new Color(190, 190, 190),
                new Color(190, 120, 60)
            };

            for (int i = 0; i < jugadores.length; i++) {
                JPanel fila = new JPanel(new BorderLayout(6, 0));
                fila.setBackground(new Color(30, 30, 85));
                fila.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 50, 110), 1),
                    new EmptyBorder(8, 10, 8, 10)
                ));
                fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

                // Posición / medalla
                String pos = (i < 3) ? medallas[i] : (i + 1) + "º";
                JLabel lblPos = new JLabel(pos, SwingConstants.CENTER);
                lblPos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                lblPos.setPreferredSize(new Dimension(28, 20));

                // Nombre del jugador
                Color colorNombre = (i < 3) ? medalColors[i] : TEXTO_COLOR;
                JLabel lblNombre = new JLabel(jugadores[i]);
                lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
                lblNombre.setForeground(colorNombre);

                // Nº de victorias
                JLabel lblV = new JLabel(victorias[i] + " ✓");
                lblV.setFont(new Font("Arial", Font.BOLD, 13));
                lblV.setForeground(VERDE);
                lblV.setHorizontalAlignment(SwingConstants.RIGHT);

                fila.add(lblPos,    BorderLayout.WEST);
                fila.add(lblNombre, BorderLayout.CENTER);
                fila.add(lblV,      BorderLayout.EAST);

                lista.add(fila);
                lista.add(Box.createVerticalStrut(5));
            }
            lista.add(Box.createVerticalGlue());
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(FONDO_PANEL);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        p.add(titulo, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }
}