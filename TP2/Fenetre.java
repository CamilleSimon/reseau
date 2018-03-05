package TP2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener; 

/**
 * @author Corentin Ballot & Simon Camille
 * 8 mars 2017
 */
public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = -7064576159917637825L;
	
	String[] tab = {"Calcul", "Vérification"};
	private JComboBox<String> combo = new JComboBox<String>(tab);
	private JTextField jtf;
	private Resultat res = new Resultat();
	 
	/**
	 * Notre fenêtre
	 */
	public Fenetre(){
		// Paramètres de la fenêtre
		this.setTitle("TP2 - Le code Hamming"); 						// Nom de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// Tue le processus lorsque l'on quitte l'application
		this.setLocationRelativeTo(null);								// Ouvre la fenêtre au milieu de l'écran
		this.setLayout(new BorderLayout());								// Définit le layout de la fenêtre

		// Initialisation du panel nord de notre layout
		JPanel top = new JPanel(new BorderLayout());
		
		combo.addActionListener(new ItemChangeAction());				// Ajoute un écouteur à combobox
		jtf = new JTextField();											// Initialisation de notre champs de texte
		jtf.addKeyListener(new TextFieldBinary());						// Ajoute un écouteur (lors de l'insertion de caractère par l'utilisateur) à notre champs de texte
		jtf.setPreferredSize(new Dimension(150, 30));					// Définit les dimension par défaut de notre champs de texte
		jtf.getDocument().addDocumentListener(new TextFieldChanged());	// Ajoute un écouteur (lors du changement du texte) à notre champs de texte
		
		top.add(jtf, BorderLayout.CENTER);								// Ajoute notre champs de texte au centre de notre panel nord (au centre pour qu'il prenne toute la largeur disponible)
		top.add(combo, BorderLayout.EAST);								// Ajoute notre combobox à notre panel nord
		
		
		res.setAction((String) combo.getSelectedItem());
		this.getContentPane().add(top, BorderLayout.NORTH);				// Ajoute notre panel "top" au nord du panel de la fenêtre
		this.getContentPane().add(res, BorderLayout.CENTER);			// Ajoute notre panel "top" au nord du panel de la fenêtre

		this.pack();
		this.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
		this.setVisible(true);   
	}
	
	/**
	 * Empêche la saisie des caractère autre que '0', '1'
	 * 
	 * @author Corentin Ballot & Simon Camille
	 *
	 */
	class TextFieldBinary implements KeyListener {
		
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {}

		public void keyTyped(KeyEvent evt) {
			char c=evt.getKeyChar();
		    if (!((c == '0') || (c == '1') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
		        evt.consume();
		    }
		}		
	}
	
	/**
	 * Met à jour la tram à représenter graphiquement du panneau 
	 * lors d'une modification de notre champs de texte
	 * 
	 * @author Corentin Ballot & Simon Camille
	 *
	 */
	class TextFieldChanged implements DocumentListener{
		public void removeUpdate(DocumentEvent arg0) {
			res.setCode(jtf.getText());
		}
		public void insertUpdate(DocumentEvent arg0) {
			res.setCode(jtf.getText());
		}
		public void changedUpdate(DocumentEvent arg0) {
			res.setCode(jtf.getText());
		}
	}
	
	
	/**
	 * Met à jour le codage à utiliser pour représenter graphiquement la
	 * tram du panneau lors d'une modification de notre combobox
	 * 
	 * @author Ballot Corentin & Simon Camille
	 *
	 */
	class ItemChangeAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			res.setAction((String) combo.getSelectedItem());
		}               
	}
	
	public static void main(String[] args) {
		new Fenetre(); // Lance le programme
	}
}