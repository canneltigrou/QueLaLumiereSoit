package business;

import java.util.ArrayList;
import java.util.Random;

/**
 * Attribut essentiel des agents. 
 * 
 * <p>Chacun de ses attributs n'est modifié QUE par le package amak </p>
 * (si par exemple l'IHM tente de modifier un blob, il demande à amak de la faire)
 * <p> ils peuvent être lus par d'aures packages :
 *  - pkg application pour l'affichage
 *  - pkg position pour envoyer les infos au téléphone </p>
 * 
 * @author Claire MEVOLHON
 * 
 * @see amak.BlobAgent la classe BlobAgent
 *
 */
public class Blob {
	/** il s'agira toujours d'un tableau à 2 doubles : 
	 * [abscisse ; ordonnée]
	 */
	private double[] coordonnee;

	private boolean real;

	/** donne une liste de @see Couleur 
	 * de la taille du nombre de globules que possède le Blob
	 * et dans le même ordre que les globules de globules_position
	 */
	private ArrayList<Couleur> globules_couleurs;
	
	/** donne la position de chacun des globules [abscisse ; ordonnée]
	 * en suposant le blob dans un carré de 100*100
	 * avec la coordonnée 0;0 dans le coin en haut à gauche
	 * et 4 globules max, d'où un diametre de 25 par globule
	 */
	private ArrayList<double[]> globules_position;

	/** verrou utilisé par les autres classes 
	 * lors d'un changement ou d'une lecture d'un attribut de ce blob 
	 */
	public final Object lock = new Object();
	
	/** verrou utilisé spécifiquement pour ce qui concerne l'ArrayList globules_couleurs
	 */
	public final Object colorLock = new Object();


	/** 
	 * Constructeur
	 * @param xcor
	 * 				abscisse de sa coordonnée
	 * @param ycor
	 * 				ordonnée de sa coordonnée
	 * @param couleur
	 * 				sa couleur de la classe @see Couleur
	 * 				une seule couleur est donnée et servira à tous les globules
	 * @param forme
	 * 				liste des positions des globules
	 * @param reel
	 * 				est-ce un blob réel ou immaginaire ?
	 */
	public Blob(double xcor, double ycor, Couleur couleur, ArrayList<double[]> forme, boolean reel) {
		coordonnee = new double[2];
		coordonnee[0] = xcor;
		coordonnee[1] = ycor;
		real = reel;
		globules_position = forme; 
		if (couleur == null) {
			System.err.println("couleur null");
			System.exit(-1);
		}
		synchronized (colorLock) {
			setGlobules_couleurs(new ArrayList<Couleur>());
			for (int i = 0; i < globules_position.size(); i++) {
				globules_couleurs.add(couleur);
			}
		}
		
	}

	
	
	/**
	 * on cree un blob a la position (xcor, ycor) 
	 * de couleur et de forme aleatoire.
	 * @param xcor
	 * 			abscisse de la position
	 * @param ycor
	 * 			ordonnée de la position
	 * @param reel
	 * 			true si le blob est réel
	 */
	public Blob(double xcor, double ycor, boolean reel) {
		coordonnee = new double[2];
		coordonnee[0] = xcor;
		coordonnee[1] = ycor;
		real = reel;
		globules_position = generateFormRandom(); 
		
		synchronized (colorLock) {
			Couleur[] couleurListe = Couleur.values();
			int indiceCouleur = (int) (Math.random() * (couleurListe.length));
			Couleur couleur = couleurListe[indiceCouleur];
			setGlobules_couleurs(new ArrayList<Couleur>());
			for (int i = 0; i < globules_position.size(); i++) {
				globules_couleurs.add(couleur);
			}
		}
		
	}

	
	/**
	 * crée une copie de ce Blob
	 * @return la copie du Blob
	 */
	public Blob copy_blob() {
		Blob res = new Blob(coordonnee[0], coordonnee[1], globules_couleurs.get(0), globules_position, real);
		res.setGlobules_couleurs(new ArrayList<Couleur>(this.globules_couleurs));
		return (res);
	}

	

	/**
	 * genere des coordonnees cartesiennes aleatoires
	 * dans un cercle de diametre D et de centre D/2;D/2
	 * @param D
	 * 			diametre dans lequel doit se situer les nouvelles coordonnées
	 * @return
	 * 			la nouvelle coordonnée
	 */
	public double[] genererCoordonneeAleaDansCercle(double D) {
		boolean isOk = false;
		double[] res = new double[2];
		double xcor = 0;
		double ycor = 0;

		while (!isOk) {
			xcor = Math.random() * (D);
			ycor = Math.random() * (D);

			if ((xcor - D / 2) * (xcor - D / 2) + (ycor - D / 2) * (ycor - D / 2) <= D * D / 4)
				isOk = true;
		}
		res[0] = xcor;
		res[1] = ycor;
		return res;
	}


	/**
	 * getter globules_position
	 * @return globules_position
	 */
	public ArrayList<double[]> getGlobules_position() {
		return globules_position;
	}
	
	/**
	 * setter globules_position
	 * @param globules_position 
	 * 			positions des globules
	 */
	public void setGlobules_position(ArrayList<double[]> globules_position) {
		synchronized (colorLock) {
			this.globules_position = globules_position;
		}
	}

	/**
	 * getter coordonnee
	 * @return coordonnee
	 */
	public double[] getCoordonnee() {
		return coordonnee;
	}

	/**
	 * setter coordonnee
	 * @param coordonnee
	 * 			coordonées du blob [abscisse;ordonnée]
	 */
	public void setCoordonnee(double[] coordonnee) {
		this.coordonnee = coordonnee;
	}

	/**
	 * getter real
	 * @return real
	 */
	public boolean isReal() {
		return real;
	}

	/**
	 * setter real
	 * @param real
	 * 		true si le blob est réel. (dans To ou lié à une personne réelle)
	 */
	public void setReal(boolean real) {
		this.real = real;
	}
	
	/**
	 * calcule la distance euclidienne entre 2 points A et B
	 * @param cooA 
	 * 			coordonnee du point A
	 * @param cooB
	 * 			coordonnée du point B
	 * @return la distance euclidienne
	 */
	private double calculeDistance(double[] cooA, double[] cooB) {
		double sum = 0;
		for (int i = 0; i < cooA.length; i++)
			sum += ((cooB[i] - cooA[i]) * (cooB[i] - cooA[i]));
		return Math.sqrt(sum);

	}

	/**
	 * retourne vrai si le blob donné en paramètre est voisin de ce blob.
	 * <p>pour cela on utilise la distance euclidienne
	 * et un rayon donné en paramètre concernant la prise en compte du voisinage </p>
	 * <p>ie le blob b est notre voisin si la distance qui nous sépare est plus petite que le radius </p>
	 * 
	 * @param b
	 * 			le blob dont on cherche à savoir s'il est voisin ou non
	 * @param radius
	 * 			le radius concernant le voisinage pris en compte
	 * @return vrai si le blob est notre voisin
	 */
	public boolean isVoisin(Blob b, int radius) {
		if (calculeDistance(b.coordonnee, this.coordonnee) < radius)
			return true;
		return false;
	}

	/**
	 * getter de Globules_couleurs.
	 * Cette méthode utilise le verrou colorLock
	 * @return une COPIE de cette liste
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Couleur> getGlobules_couleurs() {
		ArrayList<Couleur> res;
		synchronized (colorLock) {
			res = (ArrayList<Couleur>) globules_couleurs.clone();
		}
		return res;
	}

	/**
	 * setter globules_couleurs.
	 * Cette méthode utilise le verrou colorLock
	 * @param globules_couleurs
	 * 		liste des couleurs des globules dans l'ordre des positions
	 */
	public void setGlobules_couleurs(ArrayList<Couleur> globules_couleurs) {
		synchronized (colorLock) {
			this.globules_couleurs = globules_couleurs;
		}
		
	}
	
	
	/**
	 * parcours la liste des couleurs globules_couleurs
	 * et retourne la couleur qui est la plus présente dans ce blob
	 * @return la couleur la plus présente dans ce blob
	 * si une couleur est trouvée nulle, elle est mise par defaut à bleue
	 */
	public Couleur getCouleurLaPLusPresente() {
		int indice = 0;
		int nbMax = 0;
		int cpt;
		for (int i = 0; i < globules_couleurs.size(); i++) {
			cpt = 0;
			for (int j = i + 1; j < globules_couleurs.size(); j++)
				if (globules_couleurs.get(i) == globules_couleurs.get(j))
					cpt++;
			if (cpt > nbMax) {
				indice = i;
				nbMax = cpt;
			}
		}

		if (globules_couleurs.get(indice) == null) {
			System.out.println("couleur null ");
			return Couleur.BLUE;
		}
		return (globules_couleurs.get(indice));
	}

	/*
	 * ***************************************************
	 * * ***************** FORMES ************************ *
	 * ***************************************************
	 */

	/**
	 * méthode utilisée pour générer une forme 
	 * renvoie vrai si la coordonnée coo est présente dans l'ArrayList liste
	 * @param liste
	 * 				liste de coordonées
	 * @param coo
	 * 				une coordonnée
	 * @return vrai si coo appartient à liste
	 * @see #generateFormRandom()
	 */
	private boolean contenir(ArrayList<double[]> liste, double[] coo) {
		for (int i = 0; i < liste.size(); i++)
			if (liste.get(i)[0] == coo[0] && liste.get(i)[1] == coo[1])
				return (true);
		return (false);
	}

	/**
	 * renvoie dans l'ordre : 
	 * la plus petite abscisse; la plus grande abscisse; 
	 * la plus petite ordonée; la plus grande ordonnee
	 * Cette méthode est utilisée pour centrer le blob
	 * @param liste
	 * 				liste des coordonnees des globules avant leur centrage
	 * @return [XcorMin ; XcorMax; YcorMin; YcorMax]
	 */
	private double[] minMaxXYcor(ArrayList<double[]> liste) {
		double[] res = new double[4];
		res[0] = liste.get(0)[0];
		res[1] = liste.get(0)[1];
		res[2] = liste.get(0)[0];
		res[3] = liste.get(0)[1];

		double tmp;

		for (int i = 0; i < liste.size(); i++) {

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

	
	/**
	 * génère une forme totalement aléatoire (utilisé à la création d'un blob)
	 * <p>rappel : un blob est dans un carré de 100*100. les positions sont donc en % </p>
	 * <p> procédé : <ul>
	 * <li>nombre de globules donné aléatoirement entre 1 et 4 </li>
	 * <li> 1er globule posé au centre (50;50) </li>
	 * <li> chaque globule supplémentaire est posé à la suite du précédent à une position possible
	 * gauche/droite/haut/bas : choix aléatoire parmi les positions possibles </li>
	 * <li> ensuite on recentre le tout </li>
	 * </ul>
	 *
	 * @return les liste des positions des globules de la forme générée
	 */
	public ArrayList<double[]> generateFormRandom() {
		ArrayList<double[]> res = new ArrayList<>();

		double[] tmp = new double[2];
		tmp[0] = 50;
		tmp[1] = 50;
		res.add(tmp.clone());

		Random rn = new Random();
		int nbGlobules = rn.nextInt(4) + 1;

		int pos;
		ArrayList<double[]> listePossible = new ArrayList<>();

		for (int i = 1; i < nbGlobules; i++) {
			// je genere les positions possibles de prendre autour du dernier globule cree.
			listePossible.clear();

			tmp = res.get(i - 1).clone();
			// a droite
			tmp[0] += 25;
			if (!contenir(res, tmp))
				listePossible.add(tmp.clone());

			// a gauche
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

		// j'ai a ce stade, cree un blob, mais non centré.
		// centrons-le : (translation des deux axes par (Max - Min)/2
		centrerBlob(res);

		return res;
	}

	/**
	 * À partir d'une liste des coordonnées de globules non centrés
	 * donnée en référence en paramètre : 
	 * modifie directement ces coordonnées pour les centrer.
	 * <p>Cette méthode est appelée pour créer une nouvelle forme de Blob</p>
	 * <p>rappel : le tout est dans un carré 100*100.
	 * Le centre est donc en coordonnée 50;50 </p>
	 * 
	 * 
	 * @param res
	 * 				liste des coordonnees des globules mais non centré
	 */
	private void centrerBlob(ArrayList<double[]> res) {
		double[] minMaxcor = minMaxXYcor(res);
		// nouveau centre = min + (max - min)/2
		double xNouveauCentre = minMaxcor[0] + (minMaxcor[1] - minMaxcor[0]) / 2;
		double yNouveauCentre = minMaxcor[2] + (minMaxcor[3] - minMaxcor[2]) / 2;

		// à chaque position, j'ajoute le vecteur (ancien centre (50,50) - nouveau
		// Centre )
		for (int i = 0; i < res.size(); i++) {
			res.get(i)[0] += 50 - xNouveauCentre;
			res.get(i)[1] += 50 - yNouveauCentre;
		}

	}

	/**
	 * retourne l'indice du plus grand nombre dans le tableau d'entiers donné en parametre
	 * @param valeurs
	 * 			tableau d'entiers dont on cherche l'indice du plus grand nombre
	 * @return l'indice
	 */
	private int argMax(Integer[] valeurs) {
		int valMax = valeurs[0];
		int indiceMax = 0;
		for (int i = 1; i < valeurs.length; i++)
			if (valMax < valeurs[i]) {
				valMax = valeurs[i];
				indiceMax = i;
			}

		return indiceMax;
	}

	
	/**
	 * change la forme du Blob.
	 * <p>ATTENTION : ceci affecte aussi la taille de globuleCouleurs</p>
	 * <p>Ce changement ce fait en fonction de la répartition du voisinage.
	 * donné en paramètre : le nombre de voisins respectivement dans les zones
	 * Nord / Est / Sud / Ouest </p>
	 * <p>Procédé : <ul>
	 * 		<li>nombre de globules choisi aléatoirement entre 1 et 4.</li>
	 * 		<li>1er globule placé au centre (50;50)</li>
	 * 		<li>globule suivant placé dans la direction de la zone la plus peuplée</li>
	 * 		<li>à chaque affectation d'un globule, 
	 * 			on y soustrait dans cette zone le nombre de voisin max des autres zones</li>
	 * 		<li>enfin on recentre le tout </li>
	 * </ul>
	 * 
	 * @param positionVoisins nombre de voisins dans les zones [Nord;Est;Sud;Ouest]
	 */
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

		for (int i = 1; i < nbGlobules; i++) {
			tmp = res.get(i - 1).clone();
			pos = argMax(positionVoisins);

			switch (pos) {
			case 0: // il me faut me diriger vers le nord. sinon au sud.
				tmp[1] -= 25;
				if (contenir(res, tmp))
					tmp[1] += 50;
				break;
			case 1: // il me faut me dirigier vers l'est, sinon l'west
				tmp[0] += 25;
				if (contenir(res, tmp))
					tmp[0] -= 50;
				break;
			case 2: // il me faut me diriger vers le sud, sinon le nord
				tmp[1] += 25;
				if (contenir(res, tmp))
					tmp[1] -= 50;
				break;
			case 3: // il me faut me diriger vers l'ouest, sinon l'est
				tmp[0] -= 25;
				if (contenir(res, tmp))
					tmp[0] += 50;
				break;
			default:
				break;
			}
			res.add(tmp);

			tmp2 = positionVoisins[pos];
			positionVoisins[pos] = 0;
			positionVoisins[pos] -= tmp2 - positionVoisins[argMax(positionVoisins)];

		}

		// j'ai a ce stade, cree un blob, mais non centré.
		// centrons-le : (translation des deux axes par (Max - Min)/2
		centrerBlob(res);

		globules_position = res;
		Couleur couleur;
		// remettre les couleurs
		if (globules_position.size() >= globules_couleurs.size()) {
			couleur = getCouleurLaPLusPresente();
			for (int i = globules_couleurs.size(); i < globules_position.size(); i++)
				globules_couleurs.add(couleur);
		} else {
			for (int i = globules_couleurs.size(); i > globules_position.size(); i--)
				globules_couleurs.remove(i - 1);
		}
	}

}
