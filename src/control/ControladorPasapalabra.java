package control;

import model.Jugador;
import model.pasapalabra.EstadoPreguntas;
import model.pasapalabra.Pasapalabra;
import model.pasapalabra.Preguntas;
import view.pasapalabra.VentanaPasapalabra;

/**
 * Controlador del juego Pasapalabra.
 *
 * Sigue el mismo patrón MVC que ControlSnake:
 *   - Recibe eventos de la vista (botones)
 *   - Delega la lógica al modelo (Pasapalabra)
 *   - Actualiza la vista con el nuevo estado
 *
 * No contiene lógica de juego propia; toda la lógica
 * permanece en model.pasapalabra.Pasapalabra.
 */
public class ControladorPasapalabra {

    private final Pasapalabra        modelo;
    private final VentanaPasapalabra vista;

    // ────────────────────────────────────────────────────────────────────────
    public ControladorPasapalabra(Pasapalabra modelo, VentanaPasapalabra vista) {
        this.modelo = modelo;
        this.vista  = vista;

        // Conectar botones de la vista con métodos de este controlador
        vista.getBtnResponder().addActionListener(e -> procesarRespuesta());
        vista.getBtnPasapalabra().addActionListener(e -> procesarPasapalabra());
        vista.getBtnSalir().addActionListener(e -> vista.dispose());

        // Mostrar estado inicial
        refrescarVista();
    }

    // ── Procesa la respuesta escrita por el jugador ──────────────────────────
    private void procesarRespuesta() {
        if (modelo.isPartidaTerminada()) return;

        String respuesta = vista.getRespuesta().trim();
        if (respuesta.isEmpty()) return;

        Jugador jugador = modelo.getGanador(); // getGanador() devuelve el jugadorActual
        modelo.procesarTurno(jugador, respuesta);

        vista.limpiarRespuesta();
        refrescarVista();
        comprobarFinPartida();
    }

    // ── Procesa el "pasapalabra" ─────────────────────────────────────────────
    private void procesarPasapalabra() {
        if (modelo.isPartidaTerminada()) return;

        Jugador jugador = modelo.getGanador();
        modelo.procesarTurno(jugador, "pasapalabra");

        vista.limpiarRespuesta();
        refrescarVista();
        comprobarFinPartida();
    }

    // ── Actualiza todos los elementos visuales con el estado actual del modelo ─
    private void refrescarVista() {
        // Actualizar el rosco (colores de letras)
        vista.actualizarRosco(modelo.getRosco().getPreguntas());

        // Contar aciertos y fallos
        int aciertos = 0, fallos = 0;
        for (Preguntas p : modelo.getRosco().getPreguntas()) {
            if (p == null) continue;
            if (p.getEstado() == EstadoPreguntas.ACERTADA) aciertos++;
            if (p.getEstado() == EstadoPreguntas.FALLADA)  fallos++;
        }
        vista.actualizarContadores(aciertos, fallos);

        // Mostrar la siguiente pregunta pendiente
        Preguntas siguiente = modelo.getRosco().getSiguientePregunta();
        if (siguiente != null) {
            vista.mostrarPregunta(siguiente.getLetra(), siguiente.getEnunciado());
        } else {
            vista.mostrarPregunta(' ', "No quedan preguntas pendientes.");
        }
    }

    // ── Comprueba si la partida terminó y muestra resultado final ────────────
    private void comprobarFinPartida() {
        if (!modelo.isPartidaTerminada()) return;

        int aciertos = 0, fallos = 0;
        for (Preguntas p : modelo.getRosco().getPreguntas()) {
            if (p == null) continue;
            if (p.getEstado() == EstadoPreguntas.ACERTADA) aciertos++;
            if (p.getEstado() == EstadoPreguntas.FALLADA)  fallos++;
        }

        vista.mostrarResultadoFinal(aciertos, fallos);
    }
}