package view.admin;

import control.ControlAdmin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    private final ControlAdmin controlAdmin;

    public VentanaAdmin() {
        controlAdmin = new ControlAdmin();

        setTitle("Panel de Administración — Tres en Raya");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 220));
        setLayout(new BorderLayout(0, 0));

        add(panelTitulo(),  BorderLayout.NORTH);
        add(panelResumen(), BorderLayout.CENTER);
    }

    private JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(245, 245, 220));
        p.setBorder(new EmptyBorder(22, 30, 10, 30));

        JLabel titulo = new JLabel("PANEL DE ADMINISTRACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 28));
        titulo.setForeground(new Color(180, 100, 0));

        JLabel sub = new JLabel(
            "Estadísticas e historial — Tres en Raya",
            SwingConstants.CENTER);
        sub.setFont(new Font("Georgia", Font.PLAIN, 13));
        sub.setForeground(new Color(150, 100, 50));

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    private JPanel panelResumen() {
        JPanel p = new JPanel(new BorderLayout(14, 0));
        p.setBackground(new Color(245, 245, 220));
        p.setBorder(new EmptyBorder(10, 20, 20, 20));

        p.add(panelHistorial(), BorderLayout.CENTER);
        p.add(panelRanking(),   BorderLayout.EAST);
        return p;
    }

    private JPanel panelHistorial() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(new Color(255, 255, 240));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 100, 0), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lbl = new JLabel("Historial de partidas", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(new Color(180, 100, 0));

        String[] columnas = {"Nº Partida", "Jugador 1", "Jugador 2", "Ganador", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int      total  = controlAdmin.getTotal();
        int[]    nums   = controlAdmin.getNPartidas();
        String[] j1s    = controlAdmin.getJugador1s();
        String[] j2s    = controlAdmin.getJugador2s();
        String[] gans   = controlAdmin.getGanadores();
        String[] fechas = controlAdmin.getFechas();

        for (int i = 0; i < total; i++) {
            String ganador = (gans[i] == null || gans[i].equalsIgnoreCase("null") || gans[i].isEmpty())
                             ? "— Empate —" : gans[i];
            modelo.addRow(new Object[]{nums[i], j1s[i], j2s[i], ganador, fechas[i]});
        }

        JTable tabla = new JTable(modelo);
        tabla.setBackground(new Color(255, 255, 240));
        tabla.setForeground(new Color(80, 50, 20));
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(200, 180, 140));
        tabla.setSelectionBackground(new Color(210, 170, 80));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setShowVerticalLines(true);
        tabla.setShowHorizontalLines(true);

        tabla.getTableHeader().setBackground(new Color(230, 220, 190));
        tabla.getTableHeader().setForeground(new Color(180, 100, 0));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        center.setBackground(new Color(255, 255, 240));
        center.setForeground(new Color(80, 50, 20));
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(130);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(255, 255, 240));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 0), 1));

        int empates   = controlAdmin.getNumEmpates();
        int victorias = total - empates;
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pie.setOpaque(false);
        pie.add(etiquetaPie("Total partidas: " + total,    new Color(150, 100, 50)));
        pie.add(etiquetaPie("Con ganador: "   + victorias, new Color(120, 180, 120)));
        pie.add(etiquetaPie("Empates: "        + empates,  new Color(210, 170, 80)));

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

    private JPanel panelRanking() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(new Color(255, 255, 240));
        p.setPreferredSize(new Dimension(220, 0));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(120, 180, 120), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titulo = new JLabel("Ranking de victorias", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setForeground(new Color(80, 150, 80));

        Object[] ranking   = controlAdmin.getRanking();
        String[] jugadores = (String[]) ranking[0];
        int[]    victorias = (int[])    ranking[1];

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(new Color(255, 255, 240));

        if (jugadores.length == 0) {
            JLabel vacio = new JLabel("<html><center>Sin partidas<br>registradas aún</center></html>",
                                      SwingConstants.CENTER);
            vacio.setForeground(new Color(150, 100, 50));
            vacio.setFont(new Font("Arial", Font.ITALIC, 13));
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            lista.add(Box.createVerticalGlue());
            lista.add(vacio);
            lista.add(Box.createVerticalGlue());
        } else {
            String[] medallas    = {"🥇", "🥈", "🥉"};
            Color[]  medalColors = {
                new Color(210, 150, 30),
                new Color(150, 150, 150),
                new Color(160, 100, 50)
            };

            for (int i = 0; i < jugadores.length; i++) {
                JPanel fila = new JPanel(new BorderLayout(6, 0));
                fila.setBackground(new Color(240, 235, 210));
                fila.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 180, 140), 1),
                    new EmptyBorder(8, 10, 8, 10)
                ));
                fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

                String pos = (i < 3) ? medallas[i] : (i + 1) + "º";
                JLabel lblPos = new JLabel(pos, SwingConstants.CENTER);
                lblPos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                lblPos.setPreferredSize(new Dimension(28, 20));

                Color colorNombre = (i < 3) ? medalColors[i] : new Color(80, 50, 20);
                JLabel lblNombre = new JLabel(jugadores[i]);
                lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
                lblNombre.setForeground(colorNombre);

                JLabel lblV = new JLabel(victorias[i] + " ✓");
                lblV.setFont(new Font("Arial", Font.BOLD, 13));
                lblV.setForeground(new Color(80, 150, 80));
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
        scroll.getViewport().setBackground(new Color(255, 255, 240));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        p.add(titulo, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }
}