package fr.univartois.butinfo.ihm;

public class GrilleBateau {
	private int longueur, largeur;
	private PartieBateau[][] grille;
	
	public GrilleBateau(int longueur, int largeur) {
		this.longueur = longueur;
		this.largeur = largeur;
		grille = new PartieBateau[largeur][longueur];
		for(int i=0;i<grille.length;i++) {
			for(int j=0; j<grille[0].length;j++) {
				grille[i][j]= new PartieBateau();
			}
		}
	}
	
	public PartieBateau getPartie(int x, int y){
		return grille[x][y];
	}
	
	public int getLongueur() {
		return longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public boolean placerBateauVerticale(Bateau bateau, int x, int y) {
		if(x+bateau.getTaille()>longueur) {
			System.out.println("Pas assez de place");
			return false;
		}
		for (int i=x; i<x+bateau.getTaille();i++) {
			if(getPartie(i,y).getBateau()!=null){
				System.out.println("Déjà un bateau");
				return false;
			}
		}
		for (int i=x; i<x+bateau.getTaille();i++) {
			grille[i][y].setBateau(bateau,i-x);
		}
		return true;
	}
	
	public boolean placerBateauHorizontale(Bateau bateau, int x, int y) {
		if(y+bateau.getTaille()>largeur) {
			System.out.println("Pas assez de place");
			return false;
		}
		for (int i=y; i<y+bateau.getTaille();i++) {
			if(getPartie(x,i).getBateau()!=null) {
				System.out.println("Déjà un bateau");
				return false;
			}
				
		}
		for (int i=y; i<y+bateau.getTaille();i++) {
			grille[x][i].setBateau(bateau,i-y);
		}
		return true;
	}
	public boolean placerBateauHorizontaleOuVerticale(Bateau bateau, int x, int y, boolean verticale) {
		if (verticale) {
			return placerBateauVerticale(bateau,x,y);
		}
		return placerBateauHorizontale(bateau,x,y);
	}
	public void tirer(int x, int y) {
		grille[x][y].setTouche();
	}
	public boolean toutCoule() {
		for(int i=0;i<grille.length;i++) {
			for(int j=0; j<grille[0].length;i++) {
				if(grille[i][j].getBateau()!=null) {
					if(!grille[i][j].getBateau().estCoule().getValue()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	public void cleanGrille() {
		for(int i=0;i<grille.length;i++) {
			for(int j=0; j<grille[0].length;i++) {
				grille[i][j]= new PartieBateau();
			}
		}
	}
}
