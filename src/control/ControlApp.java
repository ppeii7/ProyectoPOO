package control;

import javax.swing.*;
import model.Jugador;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import view.*;
import view.admin.VentanaAdmin;

public class ControlApp {

    public ControlApp() {
    }



    static int MAX = 100;

    public void registrarUsuario(String username, String contrasena) {
        try {
            File userF = new File(".\\Data\\users.txt");
            FileWriter fw = new FileWriter(userF, true);
            PrintWriter outF = new PrintWriter(fw);

            Jugador jugador = new Jugador(username, contrasena);
            outF.println(jugador.getUsername() + ";" + jugador.getPassword());
            outF.close();
            fw.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e);
        }
    }

    public void iniciarSesion(String username, String contrasena) {
        try {
            File userF = new File(".\\Data\\users.txt");
            Scanner sc = new Scanner(userF);

            Jugador[] usuarios = new Jugador[MAX];
            int x = 0;

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(";");
                if (campos[0] == null) {
                    usuarios[x] = new Jugador("0", "0");
                } else {
                    usuarios[x] = new Jugador(campos[0], campos[1]);
                }
                x++;
            }
            sc.close();

            // buscar usuario
            int u = -1;
            for (int i = 0; i < x; i++) {
                if (usuarios[i].getUsername().equals(username.toLowerCase())) {
                    u = i;
                    break;
                }
            }

            if (u == -1) {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            } else if (usuarios[u].getPassword().equals(contrasena)) {
            	if(usuarios[u].getUsername().equals("admin")) {
            		abrirVentanaAdmin();
            	}else {
                abrirJuego(usuarios[u]);
            	}
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de sistema");
        }
    }
    
    
    public void abrirRegistro() {
        VentanaRegistro ventanaRegistro = new VentanaRegistro();
        ventanaRegistro.setVisible(true);
    }

    public void abrirJuego(Jugador jugador) {
        VentanaJuegos ventanaJuegos = new VentanaJuegos(jugador);
        ventanaJuegos.setVisible(true);
    }
    
	private void abrirVentanaAdmin() {
		VentanaAdmin ventanaAdmin = new VentanaAdmin();
		ventanaAdmin.setVisible(true);
		}

}