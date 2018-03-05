package TP1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Corentin Ballot & Simon Camille
 * 8 mars 2017
 */
public class Legende  extends JPanel {
	
	private static final long serialVersionUID = -6789407028536124078L;

	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		draw(g);
	}
	
	private void draw(Graphics g){ 
		g.setColor(Color.black); // D�finit la couleur de dessin � noir
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); 	// D�finit le type de police � "MONOSPCED" (les caract�res ont la m�me longeur)
		g.drawString(" nV", 0, 1 * this.getHeight()/4 + 3);		// Dessine "nV" � un quart de la hauteur du panel
		g.drawString(" 0V", 0, 2 * this.getHeight()/4 + 3);		// Dessine "0" � un demi de la hauteur du panel
		g.drawString("-nV", 0, 3 * this.getHeight()/4 + 3);		// Dessine "-nV" � trois quart de la hauteur du panel
	}
}

