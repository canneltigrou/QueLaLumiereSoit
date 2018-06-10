package amak;

import application.Controller;
import business.Blob;
import business.Critere;

public class Immaginaire extends BlobAgent{

	public Immaginaire(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
		
	}
	
	@Override
    protected double computeCriticality() {
		return(computeCriticalityInTideal());
	}
	
	@Override
    protected void onUpdateRender() {
    	switch(currentAction){
    	case SE_DEPLACER :
    		controller.move_blobImmaginaire(this);
    		break;
    	case CREER :
    		controller.add_blobImmaginaire(newFils);
    		break;
    	case SE_SUICIDER :
			controller.remove_blobImmaginaire(this);
			break;
		default:
			break;
    	}

    }
	
	
	
	@Override
	protected void onDecideAndAct() {
		 currentAction = Action.RESTER; // to initialise
	     BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
		 Critere most_critic = Most_critical_critere(agentNeedingHelp);
		 
		 switch (most_critic){
		 case Isolement:
			 // too many neighboors -> criticite.ISOLEMENT<0 -> I have to kill myself
			 if(criticite[Critere.Isolement.getValue()] < 0)
				 action_se_suicider();
			 else
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
	
	
	

}
