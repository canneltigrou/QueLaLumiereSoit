package positionBluetooth;
import application.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import amak.AmasThread;
import business.Blob;
import business.Couleur;
import business.Forme;


// permet de lancer une thread pour établir la connexion bluetooth
// et récolter les données en temps réel
// pour les transmettres aux environnements correspondants


public class PositionThread extends Thread{
	private ArrayList<Blob> blobList;
	private Controller controller;
	private AmasThread tAmas;
	
	public PositionThread(Controller controller, AmasThread tAmas){
			super();
			this.controller = controller;
			this.tAmas = tAmas;
			blobList = new ArrayList<Blob>();
	}
	
	
	
	
	public void run(){
		
		testByConsole();
		
	}   
	
	
	public void addBlob(Blob b){
		controller.add_blobReel(b);
		tAmas.add_blob(b);
		
	}
	
	public void removeBlob(){
		// TODO 
	}
	
	public void moveBlob(){
		
		
	}
	
	
	
	private void afficher_position_blobs()
	{
		for(int i = 0 ; i < blobList.size(); i++){
			double[] pos = blobList.get(i).getCoordonnee();
			System.out.println("" + i + " : " + pos[0] + " ; " + pos[1]);
			
		}
		
	}
	
	
	
	private void testByConsole(){
		
		
		Blob b1 = new Blob(50,20, Couleur.BLUE, 10, Forme.simple, true);
		Blob b2 = new Blob(128, 50, Couleur.RED, 10, Forme.simple, true);
		Blob b3 = new Blob(250,100, Couleur.YELLOW, 10, Forme.simple, true);
		Blob b4 = new Blob(60,100, Couleur.BLUE, 10, Forme.simple, false);

		addBlob(b1);
		addBlob(b2);
		addBlob(b3);
		addBlob(b4);
		
		
		
		
		BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
		
		String objectif = "";
		String option = "";
		String tmp = "";
		int num = 0;
		double coo[];
		double newPosX = 0;
		double newPosY = 0;
		Blob blob;
		
		while (true){
			System.out.print(" 1 - nouveau Blob \n 2 - nouvelle position : ");
			try {
				objectif = standardInput.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(objectif.equals("2")){
				afficher_position_blobs();
				System.out.println("choisissez un blob :");
				try {
					option = standardInput.readLine();
					num= Integer.parseInt(option);
					System.out.println("nouveau x :");
					tmp = standardInput.readLine();
					newPosX = Double.parseDouble(tmp);
					System.out.println("nouveau y :");
					tmp = standardInput.readLine();
					newPosY = Double.parseDouble(tmp);
					coo = new double[2];
					coo[0] = newPosX;
					coo[1] = newPosY;
					blobList.get(num).setCoordonnee(coo);;
					controller.moveBlob(blobList.get(num));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println("écrivez ses coordonnées :");
				try {
					System.out.println("nouveau x :");
					tmp = standardInput.readLine();
					newPosX = Double.parseDouble(tmp);
					System.out.println("nouveau y :");
					tmp = standardInput.readLine();
					newPosY = Double.parseDouble(tmp);
					coo = new double[2];
					coo[0] = newPosX;
					coo[1] = newPosY;
					blob = new Blob(newPosX, newPosY, Couleur.YELLOW, 8, Forme.globule_simple, true);
					blobList.add(blob);
					controller.addBlob(blob);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
			
			
			
			System.out.println("Hello " + objectif);
		}
		
		
	}
	
	
	
	
	    
}

