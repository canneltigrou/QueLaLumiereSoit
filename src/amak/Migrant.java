package amak;

import application.Controller;
import business.Blob;
import business.Critere;

public class Migrant extends BlobAgent{
	
	
	private boolean isHome;
	private boolean isRiped;
	//private int cpt_hibernation;
	
	public Migrant(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
		setHome(true);
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	
	
	@Override
	protected void onDecideAndAct() {
		currentAction = Action.RESTER; // to initialise
		if (isHome)
			action_se_deplacer();
		else
			majAspectAgent();
		BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
		Critere most_critic = Most_critical_critere(agentNeedingHelp);
		 
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
