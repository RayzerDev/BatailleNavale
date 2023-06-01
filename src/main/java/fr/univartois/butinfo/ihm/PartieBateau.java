package fr.univartois.butinfo.ihm;

import javafx.beans.property.*;

public class PartieBateau {
	private Bateau bateau;
	private int position;
	private boolean touche;

	public PartieBateau(Bateau bateau, int position) {
		this.bateau = bateau;
		if (position >= 0 && position < bateau.getTaille())
			this.position = position;
		touche = false;
	}
	public PartieBateau() {
		this(null,-1);
	}
	public int getPosition() {
		return position;
	}
	public void setTouche() {
		touche = true;
		if (bateau != null) {
			bateau.setImpact(position);
		}
	}
	public boolean getTouche() {
		return touche;
	}
	public void setBateau(Bateau b, int p) {
		bateau = b;
		position = p;
	}
	public Bateau getBateau() {
		return bateau;
	}
	public BooleanProperty estCoule() {
		if (bateau == null) {
			return null;
		}
		return bateau.estCoule();
	}
}