package amak;

import java.util.ArrayList;
import business.Blob;
import business.Critere;
import fr.irit.smac.amak.Agent;




public class BlobAgent extends Agent<MyAMAS, Tideal>{
	
	private Blob blob;
	private ArrayList<Blob> voisins; 

	private Action currentAction;
	private Blob newFils;
	
	// criticité : par convention : négative si en manque, positive si trop nombreux.
	private Integer[] criticite;
	
	//private int criticite_isolement;
	//private int criticite_stabilite_etat;
	//private int criticite_stabilite_position;
	//private int criticite_heterogeneite;
	private int criticite_globale;

	
	@Override
	protected void onInitialization() {
		this.blob = (Blob) params[0];
		blob.setCpt_state(0);
		blob.setCpt_position(0);
		criticite = new Integer[Critere.FIN.getValue()];
		super.onInitialization();
	}
	
	
	public BlobAgent(MyAMAS amas, Blob b) {
		super(amas, b);
		
	}
	
	
	@Override
    protected void onPerceive() {
		getAmas().getEnvironment().generateNeighbours(this.blob);

    }
	
	
	
	@Override
	protected void onDecideAndAct() {

		//Critere the_most_critical = Most_critical_critere();
		 Critere most_critic = Most_critical_critere();
		 
		
		
		 
		
		
		
		
		super.onDecideAndAct();
	}
	
	@Override
    protected double computeCriticality() {
		criticite[Critere.Heterogeneite.getValue()]=getAmas().getIsolement() - blob.getVoisins().size();
		//TODO

		
		
        return blob.getCpt_state();
    }


    @Override
    protected void onUpdateRender() {
    	if (currentAction.equals(Action.Se_deplacer))
    		getAmas().move_blob_on_draw(blob);
    	else
    		if (currentAction.equals(Action.Creer))
    			getAmas().add_blob_on_draw(newFils);
    		else
    			getAmas().remove_blob_on_drawing(blob);
    }


	
	
	Blob getBlob() {
		return blob;
	}
	void setBlob(Blob blob) {
		this.blob = blob;
	}

	public void addVoisin(Blob blobToAdd){
		this.voisins.add(blobToAdd);
	}
	
	public void clearVoisin(){
		this.voisins.clear();
	}
	
	public ArrayList<Blob> getVoisins() {
		return voisins;
	}


	public void setVoisins(ArrayList<Blob> voisins) {
		this.voisins = voisins;
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
	public Critere Most_critical_critere(){
		//return (Collections.max(criticite.entrySet(), Map.Entry.comparingByValue()).getKey());
		int max_valeur = criticite[0];
		int max_critere = 0;
		for (int i = 0; i<criticite.length; i++)
			if(max_valeur < criticite[i]){
				max_valeur = criticite[i];
				max_critere = i;
			}
		return Critere.valueOf(max_critere);
	}


	

	
	
	
}
	
	