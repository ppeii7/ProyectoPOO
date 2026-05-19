package control;

import java.awt.event.MouseListener;
import java.awt.event.TextListener;

import javax.swing.SwingUtilities;

import view.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ControlApp controlador = new ControlApp();
			VentanaPrincipal ventana = new VentanaPrincipal(controlador);
			ventana.setVisible(true);
		});
	}
}