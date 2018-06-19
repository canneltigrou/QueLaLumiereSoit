package amak;

import application.Controller;

//import java.util.ArrayList;

import business.Blob;
import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.Scheduling;
import javafx.application.Platform;

public class MyAMAS extends Amas<MyEnvironment>{
	
	private Controller controller;
	
	@Override
	protected void onInitialConfiguration() {
		int nbBlobs = (int) params[1];
		Migrant migrant;
		double xcor;
		double ycor;
		Blob blob;
		controller = (Controller) params[0];		
		for(int i = nbBlobs ; i > 0 ; i--){
			xcor = Math.random() * ( 100 );
			ycor = Math.random() * ( 100 );
			
			blob = new Blob(xcor,ycor, true);
			migrant = new Migrant(this, blob, controller);
			getEnvironment().addMigrant(migrant);
			controller.add_blobHibernant(migrant);
		}
		super.onInitialConfiguration();
		System.out.println("fin de l'initilisation de MyAmas");
	}
	
	public MyAMAS(MyEnvironment env, Controller controller, int nbBlobs) {
		super(env, Scheduling.DEFAULT, controller, nbBlobs);
	}

	
	@Override
    protected void onInitialAgentsCreation() {
	}
	
	
	
	protected void addAgent(Blob b){
		BlobAgent agent = new BlobAgent(this, b, controller);
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
		Platform.runLater(new Runnable() {
			public void run() {
				getEnvironment().getAgents().remove(agent);
				//agents.remove(agent);
				// normalement, prévient donc l'environnement
			}
		});
		
	}
	
	
	
}