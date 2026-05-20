package model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Partida{
	
	
	private Jugador j1;
	private Jugador j2;
	private Jugador ganador;
	private LocalDateTime fecha;
	static int nPartida = 0;
	
	public Partida(Jugador j1, Jugador j2, Jugador ganador, LocalDateTime fecha) {
		this.j1 = j1;
		this.j2 = j2;
		this.ganador = ganador;
		this.fecha = fecha;
		nPartida++;
		}

	
	public void guardarDatosPartidas(){
	
	try {
	    FileWriter fw = new FileWriter(".\\Data\\HistorialPartidas.txt", true); // true = añade al final
	    PrintWriter pw = new PrintWriter(fw);
	    
	    pw.print(nPartida+";"+j1+";"+j2+";"+ganador+";"+fecha);
	    pw.println();
	    
	    pw.close();
	} catch (Exception e) {
	    System.out.println("Error: " + e.getMessage());
	}
	
	}
	public Jugador getJ1() {
		return j1;
	}

	public void setJ1(Jugador j1) {
		this.j1 = j1;
	}

	public Jugador getJ2() {
		return j2;
	}

	public void setJ2(Jugador j2) {
		this.j2 = j2;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public int getNPartida() {
		return nPartida;
	}
	
	
	
}
