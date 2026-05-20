package model;


public class TresEnRaya extends Juego{

	
	
	public TresEnRaya(String nombre, int minJugadores, int maxJugadores) {
		super(nombre, minJugadores, maxJugadores);

		
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
