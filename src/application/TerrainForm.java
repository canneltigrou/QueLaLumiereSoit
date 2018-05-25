package application;

import java.util.HashMap;
import java.util.Map;

import business.Blob;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TerrainForm extends Parent {

	// ArrayList<Blob> blobList;
	// HashMap<Blob, BlobForm> blobList;
	private Map<Blob, BlobForm> blobList;

	public TerrainForm() {
		// blobList = new ArrayList<Blob>();
		blobList = new HashMap<Blob, BlobForm>();

		Rectangle fond_Tideal = new Rectangle();
		fond_Tideal.setWidth(400);
		fond_Tideal.setHeight(300);
		fond_Tideal.setArcWidth(30);
		fond_Tideal.setArcHeight(30);
		fond_Tideal.setFill(Color.BLACK);

		this.setTranslateX(0);// on positionne le groupe plutôt que le rectangle
		this.setTranslateY(0);

		this.getChildren().add(fond_Tideal);// on ajoute le rectangle au groupe

	}

	public void add_blob(Blob b) {

		Platform.runLater(new Runnable() {
			public void run() {
				BlobForm bf = new BlobForm(b);
				blobList.put(b, bf);
				getChildren().add(bf);
			}
		});

	}

	public void remove_blob(Blob b) {
		Platform.runLater(new Runnable() {
			public void run() {
				BlobForm bf = blobList.get(b);
				getChildren().remove(bf);
				blobList.remove(b);
			}
		});

	}

	public void move_blob(Blob b) {
		Platform.runLater(new Runnable() {
			public void run() {
				BlobForm bf = blobList.get(b);
				bf.changeBlob(b);
			}
		});
	}
}

