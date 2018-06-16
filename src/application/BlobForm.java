package application;

import java.util.ArrayList;
import business.Blob;
import business.Couleur;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

// https://openclassrooms.com/courses/les-applications-web-avec-javafx/les-noeuds-graphiques


public class BlobForm extends Parent{
	private Blob blob;
	Circle fond_blob;
	ArrayList<Circle> globules;
	Rectangle selection = null;
	
	
	public BlobForm(Blob b){
		//blobList = new HashMap<Blob, BlobForm>();
		globules = new ArrayList<Circle>();
		selection = new Rectangle(13, 13);
		selection.setFill(Color.TRANSPARENT);
		selection.setStrokeType(StrokeType.CENTERED);
		selection.setStroke(Color.TRANSPARENT);
		this.getChildren().add(selection);

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

		selection = new Rectangle(13, 13);
		selection.setFill(Color.TRANSPARENT);
		selection.setStrokeType(StrokeType.CENTERED);
		selection.setStroke(Color.TRANSPARENT);
		this.getChildren().add(selection);
		
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
	
	public BlobForm(Blob b, double[] coo, Color couleur){
		globules = new ArrayList<Circle>();
		this.setTranslateX(coo[0]);// on positionne le groupe 
		this.setTranslateY(coo[1]);
		ArrayList<double[]> positionGlobule = b.getGlobules_position();
		globules.clear();
		for(int i = 0 ; i < positionGlobule.size(); i++)
		{
			fond_blob = new Circle(positionGlobule.get(i)[0] ,positionGlobule.get(i)[1] ,2, couleur ); 
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
	
	// cette fonction est appelée si le globule n'est pas mûr et doit être repésenté blanc.
	// la couleur blanche est donc donnée en paramètre.
	public void changeBlob(Blob b, double[] coo, Color couleur){
		this.blob = b;
		this.setTranslateX(coo[0]);//positionnement du blob
        this.setTranslateY(coo[1]);
		for(int i = 0 ; i < globules.size(); i++){
			this.getChildren().remove(globules.get(i));
		}
        
        ArrayList<double[]> positionGlobule = b.getGlobules_position();
		globules.clear();
		for(int i = 0 ; i < positionGlobule.size(); i++)
		{
			fond_blob = new Circle(positionGlobule.get(i)[0] ,positionGlobule.get(i)[1] ,2, couleur ); 
	        
	        this.getChildren().add(fond_blob);//ajout du globule	
		}
	}
	
	
	public void showSelection(){
		
		assert(selection != null);
		//selection.setStrokeType(StrokeType.CENTERED);
		selection.setStroke(Color.ANTIQUEWHITE);
		
		//selection.setFill(Color.WHITE);
	}
	
	public void deleteSelection(){
		selection.setStroke(Color.TRANSPARENT);
		//selection.setFill(Color.TRANSPARENT);
	}
	
	
	
	
}
