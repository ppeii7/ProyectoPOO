package control;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;
import model.Jugador;
import model.Usuario;
import view.*;
import view.admin.VentanaAdmin;

public class ControlApp {

    public ControlApp() {}

    static int MAX = 100;
     
    private Usuario usuarioActual;
    
    public void registrarUsuario(String username, String contrasena) {
        try {
            File userF = new File(".\\Data\\Users.txt");
            
            if (usuarioExiste(username)) {
                JOptionPane.showMessageDialog(null,
                    "Ese nombre de usuario ya está en uso. Elige otro.",
                    "Usuario duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Leer todas las líneas existentes
            java.util.List<String> lineas = new java.util.ArrayList<>();
            if (userF.exists()) {
                Scanner sc = new Scanner(userF);
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if (!linea.isEmpty()) lineas.add(linea); // Si la linea no esta vacia la añade
                }
                sc.close();
            }

            // Añadir el nuevo usuario a la lista
            lineas.add(username + ";" + contrasena);

            // Reescribir el fichero limpio
            PrintWriter out = new PrintWriter(new FileWriter(userF, false)); // false = sobreescribe
            for (String linea : lineas) {
                out.println(linea);
            }
            out.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e);
        }
    }

    private boolean usuarioExiste(String username) {
        try {
            File userF = new File(".\\Data\\Users.txt");
            if (!userF.exists()) return false; // si el archivo no existe devuelve falso
            Scanner sc = new Scanner(userF);
            while (sc.hasNextLine()) {
                String[] campos = sc.nextLine().split(";"); //separa entre campos para leer usuario
                if (campos.length > 0 && campos[0].equalsIgnoreCase(username)) { 
                    sc.close();
                    return true;
                }
            }
            sc.close();
        } catch (Exception e) { /* ignorar */ }
        return false;
    }

    public void iniciarSesion(String username, String contrasena) {
        try {
            File userF = new File(".\\Data\\Users.txt");
            if (!userF.exists()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios registrados aún.");
                return;
            }
            Scanner sc = new Scanner(userF);

            Jugador[] usuarios = new Jugador[MAX];
            int x = 0;

            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue;
                String[] campos = linea.split(";");
                if (campos.length >= 2) {
                    usuarios[x++] = new Jugador(campos[0], campos[1]);
                }
            }
            sc.close();

            // Buscar usuario
            int u = -1;
            for (int i = 0; i < x; i++) {
                if (usuarios[i].getUsername().equalsIgnoreCase(username)) {
                    u = i;
                    break; // En cuanto encuentra al usuario lo iguala a otra su indice y sale del bucle
                }
            }

            if (u == -1) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            } else if (usuarios[u].getPassword().equals(contrasena)) {
                if (usuarios[u].getUsername().equalsIgnoreCase("admin")) {
                	abrirVentanaAdmin(); // Si el usuario es el admin abre la ventana de admin(stats de tres en raya)
                	} else {
                    usuarioActual = usuarios[u];
                    abrirJuego(usuarios[u]); // si usuario y contraseña son correctos abre el menu de juegos
                }
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de sistema: " + e.getMessage());
        }
    }

    public Usuario getUsuarioActual() {
    	return usuarioActual;
    }
    
    public  String getNombreUsuarioActual() {
    	return usuarioActual.getUsername();
    }
    
    public  String getContraseñaActual() {
    	return usuarioActual.getPassword();
    }
    
    public void abrirRegistro() {
        // Ahora pasa 'this' para que VentanaRegistro pueda llamar a registrarUsuario()
        VentanaRegistro ventanaRegistro = new VentanaRegistro(this);
        ventanaRegistro.setVisible(true);
    }

    public void abrirJuego(Jugador jugador) {
        VentanaJuegos ventanaJuegos = new VentanaJuegos(jugador, this);
        ventanaJuegos.setVisible(true);
    }

    private void abrirVentanaAdmin() {
        VentanaAdmin ventanaAdmin = new VentanaAdmin();
        ventanaAdmin.setVisible(true);
    }
}