package fr.univartois.butinfo.ihm;

public enum BateauType {
	PORTEAVION(4),
	CROISEUR(3),
	SOUSMARINS(2),
	TORPILLEUR(1);
	
	private final int size;
	BateauType(int size){
		this.size = size;
	}
	public int getSize() {
		return size;
	}
};