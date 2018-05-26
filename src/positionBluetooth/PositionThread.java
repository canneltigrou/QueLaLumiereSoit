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


// permet de lancer un thread pour établir la connexion bluetooth
// et récolter les données en temps réel
// pour les transmettres aux environnements correspondants

// REMARQUE : les methodes console_xxx sont vouées à disparaître
// les méthodes : addBlob, moveBlob et removeBlob doivent rester.


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
	
	public void removeBlob(Blob b){
		controller.remove_blobReel(b);
		tAmas.remove_blob(b);
	}
	
	public void moveBlob(Blob b){
		controller.move_blobReel(b);
		tAmas.move_blob(b);
		
	}
	
	private void console_afficher_blobs()
	{
		for(int i = 0 ; i < blobList.size(); i++){
			
			Couleur couleurG1 = blobList.get(i).getGlobules_couleurs().get(0);
			Forme forme = blobList.get(i).getForme();
			double[] pos = blobList.get(i).getCoordonnee();
			System.out.println("" + i + " : " + couleurG1 + " " + forme + " " + pos[0] + " ; " + pos[1]);			
		}	
	}
	
	private void console_afficher_formes_enum()
	{
		System.out.println("FORMES POSSIBLES :");
		Forme[] listeFormes = Forme.values();
		for(int i = 0 ; i < listeFormes.length; i++){
			System.out.println("" + i + " : " + listeFormes[i]);			
		}	
	}
	
	private void console_afficher_couleurs_enum(){
		System.out.println("COULEURS POSSIBLES :");
		Couleur[] listeCouleurs = Couleur.values();
		for(int i = 0 ; i < listeCouleurs.length; i++){
			System.out.println("" + i + " : " + listeCouleurs[i]);			
		}	
	}
	
	
	private void console_ajouterBlob(BufferedReader standardInput){
		
		Blob blob = new Blob(0,0, Couleur.BLUE, 1, Forme.carre, true);
		blob = console_formulaireBlob(standardInput, blob);
		blobList.add(blob);
		addBlob(blob);
		
	}
	
	private Blob console_formulaireBlob(BufferedReader standardInput, Blob blob){
		System.out.println("nouveau x :");
		String tmp;
		// position :
		try {
			tmp = standardInput.readLine();
			double newPosX = Double.parseDouble(tmp);
			System.out.println("nouveau y :");
			tmp = standardInput.readLine();
			double newPosY = Double.parseDouble(tmp);
			double[] coo = new double[2];
			coo[0] = newPosX;
			coo[1] = newPosY;
			blob.setCoordonnee(coo);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Forme :
		console_afficher_formes_enum();
		try {
			System.out.println("choisissez une forme :");
			tmp = standardInput.readLine();
			
			Forme[] listeFormes = Forme.values();
			int indice = Integer.parseInt(tmp);
			System.out.println(listeFormes[indice]);
			blob.setForme(listeFormes[indice]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Couleur :
				console_afficher_couleurs_enum();
				try {
					System.out.println("choisissez une couleur ('n' pour ne pas changer de couleur):");
					tmp = standardInput.readLine();
					
					if(!tmp.equals("n")){
						Couleur[] listeCouleurs = Couleur.values();
						int indice = Integer.parseInt(tmp);
						ArrayList<Couleur> listeGlobuleCouleur = new ArrayList<Couleur>();
						for(int i = 0 ; i<blob.getGlobules_position().size() ; i++)
							listeGlobuleCouleur.add(listeCouleurs[indice]);
						blob.setGlobules_couleurs(listeGlobuleCouleur);
						System.out.println("coucou");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return(blob);
	}
	
	private void console_modifierBlob(BufferedReader standardInput){
		console_afficher_blobs();
		System.out.println("choisissez un blob :");
		Blob blob;
		try {
			String option = standardInput.readLine();
			int num = Integer.parseInt(option);
			blob = blobList.get(num);
			blobList.set(num, console_formulaireBlob(standardInput, blob));
			moveBlob(blobList.get(num));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void console_suprimerBlob(BufferedReader standardInput){
		console_afficher_blobs();
		System.out.println("choisissez un blob :");
		Blob blob;
		try {
			String option = standardInput.readLine();
			int num = Integer.parseInt(option);
			blob = blobList.get(num);
			removeBlob(blob);
			blobList.remove(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void testByConsole(){
		/*
		Blob b1 = new Blob(50,20, Couleur.BLUE, 10, Forme.simple, true);
		Blob b2 = new Blob(128, 50, Couleur.RED, 10, Forme.carre, true);
		Blob b3 = new Blob(250,100, Couleur.YELLOW, 10, Forme.simple, true);
		Blob b4 = new Blob(60,100, Couleur.BLUE, 10, Forme.carre, false);

		addBlob(b1);
		addBlob(b2);
		addBlob(b3);
		addBlob(b4);
		*/
		
		
		BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
		
		String objectif = "";
		
		while (true){
			System.out.print(" 1 - ajouter un Blob \n 2 - modifier un Blob \n 3 - supprimer un Blob :\n");
			try {
				objectif = standardInput.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(objectif.equals("2"))
				console_modifierBlob(standardInput);
			else
				if(objectif.equals("3"))
					console_suprimerBlob(standardInput);
				else
					console_ajouterBlob(standardInput);		
		}
		
		
	}
	    
}

