package control;

import javax.swing.JFrame;
import model.*;
import model.pasapalabra.Pasapalabra;
import view.*;
import view.pasapalabra.VentanaDificultadPasapalabras;
import view.pasapalabra.VentanaPasapalabra;
import view.tresenraya.VentanaRegistroTresEnRaya;

public class ControlJuego {

	private final ControlApp controlApp;

	public ControlJuego(ControlApp controlApp) {
		this.controlApp = controlApp;
	}

	// ── Snake ────────────────────────────────────────────────────────────────
	public void abrirVentanaSnake() {
		JFrame ventana = new JFrame("Snake");
		ventana.setSize(616, 700);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);

		ModeloSnake modelo = new ModeloSnake();
		VentanaSnake vista = new VentanaSnake(modelo);
		ControlSnake controlador = new ControlSnake(modelo, vista);

		ventana.add(vista);
		ventana.setVisible(true);
	}

	// ── Pong ─────────────────────────────────────────────────────────────────
	public void abrirVentanaPong() {
		JFrame ventana = new JFrame("Pong");
		ventana.setSize(600, 400);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);

		// Pregunta al jugador si quiere jugar contra la CPU o contra otro jugador
		String[] opciones = { "Contra la CPU", "Dos jugadores" };
		int eleccion = javax.swing.JOptionPane.showOptionDialog(null, "¿Cómo quieres jugar al Pong?", "Modo de juego",
				javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opciones,
				opciones[0]);

		if (eleccion < 0)
			return; // canceló

		boolean modoCPU = (eleccion == 0);

		ModeloPong modelo = new ModeloPong(modoCPU);
		VentanaPong vista = new VentanaPong(modelo);
		ControlPong controlador = new ControlPong(modelo, vista);

		ventana.add(vista);
		ventana.setVisible(true);
	}
	
	public void abrirVentanaPasapalabra(Jugador jugador) {

    // ¿Hay una partida guardada para este usuario?
    if (Pasapalabra.hayProgresoGuardado(jugador.getUsername())) {

        int opcion = javax.swing.JOptionPane.showConfirmDialog(
            null,
            "Tienes una partida guardada.\n¿Quieres continuar donde lo dejaste?",
            "Partida guardada",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            // Cargar la ruta guardada y reanudar directamente
            String rutaGuardada = Pasapalabra.getRutaGuardada(jugador.getUsername());
            if (rutaGuardada != null) {
                VentanaPasapalabra.abrirConProgreso(jugador, rutaGuardada);
                return;
            }
        } else {
            // El jugador descarta la partida guardada
            Pasapalabra.eliminarProgreso(jugador.getUsername());
        }
    }

    // Sin partida guardada (o descartada): mostrar pantalla de dificultad
    new VentanaDificultadPasapalabras(jugador).setVisible(true);
}

	public void abrirVentanaRegistroTresEnRaya() {

		VentanaRegistroTresEnRaya vregistro = new VentanaRegistroTresEnRaya(controlApp);
		vregistro.setVisible(true);
	}

}