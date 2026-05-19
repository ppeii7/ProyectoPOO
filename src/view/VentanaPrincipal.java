package view;

import javax.swing.*;

import control.*;

import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame implements MouseListener, TextListener, ActionListener{

	private ControlApp controlador;
	
	public VentanaPrincipal(ControlApp controlador) {

		this.controlador = controlador;
		setTitle("Patata Caliente");
		setSize(1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la app al cerrar la ventana
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
	    JTextField CajaUsuario = new JTextField("Nombre de usuario");
	    CajaUsuario.setFont(fuente);
	    CajaUsuario.setForeground(Color.GRAY);
	    CajaUsuario.setPreferredSize(new Dimension(200, 30));
	    CajaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (CajaUsuario.getText().equals("Nombre de usuario")) {
	            	CajaUsuario.setText("");
	            	CajaUsuario.setForeground(Color.BLACK);
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (CajaUsuario.getText().isEmpty()) {
	            	CajaUsuario.setText("Nombre de usuario");
	            	CajaUsuario.setForeground(Color.GRAY);
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 1;
	    panelCentral.add(CajaUsuario, gbc);

	 // Caja de contraseña
	    JPasswordField CajaContraseña = new JPasswordField("Contraseña");
	    CajaContraseña.setFont(fuente);
	    CajaContraseña.setForeground(Color.GRAY);
	    CajaContraseña.setEchoChar((char) 0); // ← añade esto aquí
	    CajaContraseña.setPreferredSize(new Dimension(200, 30));
	    CajaContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (String.valueOf(CajaContraseña.getPassword()).equals("Contraseña")) {
	                CajaContraseña.setText("");
	                CajaContraseña.setForeground(Color.BLACK);
	                CajaContraseña.setEchoChar('•'); // ← activa puntos al escribir
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (String.valueOf(CajaContraseña.getPassword()).isEmpty()) {
	                CajaContraseña.setText("Contraseña");
	                CajaContraseña.setForeground(Color.GRAY);
	                CajaContraseña.setEchoChar((char) 0); // ← desactiva puntos al volver placeholder
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 3;
	    panelCentral.add(CajaContraseña, gbc);

	    // Botón iniciar sesión
	    JButton btnLogin = new JButton("Iniciar sesión");
	    btnLogin.setFont(fuente);
	    btnLogin.setPreferredSize(new Dimension(200, 30));
	    gbc.gridx = 0; gbc.gridy = 4;
	    panelCentral.add(btnLogin, gbc);
	    
	    btnLogin.addMouseListener(new MouseAdapter() {
	    	
	    	@Override
	    	public void mouseClicked(MouseEvent e) {

	            String usuario = CajaUsuario.getText();
	            String contraseña = String.valueOf(CajaContraseña.getPassword());
	            controlador.iniciarSesion(usuario, contraseña);
	    	}
	    });
	    

	    // Botón registrarse
	    JButton btnRegistro = new JButton("Registrarse");
	    btnRegistro.setFont(fuente);
	    btnRegistro.setPreferredSize(new Dimension(200, 30));
	    gbc.gridx = 0; gbc.gridy = 5;
	    panelCentral.add(btnRegistro, gbc);

	    add(panelCentral, BorderLayout.CENTER);
	    
	    btnRegistro.addMouseListener(new MouseAdapter() {
	    	
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		controlador.abrirRegistro();
	    	}
	    });
	}

	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
