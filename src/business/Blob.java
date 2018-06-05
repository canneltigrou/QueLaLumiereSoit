package business;

import java.util.ArrayList;

import com.sun.org.apache.xml.internal.resolver.readers.XCatalogReader;

public class Blob {
	//private String id;
	private int pulsation;
	private Forme forme;
	private double[] coordonnee;
	// liste des voisins Reels pour TR, utilisé pour l'apparence du blob, mais à voir le taux de rafraichissement
	private ArrayList<Blob> voisins;
	
	private int cpt_state;
	private int cpt_position;
	private boolean real;
	private double pas = 10;
	
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
		return(new Blob(coordonnee[0], coordonnee[1], globules_couleurs.get(0), pulsation, forme, real));
	}
	
	// fonction qui à partir de coordonnées initiales, propose de nouvelles coordonnées à un certain rayon (le pas).
	public double[] nouvellesCoordonnees(){
		double[] res = new double[2];
		// coo[0] - pas < res[0] < coo[0] + pas
		res[0] = (Math.random() * 2 * pas) - pas + coordonnee[0];
		
		// j'utilise l'equation d'un cercle de rayon pas.
		// (res[0] - coo[0])² + (res[1] - coo[1])² = pas²
		// à partir de res[0], j'ai 2 solutions possible pour res[1]. 1 positive, une négative. choisissons aléatoirement.
		double sign = 1;
		if (Math.random() < 0.5)
			sign = -1;
		res[1] =  coordonnee[1] + (sign * Math.sqrt(pas * pas + (res[0] - coordonnee[0]) * (res[0] - coordonnee[0]) ));
		return res;
	}
	
	
	

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
		// TODO à voir avec Maria : comment changer les couleurs lors d'un changement de forme ?
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

	
	public ArrayList<Blob> getVoisins() {
		return voisins;
	}
	public void setVoisins(ArrayList<Blob> voisins) {
		this.voisins = voisins;
	}
	
	public void addVoisin(Blob blobToAdd){
		this.voisins.add(blobToAdd);
	}
	
	public void clearVoisin(){
		this.voisins.clear();
	}

	public int getCpt_state() {
		return cpt_state;
	}

	public void setCpt_state(int cpt_state) {
		this.cpt_state = cpt_state;
	}

	public int getCpt_position() {
		return cpt_position;
	}

	public void setCpt_position(int cpt_position) {
		this.cpt_position = cpt_position;
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
	
	
	
	
	

}
