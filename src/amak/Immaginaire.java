package amak;

import application.Controller;
import business.Blob;
import business.Critere;

public class Immaginaire extends BlobAgent{

	public Immaginaire(MyAMAS amas, Blob b, Controller controller) {
		super(amas, b, controller);
		// TODO Auto-generated constructor stub
		
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
	     BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
		 Critere most_critic = Most_critical_critere(agentNeedingHelp);
		 
		 switch (most_critic){
		 case Isolement:
			 // trop de voisins -> criticite.ISOLEMENT<0 -> je me suicide
			 if(criticite[Critere.Heterogeneite.getValue()] < 0)
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
