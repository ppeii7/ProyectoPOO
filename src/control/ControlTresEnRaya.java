package control;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Jugador;
import model.Partida;
import model.TresEnRaya;
import view.tresenraya.VentanaTresEnRaya;

public class ControlTresEnRaya {

	private Jugador j1;
	private Jugador j2;
	private TresEnRaya modelo;
	private VentanaTresEnRaya vista;

	// ── Autenticación ────────────────────────────────────────────────────────
	public Jugador autenticarJugador(String username, String contrasena) {
		try {
			java.io.File userF = new java.io.File(".\\Data\\Users.txt");
			if (!userF.exists())
				return null;

			java.util.Scanner sc = new java.util.Scanner(userF);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine().trim();
				if (linea.isEmpty())
					continue;
				String[] campos = linea.split(";");
				if (campos.length >= 2 && campos[0].equalsIgnoreCase(username) && campos[1].equals(contrasena)) {
					sc.close();
					return new Jugador(campos[0], campos[1]);
				}
			}
			sc.close();
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "Error al autenticar: " + e.getMessage());
		}
		return null;
	}

	// ── Añadir jugadores ─────────────────────────────────────────────────────
	public boolean añadirJugadores(String userJ1, String passJ1, String userJ2, String passJ2) {
		j1 = autenticarJugador(userJ1, passJ1);
		j2 = autenticarJugador(userJ2, passJ2);

		if (j1 == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Jugador 1: usuario o contraseña incorrectos.");
			return false;
		}
		if (j2 == null) {
			javax.swing.JOptionPane.showMessageDialog(null, "Jugador 2: usuario o contraseña incorrectos.");
			return false;
		}
		return true;
	}
	Partida partida;
	public void setGanador(Jugador ganador) {
	    partida.setGanador(ganador);
	}

	public void crearPartida() {
	    modelo  = new TresEnRaya(j1, j2);
	    partida = new Partida(j1, j2, null, LocalDateTime.now()); // ganador null al inicio
	    vista   = new VentanaTresEnRaya(j1, j2, this); // pasa el controlador
	}
	
	public void guardarDatosPartidas(){
		
		try {
		    FileWriter fw = new FileWriter(".\\Data\\HistorialTresEnRaya.txt", true); // true = añade al final
		    PrintWriter pw = new PrintWriter(fw);
		    
		    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		    pw.println(partida.getNPartida() + ";" +
		               j1.getUsername() + ";" +           
		               j2.getUsername() + ";" +
		               partida.getGanador().getUsername() + ";" +
		               partida.getFecha().format(formato));
		    pw.close();
		} catch (Exception e) {
		    System.out.println("Error: " + e.getMessage());
		}
		
		}

	// ── Getters ──────────────────────────────────────────────────────────────
	public Jugador getJ1() {
		return j1;
	}

	public Jugador getJ2() {
		return j2;
	}

	public TresEnRaya getModelo() {
		return modelo;
	}

	public VentanaTresEnRaya getVista() {
		return vista;
	}
}