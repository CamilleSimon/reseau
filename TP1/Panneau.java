package TP1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
 
import javax.swing.JPanel;

/**
 * @author Corentin Ballot & Simon Camille
 * 8 mars 2017
 */
public class Panneau extends JPanel {

	private static final long serialVersionUID = 6084816540577024827L;
	
	private String code = "";
	private String encodage = "";

	public void paintComponent(Graphics g){
		g.setColor(Color.white);								// Definit la couleur de fond de notre graphique
		g.fillRect(0, 0, this.getWidth(), this.getHeight());	// Dessine le fond de notre panel avec la couleur d�finit
		draw(g);      
	}
 
	private void draw(Graphics g){
		
		// Dessin des tic d'horloge
		g.setColor(Color.red); // Definit la couleur de dessin � rouge
		for (int i = 1; i < code.length(); i++) { // Pour chaque tic
			// Dessine une ligne de s�paration :
			g.drawLine(i * this.getWidth() / code.length(), 0, i * this.getWidth() / code.length(), this.getHeight());
		}
		
		// Dessin des rep�res de tension
		g.setColor(Color.gray); // Definit la couleur de dessin � gris
		float dash[] = {3.0f}; 	// Table des param�tres pour dessiner des figures en pointill�s
	    BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f); // Initialise un type de trac� pointill�
	    ((Graphics2D) g).setStroke(dashed); // D�finit le type du trac� � pointill�
	    
	    g.drawLine(0, 1 * this.getHeight()/4, this.getWidth(), 1 * this.getHeight()/4); // Dessine une ligne horizontal � un quart de la hauteur du panel
	    g.drawLine(0, 2 * this.getHeight()/4, this.getWidth(), 2 * this.getHeight()/4); // Dessine une ligne horizontal � un demi de la hauteur du panel
	    g.drawLine(0, 3 * this.getHeight()/4, this.getWidth(), 3 * this.getHeight()/4); // Dessine une ligne horizontal � trois quarts de la hauteur du panel
	    
	    // Dessin des rep�res en milieu de tic d'horloge
	    for (int i = 0; i < code.length(); i++) { // Pour chaque tic
	    	// Dessine une ligne vertical (entre les lignes des rep�res de tension) en milieu de tic d'horloge :
			g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 1 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
		}
	    
	    // Affichage du bit correspondant pour chaque tic d'horloge
	    g.setColor(Color.black); // D�finit la couleur de dessin � noir
	    for (int i = 0; i < code.length(); i++) { // Pour chaque tic
	    	// Dessine le bit correspondant au tic d'horloge
			g.drawString(code.charAt(i) + "", (int)((i + 0.5) * this.getWidth() / code.length()) - 2, 4 * this.getHeight()/4 - 5);
		}
		
	    // Dessin du signal
		((Graphics2D) g).setStroke(new BasicStroke(3)); // D�finit le type du trac� � continu
		
		int prev = -1; // Tension pr�c�dement rencontr�
		
		switch(encodage){
			case "NRZ": // Si l'encodage est NZR
				for(int i = 0; i < code.length(); i++){ // Pour chaque tic
					if(i > 0 && code.charAt(i-1) != code.charAt(i)) // Si ce n'est pas le premier tic, et que le bit pr�c�dent est diff�rent du bit courrant
						g.drawLine((int)((i) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i) * this.getWidth() / code.length()), this.getHeight()/4); // Dessine une ligne vertical (passage de -nV � nV, ou inversement)
					
					if(code.charAt(i) == '0') // Si le bit courrant est un 0
						g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), 3 * this.getHeight()/4); // Dessine une ligne � trois quarts de la hauteur du panel (-nV)
						
					else
						g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), this.getHeight()/4); // Dessine une ligne � un quarts de la hauteur du panel (nV)
				}
				break;
			case "Manhester": // Si l'encodage est Manhester
				for(int i = 0; i < code.length(); i++){ // Pour chaque tic
					if(i > 0 && code.charAt(i) == code.charAt(i-1)) // Si ce n'est pas le premier tic, et que le bit pr�c�dent est le m�me que le bit courrant
						g.drawLine((int)((i) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i) * this.getWidth() / code.length()), this.getHeight()/4); // Dessine une ligne vertical (passage de -nV � nV, ou inversement)
					
					if(code.charAt(i) == '0'){ // Si le bit courrant est un 0
						g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4); 			// Dessine un trait de longueur un demi tic d'horloge � trois quarts de la hauteur du panel (-nV)
						g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4); // Dessine un trait vertical (passage de -nV � nV)
						g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), this.getHeight()/4); 		// Dessine un trait de longueur un demi tic d'horloge � un quarts de la hauteur du panel (nV)
					}
					else{
						g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);					// Dessine un trait de longueur un demi tic d'horloge � un quarts de la hauteur du panel (nV)
						g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4); // Dessine un trait vertical (passage de nV � -nV)
						g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), 3 * this.getHeight()/4); // Dessine un trait de longueur un demi tic d'horloge � trois quarts de la hauteur du panel (-nV)
					}
				}
				break;
			case "Manhester diff�rentiel": // Si l'encodage est Manhester diff�rentiel
				for(int i = 0; i < code.length(); i++){ // Pour chaque tic
					if(code.charAt(i) == '0'){ // Si le bit courrant est un 0
						if(i > 0) // Si ce n'est pas le premier tic
							g.drawLine((int)((i) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i) * this.getWidth() / code.length()), this.getHeight()/4); // Dessine une ligne vertical (passage de -nV � nV, ou inversement)
						if(prev == -1){ // Si la tension pr�c�dente est n�gative
							g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
						}
						else {
							g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), this.getHeight()/4);
						}
					}
					else {
						if(prev == -1){ // Si la tension pr�c�dente est n�gative
							g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), this.getHeight()/4);
							prev = 1; // Inversion de la tension
						}
						else {
							g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
							prev = -1; // Inversion de la tension
						}
					}
				}
				break;
			case "Miller" : // Si l'encodage est Miller
				for(int i = 0; i < code.length(); i++){ // Pour chaque tic d'horloge
					if(code.charAt(i) == '0'){ // Si le bit courrant est un 0
						if(i == 0 || code.charAt(i-1) == '1'){ // Si c'est le premier bit, ou que le bit pr�c�dent est un 1
							if(prev == -1)  // Si la tension pr�c�dente est n�gative
								g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), 3 * this.getHeight()/4);
							else 
								g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), this.getHeight()/4);
						}
						else {
							g.drawLine((int)((i) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i) * this.getWidth() / code.length()), this.getHeight()/4);
							if(prev == 1){ // Si la tension pr�c�dente est positive
								g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), 3 * this.getHeight()/4);
								prev = -1; // Inversion de la tension
							}
							else {
								g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (i + 1) * this.getWidth() / code.length(), this.getHeight()/4);
								prev = 1; // Inversion de la tension
							}
						}
					}
					else {
						if(prev == -1){ // Si la tension pr�c�dente est negative
							g.drawLine(i * this.getWidth() / code.length(), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), this.getHeight()/4);
							prev = 1; // Inversion de la tension
						}
						else {
							g.drawLine(i * this.getWidth() / code.length(), this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 0.5) * this.getWidth() / code.length()), this.getHeight()/4);
							g.drawLine((int)((i + 0.5) * this.getWidth() / code.length()), 3 * this.getHeight()/4, (int)((i + 1) * this.getWidth() / code.length()), 3 * this.getHeight()/4);
							prev = -1; // Inversion de la tension
						}
					}
				}
		}
	}
	
	/**
	 * Setter de code</br>
	 * Permet de mettre � jour la tram � dessiner
	 * 
	 * @param code La tram � dessiner
	 */
	public void setCode(String code){
		this.code = code;
		this.repaint();	// Redessine notre graphique
	}
  
	/**
	 * Setter de encodage</br>
	 * Permet de mettre � jour le codage � utiliser pour dessiner la tram
	 * 
	 * @param encoding L'encodage � utiliser
	 */
	public void setEncoding(String encoding){
		this.encodage = encoding;
		this.repaint();	// Redessine notre graphique
	}
}