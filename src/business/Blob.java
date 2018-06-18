package business;

import java.util.ArrayList;

public class Blob {
	//private String id;
	private int pulsation;
	private Forme forme;
	private double[] coordonnee;
	// liste des voisins Reels pour TR, utilis� pour l'apparence du blob, mais � voir le taux de rafraichissement
	
	private boolean real;
	
	private ArrayList<Couleur> globules_couleurs;
	private ArrayList<double[]> globules_position;

	public Blob()
	{
		coordonnee = new double[2];
	}
	
	public Blob(double xcor, double ycor, Couleur couleur, int pulsation, Forme forme, boolean reel)
	{
		coordonnee = new double[2];
		coordonnee[0] = xcor;
		coordonnee[1] = ycor;
		this.pulsation = pulsation;
		this.forme = forme;
		real = reel;
		globules_position = forme.creerPosition(forme);
		setGlobules_couleurs(new ArrayList<Couleur>());
		for (int i = 0; i < globules_position.size(); i++){
			globules_couleurs.add(couleur);
		}
	}
	
	
	public Blob copy_blob(){
		Blob res = new Blob(coordonnee[0], coordonnee[1], globules_couleurs.get(0), pulsation, forme, real);
		res.setGlobules_couleurs(new ArrayList<Couleur>( this.globules_couleurs));
		return(res);
	}
	
	
	/*
	// fonction qui � partir de coordonn�es initiales, propose de nouvelles coordonn�es � un certain rayon (le pas).
	public double[] nouvellesCoordonnees(){
		double[] res = new double[2];
		// coo[0] - pas < res[0] < coo[0] + pas
		res[0] = (Math.random() * 2 * pas) - pas + coordonnee[0];
		
		// j'utilise l'equation d'un cercle de rayon pas.
		// (res[0] - coo[0])� + (res[1] - coo[1])� = pas�
		// � partir de res[0], j'ai 2 solutions possible pour res[1]. 1 positive, une n�gative. choisissons al�atoirement.
		double sign = 1;
		if (Math.random() < 0.5)
			sign = -1;
		res[1] =  coordonnee[1] + (sign * Math.sqrt(pas * pas + (res[0] - coordonnee[0]) * (res[0] - coordonnee[0]) ));
		return res;
	}*/
	
	
	

	public ArrayList<double[]> getGlobules_position() {
		return globules_position;
	}


	public void setGlobules_position(ArrayList<double[]> globules_position) {
		this.globules_position = globules_position;
	}


	public int getPulsation() {
		return pulsation;
	}

	public void setPulsation(int pulsation) {
		this.pulsation = pulsation;
	}

	public Forme getForme() {
		return forme;
	}

	public void setForme(Forme forme) {
		this.forme = forme;
		globules_position = forme.creerPosition(forme);
		for (int i = 0; i < globules_position.size(); i++){
			Couleur couleur = globules_couleurs.get(0);
			globules_couleurs.clear();
			globules_couleurs.add(couleur);
		}
	}

	public double[] getCoordonnee() {
		return coordonnee;
	}

	public void setCoordonnee(double[] coordonnee) {
		this.coordonnee = coordonnee;
	}


	public boolean isReal() {
		return real;
	}

	public void setReal(boolean real) {
		this.real = real;
	}
	
	
	/* calcule la distance euclidienne entre 2 points cooA et cooB */
	private double calculeDistance(double[] cooA, double[] cooB){
		double sum = 0;
		for(int i = 0; i < cooA.length ; i++)
			sum += ((cooB[i] - cooA[i])*(cooB[i] - cooA[i]));
		return Math.sqrt(sum);		
		
	}
	
	public boolean isVoisin(Blob b, int radius)
	{
		if (calculeDistance(b.coordonnee, this.coordonnee) < radius)
			return true;
		return false;
	}

	public ArrayList<Couleur> getGlobules_couleurs() {
		return globules_couleurs;
	}

	public void setGlobules_couleurs(ArrayList<Couleur> globules_couleurs) {
		this.globules_couleurs = globules_couleurs;
	}
	
	// permet de changer de forme en choisissant une forme al�atoire.
	public void changeForme() {
		Forme[] formeListe = Forme.values();
		int indiceForme = (int) (Math.random() * (formeListe.length));
		this.forme = formeListe[indiceForme];
		globules_position = forme.creerPosition(forme);
		for (int i = 0; i < globules_position.size(); i++){
			Couleur couleur = globules_couleurs.get(0);
			globules_couleurs.clear();
			globules_couleurs.add(couleur);
		}
	}
	
	public Couleur getCouleurLaPLusPresente(){
		int indice = 0;
		int nbMax = 0;
		int cpt;
		for (int i = 0; i < globules_couleurs.size(); i++ )
		{
			cpt = 0;
			for ( int j = i + 1; j < globules_couleurs.size(); j++)
				if(globules_couleurs.get(i) == globules_couleurs.get(j))
					cpt++;
			if(cpt > nbMax)
			{
				indice = i;
				nbMax = cpt;
			}
		}
		return(globules_couleurs.get(indice));
	}
}
