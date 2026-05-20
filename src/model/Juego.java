package model;

/**
 * Clase abstracta Juego.
 * Define el contrato común que debe cumplir cualquier juego del sistema.
 * Cada juego concreto (Pasapalabra, Ahorcado, etc.) extiende esta clase.
 */
public abstract class Juego {

    /** Nombre identificativo del juego (ej: "Pasapalabra", "Ahorcado"). */
    private String nombre;

    private int minJugadores;

    private int maxJugadores;

    public Juego(String nombre, int minJugadores, int maxJugadores) {
        this.nombre = nombre;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
    }


    public abstract void inicializar();

    public abstract String procesarTurno(Jugador jugador, String entrada);

    public abstract boolean isPartidaTerminada();

    public abstract int getPuntuacion(Jugador jugador);

    public abstract Jugador getGanador();

    public abstract String getEstadoVisible();

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public int getMinJugadores() {
        return minJugadores;
    }

    public int getMaxJugadores() {
        return maxJugadores;
    }

    @Override
    public String toString() {
        return "Juego{nombre='" + nombre + "'}";
    }
}