package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.ModeloSnake;

public class VentanaSnake extends JPanel {
    
    private ModeloSnake modelo;

    public VentanaSnake(ModeloSnake modelo) {
        this.modelo = modelo;
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int px = col * modelo.CELL_SIZE;
                int py = row * modelo.CELL_SIZE + 60; 

                switch (modelo.mapa.mapa[row][col]) {
                    case 1 -> { 
                        g.setColor(new Color(40, 40, 40));
                        g.fillRect(px, py, modelo.CELL_SIZE, modelo.CELL_SIZE);
                    }
                    case 2 -> { 
                        g.setColor(new Color(50, 200, 50));
                        g.fillRect(px, py, modelo.CELL_SIZE, modelo.CELL_SIZE);
                        g.setColor(new Color(20, 140, 20));
                        g.fillRect(px + 5, py + 5, modelo.CELL_SIZE - 10, modelo.CELL_SIZE - 10);
                    }
                    case 3 -> { 
                        g.setColor(new Color(80, 170, 80));
                        g.fillRect(px, py, modelo.CELL_SIZE, modelo.CELL_SIZE);
                    }
                    case 4 -> { 
                        g.setColor(new Color(220, 50, 50));
                        g.fillOval(px + 4, py + 4, modelo.CELL_SIZE - 8, modelo.CELL_SIZE - 8);
                    }
                    case 5 -> { 
                        g.setColor(new Color(253, 191, 0));
                        g.fillOval(px + 4, py + 4, modelo.CELL_SIZE - 8, modelo.CELL_SIZE - 8);
                    }
                    case 6 -> { 
                        g.setColor(new Color(34, 139, 34));
                        g.fillOval(px + 4, py + 4, modelo.CELL_SIZE - 8, modelo.CELL_SIZE - 8);
                    }
                    default -> { 
                        g.setColor(new Color(230, 230, 210));
                        g.fillRect(px, py, modelo.CELL_SIZE, modelo.CELL_SIZE);
                        g.setColor(new Color(200, 200, 180));
                        g.drawRect(px, py, modelo.CELL_SIZE, modelo.CELL_SIZE);
                    }
                }
            }
        }

        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, 600, 60);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString("Puntos: " + modelo.ptos, 20, 42);

        if (modelo.nextDirRow == 0 && modelo.nextDirCol == 0 && !modelo.crash) {
            g.setColor(new Color(255, 255, 255, 180));
            g.setFont(new Font("Consolas", Font.PLAIN, 20));
            g.drawString("Pulsa una tecla para empezar", 140, 42);
        }

        if (modelo.crash) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 60, 600, 600);
            g.setColor(Color.RED);
            g.setFont(new Font("Consolas", Font.BOLD, 60));
            g.drawString("GAME OVER", 90, 350);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Consolas", Font.PLAIN, 22));
            g.drawString("Puntos: " + modelo.ptos, 240, 410);
            g.drawString("Pulsa R para reiniciar", 175, 450);
        }
    }
}