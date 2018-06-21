package positionBluetooth;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import amak.AmasThread;
import amak.Migrant;

public class PositionSimulationThread extends Thread{

	private ArrayList<Migrant> blobActifs;
	private AmasThread tAmas;
	private Timer timer;
	public boolean is_interrupt;
	
	public PositionSimulationThread(AmasThread tAmas, ArrayList<Migrant> migrants){
		super();
		
		this.tAmas = tAmas;
		blobActifs = migrants;
		
	}
	
	public void moveBlob(Migrant b, double[] coo){
		tAmas.move_blob(b, coo);	
	}
	
	
	private void bouger_blobs() {
		double[] coo;
		
		for(Migrant blob : blobActifs) {
			coo = blob.getAmas().getEnvironment().nouvellesCoordonnees(blob, 1, blob.getPastDirection());
			moveBlob(blob, coo);
		}
	}
	
	public void interruption() {
        timer.cancel();
		is_interrupt = true;
		
    }
	
	//@Override
	public void demarrer(ArrayList<Migrant> migrants) {
		blobActifs = migrants;
		timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
			    // Your database code here
				  bouger_blobs();
			  }
			}, 1*1000, 1*1000);
		
		System.out.println("hey !");
		is_interrupt = false;
	}
	
	
	
	@Override
	public void run(){
		
		is_interrupt = true;
		
	}

	public boolean isIs_interrupt() {
		return is_interrupt;
	}

	public void setIs_interrupt(boolean is_interrupt) {
		this.is_interrupt = is_interrupt;
	}   
	
	

	
	
	
	
	
	
	
	
}
