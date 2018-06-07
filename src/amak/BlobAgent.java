package amak;

import java.util.ArrayList;
import java.util.Iterator;

import application.Controller;
import business.Blob;
import business.Critere;
import fr.irit.smac.amak.Agent;


enum Action { CREER, SE_DEPLACER, SE_SUICIDER, RESTER };

public class BlobAgent extends Agent<MyAMAS, MyEnvironment>{
	
	protected Blob blob;
	private ArrayList<BlobAgent> voisins; 
	
	protected Action currentAction;
	protected Immaginaire newFils;
	//private Blob agentNeedingHelp;
	
	// criticité : par convention : négative si en manque, positive si trop nombreux.
	protected Integer[] criticite;
	
	//private int criticite_isolement;
	//private int criticite_stabilite_etat;
	//private int criticite_stabilite_position;
	//private int criticite_heterogeneite;
	protected int criticite_globale;
	
	protected Controller controller;

	
	@Override
	protected void onInitialization() {
		this.blob = (Blob) params[0];
		blob.setCpt_state(0);
		blob.setCpt_position(0);
		criticite = new Integer[Critere.FIN.getValue()];
		for(int i = 0; i < Critere.FIN.getValue(); i++)
			criticite[i] = 0;
		controller = (Controller) params[1];
		voisins = new ArrayList<>();
		super.onInitialization();
	}
	
	
	public BlobAgent(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
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
		int criticiteMax = criticite_globale;
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
		/*double[] coo = newBlob.getCoordonnee();
		coo[0] += 16;
		coo[1] += 16;*/
		newBlob.setCoordonnee(getAmas().getEnvironment().nouvellesCoordonnees(this, 2));
		newFils = new Immaginaire(getAmas(), newBlob, controller);
		
		getAmas().getEnvironment().addAgent(newFils);		
		
	}
	
	protected void action_se_deplacer(){
		double[] tmp = getAmas().getEnvironment().nouvellesCoordonnees(this, 0.2);
		blob.setCoordonnee(tmp);
		currentAction = Action.SE_DEPLACER;	
	}
	
	
	
	
	
	/*
	@Override
	protected void onDecideAndAct() {
	     BlobAgent agentNeedingHelp = getMoreCriticalAgent();
		 Critere most_critic = Most_critical_critere(agentNeedingHelp);
		 
		 if (!isOutside())
			 action_se_suicider();
		 else{
		 
		 switch (most_critic){
		 case Isolement:
			 // too many neighboors -> criticite.ISOLEMENT<0 -> I have to kill myself
			 if(criticite[Critere.Heterogeneite.getValue()] < 0)
				 action_se_suicider();
			 else
				 action_creer();
			 break;
				 
		 case Stabilite_etat:
			 break;
			 
		 case Stabilite_position:
			 break;
			 
		 case Heterogeneite:
			 break;
		 
		 default:
			break;		 
		 }
		 }
		super.onDecideAndAct();
	}
	*/
	
    /*protected double computeCriticality() {
		System.out.println("début calcul de criticité");
		criticite[Critere.Heterogeneite.getValue()]= getAmas().getEnvironment().getIsolement() - voisins.size();
		System.out.println("1ere partie calculée");
		criticite[Critere.Isolement.getValue()] = 0;
		criticite[Critere.Stabilite_etat.getValue()] = 0;
		criticite[Critere.Stabilite_position.getValue()] = 0;
		
		criticite_globale = criticite[Critere.Heterogeneite.getValue()] + criticite[Critere.Isolement.getValue()] + criticite[Critere.Stabilite_etat.getValue()] + criticite[Critere.Stabilite_position.getValue()];
		
		System.out.println("fin de calcul de la criticité");
        return blob.getCpt_state();
    }*/

	
    protected double computeCriticalityInTideal() {
		criticite[Critere.Isolement.getValue()]= getAmas().getEnvironment().getIsolement() - voisins.size();
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


	public int getCriticite_globale() {
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
		int max_valeur = agent.criticite[0];
		int max_critere = 0;
		for (int i = 0; i<criticite.length; i++)
			if(max_valeur < criticite[i]){
				max_valeur = criticite[i];
				max_critere = i;
			}
		return Critere.valueOf(max_critere);
	}


	public Integer[] getCriticite() {
		return criticite;
	}

}
	
	