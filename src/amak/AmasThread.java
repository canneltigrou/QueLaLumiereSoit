package amak;
import application.Controller;
import javafx.application.Platform;
import positionBluetooth.PositionThread;

public class AmasThread extends Thread{
	Controller controller;
	MyAMAS myAmas;
	PositionThread tposition;
	int nbBlobs;
	
	public PositionThread getTposition() {
		return tposition;
	}

	public void setTposition(PositionThread tposition) {
		this.tposition = tposition;
	}

	public AmasThread(int nbBlobs){
			super();
			//this.controller = controller;
			this.nbBlobs = nbBlobs;
	}
	
	// le thread PositionBluetooth transmet le mouvement d'un blob reel, lequel est associé ici à un blob agent
	// il nous faut trouver le bon blobAgent et prévenir l'environnemnt et MyAmas.
	/*
	public void add_blob(Blob b){
		Blob blob = b.copy_blob();
		BlobAgent agent = new BlobAgent(myAmas,blob);
		myAmas.getEnvironment().addAgent(agent);
		//map.put(b, agent);
	}
	*/
		 
	
	
	
	public void move_blob(Migrant b, double[] coo){
		
		b.getBlob().setCoordonnee(coo);
		
		//controller.move_blobMigrant(b);
	}
	
	public void t0_to_tr(Migrant blob){

		Platform.runLater(new Runnable() {
			public void run() {
				blob.t0_to_tr();
			}
		});
		
	}
	
	public void tr_to_t0(Migrant blob){
		Platform.runLater(new Runnable() {
			public void run() {
				blob.tr_to_t0();
			}
		});
	}
	
	
	
	public void run(){

		MyEnvironment env = new MyEnvironment(controller);
        myAmas = new MyAMAS(env, nbBlobs);
        //controller.setBlobHibernants(env.getHibernants());
        
        
        
        PositionThread tPosition = new PositionThread(this, env.getHibernants());
		tPosition.start();
        
	}

	//public Controller getController() {
		//return controller;
	//}

	public void setController(Controller controller) {
		//this.controller = controller;
	}
	
	
	public void setIsolement(int isolement){
		Platform.runLater(new Runnable() {
			public void run() {
				myAmas.getEnvironment().setIsolement(isolement);
				System.out.println("tAmas : changement Taux d'isolement à " + isolement);
			}
		});
	}

	public void setHeterogeneite(int heterogeneite) {
		Platform.runLater(new Runnable() {
			public void run() {
				myAmas.getEnvironment().setHeterogeneite(heterogeneite);
				System.out.println("tAmas : changement Taux d'hétérogénéité " + heterogeneite);
			}
		});		
	}


	public void setStabilitePosition(int stabilite_position) {
		Platform.runLater(new Runnable() {
			public void run() {
				myAmas.getEnvironment().setStabilite_position(stabilite_position);
				System.out.println("tAmas : changement de la Stabilité des positions à " + stabilite_position);
			}
		});		
		
	}

	public void setTauxMurissement(int tauxMurissemnt) {
		Platform.runLater(new Runnable() {
			public void run() {
				myAmas.getEnvironment().setTauxMurissemnt(tauxMurissemnt);
				System.out.println("tAmas : changement Taux Murissement à " + tauxMurissemnt);
			}
		});		
	}	
}

