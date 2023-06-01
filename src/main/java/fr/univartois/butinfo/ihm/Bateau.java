package fr.univartois.butinfo.ihm;
import javafx.beans.property.*;

public class Bateau {
	private int taille;
	private boolean[] parties; 	
	private int nb_impact;
	private BateauType type;
	private BooleanProperty coule;
	
	public Bateau(BateauType type) {
		coule = new SimpleBooleanProperty();
		coule.set(false);
		this.type = type;
		taille = type.getSize();
		parties = new boolean[taille];
		nb_impact = 0;
		
	}
	public BateauType getType() {
		return type;
	}
	public int getTaille() {
		return taille;
	}
	public void setImpact(int indice) {
		parties[indice] = true;
		nb_impact++;
		coule.set(nb_impact == taille);
	}
	public BooleanProperty estCoule() {
		return coule;
	}
}
