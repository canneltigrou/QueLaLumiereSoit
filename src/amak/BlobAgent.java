package amak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import application.Controller;
import business.Blob;
import business.Couleur;
import business.Critere;
import fr.irit.smac.amak.Agent;


enum Action { CREER, SE_DEPLACER, SE_SUICIDER, RESTER, CHANGER_COULEUR, CHANGER_FORME };

public class BlobAgent extends Agent<MyAMAS, MyEnvironment>{
	
	protected Blob blob;
	private ArrayList<BlobAgent> voisins; 
	
	protected Action currentAction;
	protected Immaginaire newFils;
	//private Blob agentNeedingHelp;
	protected Action actionPassive;
	
	// criticité : par convention : négative si en manque, positive si trop nombreux.
	protected double[] criticite;
	
	protected double criticite_globale;
	
	protected Controller controller;
	
	// lié aux décisions 'passives' : en fonction de l'etat du voisinage
	private int nbExperience; // le nombre d'expériences coopératives. Agit sur la forme
	private HashMap<BlobAgent, Integer> connaissance; // répertorie le temps passé avec un agent
	private int nbExperiencesRequises = 3;
	private int tpsConnaissanceRequise = 2;
	
	@Override
	protected void onInitialization() {
		this.blob = (Blob) params[0];
		blob.setCpt_state(0);
		blob.setCpt_position(0);
		criticite = new double[Critere.FIN.getValue()];
		for(int i = 0; i < Critere.FIN.getValue(); i++)
			criticite[i] = 0;
		controller = (Controller) params[1];
		voisins = new ArrayList<>();
		nbExperience = 0;
		connaissance = new HashMap<>();
		super.onInitialization();
	}
	
	
	public BlobAgent(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
	}
	
	
	// pour le moment prend la couleur du 1er globule présent chez mon voisin
	protected void changer_de_couleur(BlobAgent voisin){
		ArrayList<Couleur> listeCouleurs = blob.getGlobules_couleurs();
		listeCouleurs.set(0, voisin.blob.getGlobules_couleurs().get(0));
		blob.setGlobules_couleurs(listeCouleurs);
	}
	
	// Le changement de forme se fait en choisissant une forme aléatoire.
	protected void changer_de_forme(){
		blob.changeForme();
		nbExperience = 0;
	}
	
	protected void majAspectAgent(){
		// La forme s'acquiert à partir d'un nombre d'expérience atteint.
		if (nbExperience >= nbExperiencesRequises)
			changer_de_forme();
		
		// la pulsation dépend du nombre de voisins alentour
		blob.setPulsation(voisins.size());
		
		// la couleur s'acquiert si un voisin est présent depuis un temps défini.
		Set<BlobAgent> blobsConnus = (Set<BlobAgent>) connaissance.keySet();
		for (BlobAgent blobConnu : blobsConnus) {
			if(connaissance.get(blobConnu) > tpsConnaissanceRequise ){
				changer_de_couleur(blobConnu);
				connaissance.put(blobConnu, 0);
			}
		}
		
		
		// ITERATION
		if (actionPassive == Action.CHANGER_COULEUR || actionPassive == Action.CHANGER_FORME )
			nbExperience++;
		
		// maj des connaissances:
		for(int i = 0; i < voisins.size(); i++){
			if(connaissance.containsKey(voisins.get(i))){
				connaissance.put(voisins.get(i), connaissance.get(voisins.get(i)) + 1);
			}
			else 
				connaissance.put(voisins.get(i), 0);
		}
		
		
		
	}
	
	
	@Override
    protected void onPerceive() {
		getAmas().getEnvironment().generateNeighbours(this);
		// Nothing goes here as the perception of neighbors criticality is already made
        // by the framework
		
    }
	
	
	/* renvoie l'agent le plus critique parmi ses voisins, incluant lui-même*/
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
		double[] tmp = getAmas().getEnvironment().nouvellesCoordonnees(this, 0.2);
		blob.setCoordonnee(tmp);
		currentAction = Action.SE_DEPLACER;	
	}
	
	
	
	
	
	protected double computeCriticalityIsolement(){
		return(getAmas().getEnvironment().getIsolement() - voisins.size());
	}
	
	
	
	
    protected double computeCriticalityInTideal() {
		criticite[Critere.Isolement.getValue()]= computeCriticalityIsolement();
		criticite[Critere.Heterogeneite.getValue()] = 0;
		criticite[Critere.Stabilite_etat.getValue()] = 0;
		criticite[Critere.Stabilite_position.getValue()] = 0;
		
		criticite_globale = criticite[Critere.Heterogeneite.getValue()] + criticite[Critere.Isolement.getValue()] + criticite[Critere.Stabilite_etat.getValue()] + criticite[Critere.Stabilite_position.getValue()];
		
        return blob.getCpt_state();
    }

	
	
	
	
	public Blob getBlob() {
		return blob;
	}
	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	public void addVoisin(BlobAgent blobToAdd){
		this.voisins.add(blobToAdd);
		//this.blob.addVoisin(blobToAdd.blob);
	}
	
	public void clearVoisin(){
		this.voisins.clear();
		//this.blob.clearVoisin();
	}
	
	public ArrayList<BlobAgent> getVoisins() {
		return voisins;
	}


	public void setVoisins(ArrayList<BlobAgent> voisins) {
		this.voisins = voisins;
		blob.clearVoisin();
		for(int i = 0; i<voisins.size(); i++){
			blob.addVoisin(voisins.get(i).blob);
		}		
	}


	public double getCriticite_globale() {
		return criticite_globale;
	}


	public void setCriticite_globale(int criticite_globale) {
		this.criticite_globale = criticite_globale;
	}
	
	
	
	
	
	/* ******************************************************
	 *  **** 		about the criticity					****
	 ****************************************************** */
	
	// retourne le critere qui a une plus grande criticité
	public Critere Most_critical_critere(BlobAgent agent){
		//return (Collections.max(criticite.entrySet(), Map.Entry.comparingByValue()).getKey());
		double max_valeur = agent.criticite[0];
		int max_critere = 0;
		for (int i = 0; i<criticite.length; i++)
			if(max_valeur < criticite[i]){
				max_valeur = criticite[i];
				max_critere = i;
			}
		return Critere.valueOf(max_critere);
	}


	public double[] getCriticite() {
		return criticite;
	}

}
	
	