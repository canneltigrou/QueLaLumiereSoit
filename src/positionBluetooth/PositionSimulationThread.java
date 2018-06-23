package positionBluetooth;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import amak.AmasThread;
import amak.Migrant;
import javafx.application.Platform;

public class PositionSimulationThread extends Thread{

	private ArrayList<Migrant> blobActifs;
	private AmasThread tAmas;
	private Timer timer;
	public boolean is_interrupt;
	
	@SuppressWarnings("unchecked")
	public PositionSimulationThread(AmasThread tAmas, ArrayList<Migrant> migrants){
		super();
		
		this.tAmas = tAmas;
		blobActifs = new ArrayList<>();
		blobActifs = (ArrayList<Migrant>) migrants.clone();		
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
	public void demarrer() {
		
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
	
	

	public void add_blob(Migrant blob) {
		Platform.runLater(new Runnable() {
			public void run() {
				blobActifs.add(blob);
			}
		});	
	}
	
	public void remove_blob(Migrant blob) {
		Platform.runLater(new Runnable() {
			public void run() {
				blobActifs.remove(blob);
			}
		});	
	}
	
	
	
	
	
	
}
