package amak;

import java.util.HashMap;

import application.Controller;
import business.Blob;

public class AmasThread extends Thread{
	Controller controller;
	HashMap<Blob, BlobAgent> map;
	
	public AmasThread(Controller controller){
			super();
			this.controller = controller;
	}
	
	// le thread PositionBluetooth transmet le mouvement d'un blob reel, lequel est associé ici à un blob agent
	// il nous faut trouver le bon blobAgent et prévenir l'environnemnt et MyAmas.
	public void add_blob(Blob b){
		
		
	}
	
	
	public void remove_blob(Blob b){
		
	}
	
	public void move_blob(Blob b){
		
	}
	
	
	public void run(){
		
		Tideal env = new Tideal();
        new MyAMAS(env, controller);
        
	}
	
	
	
	
	
}

