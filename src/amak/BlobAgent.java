package amak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import application.Controller;
import business.Blob;
import business.Couleur;
import business.Critere;
import business.CriticalityFunction;
import fr.irit.smac.amak.Agent;


enum Action { CREER, SE_DEPLACER, SE_SUICIDER, RESTER, CHANGER_COULEUR, CHANGER_FORME, MURIR };

public class BlobAgent extends Agent<MyAMAS, MyEnvironment>{
	
	protected Blob blob;
	protected ArrayList<BlobAgent> voisins; 
	
	protected Action currentAction;
	protected Immaginaire newFils;
	protected Couleur couleurEnvironnante;
	protected Action actionPassive;
	protected double[] pastDirection;
	
	
	// criticite : par convention : negative si en manque, positive si trop nombreux.
	protected double[] criticite;
	
	protected double criticite_globale;
	protected double[] directGeneral;
	protected double epsilon = 0.05;
	protected double degreChangement;
	protected double nbChangements;
	protected double moyenneChangements;
	
	static private CriticalityFunction fctCriticalityStabiliteEtat = new CriticalityFunction(new Double(-10), new Double(10), new Double(2), new Double(2));

	protected Controller controller;
	
	// lie aux decisions 'passives' : en fonction de l'etat du voisinage
	private int nbExperience; // le nombre d'exp�riences coop�ratives. Agit sur la forme
	private HashMap<BlobAgent, Integer> connaissance; // r�pertorie le temps pass� avec un agent
	private int nbExperiencesRequises = 3;
	private int tpsConnaissanceRequise = 2;
	
	@Override
	protected void onInitialization() {
		this.blob = (Blob) params[0];
		criticite = new double[Critere.FIN.getValue()];
		for(int i = 0; i < Critere.FIN.getValue(); i++)
			criticite[i] = 0;
		controller = (Controller) params[1];
		voisins = new ArrayList<>();
		nbExperience = 0;
		connaissance = new HashMap<>();
		directGeneral = new double[2];
		directGeneral[0] = 0;
		directGeneral[1] = 0;
		degreChangement = 0;
		super.onInitialization();
	}
	
	
	public BlobAgent(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
	}
	
	// renvoie la moyenne des positions (dim2)
	private double[] calcule_moyenne(ArrayList<double[]> maListe){
		double[] res = new double[2];
		res[0] = 0;
		res[1] = 0;
		int i;
		for(i = 0; i < maListe.size(); i++){
			res[0] += maListe.get(i)[0];
			res[1] += maListe.get(i)[1];
		}
		res[0] = res[0] / i;
		res[1] = res[1] / i;
		return res;
	}
	
	
	protected void changer_de_couleur_passif(BlobAgent voisin){
		ArrayList<Couleur> listeMesCouleurs = blob.getGlobules_couleurs();
		double[] centreVoisin = voisin.getBlob().getCoordonnee().clone();
		// probleme la coordonnee du voisin pointe en haut a droite. Il faut la centrer.
		double[] tmp = calcule_moyenne(voisin.getBlob().getGlobules_position());
		centreVoisin[0] += tmp[0];
		centreVoisin[1] += tmp[1];
		
		// trouvons quel est le globule le plus proche du voisin.
		ArrayList<double[]> listePosGlob = blob.getGlobules_position();
		// les position des globules sont relative a la position du blob.
		// on va donc enlever la position du blob a celle du voisin, pour ne pas calculer la position exacte des globules � chaque fois.
		centreVoisin[0] -= blob.getCoordonnee()[0];
		centreVoisin[1] -= blob.getCoordonnee()[1];
		double distance;
		int indiceMin =  0;
		double distanceMin = Math.sqrt((centreVoisin[0] - listePosGlob.get(0)[0]) * (centreVoisin[0] - listePosGlob.get(0)[0]) + ((centreVoisin[1] - listePosGlob.get(0)[1]) * (centreVoisin[1] - listePosGlob.get(0)[1])));
		for (int i = 1; i<listePosGlob.size(); i++)
		{
			distance = Math.sqrt((centreVoisin[0] - listePosGlob.get(i)[0]) * (centreVoisin[0] - listePosGlob.get(i)[0]) + ((centreVoisin[1] - listePosGlob.get(i)[1]) * (centreVoisin[1] - listePosGlob.get(i)[1])));
			if (distance < distanceMin){
				distanceMin = distance;
				indiceMin = i;
			}
		}

		// on modifie la couleur de ce globule en la couleur la plus pr�sente de notre voisin
		listeMesCouleurs.set(indiceMin, voisin.blob.getCouleurLaPLusPresente());
		blob.setGlobules_couleurs(listeMesCouleurs);
		nbChangements++;
	}
		
	// Le changement de forme se fait en choisissant une forme aleatoire.
	protected void changer_de_forme(){
		blob.changeForme();
		nbExperience = 0;
	}
	
	protected void majAspectAgent(){
		// La forme s'acquiert a partir d'un nombre d'exp�rience atteint.
		if (nbExperience >= nbExperiencesRequises)
			changer_de_forme();
		
		// la pulsation depend du nombre de voisins alentour
		blob.setPulsation(voisins.size());
		
		// la couleur s'acquiert si un voisin est present depuis un temps defini.
		/*
		Set<BlobAgent> blobsConnus = (Set<BlobAgent>) connaissance.keySet();
		for (BlobAgent blobConnu : blobsConnus) {
			if(connaissance.get(blobConnu) > tpsConnaissanceRequise ){
				changer_de_couleur_passif(blobConnu);
				connaissance.put(blobConnu, 0);
			}
		}
		*/
		
		
		// ITERATION
		if (actionPassive == (Action.CHANGER_COULEUR) || actionPassive == (Action.CHANGER_FORME ))
			nbExperience++;
		
		// maj des connaissances:
		/*
		for(int i = 0; i < voisins.size(); i++){
			if(connaissance.containsKey(voisins.get(i))){
				connaissance.put(voisins.get(i), connaissance.get(voisins.get(i)) + 1);
			}
			else 
				connaissance.put(voisins.get(i), 0);
		}
		*/
	}
	
	
	@Override
    protected void onPerceive() {
		getAmas().getEnvironment().generateNeighbours(this);		
    }
	
	
	
	
	
	/* **************************************************************** *
	 * ********** 				ACTION				******************* *
	 * **************************************************************** */
	
	protected void action_se_suicider(){
		currentAction = Action.SE_SUICIDER;
		getAmas().getEnvironment().removeAgent(this);
		destroy();
	}

	protected void action_creer(){
		currentAction = Action.CREER;
		Blob newBlob = blob.copy_blob();
		newBlob.setCoordonnee(getAmas().getEnvironment().nouvellesCoordonnees(this, 2));
		newFils = new Immaginaire(getAmas(), newBlob, controller);
		getAmas().getEnvironment().addAgent(newFils);		
		
	}
	
	protected void action_se_deplacer(){
		double[] tmp = getAmas().getEnvironment().nouvellesCoordonnees(this, Math.random() * 1.2, pastDirection);
		blob.setCoordonnee(tmp);
		currentAction = Action.SE_DEPLACER;
		
		directGeneral[0] = 0.6 * pastDirection[0] + 0.4 * directGeneral[0];
		directGeneral[1] = 0.6 * pastDirection[1] + 0.4 * directGeneral[1];
	}
	
	
	// CHANGEMENT DE COULEUR .... Pour ne pas perdre les couleurs aquises par experience, 
	// je choisis de changer la couleur qui est la plus frequente parmi mes globules.
	// action de changer de couleur en prenant une couleur aleatoire
	protected void action_changerCouleur(){
		// choix d'une nouvelle couleur
		Couleur[] couleurListe = Couleur.values();
		int indiceCouleur = (int) (Math.random() * ( couleurListe.length ));
		Couleur nvlleCouleur = couleurListe[indiceCouleur];
		
		
		Couleur MostPresentCouleur = blob.getCouleurLaPLusPresente();
		ArrayList<Couleur> listeGlobulesCouleur = blob.getGlobules_couleurs();
		for (int i = 0; i < listeGlobulesCouleur.size(); i++){
			if (listeGlobulesCouleur.get(i).equals(MostPresentCouleur))
				listeGlobulesCouleur.set(i, nvlleCouleur);
		}
		nbChangements++;
	}
	
	// action de changer de couleur en prenant celle la plus presente dans l'environnement, 
	// laquelle est donnee en argument.
	protected void action_changerCouleur(Couleur couleur){
				
				
				Couleur MostPresentCouleur = blob.getCouleurLaPLusPresente();
				ArrayList<Couleur> listeGlobulesCouleur = blob.getGlobules_couleurs();
				for (int i = 0; i < listeGlobulesCouleur.size(); i++){
					if (listeGlobulesCouleur.get(i).equals(MostPresentCouleur))
						listeGlobulesCouleur.set(i, couleur);
				}
				nbChangements++;
	}
	
	
	/* ************************************************************************ *
	 * ************** 			CRITICALITY 		*************************** *
	 * ************************************************************************ */
	
	protected double computeCriticalityIsolement(){
		return(getAmas().getEnvironment().getIsolement() - voisins.size());
	}
	
	protected double computeCriticalityHeterogeneite(){
		
		// recuperation des couleurs environnantes
		HashMap<Couleur, Integer> couleurs = new HashMap<>();
		Couleur couleur;
		for(int i = 0; i < voisins.size() ; i++){
			couleur = voisins.get(i).getBlob().getCouleurLaPLusPresente();
			if (couleurs.containsKey(couleur))
				couleurs.put(couleur, 1 + couleurs.get(couleur));
			else
				couleurs.put(couleur, 1);
		}
		
		// recuperation de la couleur la plus presente.
		Set<Couleur> couleurSet = couleurs.keySet();
		int maxNbCouleur = 0;
		int tmp;
		for (Couleur clr : couleurSet){
			if (( tmp = couleurs.get(clr)) > maxNbCouleur)
			{
				maxNbCouleur = tmp;
				couleurEnvironnante = clr;
			}
		}
		
		// calcul de la criticite autour de cette couleur.
		double nbVoisinsOptimal = ((100 - getAmas().getEnvironment().getHeterogeneite()) / 100) * voisins.size(); 
		return(maxNbCouleur - nbVoisinsOptimal);	
	}
	
	protected double computeCriticalityStabilitePosition(){
		// calcul du nombre de voisins qui "bougent".
		// chaque voisin bouge ssi DirectGeneral > (eps,eps)
		double nbBougent = 0;
		for(int i = 0; i<voisins.size(); i++){
			if (voisins.get(i).getDirectGeneral()[0] + voisins.get(i).getDirectGeneral()[1] > epsilon)
				nbBougent ++;
		}
		
		// nb de voisins optimal qui devraient bouger, selon le curseur.
		double nbOptimal = (getAmas().getEnvironment().getStabilite_position() / 100) * voisins.size();

		if(nbBougent < nbOptimal)
			return(nbOptimal - nbBougent); 

		// le probl�me, si trop de blobs bougent autour, je ne veux pas lever la criticit�, afin d'esp�rer agir pour une autre criticit�.
		return(0);
	}
	
	protected double computeCriticalityStabiliteEtat(){
		// calcule de la moyenne des changements effectu�s alentour:
		double moyenne = 0;
		for (int i = 0; i< voisins.size(); i++){
			moyenne += voisins.get(i).getNbChangements();
		}
		moyenne /= voisins.size();
		
		double res = fctCriticalityStabiliteEtat.compute(moyenneChangements - moyenne);
		moyenneChangements = moyenne;
		return(res);
			
	}
	
    protected double computeCriticalityInTideal() {
		criticite[Critere.Isolement.getValue()]= computeCriticalityIsolement();
		criticite[Critere.Heterogeneite.getValue()] = computeCriticalityHeterogeneite();
		criticite[Critere.Stabilite_etat.getValue()] = 0;
		criticite[Critere.Stabilite_position.getValue()] = computeCriticalityStabilitePosition();
		
		criticite_globale = criticite[Critere.Heterogeneite.getValue()] + criticite[Critere.Isolement.getValue()] + criticite[Critere.Stabilite_etat.getValue()] + criticite[Critere.Stabilite_position.getValue()];
		
        return criticite_globale;
    }

    
    // retourne le critere qui a une plus grande criticite
 	public Critere Most_critical_critere(double[] subjectCriticity){
 		//return (Collections.max(criticite.entrySet(), Map.Entry.comparingByValue()).getKey());
 		double max_valeur = subjectCriticity[0];
 		int max_critere = 0;
 		for (int i = 0; i<subjectCriticity.length; i++)
 			if(Math.abs(max_valeur) < Math.abs(subjectCriticity[i])){
 				max_valeur = subjectCriticity[i];
 				max_critere = i;
 			}
 		return Critere.valueOf(max_critere);
 	}
 	
 	/* renvoie l'agent le plus critique parmi ses voisins, incluant lui-meme*/
	protected BlobAgent getMoreCriticalAgent(){
		Iterator<BlobAgent> itr = voisins.iterator();
		double criticiteMax = criticite_globale;
		BlobAgent res = this;
	    while(itr.hasNext()) {
	       BlobAgent blobagent = itr.next();
	       if(blobagent.criticite_globale > criticiteMax){
	    	   criticiteMax = blobagent.criticite_globale;
	    	   res = blobagent;
	       }
	    }
	    return (res);
	}


 	public double[] getCriticite() {
 		return criticite;
 	}
 	
 	
 	
	/* ******************************************	**************************
	 * ***********			GETTER / SETTER			**************************
	 * *********************************************************************** */
	
	
	public Blob getBlob() {
		return blob;
	}
	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	public void addVoisin(BlobAgent blobToAdd){
		this.voisins.add(blobToAdd);
	}
	
	public void clearVoisin(){
		this.voisins.clear();
	}
	
	public ArrayList<BlobAgent> getVoisins() {
		return voisins;
	}


	public void setVoisins(ArrayList<BlobAgent> voisins) {
		this.voisins = voisins;
	}


	public double getCriticite_globale() {
		return criticite_globale;
	}


	public void setCriticite_globale(int criticite_globale) {
		this.criticite_globale = criticite_globale;
	}
	
	public double[] getPastDirection() {
		return pastDirection;
	}


	public void setPastDirection(double[] pastDirection) {
		this.pastDirection = pastDirection;
	}


	public double[] getDirectGeneral() {
		return directGeneral;
	}


	public void setDirectGeneral(double[] directGeneral) {
		this.directGeneral = directGeneral;
	}


	public double getNbChangements() {
		return nbChangements;
	}



}
	
	