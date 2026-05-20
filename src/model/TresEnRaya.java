package model;

import model.Jugador;

public class TresEnRaya {

	private Jugador j1;
	private Jugador j2;
	private String[] tablero = new String[9];
	private boolean turnoJ1 = true;

	public TresEnRaya(Jugador j1, Jugador j2) {
		this.j1 = j1;
		this.j2 = j2;
		reiniciar();
	}

	public boolean colocarFicha(int indice) {
		if (!tablero[indice].isEmpty())
			return false; // casilla ocupada
		tablero[indice] = turnoJ1 ? "X" : "O";
		return true;
	}

	public boolean hayGanador() {
		int[][] combinaciones = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // filas
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // columnas
				{ 0, 4, 8 }, { 2, 4, 6 } // diagonales
		};
		for (int[] c : combinaciones) {
			if (!tablero[c[0]].isEmpty() && tablero[c[0]].equals(tablero[c[1]])
					&& tablero[c[1]].equals(tablero[c[2]])) {
				return true;
			}
		}
		return false;
	}

	public int[] getCombinacionGanadora() {
		int[][] combinaciones = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
				{ 0, 4, 8 }, { 2, 4, 6 } };
		for (int[] c : combinaciones) {
			if (!tablero[c[0]].isEmpty() && tablero[c[0]].equals(tablero[c[1]])
					&& tablero[c[1]].equals(tablero[c[2]])) {
				return c;
			}
		}
		return null;
	}

	public boolean tableroLleno() {
		for (String s : tablero) {
			if (s.isEmpty())
				return false;
		}
		return true;
	}

	public void cambiarTurno() {
		turnoJ1 = !turnoJ1;
	}

	public void reiniciar() {
		for (int i = 0; i < 9; i++)
			tablero[i] = "";
		turnoJ1 = true;
	}

	// ── Getters ──────────────────────────────────────────────────────────────
	public String[] getTablero() {
		return tablero;
	}

	public boolean isTurnoJ1() {
		return turnoJ1;
	}

	public Jugador getJ1() {
		return j1;
	}

	public Jugador getJ2() {
		return j2;
	}

	public Jugador getJugadorActual() {
		return turnoJ1 ? j1 : j2;
	}

	public Jugador getGanador() {
		return turnoJ1 ? j1 : j2;
	}
}