package amak;

import java.util.ArrayList;

import application.Controller;
import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.Scheduling;

public class MyEnvironment extends Environment {
	private ArrayList<BlobAgent> agents;
	private ArrayList<Migrant> hibernants;
	private int radius = 5;
	/* possede les valeurs des differents sliders*/
	private int isolement;
	private int stabilite_etat;
	private int stabilite_position;
	private int heterogeneite;
	private int distanceRepresentation;
	
	/* communication avec l'interface graphique */
	private Controller controller;
	
	
	public MyEnvironment(Controller controller) {
		super(Scheduling.DEFAULT, controller);
	}
	
	
	@Override
	public void onInitialization(){
		agents = new ArrayList<BlobAgent>();
		hibernants = new ArrayList<Migrant>();
		//this.setRealBlobs(realBlobs);
		this.controller = (Controller) params[0];
		isolement = controller.getIsolement();
		stabilite_etat = controller.getStabiliteHeterogeneite();
		stabilite_position = controller.getStabilitePosition();
		heterogeneite = controller.getHeterogenite();
		setDistanceRepresentation(controller.getDistanceRepresentation());
		
		
	}

	

	public ArrayList<BlobAgent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<BlobAgent> agent) {
		this.agents = agent;
	}
	
	
	
	private void generateNeighboursTideal(BlobAgent subject){
		for (int j = 0; j < agents.size(); j++ )
		{
			if(subject.getBlob().isVoisin(agents.get(j).getBlob(), radius))
				subject.addVoisin(agents.get(j));
		}
	}
	private void generateNeighboursToriginel(BlobAgent subject){
		for (int j = 0; j < hibernants.size(); j++ )
		{
			if(subject.getBlob().isVoisin(hibernants.get(j).getBlob(), radius))
				subject.addVoisin(hibernants.get(j));
		}
	}
	
	
	public void generateNeighbours(BlobAgent subject){
		subject.clearVoisin();
		if( (subject instanceof Migrant) || !((Migrant)subject).isHome())
			generateNeighboursToriginel(subject);
		else
			generateNeighboursTideal(subject);
	}


	public ArrayList<Migrant> getHibernants() {
		return hibernants;
	}


	public void setHibernants(ArrayList<Migrant> hibernants) {
		this.hibernants = hibernants;
	}


	public void addAgent(BlobAgent agent) {
		agents.add(agent);
	}

	
	public void removeAgent(BlobAgent agent) {
		agents.remove(agent);
	}
	
	public void addMigrant(Migrant migrant) {
		hibernants.add(migrant);
	}	

	public void t0_to_tr(Migrant migrant){
		hibernants.remove(migrant);
		agents.add(migrant);
	}
	
	public void tr_to_t0(Migrant migrant){
		agents.remove(migrant);
		hibernants.add(migrant);
	}
	
	/* **************************************************************
	 * **		interaction avec l'interface graphique 			 ** *
	 ***************************************************************** */
	 /*
	public void add_blob_on_draw(BlobAgent b){
		controller.add_blobAgent(b);
	}
	
	public void move_blob_on_draw(BlobAgent b){
		controller.move_blobAgent(b);
	}
	
	public void remove_blob_on_draw(BlobAgent b){
		controller.remove_blobAgent(b);
	}
	*/
	
	
	
	/* *****************************************************************************************
	 * *********************   getter / setter			****************************************
	 * ************************************************************************************* * */
	
	public int getIsolement() {
		return isolement;
	}

	public void setIsolement(int isolement) {
		this.isolement = isolement;
	}

	public int getStabilite_etat() {
		return stabilite_etat;
	}

	public void setStabilite_etat(int stabilite_etat) {
		this.stabilite_etat = stabilite_etat;
	}

	public int getStabilite_position() {
		return stabilite_position;
	}

	public void setStabilite_position(int stabilite_position) {
		this.stabilite_position = stabilite_position;
	}

	public int getHeterogeneite() {
		return heterogeneite;
	}

	public void setHeterogeneite(int heterogeneite) {
		this.heterogeneite = heterogeneite;
	}


	public Controller getController() {
		return controller;
	}


	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getDistanceRepresentation() {
		return distanceRepresentation;
	}

	public void setDistanceRepresentation(int distanceRepresentation) {
		this.distanceRepresentation = distanceRepresentation;
	}


	
}
