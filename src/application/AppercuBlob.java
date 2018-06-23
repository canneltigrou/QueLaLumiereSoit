package application;

import amak.BlobAgent;
import javafx.application.Platform;
import javafx.scene.Parent;

public class AppercuBlob extends Parent{

	BlobForm blobForm;
	BlobAgent agent;
	double[] coordonnee;
	
	public AppercuBlob(){
		coordonnee = new double[2];
		coordonnee[0] = 50;
		coordonnee[1] = 50;
	}
	
	
	
	public void add_blob(BlobAgent agent) {
		this.agent = agent;
		
		Platform.runLater(new Runnable() {
			public void run() {
				blobForm = new BlobForm(agent.getBlob(), coordonnee, 200);
				getChildren().add(blobForm);
				
			}
		});

	}

	public void remove_blob(BlobAgent agent) {
		Platform.runLater(new Runnable() {
			public void run() {
				getChildren().remove(blobForm);
			}
		});

	}

	public void move_blob(BlobAgent agent) {
		Platform.runLater(new Runnable() {
			public void run() {
				blobForm.changeBlob(agent.getBlob(), coordonnee, 200);
			}
		});
	}
	
}
