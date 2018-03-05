package TP2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Corentin Ballot & Simon Camille
 * 9 mars 2017
 */
public class Resultat extends JPanel {

	private static final long serialVersionUID = 6290494706365057096L;
	
	private String code = "";
	private String action = "";
	
	JTextField jtf = new JTextField();
	
	public Resultat() {
		super(new BorderLayout());
		
		jtf.setEditable(false);
		jtf.setPreferredSize(new Dimension(this.getWidth(), 30));
		jtf.setHorizontalAlignment(JTextField.CENTER);
		
		this.add(jtf, BorderLayout.CENTER);
		this.setVisible(true);
		
		if(this.action.equals("Calcul"))
			codage();
		else
			correction();
	}
	
	/**
	 * Fonction de détection d'érreur dans la trame. Indique "OK" dans le JTextField si la tram est valide, "ERREUR" accompagné de la position de l'érreur (le premier bit est d'indice 1) sinon
	 */
	private void correction() {
		try{
			// Calcul de la longueur de la trame
			int n = 3;
			while(((Math.pow(2, n) -1)) < code.length()){
				n++;
			}
			
			char message[] = new StringBuilder(code).reverse().toString().toCharArray();
			char[] code_hamming = new char[n];
			
			int indice_code_hamming = 0;
			for (int i = 1; i < message.length + 1; i++) {
				if(estUnePuissanceDeDeux(i)){ // Bit de controle
					
					int somme_des_bits = 0;
					// On en prend i bits, on en saute i
					for(int k = message.length -1; k >= 0; k -= i) {
						for (int j = 0; j < i; j++) {
							somme_des_bits += Integer.parseInt("" + message[k-j]);
						}
						k-= i;
					}
					
					if(somme_des_bits % 2 == 0) // Si la somme des bits est paire, on indique 0
						code_hamming[indice_code_hamming] = '0';
					else // Sinon, on indique 1
						code_hamming[indice_code_hamming] = '1';
					
					indice_code_hamming ++;
				}
			}
			
			// Verification du resultat
			int erreur = -1;
			for (int i = 0; i < code_hamming.length; i++) {
				if(code_hamming[i] == '1')
					erreur = (int) Math.pow(2, i);
			}
			
			// Si le code contient une erreur, on affiche sa position
			if(erreur > -1)
				jtf.setText("ERREUR : position " + (erreur +1));
			// Affiche OK sinon
			else
				jtf.setText("OK");
		}catch (ArrayIndexOutOfBoundsException e) { // S'il y a une sortie de tableau, on intercepte l'erreur, et on l'indique à l'utilisateur
			jtf.setText("ERREUR : longeur de trame invalide");
		}
		
	}
	
	/**
	 * Fonction de calcul du code de Hamming. Calcul le code de Hamming correspondant à la trame saisie.
	 */
	private void codage() {
		// Calcul de la longueur de la trame
		int n = 3;
		while(((Math.pow(2, n) -1) -n) < this.code.length()){
			n++;
		}
		
		String code = new StringBuilder(this.code).reverse().toString();	// Inversion de la trame
		for (int i = code.length(); i < ((Math.pow(2, n) -1) -n); i++) {	
			code += "0";													// On complete les bits manquant en ajoutant des 0
		}
		
		char message[] = new char[(int) (Math.pow(2, n) -1)];				// Definition du tableau contenant le code de Hamming
		for (int i = 0; i < message.length; i++) {
			message[i] = '0';												// Initialisation du tableau avec des 0
		}
		
		// Insertion des bits de la trame saisie pas l'utilisateur dans le tableau du code de Hamming
		int iteration_code = 0;
		for (int i = 1; i < message.length + 1; i++) {
			if(!estUnePuissanceDeDeux(i)){ // Pas bit de controle
				message[i-1] = code.charAt(iteration_code);
				iteration_code ++;
			}
		}
		
		// Calcul des bits de parités
		for (int i = 1; i < message.length + 1; i++) {
			if(estUnePuissanceDeDeux(i)){ // Bit de controle

				int somme_des_bits = 0;
				for(int k = message.length -1; k >= 0; k -= i) { // On en prend i+1, on en saute i+1
					for (int j = 0; j < i; j++) {
						somme_des_bits += Integer.parseInt("" + message[k-j]); // Ajout à somme_des_bits la valeur de message
					}
					k-= i;
				}
				
				if(somme_des_bits % 2 == 0) // Si la somme des bits est paire, on indique 0
					message[i-1] = '0';
				else // On indique 1 sinon
					message[i-1] = '1';
			}
		}
		
		// Transformation du tableau de char en Chaine
		String f_code = "";
		for (int i = 0; i < message.length; i++) {
			f_code += message[i];
		}
		f_code = new StringBuilder(f_code).reverse().toString();
		
		jtf.setText(f_code); // Affichage du resultat dans le JTextField
		
	}
	
	
	/**
	 * Permet de savoir si un nombre est une puissance de deux ou non
	 * 
	 * @param i Nombre à tester
	 * @return true si i est une puissance de deux, false sinon
	 */
	private boolean estUnePuissanceDeDeux(int i) {
		int n = -1;
		while(i > Math.pow(2, n++)){
			if(i == Math.pow(2, n))
				return true;
		}
		return false;
	}

	/**
	 * Setter de code</br>
	 * Permet de mettre à jour la trame sur laquelle on travail
	 * 
	 * @param code La tram à dessiner
	 */
	public void setCode(String code){
		this.code = code;
		if(this.action.equals("Calcul"))
			codage();
		else
			correction();
	}

	/**
	 * Setter de action</br>
	 * Permet de selectionner l'action à effectuer sur la trame entrée
	 * (calcul tu code de Hamming ou correction d'erreur)
	 * 
	 * @param encoding L'encodage à utiliser
	 */
	public void setAction(String action){
		this.action = action;
		if(this.action.equals("Calcul"))
			codage();
		else
			correction();
	}
	
}