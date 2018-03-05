package TP3;

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
	private String polynomeGenerateur = "";
	
	JTextField jtf = new JTextField();
	
	public Resultat() {
		super(new BorderLayout());
		
		jtf.setEditable(false);
		jtf.setPreferredSize(new Dimension(this.getWidth(), 30));
		jtf.setHorizontalAlignment(JTextField.CENTER);
		
		this.add(jtf, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * Fonction de détection d'érreur  la trame. Indique "OK" dans le JTextField si la tram est valide, 
	 * "ERREUR" accompagné du code d'erreur sinon
	 */
	private void correction() {
		String code = this.code;
			
		System.out.println("==========================");
		System.out.println("Etapes de vérifications : ");
		System.out.println(code);
		
		if(this.code.length() > this.polynomeGenerateur.length())
			while(degrePolyome(code) >= degrePolyome(this.polynomeGenerateur) && degrePolyome(code) > 0){
				System.out.println(this.polynomeGenerateur);
				for (int i = 0; i <= degrePolyome(this.code) + degrePolyome(this.polynomeGenerateur); i++) 
					System.out.print("-");
				System.out.println();
				
				code = soustractionBinaire(code, this.polynomeGenerateur);
				
				System.out.println(code);
			}
		
		if(code.isEmpty())
			jtf.setText("OK");
		else{
			while(code.length() < degrePolyome(this.polynomeGenerateur))
				code = '0' + code;
			jtf.setText("ERREUR : " + code);
		}
	}
	
	/**
	 * Fonction de calcul du CRC. Calcul le CRC correspondant à la trame saisie.
	 */
	private void codage() {
		String code = this.code;
		
		for (int i = 0; i < degrePolyome(this.polynomeGenerateur); i++)
			code += "0";
			
		System.out.println("===================");
		System.out.println("Etapes de calcul : ");
		System.out.println(code);
		
		if(this.code.length() > this.polynomeGenerateur.length())
			while(degrePolyome(code) >= degrePolyome(this.polynomeGenerateur)){
				System.out.println(this.polynomeGenerateur);
				for (int i = 0; i <= degrePolyome(this.code) + degrePolyome(this.polynomeGenerateur); i++) 
					System.out.print("-");
				System.out.println();
				
				code = soustractionBinaire(code, this.polynomeGenerateur);
				
				System.out.println(code);
			}
		
		while(code.length() < degrePolyome(this.polynomeGenerateur))
			code = '0' + code;
		
		jtf.setText(this.code + "" + code);
	}
	
	/**
	 * Effectue une division euclidienne dans laquelle on ne tient pas compte du quotient
	 * 
	 * @param terme Premier terme de la soustraction
	 * @param terme_soustrait Second terme de la soustraction
	 * @return 
	 */
	private String soustractionBinaire(String terme, String terme_soustrait) {
		char[] terme_array = terme.toCharArray();
		//Calcul
		for (int i = 0; i < terme_soustrait.length(); i++) {
			if(terme.charAt(i) == terme_soustrait.charAt(i))
				terme_array[i] = '0';
			else
				terme_array[i] = '1';			 
		}
		
		// Formatage du resultat
		String result = "";
		boolean un_rencontre = false;
		for (int i = 0; i < terme_array.length; i++) {
			if(terme_array[i] == '0'){
				if(un_rencontre)
					result += '0';
			}
			else {
				result += '1';
				un_rencontre = true;
			}
		}
		
		return result;
	}

	/**
	 * Détermine le degré d'un polynome
	 * 
	 * @param i La trame à tester
	 * @return Le degré du polynome passé en paramètre
	 */
	private int degrePolyome(String i) {
		int j;
		for (j = 0; j < i.length() && i.charAt(j) == '0'; j++) {
			
		}
		return i.length() - 1 - j;
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

	/**
	 * Setter de polynome_genereateur</br>
	 * Permet de selectionner le polynome à utiliser pour calculer ou vérifier la trame
	 * 
	 * @param encoding L'encodage à utiliser
	 */
	public void setPolynome(String polynome){
		this.polynomeGenerateur = polynome;
		if(this.action.equals("Calcul"))
			codage();
		else
			correction();
	}
	
}