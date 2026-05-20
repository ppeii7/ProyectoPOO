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

        // "e ->" es una expresion lambda, es para no tener que meter el ActionListener() dentro y decirle que hacer si picas el boton
        vista.getBtnResponder().addActionListener(e   -> procesarRespuesta());
        vista.getBtnPasapalabra().addActionListener(e -> procesarPasapalabra());
        vista.getBtnSalir().addActionListener(e       -> {
            // Al salir en medio de la partida se guarda el progreso
            modelo.guardarProgreso();
            vista.dispose();
        });

        refrescarVista(); // Para que se pinten los colores en cuanto abras la ventana
    }

    private void procesarRespuesta() {
        if (modelo.isPartidaTerminada()) return; // Si la partida esta terminada no hace nada

        String respuesta = vista.getRespuesta().trim(); //Registra la respuesta del usuario con lo del trim por si puso algun espacio que sobraba
        if (respuesta.isEmpty()) return; // Si la respuesta esta vacia no hace nada para no gastar turnos

        Jugador jugador = modelo.getGanador(); // saco el jugador actual. Usamos getGanador() porque era una clase abstracta y la adaptamos para esto
        modelo.procesarTurno(jugador, respuesta); // Mira si es correcta la respuesta

        // ── Guardar progreso tras cada turno ─────────────────────────────────
        modelo.guardarProgreso();

        vista.limpiarRespuesta(); //Borra la respuesta del cuadro de texto
        refrescarVista(); //vuelvo a pintar
        comprobarFinPartida(); //Miro si la partida ya termino
    }

    private void procesarPasapalabra() {
        if (modelo.isPartidaTerminada()) return; // Si la partida esta terminada no hace nada

        Jugador jugador = modelo.getGanador(); // saco el jugador actual. Usamos getGanador() porque era una clase abstracta y la adaptamos para esto
        modelo.procesarTurno(jugador, "pasapalabra");  //Le paso como respuesta "Pasapalabra"

        // ── Guardar progreso tras cada turno ─────────────────────────────────
        modelo.guardarProgreso();

        vista.limpiarRespuesta(); //Borra la respuesta del cuadro de texto
        refrescarVista(); // vuelvo a pintar
        comprobarFinPartida(); // Miro si ya termino la partida
    }

    private void refrescarVista() {
        vista.actualizarRosco(modelo.getRosco().getPreguntas()); //Actualiza los colores del rosco

        int aciertos = 0, fallos = 0;
        for (Preguntas p : modelo.getRosco().getPreguntas()) { // Cuenta los aciertos y fallos
            if (p == null) continue; // Si no hay nada se salta todo lo del bucle
            if (p.getEstado() == EstadoPreguntas.ACERTADA) aciertos++;
            if (p.getEstado() == EstadoPreguntas.FALLADA)  fallos++;
        }
        vista.actualizarContadores(aciertos, fallos); //Actualiza los aciertos y fallos en la ventana

        Preguntas siguiente = modelo.getRosco().getSiguientePregunta(); //Saca la siguiente pregunta del rosco
        if (siguiente != null) {
            vista.mostrarPregunta(siguiente.getLetra(), siguiente.getEnunciado());
        } else {
            vista.mostrarPregunta(' ', "No quedan preguntas pendientes.");
        }
    }

    private void comprobarFinPartida() {
        if (!modelo.isPartidaTerminada()) return; //Si la partida esta terminada no hago nada

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