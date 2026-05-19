package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import model.ModeloSnake;
import view.VentanaSnake;

public class ControlSnake implements ActionListener, KeyListener {
    
    private ModeloSnake modelo;
    private VentanaSnake vista;
    private Timer timer;

    public ControlSnake(ModeloSnake modelo, VentanaSnake vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        this.vista.addKeyListener(this);
        
        this.timer = new Timer(150, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Actualizamos lógica
        modelo.actualizarLogica();
        
        // Si hay choque tras actualizar la lógica, detenemos el timer
        if (modelo.crash) {
            timer.stop();
        }
        
        // Repintamos
        vista.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if ((tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) && modelo.dirCol != 1) {
            modelo.nextDirRow = 0;  modelo.nextDirCol = -1;
        }
        if ((tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) && modelo.dirCol != -1) {
            modelo.nextDirRow = 0;  modelo.nextDirCol = 1;
        }
        if ((tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) && modelo.dirRow != 1) {
            modelo.nextDirRow = -1; modelo.nextDirCol = 0;
        }
        if ((tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) && modelo.dirRow != -1) {
            modelo.nextDirRow = 1;  modelo.nextDirCol = 0;
        }
        
        if (tecla == KeyEvent.VK_ESCAPE) {
            if (timer.isRunning()) timer.stop();
            else if (!modelo.crash) timer.start();
        }
        
        if (tecla == KeyEvent.VK_R && (!timer.isRunning() || modelo.crash)) {
            modelo.reiniciar();
            timer.start();
            vista.repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
