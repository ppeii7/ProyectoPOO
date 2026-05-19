package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.ModeloPong;

public class VentanaPong extends JPanel {
    
    private ModeloPong modelo;

    public VentanaPong(ModeloPong modelo) {
        this.modelo = modelo;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Pintar paletas y bola leyendo los datos del modelo
        g.setColor(Color.WHITE);
        g.fillRect(modelo.x1, modelo.y1, 10, 70);
        g.fillRect(modelo.x2, modelo.y2, 10, 70);
        g.fillOval(modelo.xB, modelo.yB, 20, 20);
        
        // Pintar marcador
        g.setColor(Color.GRAY);
        g.setFont(new Font("Consolas", Font.BOLD, 50));
        g.drawString(modelo.ptos1 + "-" + modelo.ptos2, 230, 100);
        
        // Pintar mensaje de victoria si el juego terminó
        if (modelo.juegoTerminado) {
            g.setColor(Color.YELLOW);
            g.drawString(modelo.mensajeGanador, 60, 100);
        }
    }
}