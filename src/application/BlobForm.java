package application;

import java.util.ArrayList;
import java.util.Set;

import business.Blob;
import business.Couleur;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

// https://openclassrooms.com/courses/les-applications-web-avec-javafx/les-noeuds-graphiques


public class BlobForm extends Parent{
	private Blob blob;
	Circle fond_blob;
	ArrayList<Circle> globules;
	
	public BlobForm(Blob b){
		//blobList = new HashMap<Blob, BlobForm>();
		globules = new ArrayList<Circle>();
		Rectangle fond_Tideal = new Rectangle();
		fond_Tideal.setWidth(16);
		fond_Tideal.setHeight(16);
		fond_Tideal.setFill(Color.TRANSPARENT);

		this.setTranslateX(b.getCoordonnee()[0]);// on positionne le groupe plutôt que le rectangle
		this.setTranslateY(b.getCoordonnee()[1]);

		this.getChildren().add(fond_Tideal);// on ajoute le rectangle au groupe
		
		ArrayList<double[]> positionGlobule = b.getGlobules_position();
		ArrayList<Couleur> couleurGlobule = b.getGlobules_couleurs();
		globules.clear();
		for(int i = 0 ; i < positionGlobule.size(); i++)
		{
			fond_blob = new Circle(positionGlobule.get(i)[0] ,positionGlobule.get(i)[1] ,2, couleurGlobule.get(i).getColor(couleurGlobule.get(i)) ); 
	        globules.add(fond_blob);
	        this.getChildren().add(fond_blob);//ajout du rectangle de fond			
		}
		

    }
	
	public void changeBlob(Blob b){
		this.blob = b;
		//new Circle(5, Color.YELLOW); // TODO ne pas faire un new. juste changer la couleur
		this.setTranslateX(blob.getCoordonnee()[0]);//positionnement du blob
        this.setTranslateY(blob.getCoordonnee()[1]);
		for(int i = 0 ; i < globules.size(); i++){
			this.getChildren().remove(globules.get(i));
		}
        
        ArrayList<double[]> positionGlobule = b.getGlobules_position();
		ArrayList<Couleur> couleurGlobule = b.getGlobules_couleurs();
		globules.clear();
		for(int i = 0 ; i < positionGlobule.size(); i++)
		{
			fond_blob = new Circle(positionGlobule.get(i)[0] ,positionGlobule.get(i)[1] ,2, couleurGlobule.get(i).getColor(couleurGlobule.get(i)) ); 
	        
	        this.getChildren().add(fond_blob);//ajout du rectangle de fond			
		}
	}
	
}
