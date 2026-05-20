package model;

import java.awt.Rectangle;
import java.util.Random;

public class ModeloPong extends Juego{
    Random rand = new Random();
    
    // Variables de estado
    public int ptos1=0, ptos2=0;
    public int x1=7, x2=520, xB=270;
    public int y1=100, y2=100, yB=100;
    public int velocidadXB = rand.nextInt(6)-3;
    public int velocidadYB = rand.nextInt(11)-5;
    public int velocidadY1=0, velocidadY2=0;
    
    public boolean juegoTerminado = false;
    public String mensajeGanador = "";
    
    // Nuevo: Indicador de modo de juego
    public boolean modoCPU;

    // Constructor que recibe el modo de juego
    public ModeloPong(boolean modoCPU) {
    	super("Pong",1,2);
        this.modoCPU = modoCPU;
    }

    public void reiniciar() {
        ptos1=0; ptos2=0;
        x1=7; x2=520; xB=270;
        y1=100; y2=100; yB=100;
        velocidadXB = rand.nextInt(6)-3;
        velocidadYB = rand.nextInt(11)-5;
        velocidadY1=0; velocidadY2=0;
        juegoTerminado = false;
        mensajeGanador = "";
    }

    public void actualizarFisicas() {
        if(juegoTerminado) return;

        if(velocidadXB==0) velocidadXB=rand.nextInt(11)-5;
        if(velocidadYB==0) velocidadYB=rand.nextInt(11)-5;
        
        // Lógica de la CPU: La paleta 2 copia la velocidad Y de la bola
        if(modoCPU) {
            velocidadY2 = velocidadYB;
        }
        
        xB+=velocidadXB;
        y1+=velocidadY1;
        y2+=velocidadY2;
        yB+=velocidadYB;
        
        Rectangle rect1 = new Rectangle(x1, y1, 10, 70);
        Rectangle rect2 = new Rectangle(x2, y2, 10, 70);
        Rectangle bola = new Rectangle(xB, yB, 15, 15);
        
        if(yB<0 || yB>312) velocidadYB*=-1;
        
        if(y1<0) y1=0;
        if(y1>270) y1=270;
        if(y2<0) y2=0;
        if(y2>270) y2=270;
        
        if(bola.intersects(rect1)) {
            velocidadXB*=-1 ;
            velocidadXB+=1 ;
        }
        if(bola.intersects(rect2)) {
            velocidadXB*=-1;
            velocidadXB-=1 ;
        }
        
        // Lógica de puntuación combinada
        if(xB<0) {
            ptos2++;
            xB=270; yB=100; velocidadXB=5;
            if(modoCPU) { y1=100; y2=100; } // Reseteo extra que tenías en CPU
        }
        if (xB>x2) { 
            ptos1++;
            xB=270; yB=100; velocidadXB=-5;
            if(modoCPU) { y1=100; y2=100; }
        }

        if (ptos1>=7) {
            juegoTerminado = true;
            mensajeGanador = "Gana jugador 1!";
        } else if (ptos2>=7) {
            juegoTerminado = true;
            mensajeGanador = modoCPU ? "Gana la CPU!" : "Gana jugador 2!";
        }
    }

	@Override
	public void inicializar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String procesarTurno(Jugador jugador, String entrada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartidaTerminada() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPuntuacion(Jugador jugador) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Jugador getGanador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEstadoVisible() {
		// TODO Auto-generated method stub
		return null;
	}
}