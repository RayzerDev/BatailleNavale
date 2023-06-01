package fr.univartois.butinfo.ihm;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BatailleNavaleController {
	private Random r = new Random();
	private GrilleBateau grilleJoueur = new GrilleBateau(10,10);
	private GrilleBateau grilleAdversaire = new GrilleBateau(10,10);
	private int jb1 = 1;
	private int jb2 = 2;
	private int jb3 = 3;
	private int jb4 = 4;
	private int ab1 = 0;
	private int ab2 = 0;
	private int ab3 = 0;
	private int ab4 = 0;
	private Bateau currentBoat = new Bateau(BateauType.PORTEAVION);
	public static enum Rotate {
		Horizontale,
		Verticale
	}
	private Rotate currentRotate = Rotate.Verticale;
    @FXML
    private Label aB1,aB2,aB3,aB4,statutText;
    @FXML
    private GridPane plateauAdversaire,plateauJoueur;
    @FXML
    private Button buttonB1,buttonB2,buttonB3,buttonB4;
    
    private Button[][] joueurButtons = new Button[10][10];
    private Button[][] adversaireButtons = new Button[10][10];
    @FXML
    void initialize() {
    	for(int i=0;i<10;i++) {
    		for(int j=0;j<10;j++) {
    			joueurButtons[i][j] = new Button();
    			adversaireButtons[i][j] = new Button();
    			adversaireButtons[i][j].setDisable(true);
    			
    			adversaireButtons[i][j].setMinSize(20, 20);
    			joueurButtons[i][j].setMinSize(20, 20);
    			
    			joueurButtons[i][j].setOnAction(this::putBoat);
    			adversaireButtons[i][j].setOnAction(this::shoot);
    			
    			plateauAdversaire.add(adversaireButtons[i][j], j, i);
    			plateauJoueur.add(joueurButtons[i][j], j, i);
    		
    			
    		}
    	}
    }
    void confButton(int i, int j) {
		grilleAdversaire.getPartie(i, j).estCoule().addListener(
			    (p, o, n) -> setBack(i,j,adversaireButtons));
    }
    void setBack(int i,int j, Button[][] liste) {
    	liste[i][j].setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
    }
    @FXML
    void shoot(ActionEvent event) {
		Button btn = (Button) event.getSource();
		btn.setDisable(true);
		int row = GridPane.getRowIndex(btn);
		int column = GridPane.getColumnIndex(btn);
		System.out.println(row + "," + column);
		grilleAdversaire.tirer(row, column);
		btn.setDisable(true);
		PartieBateau partie = grilleAdversaire.getPartie(row, column);
		if(partie.getBateau() != null) {
			btn.setText("X");
			if(partie.estCoule().get()) {
				BateauType tBateau = partie.getBateau().getType();
				switch(tBateau) {			
	    		case PORTEAVION:
	    			jb1++;
	    			aB1.setText(""+jb1);
	    			break;
	    			
	    		case CROISEUR:
	    			jb2++;
	    			aB2.setText(""+jb2);
	    			break;
	    		
				case SOUSMARINS:
	    			jb3++;
	    			aB3.setText(""+jb3);
					break;
	    			
				case TORPILLEUR:
	    			jb4++;
	    			aB4.setText(""+jb4);
					break;
	    		}
			}
		}
		else {
			tirAleatoire();	
		}
    }
    @FXML
    void putBoat(ActionEvent event) {
    	if (currentBoat!=null) {
    		Button btn = (Button) event.getSource();
    		int row = GridPane.getRowIndex(btn);
    		int column = GridPane.getColumnIndex(btn);
    		boolean rslt;
    		switch(currentRotate) {
    		case Horizontale:
    			rslt = grilleJoueur.placerBateauHorizontale(currentBoat, row, column);
        		if(!rslt)
        			return;
        		for (int i=column; i<column+currentBoat.getTaille();i++){
        			joueurButtons[row][i].setDisable(true);
        			joueurButtons[row][i].setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY,Insets.EMPTY)));
        		}

        		break;
    		case Verticale:
    			rslt = grilleJoueur.placerBateauVerticale(currentBoat, row, column);
        		if(!rslt)
        			return;
        		for (int i=row; i<row+currentBoat.getTaille();i++){
        			joueurButtons[i][column].setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY,Insets.EMPTY)));
        			joueurButtons[i][column].setDisable(true);
        		}
        		break;
    		};
    		switch(currentBoat.getType()) {
    		case PORTEAVION:
    			jb1--;
    			if(jb1==0)
    				buttonB1.setDisable(true);
    			break;
    			
    		case CROISEUR:
    			jb2--;
    			if(jb2==0)
    				buttonB2.setDisable(true);
    			break;
    		
			case SOUSMARINS:
    			jb3--;
				if(jb3==0)
					buttonB3.setDisable(true);
				break;
    			
			case TORPILLEUR:
    			jb4--;
				if(jb4==0)
					buttonB4.setDisable(true);
				break;
    		}
    		if(jb1>0) {
    			currentBoat = new Bateau(BateauType.PORTEAVION);
    		}
    		else if(jb2>0){
    			currentBoat = new Bateau(BateauType.CROISEUR);
    		}
    		else if(jb3>0){
    			currentBoat = new Bateau(BateauType.SOUSMARINS);
    		}
    		else if(jb4>0){
    			currentBoat = new Bateau(BateauType.TORPILLEUR);
    		}
    		else {
    			currentBoat = null;
    		}
    	}
    }
    public void tirAleatoire() {
    	boolean rslt = true;
    	int x = 0;
    	int y = 0;
		while(rslt) {
    		x = r.nextInt(grilleJoueur.getLongueur());
    		y = r.nextInt(grilleJoueur.getLargeur());
    		rslt = grilleAdversaire.getPartie(x, y).getTouche();
		}
		grilleJoueur.tirer(x, y);
		joueurButtons[x][y].setText("x");
		if(grilleJoueur.getPartie(x, y).getBateau()!=null) {
			tirAleatoire();
			
		}
    }
    public void placeAleatoire() {
    	while(ab1 <1) {
        	boolean rslt = false;
    		while(!rslt) {
        		int x = r.nextInt(grilleAdversaire.getLongueur());
        		int y = r.nextInt(grilleAdversaire.getLargeur());
        		rslt = grilleAdversaire.placerBateauHorizontaleOuVerticale(new Bateau(BateauType.PORTEAVION),x,y,r.nextBoolean());
    		}
    		ab1++;
    	}
    	while(ab2 < 2) {
        	boolean rslt = false;
    		while(!rslt) {
        		int x = r.nextInt(grilleAdversaire.getLongueur());
        		int y = r.nextInt(grilleAdversaire.getLargeur());
        		rslt = grilleAdversaire.placerBateauHorizontaleOuVerticale(new Bateau(BateauType.CROISEUR),x,y,r.nextBoolean());
    		}
    		ab2++;
    	}
		while (ab3<3) {
        	boolean rslt = false;
    		while(!rslt) {
        		int x = r.nextInt(grilleAdversaire.getLongueur());
        		int y = r.nextInt(grilleAdversaire.getLargeur());
        		rslt = grilleAdversaire.placerBateauHorizontaleOuVerticale(new Bateau(BateauType.SOUSMARINS),x,y,r.nextBoolean());
    		}
    		ab3++;
		}
    	while(ab4<4) {
        	boolean rslt = false;
    		while(!rslt) {
        		int x = r.nextInt(grilleAdversaire.getLongueur());
        		int y = r.nextInt(grilleAdversaire.getLargeur());
        		rslt = grilleAdversaire.placerBateauHorizontaleOuVerticale(new Bateau(BateauType.TORPILLEUR),x,y,r.nextBoolean());
    		}
    		ab4++;
    	}
    	for(int i=0;i<10;i++) {
    		for(int j=0;j<10;j++) {
    				adversaireButtons[i][j].setDisable(false);
    		}
    	}
    }
    @FXML
    void onChangeRotate(ActionEvent event) {
    	if(currentRotate == Rotate.Horizontale)
    		currentRotate = Rotate.Verticale;
    	else {
    		currentRotate = Rotate.Horizontale;
    	}
    	Button btn = (Button) event.getSource();
    	btn.setText(currentRotate+"");
    }
    @FXML
    void onRestart() {

    }
    @FXML
    void onStart(ActionEvent event) {
    	if((jb1+jb2+jb3+jb4)==0){
    		Button btn = (Button) event.getSource();
    		btn.setDisable(true);
    		placeAleatoire();
    		statutText.setText("A vous de tirer !");
        	for(int i=0;i<10;i++) {
        		for(int j=0;j<10;j++) {
	    		if(grilleAdversaire.getPartie(i, j).estCoule()!= null) {
	    			confButton(i,j);
        		}
        		}
        	}
    	}
    	else {
    		statutText.setText("Vous n'avez pas placé tous vos bateaux");
    	}
    }
    @FXML
    void CreateCroiseur(ActionEvent event) {
    	currentBoat = new Bateau(BateauType.CROISEUR);
    }

    @FXML
    void CreatePorteAvion(ActionEvent event) {
    	currentBoat = new Bateau(BateauType.PORTEAVION);
    }

    @FXML
    void CreateSsMarin(ActionEvent event) {
    	currentBoat = new Bateau(BateauType.SOUSMARINS);
    }

    @FXML
    void CreateTorpilleur(ActionEvent event) {
    	currentBoat = new Bateau(BateauType.TORPILLEUR);
    }
}
