package application;

import java.util.ArrayList;
import business.Blob;
import business.Couleur;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;

// https://openclassrooms.com/courses/les-applications-web-avec-javafx/les-noeuds-graphiques


public class BlobForm extends Parent{
	private Blob blob;
	Circle fond_blob;
	ArrayList<Circle> globules;
	
	public BlobForm(Blob b){
		//blobList = new HashMap<Blob, BlobForm>();
		globules = new ArrayList<Circle>();


		this.setTranslateX(b.getCoordonnee()[0]);// on positionne le groupe 
		this.setTranslateY(b.getCoordonnee()[1]);
		
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
	
	public BlobForm(Blob b, double[] coo){
		//blobList = new HashMap<Blob, BlobForm>();
		globules = new ArrayList<Circle>();


		this.setTranslateX(coo[0]);// on positionne le groupe 
		this.setTranslateY(coo[1]);
		
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
	        
	        this.getChildren().add(fond_blob);//ajout du globule	
		}
	}
	public void changeBlob(Blob b, double[] coo){
		this.blob = b;
		this.setTranslateX(coo[0]);//positionnement du blob
        this.setTranslateY(coo[1]);
		for(int i = 0 ; i < globules.size(); i++){
			this.getChildren().remove(globules.get(i));
		}
        
        ArrayList<double[]> positionGlobule = b.getGlobules_position();
		ArrayList<Couleur> couleurGlobule = b.getGlobules_couleurs();
		globules.clear();
		for(int i = 0 ; i < positionGlobule.size(); i++)
		{
			fond_blob = new Circle(positionGlobule.get(i)[0] ,positionGlobule.get(i)[1] ,2, couleurGlobule.get(i).getColor(couleurGlobule.get(i)) ); 
	        
	        this.getChildren().add(fond_blob);//ajout du globule	
		}
	}
	
}
