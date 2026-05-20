package control;

import model.Jugador;
import model.pasapalabra.EstadoPreguntas;
import model.pasapalabra.Pasapalabra;
import model.pasapalabra.Preguntas;
import view.pasapalabra.VentanaPasapalabra;

public class ControladorPasapalabra {

    private final Pasapalabra        modelo;
    private final VentanaPasapalabra vista;

    public ControladorPasapalabra(Pasapalabra modelo, VentanaPasapalabra vista) {
        this.modelo = modelo;
        this.vista  = vista;

        vista.getBtnResponder().addActionListener(e   -> procesarRespuesta());
        vista.getBtnPasapalabra().addActionListener(e -> procesarPasapalabra());
        vista.getBtnSalir().addActionListener(e       -> {
            // Al salir en medio de la partida se guarda el progreso
            modelo.guardarProgreso();
            vista.dispose();
        });

        refrescarVista();
    }

    private void procesarRespuesta() {
        if (modelo.isPartidaTerminada()) return;

        String respuesta = vista.getRespuesta().trim();
        if (respuesta.isEmpty()) return;

        Jugador jugador = modelo.getGanador();
        modelo.procesarTurno(jugador, respuesta);

        // ── Guardar progreso tras cada turno ─────────────────────────────────
        modelo.guardarProgreso();

        vista.limpiarRespuesta();
        refrescarVista();
        comprobarFinPartida();
    }

    private void procesarPasapalabra() {
        if (modelo.isPartidaTerminada()) return;

        Jugador jugador = modelo.getGanador();
        modelo.procesarTurno(jugador, "pasapalabra");

        // ── Guardar progreso tras cada turno ─────────────────────────────────
        modelo.guardarProgreso();

        vista.limpiarRespuesta();
        refrescarVista();
        comprobarFinPartida();
    }

    private void refrescarVista() {
        vista.actualizarRosco(modelo.getRosco().getPreguntas());

        int aciertos = 0, fallos = 0;
        for (Preguntas p : modelo.getRosco().getPreguntas()) {
            if (p == null) continue;
            if (p.getEstado() == EstadoPreguntas.ACERTADA) aciertos++;
            if (p.getEstado() == EstadoPreguntas.FALLADA)  fallos++;
        }
        vista.actualizarContadores(aciertos, fallos);

        Preguntas siguiente = modelo.getRosco().getSiguientePregunta();
        if (siguiente != null) {
            vista.mostrarPregunta(siguiente.getLetra(), siguiente.getEnunciado());
        } else {
            vista.mostrarPregunta(' ', "No quedan preguntas pendientes.");
        }
    }

    private void comprobarFinPartida() {
        if (!modelo.isPartidaTerminada()) return;

        // ── Partida completada: borrar el fichero de progreso ─────────────────
        Pasapalabra.eliminarProgreso(modelo.getGanador().getUsername());

        int aciertos = 0, fallos = 0;
        for (Preguntas p : modelo.getRosco().getPreguntas()) {
            if (p == null) continue;
            if (p.getEstado() == EstadoPreguntas.ACERTADA) aciertos++;
            if (p.getEstado() == EstadoPreguntas.FALLADA)  fallos++;
        }
        vista.mostrarResultadoFinal(aciertos, fallos);
    }
}