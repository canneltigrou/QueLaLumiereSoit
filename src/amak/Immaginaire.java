package amak;

import application.Controller;
import application.ExceptionHandler;
import business.Blob;
import business.Critere;
import fr.irit.smac.amak.tools.Log;

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
		if (tps + 1000 < System.currentTimeMillis() && !(currentAction.equals(Action.CREER) || currentAction.equals(Action.SE_SUICIDER))) {
			return;
		}
		tps = System.currentTimeMillis();
		try {
			switch(currentAction){
	    	case SE_DEPLACER :
	    		
	            {
	        		controller.move_blobImmaginaire(this);
	            }
	    		break;
	    	case CREER :
	    		
	            {
	    			controller.add_blobImmaginaire(newFils);
	            }
	    		break;
	    	case SE_SUICIDER :
	    		
	            {
	    			controller.remove_blobImmaginaire(this);
	            }
				break;
			default:
				break;
	    	}
    	} catch(Exception e)
		{
			ExceptionHandler eh = new ExceptionHandler();
			eh.showError(e);
		}

    }
	
	@Override
	protected void action_se_deplacer(){
		double[] tmp = getAmas().getEnvironment().nouvellesCoordonnees(this, Math.random() * 0.7, pastDirection);
		blob.setCoordonnee(tmp);
		currentAction = Action.SE_DEPLACER;
		
		directGeneral[0] = 0.6 * pastDirection[0] + 0.4 * directGeneral[0];
		directGeneral[1] = 0.6 * pastDirection[1] + 0.4 * directGeneral[1];
	}
	
	
	@Override
	protected void onDecideAndAct() {

		Log.debug("quela", "begin da immag");
		try {
			{ 
				int debuguig = 0;
				Log.debug("quela", "imag decide "+(debuguig++));
				nbChangements = 0;
				Log.debug("quela", "imag decide "+(debuguig++));
				 majAspectAgent();		 
					Log.debug("quela", "imag decide "+(debuguig++));
				 currentAction = Action.RESTER; // to initialise
					Log.debug("quela", "imag decide "+(debuguig++));
			     BlobAgent agentNeedingHelp = super.getMoreCriticalAgent();
					Log.debug("quela", "imag decide "+(debuguig++));
				 Critere most_critic = Most_critical_critere(agentNeedingHelp.getCriticite());
					Log.debug("quela", "imag decide "+(debuguig++));
				 //System.out.println("criticite de stabilit� de position = " + criticite[Critere.Stabilite_position.getValue()]);
				 //System.out.println("le plus critique est : " + most_critic.toString());
				 
				 switch (most_critic){
				 case Isolement:
						Log.debug("quela", "imag decide iso");
					 // too many neighboors -> criticite.ISOLEMENT<0 -> I have to kill myself
					 if(criticite[Critere.Isolement.getValue()] < 0)
						 action_se_suicider();
					 else
						 action_creer();
					 break;
						 
				 case Stabilite_etat:

						Log.debug("quela", "imag decide stabeta");
					 
					 
					 break;
					 
				 case Stabilite_position:
						Log.debug("quela", "imag decide stab pos");
					 action_se_deplacer();
					 break;
					 
				 case Heterogeneite:
						Log.debug("quela", "imag decide eheteroge");
					 //System.out.println(" \t avec : " + criticite[Critere.Heterogeneite.getValue()]);
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
			}

			Log.debug("quela", "imag decide before super decide and act");
			super.onDecideAndAct();

			Log.debug("quela", "imag decide before catch");
		} catch(Exception e)
		{
			Log.debug("quela", "imag decide catch");
			ExceptionHandler eh = new ExceptionHandler();
			eh.showError(e);
		}
		Log.debug("quela", "end da immag");
	}
	
	
	

}
