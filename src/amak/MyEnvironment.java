package amak;

import java.util.ArrayList;

import application.Controller;
import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.Scheduling;

public class MyEnvironment extends Environment {
	private ArrayList<BlobAgent> agents;
	private ArrayList<Migrant> hibernants;
	private int radius = 4; // radius utilisé pour les différents sliders
	/* possede les valeurs des differents sliders*/
	private int isolement;
	private int stabilite_etat;
	private int stabilite_position;
	private int heterogeneite;
	private int distanceRepresentation;
	private int tauxMurissemnt;
	public double rayonTerrain = 12.5; // exprimé en metres
	
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
		if( (subject instanceof Migrant) && ((Migrant)subject).isHome())
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

	//Cette fonction est appelée par l'agent après avoir fait le changement.
	// il ne reste donc ici qu'à mettre à jour les listes dans l'environnement
	public void addAgent(BlobAgent agent) {
		agents.add(agent);
	}

	//Cette fonction est appelée par l'agent après avoir fait le changement.
	// il ne reste donc ici qu'à mettre à jour les listes dans l'environnement
	public void removeAgent(BlobAgent agent) {
		agents.remove(agent);
	}
	
	//Cette fonction est appelée par l'agent après avoir fait le changement.
	// il ne reste donc ici qu'à mettre à jour les listes dans l'environnement
	public void addMigrant(Migrant migrant) {
		hibernants.add(migrant);
	}	

	//Cette fonction est appelée par l'agent après avoir fait le changement.
	// il ne reste donc ici qu'à mettre à jour les listes dans l'environnement
	public void t0_to_tr(Migrant migrant){
		hibernants.remove(migrant);
		agents.add(migrant);
	}
	
	//Cette fonction est appelée par l'agent après avoir fait le changement.
	// il ne reste donc ici qu'à mettre à jour les listes dans l'environnement
	public void tr_to_t0(Migrant migrant){
		agents.remove(migrant);
		hibernants.add(migrant);
	}
	
	
	// indique si la coordonnée entrée en paramètre est valide, ie si elle n'est pas hors terrain.
	// returne true if ok. 
	//Ici il s'agit de To : valide si compris dans un carré de 100*100
	private boolean isValideInTo(double[] coo){
		if (0 < coo[0] && coo[0] < 100 && 0 < coo[1] && coo[1] < 100)
			return true;
		return false;
	}
	
	// indique si la coordonnée entrée en paramètre est valide, ie si elle n'est pas hors terrain.
	// returne true if ok. 
	//Ici il s'agit de Tr ou Ti : valide si compris dans un cercle de rayon RayonTerrain et de centre (RayonTerrain;RayonTerrain)
	private boolean isValideInTi(double[] coo){
		if ((coo[0] - rayonTerrain)*(coo[0] - rayonTerrain) + (coo[1] - rayonTerrain) * (coo[1] - rayonTerrain) <= rayonTerrain * rayonTerrain)
			return true;
		return false;
	}
	
	
	
	
	
	// fonction qui à partir de coordonnées initiales, propose de nouvelles coordonnées à un certain rayon (le pas).
		public double[] nouvellesCoordonnees(BlobAgent agent, double pas){
			double[] res = new double[2];
			double[] coordonnee = agent.getBlob().getCoordonnee();
			
			// Je dois prendre en compte les bordures. Je décide de ne pas compliquer les calculs : 
			// Je mets le tout dans une boucle, et je relance l'aléatoire si je suis en dehors du terrain.
			boolean isOK = false;
			while(!isOK){
			
				//normalement : coo[0] - pas < res[0] < coo[0] + pas
				res[0] = (Math.random() * 2 * pas) - pas + coordonnee[0];
			
				// j'utilise l'equation d'un cercle de rayon pas.
				// (res[0] - coo[0])² + (res[1] - coo[1])² = pas²
				// à partir de res[0], j'ai 2 solutions possible pour res[1]. 1 positive, une négative. choisissons aléatoirement.
				double sign = 1;
				if (Math.random() < 0.5)
					sign = -1;
				res[1] =  coordonnee[1] + (sign * Math.sqrt(pas * pas + (res[0] - coordonnee[0]) * (res[0] - coordonnee[0]) ));
				if( (agent instanceof Migrant) && ((Migrant)agent).isHome())
					isOK = isValideInTo(res);
				else
					isOK = isValideInTi(res);
			}	
			return res;
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
