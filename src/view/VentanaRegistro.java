package view;

import javax.swing.*;

import control.ControlApp;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaRegistro extends JFrame {

	private ControlApp controlador;

	public VentanaRegistro(ControlApp controlador) {

		this.controlador= controlador;
		
		setTitle("Patata Caliente");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setLocationRelativeTo(null); // Centra la ventana al centro

		setLayout(new BorderLayout());

		inicializarComponentes();
	}

	private void inicializarComponentes() {
		
		Font fuente = new Font("Limelight", Font.BOLD, 14);
		
	    JPanel panelCentral = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);

	    // Caja de texto usuario
	    JTextField cajaUsuario = new JTextField("Nombre de usuario");
	    cajaUsuario.setFont(fuente);
	    cajaUsuario.setForeground(Color.GRAY);
	    cajaUsuario.setPreferredSize(new Dimension(200, 30));
	    cajaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (cajaUsuario.getText().equals("Nombre de usuario")) {
	            	cajaUsuario.setText("");
	            	cajaUsuario.setForeground(Color.BLACK);
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (cajaUsuario.getText().isEmpty()) {
	            	cajaUsuario.setText("Nombre de usuario");
	            	cajaUsuario.setForeground(Color.GRAY);
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 0;
	    panelCentral.add(cajaUsuario, gbc);

	 // Caja de contraseña
	    JPasswordField cajaContraseña = new JPasswordField("Contraseña");
	    cajaContraseña.setFont(fuente);
	    cajaContraseña.setForeground(Color.GRAY);
	    cajaContraseña.setEchoChar((char) 0); // ← añade esto aquí
	    cajaContraseña.setPreferredSize(new Dimension(200, 30));
	    cajaContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (String.valueOf(cajaContraseña.getPassword()).equals("Contraseña")) {
	            	cajaContraseña.setText("");
	            	cajaContraseña.setForeground(Color.BLACK);
	            	cajaContraseña.setEchoChar('•'); // ← activa puntos al escribir
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (String.valueOf(cajaContraseña.getPassword()).isEmpty()) {
	            	cajaContraseña.setText("Contraseña");
	            	cajaContraseña.setForeground(Color.GRAY);
	                cajaContraseña.setEchoChar((char) 0); // ← desactiva puntos al volver placeholder
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 1;
	    panelCentral.add(cajaContraseña, gbc);


	    // Botón registrarse
	    JButton btnRegistro = new JButton("Registrarme");
	    btnRegistro.setFont(fuente);
	    btnRegistro.setPreferredSize(new Dimension(200, 30));
	    gbc.gridx = 0; gbc.gridy = 3;
	    panelCentral.add(btnRegistro, gbc);

	    add(panelCentral, BorderLayout.CENTER);
	    
	    btnRegistro.addMouseListener(new MouseAdapter() {
	    	
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		String pass = String.valueOf(cajaContraseña.getPassword());
	    		controlador.registrarUsuario(cajaUsuario.getText(),pass);
	    		
	    		dispose();
	    	}
	    });
	}
	}


