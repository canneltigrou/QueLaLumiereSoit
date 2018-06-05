package amak;

import application.Controller;
import business.Blob;
import business.Critere;

public class Migrant extends BlobAgent{
	
	
	private boolean isHome;

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
		BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
		 Critere most_critic = Most_critical_critere(agentNeedingHelp);
		 
		 switch (most_critic){
		 case Isolement:
			 // trop de voisins -> criticite.ISOLEMENT<0 -> je me suicide
			 if(criticite[Critere.Heterogeneite.getValue()] > 0)
				 action_creer();
			 break;
				 
		 case Stabilite_etat:
			 break;
			 
		 case Stabilite_position:
			 break;
			 
		 case Heterogeneite:
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
    		controller.add_blobImmaginaire(newFils);
    		break;
		default:
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
	
	
	
	
}
