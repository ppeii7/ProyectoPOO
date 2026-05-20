package view.tresenraya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.ControlApp;
import control.ControlTresEnRaya;

public class VentanaRegistroTresEnRaya extends JFrame{

 

	ControlApp controlApp = new ControlApp();


		public VentanaRegistroTresEnRaya(ControlApp controlApp) {
			this.controlApp = controlApp;
			setTitle("Registro");
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
		      
		    JLabel label1 = new JLabel("Jugador 1");
		    label1.setFont(fuente);
		    label1.setSize(200,30);
		    gbc.gridx = 0; gbc.gridy = 0;
		    panelCentral.add(label1, gbc);	    
		    
		    
		    JLabel label2 = new JLabel("Jugador 2");
		    label2.setFont(fuente);
		    label2.setSize(200,30);
		    gbc.gridx = 1; gbc.gridy = 0;
		    panelCentral.add(label2, gbc);
		    
		    
		    JTextField cajaUsuarioJ1 = new JTextField("Nombre de usuario");
		    cajaUsuarioJ1.setFont(fuente);
		    cajaUsuarioJ1.setForeground(Color.GRAY);
		    cajaUsuarioJ1.setPreferredSize(new Dimension(200, 30));
		    cajaUsuarioJ1.setText(controlApp.getNombreUsuarioActual());
		    gbc.gridx = 0; gbc.gridy = 1;
		    panelCentral.add(cajaUsuarioJ1, gbc);
		    
		    JPasswordField cajaContraseñaJ1 = new JPasswordField("Nombre de usuario");
		    cajaContraseñaJ1.setFont(fuente);
		    cajaContraseñaJ1.setForeground(Color.GRAY);
		    cajaContraseñaJ1.setPreferredSize(new Dimension(200, 30));
		    cajaContraseñaJ1.setText(controlApp.getContraseñaActual());
		    gbc.gridx = 0; gbc.gridy = 2;
		    panelCentral.add(cajaContraseñaJ1, gbc);
		    
		    // Caja de texto usuario
		    JTextField cajaUsuarioJ2 = new JTextField("Nombre de usuario");
		    cajaUsuarioJ2.setFont(fuente);
		    cajaUsuarioJ2.setForeground(Color.GRAY);
		    cajaUsuarioJ2.setPreferredSize(new Dimension(200, 30));
		    cajaUsuarioJ2.addFocusListener(new java.awt.event.FocusAdapter() {
		        public void focusGained(java.awt.event.FocusEvent e) {
		            if (cajaUsuarioJ2.getText().equals("Nombre de usuario")) {
		            	cajaUsuarioJ2.setText("");
		            	cajaUsuarioJ2.setForeground(Color.BLACK);
		            }
		        }
		        public void focusLost(java.awt.event.FocusEvent e) {
		            if (cajaUsuarioJ2.getText().isEmpty()) {
		            	cajaUsuarioJ2.setText("Nombre de usuario");
		            	cajaUsuarioJ2.setForeground(Color.GRAY);
		            }
		        }
		    });
		    gbc.gridx = 1; gbc.gridy = 1;
		    panelCentral.add(cajaUsuarioJ2, gbc);

		 // Caja de contraseña
		    JPasswordField cajaContraseñaJ2 = new JPasswordField("Contraseña");
		    cajaContraseñaJ2.setFont(fuente);
		    cajaContraseñaJ2.setForeground(Color.GRAY);
		    cajaContraseñaJ2.setEchoChar((char) 0); // ← añade esto aquí
		    cajaContraseñaJ2.setPreferredSize(new Dimension(200, 30));
		    cajaContraseñaJ2.addFocusListener(new java.awt.event.FocusAdapter() {
		        public void focusGained(java.awt.event.FocusEvent e) {
		            if (String.valueOf(cajaContraseñaJ2.getPassword()).equals("Contraseña")) {
		            	cajaContraseñaJ2.setText("");
		            	cajaContraseñaJ2.setForeground(Color.BLACK);
		            	cajaContraseñaJ2.setEchoChar('•'); // ← activa puntos al escribir
		            }
		        }
		        public void focusLost(java.awt.event.FocusEvent e) {
		            if (String.valueOf(cajaContraseñaJ2.getPassword()).isEmpty()) {
		            	cajaContraseñaJ2.setText("Contraseña");
		            	cajaContraseñaJ2.setForeground(Color.GRAY);
		            	cajaContraseñaJ2.setEchoChar((char) 0); // ← desactiva puntos al volver placeholder
		            }
		        }
		    });
		    gbc.gridx = 1; gbc.gridy = 2;
		    panelCentral.add(cajaContraseñaJ2, gbc);


		    JButton btnAñadirJugador = new JButton("Añadir jugadores");
		    btnAñadirJugador.setFont(fuente);
		    btnAñadirJugador.setPreferredSize(new Dimension(200, 30));
		    gbc.gridx = 1; gbc.gridy = 3;
		    panelCentral.add(btnAñadirJugador, gbc);

		    add(panelCentral, BorderLayout.CENTER);
		    
		    btnAñadirJugador.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            ControlTresEnRaya controlTresEnRaya = new ControlTresEnRaya();

		            boolean ok = controlTresEnRaya.añadirJugadores(
		                cajaUsuarioJ1.getText(),
		                new String(cajaContraseñaJ1.getPassword()),
		                cajaUsuarioJ2.getText(),
		                new String(cajaContraseñaJ2.getPassword())
		            );

		            if (ok) {
		                controlTresEnRaya.crearPartida();
		                dispose();
		            }
		        }
		    });
	        
	        
	}
	
}