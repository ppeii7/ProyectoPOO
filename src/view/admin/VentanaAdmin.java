package view.admin;

import control.ControlAdmin;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaAdmin extends JFrame {
	public VentanaAdmin() {
		setTitle("Panel admin");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la app al cerrar la ventana
		setLocationRelativeTo(null); // Centra la ventana al centro

		setLayout(new BorderLayout());

		inicializarComponentes();
	}

	private void inicializarComponentes() {

	}
}