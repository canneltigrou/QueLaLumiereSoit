package amak;

import java.util.ArrayList;

import application.Controller;
import business.Blob;
import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.Scheduling;
import javafx.application.Platform;

public class MyAMAS extends Amas<Tideal>{
	

	/* possede les valeurs des differents sliders*/
	private int isolement;
	private int stabilite_etat;
	private int stabilite_position;
	private int heterogeneite;
	private int distanceRepresentation;
	private ArrayList<BlobAgent> agents;
	
	/* communication avec l'interface graphique */
	private Controller controller;
	
	@Override
	protected void onInitialConfiguration() {
		this.controller = (Controller) params[0];
		isolement = controller.getIsolement();
		stabilite_etat = controller.getStabiliteHeterogeneite();
		stabilite_position = controller.getStabilitePosition();
		heterogeneite = controller.getHeterogenite();
		setDistanceRepresentation(controller.getDistanceRepresentation());
		
		super.onInitialConfiguration();
	}
	
	public MyAMAS(Tideal env, Controller controller) {
		super(env, Scheduling.DEFAULT);
	}

	
	@Override
    protected void onInitialAgentsCreation() {
		agents = new ArrayList<BlobAgent>();
	}
	
	
	
	protected void addAgent(Blob b){
		BlobAgent agent = new BlobAgent(this, b);
		getEnvironment().addAgent(agent);
	}
	
	protected void moveAgent(Blob b, BlobAgent agent){
		Platform.runLater(new Runnable() {
			public void run() {
				agent.setBlob(b);
				// normalement, prévient donc l'environnement
			}
		});
	}
	
	protected void removeAgent(Blob b, BlobAgent agent){
		// TODO
		
	}
	
	
	
	/* **************************************************************
	 * **		interaction avec l'interface graphique 			 ** *
	 ***************************************************************** */
	 
	public void add_blob_on_draw(Blob b){
		controller.add_blobAgent(b);
	}
	
	public void move_blob_on_draw(Blob b){
		controller.move_blobAgent(b);
	}
	
	public void remove_blob_on_drawing(Blob b){
		controller.remove_blobAgent(b);
	}
	
	
	
	
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
