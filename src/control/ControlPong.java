package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import model.ModeloPong;
import view.VentanaPong;

public class ControlPong implements ActionListener, KeyListener {
    
    private ModeloPong modelo;
    private VentanaPong vista;
    private Timer timer;

    public ControlPong(ModeloPong modelo, VentanaPong vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.addKeyListener(this);
        timer = new Timer(10, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        modelo.actualizarFisicas();
        if (modelo.juegoTerminado) timer.stop();
        vista.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {    
        int tecla = e.getKeyCode();
        
        // Las teclas W y S siempre controlan al jugador 1
        if (tecla == KeyEvent.VK_W) modelo.velocidadY1 = -5;
        if (tecla == KeyEvent.VK_S) modelo.velocidadY1 = 5; 
        
        // Las flechas cambian de función dependiendo del modo
        if (tecla == KeyEvent.VK_UP) {
            if (modelo.modoCPU) modelo.velocidadY1 = -5;
            else modelo.velocidadY2 = -5;
        }
        if (tecla == KeyEvent.VK_DOWN) {
            if (modelo.modoCPU) modelo.velocidadY1 = 5;
            else modelo.velocidadY2 = 5;
        }
        
        if (tecla == KeyEvent.VK_ESCAPE) {
            if (timer.isRunning()) timer.stop(); 
            else if (!modelo.juegoTerminado) timer.start();
        }
        
        if (tecla == KeyEvent.VK_R && !timer.isRunning()) {
            modelo.reiniciar();
            timer.start();
        }
        
        if(!timer.isRunning() && !modelo.juegoTerminado) {
            if(tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_W || tecla == KeyEvent.VK_S) {
                timer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();
        
        if (tecla == KeyEvent.VK_W || tecla == KeyEvent.VK_S) modelo.velocidadY1 = 0; 
        
        if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN) {
            if (modelo.modoCPU) modelo.velocidadY1 = 0;
            else modelo.velocidadY2 = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}