package amak;

import java.util.ArrayList;

import business.Blob;
import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.Scheduling;

public class Tideal extends Environment {
	private ArrayList<BlobAgent> agents;
	private int radius = 5;
	
	
	public Tideal() {
		super(Scheduling.DEFAULT);
	}
	
	
	@Override
	public void onInitialization(){
		agents = new ArrayList<BlobAgent>();
		//this.setRealBlobs(realBlobs);
	}

	

	public ArrayList<BlobAgent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<BlobAgent> agent) {
		this.agents = agent;
	}
	
	
	
	public void generateNeighbours(Blob subject){
		int j;
		
		subject.clearVoisin();
		for (j = 0; j < agents.size(); j++ )
		{
			if(subject.isVoisin(agents.get(j).getBlob(), radius))
				subject.addVoisin(agents.get(j).getBlob());
		}
		
	}


	public void addAgent(BlobAgent agent) {
		agents.add(agent);	
	}
	
	

}
