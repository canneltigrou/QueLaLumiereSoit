package amak;

import application.Controller;
import business.Blob;
import business.Critere;

public class Migrant extends BlobAgent{
	
	
	private boolean isHome;
	private boolean isRiped;
	private int cptRiped;
	//private int cpt_hibernation;
	private int nbRipedIdeal = 1;
	
	
	
	public Migrant(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
		isHome = true;
		isRiped = false;
		cptRiped = 1;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	
	
	// boolean renvoyant true avec une probabilité de 'tauxMurissement' géré dans l'IHM.
	private boolean mustRipe(){
		return( Math.random() * 100 < getAmas().getEnvironment().getTauxMurissemnt());
	}
	
	
	
	private double computeCriticalityMurissement(){
		// je compte le nombre de voisins murs autour de moi.
		double cpt = 0;
		for (int i = 0; i< voisins.size(); i++){
			if( ( (Migrant)(voisins.get(i))).isRiped)
				cpt++;
		}
		return(nbRipedIdeal - cpt);
	}
	
	
	private void action_murir(){
		isRiped = true;
		cptRiped = 20;
	}
	
	@Override
	protected void onDecideAndAct() {
		nbChangements = 0;
		currentAction = Action.RESTER; // to initialise
		if (isHome){
			if(isRiped){
				if(cptRiped <= 0)
					isRiped = false;
				else
					cptRiped--;
			}
			else
				if(mustRipe())
					action_murir(); //isRiped = true;
			
			action_se_deplacer();
		}			
		else
			majAspectAgent();
		BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
		Critere most_critic = Most_critical_critere(agentNeedingHelp.getCriticite());
		
		// Si je suis sans TR/TI ne peux pas me mouvoir. Je ne peux donc pas gérer la criticité de position
		// Je vais aider le plus critique sur une autre de ses criticités.
		if (!isHome && most_critic == Critere.Stabilite_position){
			double[] tmp = agentNeedingHelp.getCriticite();
			tmp[Critere.Heterogeneite.getValue()] = 0;
			most_critic = Most_critical_critere(tmp);
		}
		
		 switch (most_critic){
		 case Isolement:
			 // too few neighboors -> criticite.ISOLEMENT > 0 -> I have procreate
			 if(criticite[Critere.Isolement.getValue()] > 0)
				 action_creer();
			 break;
				 
		 case Stabilite_etat:
			 break;
			 
		 case Stabilite_position:
			 break;
			 
		 case Heterogeneite:
			 // if >0 then it's too homogeneous. --> I change the color in a random one.
			 // else it's too heterogeneous.  -> I change my color to the most present color
			 if(criticite[Critere.Heterogeneite.getValue()] > 0)
				 action_changerCouleur();
			 else
				 action_changerCouleur(couleurEnvironnante);

			 break;
		 
		 default:
			break;		 
		 }
		super.onDecideAndAct();
	
	}
	
	
	@Override
    protected void onUpdateRender() {
    	switch(currentAction){
    	case CREER :
    		super.controller.add_blobImmaginaire(newFils);
    		break;
    		
		default:
			if(isHome)
    			super.controller.move_blobHibernant(this);
    		else
    			super.controller.move_blobMigrant(this);
    		break;
    	}

    }
	
	public void t0_to_tr(){
		isHome = false;
		getAmas().getEnvironment().t0_to_tr(this);
		controller.add_blobMigrant(this);
		controller.remove_blobHibernant(this);
	}

	public void tr_to_t0(){
		isHome = true;
		getAmas().getEnvironment().tr_to_t0(this);
		controller.add_blobHibernant(this);
		controller.remove_blobMigrant(this);
	}
	
	
	private double computeCriticalityInTo(){
		criticite[Critere.Murissement.getValue()] =	computeCriticalityMurissement();

		return criticite[Critere.Murissement.getValue()]; // TODO
	}
	
	@Override
    protected double computeCriticality() {
		if(!isHome)
			return(computeCriticalityInTideal());
		return(0);
	}

	public boolean isRiped() {
		return isRiped;
	}

	public void setRiped(boolean isRiped) {
		this.isRiped = isRiped;
	}
	
	
	
	
}
