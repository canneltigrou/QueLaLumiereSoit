package amak;
import application.Controller;
import positionBluetooth.PositionThread;

public class AmasThread extends Thread{
	Controller controller;
	MyAMAS myAmas;
	PositionThread tposition;
	//HashMap<Blob, BlobAgent> map;
	
	public PositionThread getTposition() {
		return tposition;
	}

	public void setTposition(PositionThread tposition) {
		this.tposition = tposition;
	}

	public AmasThread(Controller controller){
			super();
			this.controller = controller;
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
		
		controller.move_blobMigrant(b);
	}
	
	public void t0_to_tr(Migrant blob){
		blob.t0_to_tr();
	}
	
	public void tr_to_t0(Migrant blob){
		blob.tr_to_t0();
	}
	
	
	
	public void run(){

		MyEnvironment env = new MyEnvironment(controller);
        myAmas = new MyAMAS(env, controller);
        
        PositionThread tPosition = new PositionThread(this, env.getHibernants());
		tPosition.start();
        
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}

