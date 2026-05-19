package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase Administrador, extiende Usuario.
 */
public class Administrador extends Usuario {

    // Usuario especial predefinido
    private  static String USERNAME_ADMIN = "admin";
    private static String PASSWORD_ADMIN = "admin1234";

public Administrador() {
    super(USERNAME_ADMIN, PASSWORD_ADMIN);
}  

    @Override
    public String toString() {
        return "Administrador{username='" + getUsername() + "'}";
    }
}