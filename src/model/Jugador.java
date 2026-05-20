package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Jugador, extiende Usuario.
 */
public class Jugador extends Usuario {

	

    public Jugador(String username, String password) {
        super(username, password);
    }


    @Override
    public String toString() {
        return "Jugador{username='" + getUsername() + "', partidas=" + "}";
    }
}