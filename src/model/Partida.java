package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import view.tresenraya.VentanaTresEnRaya;

public class Partida{
	
	
	private Jugador j1;
	private Jugador j2;
	private String resultado;
	private Jugador ganador;
	private LocalDateTime fecha;
	
	public Partida(Jugador j1, Jugador j2, String resultado, Jugador ganador, LocalDateTime fecha) {
		this.j1 = j1;
		this.j2 = j2;
		this.resultado = resultado;
		this.ganador = ganador;
		this.fecha = fecha;
		new VentanaTresEnRaya();
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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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
	
	
	
}
