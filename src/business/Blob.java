package business;

import java.util.ArrayList;
import java.util.Random;


public class Blob {
	private int pulsation;
	private double[] coordonnee;
	
	private boolean real;
	
	private ArrayList<Couleur> globules_couleurs;
	private ArrayList<double[]> globules_position;

	public Blob()
	{
		coordonnee = new double[2];
	}
	
	public Blob(double xcor, double ycor, Couleur couleur, int pulsation, ArrayList<double[]> forme , boolean reel)
	{
		coordonnee = new double[2];
		coordonnee[0] = xcor;
		coordonnee[1] = ycor;
		this.pulsation = pulsation;
		//this.forme = forme;
		real = reel;
		globules_position = forme; //forme.creerPosition(forme);
		setGlobules_couleurs(new ArrayList<Couleur>());
		for (int i = 0; i < globules_position.size(); i++){
			globules_couleurs.add(couleur);
		}
	}
	
	// on crée un blob à la position (xcor, ycor) de couleur et de forme aléatoire.
	public Blob(double xcor, double ycor, boolean reel)
	{
		coordonnee = new double[2];
		coordonnee[0] = xcor;
		coordonnee[1] = ycor;
		//this.pulsation = pulsation;
		real = reel;
		globules_position = generateFormRandom(); //forme.creerPosition(forme);
		Couleur[] couleurListe = Couleur.values();
		int indiceCouleur = (int) (Math.random() * ( couleurListe.length ));
		Couleur couleur = couleurListe[indiceCouleur];
		setGlobules_couleurs(new ArrayList<Couleur>());
		for (int i = 0; i < globules_position.size(); i++){
			globules_couleurs.add(couleur);
		}
	}
	
	
	public Blob copy_blob(){
		Blob res = new Blob(coordonnee[0], coordonnee[1], globules_couleurs.get(0), pulsation, globules_position, real);
		res.setGlobules_couleurs(new ArrayList<Couleur>( this.globules_couleurs));
		return(res);
	}
	
	
	/*
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
	}*/
	
	// genere des coordonnées cartésiennes aleatoires dans un cercle de diametre D et de centre D/2;D/2
		public double[] genererCoordonneeAleaDansCercle(double D){
			boolean isOk = false;
			double[] res = new double[2];
			double xcor = 0;
			double ycor = 0;
			
			while(!isOk)
			{
				xcor = Math.random() * ( D );
				ycor = Math.random() * ( D );
				
				if ((xcor - D/2)*(xcor - D/2) + (ycor - D/2)*(ycor - D/2) <= D * D / 4)
					isOk = true;
			}
			res[0] = xcor;
			res[1] = ycor;
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
	
	// permet de changer de forme en choisissant une forme aléatoire.
	/*
	public void changeForme() {
		
		 generateFormRandom();
		 Couleur couleur = globules_couleurs.get(0);
		 globules_couleurs.clear();
		 for (int i = 0; i < globules_position.size(); i++){ //TODO : il ne dois pas perdre toutes ses couleurs !
			globules_couleurs.add(couleur);
		}
	}*/
	
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
	
	
	/* ***************************************************************************** *
	 * *****************			FORMES					************************ *
	 * ***************************************************************************** */
	
	
	private boolean contenir(ArrayList<double[]> liste, double[] coo){
		for(int i = 0; i<liste.size(); i++)
			if(liste.get(i)[0] == coo[0] && liste.get(i)[1] == coo[1])
				return(true);
		return(false);
	}
	
	// renvoie dans l'ordre : le Xcor min; le Xcor max; le Ycor min; le Ycor max
	private double[] minMaxXYcor(ArrayList<double[]> liste){
		double[] res = new double[4];
		res[0] = liste.get(0)[0];
		res[1] = liste.get(0)[1];
		res[2] = liste.get(0)[0];
		res[3] = liste.get(0)[1];
		
		double tmp;
				
		for(int i = 0; i < liste.size(); i++)
		{
			
			if ((tmp = liste.get(i)[0]) < res[0])
				res[0] = tmp;
			else if (tmp > res[1])
				res[1] = tmp;
			if ((tmp = liste.get(i)[1]) < res[2])
				res[2] = tmp;
			else if (tmp > res[3])
				res[3] = tmp;
		}
		
		return res;
	}
	
	
	// je considère un blob dans un carré de 100*100. les positions sont donc en %
	public ArrayList<double[]> generateFormRandom(){
		ArrayList<double[]> res = new ArrayList<>();
		
		double[] tmp = new double[2];
		tmp[0] = 50;
		tmp[1] = 50;
		res.add(tmp.clone());
		
		Random rn = new Random();
		int nbGlobules = rn.nextInt(4) + 1;
		
		int pos;
		ArrayList<double[]> listePossible = new ArrayList<>();
		
		for (int i = 1; i < nbGlobules ; i++)
		{
			// je génère les positions possibles de prendre autour du dernier globule créé.
			listePossible.clear();
			
			tmp = res.get(i - 1).clone();
			// à droite
			tmp[0] += 25;
			if (!contenir(res, tmp))
				listePossible.add(tmp.clone());
			
			// à gauche
			tmp[0] -= 50;
			if (!contenir(res, tmp))
				listePossible.add(tmp.clone());
			
			// en haut
			tmp[0] += 25;
			tmp[1] -= 25;
			if (!contenir(res, tmp))
				listePossible.add(tmp.clone());
			
			// en bas
			tmp[1] += 50;
			if (!contenir(res, tmp))
				listePossible.add(tmp.clone());
			
			
			rn = new Random();
			if (i == 1)
				pos = rn.nextInt(4);
			else
				pos = rn.nextInt(3);
			
			res.add(listePossible.get(pos));
		}
		
		// j'ai à ce stade, créé un blob, mais non centré.
		// centrons-le : (translation des deux axes par (Max - Min)/2
		centrerBlob(res);
		
		return res;
	}

	private void centrerBlob(ArrayList<double[]> res) {
		double[] minMaxcor = minMaxXYcor(res);
		// nouveau centre = min + (max - min)/2
		double xNouveauCentre = minMaxcor[0] + (minMaxcor[1] - minMaxcor[0]) /2;
		double yNouveauCentre = minMaxcor[2] + (minMaxcor[3] - minMaxcor[2]) /2;
		
		// à chaque position, j'ajoute le vecteur (ancien centre (50,50) - nouveau Centre )
		for (int i = 0; i < res.size(); i++){
			res.get(i)[0] += 50 - xNouveauCentre;
			res.get(i)[1] += 50 - yNouveauCentre;
		}
		
	}
	
	
	// retourne l'indice du plus grand nombre dans le tableau en parametre
	private int argMax(Integer[] valeurs) {
		int valMax = valeurs[0];
		int indiceMax = 0;
		for (int i = 1; i < valeurs.length; i++)
			if(valMax < valeurs[i])
			{
				valMax = valeurs[i];
				indiceMax = i;
			}
		
		return indiceMax;		
	}
	
	
	
	public void changeForme(Integer[] positionVoisins) {
		
		ArrayList<double[]> res = new ArrayList<>();
		
		double[] tmp = new double[2];
		tmp[0] = 50;
		tmp[1] = 50;
		res.add(tmp.clone());
		
		Random rn = new Random();
		int nbGlobules = rn.nextInt(4) + 1;
		
		int pos;
		int tmp2;
		
		for (int i = 1; i < nbGlobules ; i++)
		{
			tmp = res.get(i - 1).clone();
			pos = argMax(positionVoisins);
			
			switch(pos) {
				case 0: // il me faut me diriger vers le nord. sinon au sud.
					tmp[1]-= 25;
					if (contenir(res, tmp))
						tmp[1]+=50;
					break;
				case 1 : // il me faut me dirigier vers l'est, sinon l'west
					tmp[0]+= 25;
					if (contenir(res, tmp))
						tmp[0]-=50;
					break; 
				case 2 : // il me faut me diriger vers le sud, sinon le nord
					tmp[1]+= 25;
					if (contenir(res, tmp))
						tmp[1]-=50;
					break; 
				case 3 : // il me faut me diriger vers l'ouest, sinon l'est
					tmp[0]-= 25;
					if (contenir(res, tmp))
						tmp[0]+=50;
					break;
				default :
					break;
			}
			res.add(tmp);
			
			tmp2 = positionVoisins[pos];
			positionVoisins[pos] = 0;
			positionVoisins[pos] -= tmp2 - positionVoisins[argMax(positionVoisins)];
			
		}
		
		
		
		// j'ai à ce stade, créé un blob, mais non centré.
		// centrons-le : (translation des deux axes par (Max - Min)/2
		centrerBlob(res);
		
		globules_position = res;
		Couleur couleur;
		// remettre les couleurs 
		if(globules_position.size() >= globules_couleurs.size()) {
			couleur = getCouleurLaPLusPresente();
			for(int i = globules_couleurs.size() ; i < globules_position.size() ; i++ )
				globules_couleurs.add(couleur);
		}
		else
		{
			for(int i = globules_couleurs.size(); i > globules_position.size(); i--)
				globules_couleurs.remove(i-1);
		}
	}
	
	
	
	
}
