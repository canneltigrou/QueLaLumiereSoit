package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import amak.AmasThread;
import amak.BlobAgent;
import amak.Immaginaire;
import amak.Migrant;
import business.Blob;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

public class Controller implements Initializable{
	
	private ArrayList<Migrant> blobHibernants;
	private ArrayList<Migrant> blobActifs;
	
	
    @FXML
    private TableView<?> testtableview;

    @FXML
    private Slider Sdiso;

    @FXML
    private Slider sHeterogeneite;

    @FXML
    private Label AffichDiso;

    @FXML
    private Slider sStabilitePosition;

    @FXML
    private Slider sStabiliteEtat;

    @FXML
    private AnchorPane panelTideal;

    @FXML
    private AnchorPane panelTreel;
    
    @FXML
    private AnchorPane panelToriginel;
    
    @FXML
    private Slider STauxMurissement;
    
    @FXML
    private Slider SDistanceRealite;
    
    @FXML
    private Slider sRadiusVoisins;
    
    @FXML
    private Button buttonSortirBlob;
    
    @FXML
    private Button buttonChangerBlob;
    
    @FXML
    private Button buttonOKNbBlobs;
    
    @FXML
    private TextField textFieldNbBlobs;
    
    
    

    private TerrainForm tideal;  
    private TerrainForm treel;    
    private ToForm toriginel;
    
    private AmasThread tAmas;
    
    DoubleProperty diso = new SimpleDoubleProperty(0);
    DoubleProperty hetero = new SimpleDoubleProperty(0);
    DoubleProperty stabPos = new SimpleDoubleProperty(0);
    DoubleProperty stabEtat = new SimpleDoubleProperty(0);
    DoubleProperty tauxMur = new SimpleDoubleProperty(0);

    DoubleProperty radiusVoisins = new SimpleDoubleProperty(0);
    DoubleProperty distanceRealite = new SimpleDoubleProperty(0);


	@FXML
    void clicIso(MouseEvent event) {
    	
    	System.out.println(" Valeur Degrés d'isolement : " + diso.get() + "\n");
    	tAmas.setIsolement(diso.getValue().intValue());
    }
	
	@FXML
    void clicHeter(MouseEvent event) {
    	System.out.println(" Valeur Degrés d'heterogénéité : " + hetero.get() + "\n");
    	tAmas.setHeterogeneite(hetero.getValue().intValue());
    }
	
	@FXML
    void clicStabPos(MouseEvent event) {
    	System.out.println(" Valeur de la stabilité de la position du voisinage : " + stabPos.get() + "\n");
    	tAmas.setStabilitePosition(stabPos.getValue().intValue());
    }
	
	@FXML
    void clicEtatVois(MouseEvent event) {
    	System.out.println(" Valeur de la stabilité de l'etat du voisinage : " + stabEtat.get() + "\n");
    	tAmas.setStabiliteEtat(stabEtat.getValue().intValue());
    }
	
	@FXML
    void clicTauxMur(MouseEvent event) {
    	System.out.println(" Valeur de la stabilité du taux de murriseement : " + tauxMur.get() + "\n");
    	tAmas.setTauxMurissement(tauxMur.getValue().intValue());
    }
	
	@FXML
    void clicDistRea(MouseEvent event) {
    	System.out.println(" Valeur de la distance à  la réalité : " + distanceRealite.get() + "\n");
    	tAmas.setDistanceRealite(distanceRealite.getValue().intValue());
    }
	
    @FXML
    void clicRadiusVoisins(MouseEvent event) {
    	System.out.println(" Valeur du radius des voisins : " + radiusVoisins.get() + "\n");
    }
	
    @FXML
    void onClicButtonSortirBlob(MouseEvent event) {
    }
	
    @FXML
    void onClicButtonModifierBlob(MouseEvent event) {
    	
    }

    @FXML
    void onClicButtonOKnbBlobs(MouseEvent event) {
		System.out.println(textFieldNbBlobs.textProperty().getValue());
		int nbBlobs = Integer.parseInt(textFieldNbBlobs.textProperty().getValue());
		
		
    	tAmas = new AmasThread(this, nbBlobs);
		tAmas.start();
		 
		buttonOKNbBlobs.setDisable(true);
    }
    
    
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		diso.bind(Sdiso.valueProperty());
		hetero.bind(sHeterogeneite.valueProperty());
		stabPos.bind(sStabilitePosition.valueProperty());
		stabEtat.bind(sStabiliteEtat.valueProperty());
		tauxMur.bind(STauxMurissement.valueProperty());
		radiusVoisins.bind(sRadiusVoisins.valueProperty());
		distanceRealite.bind(SDistanceRealite.valueProperty());
	    
		
		tideal = new TerrainForm();
		panelTideal.getChildren().add(tideal);
		
		treel = new TerrainForm();
		panelTreel.getChildren().add(treel);
		
		toriginel = new ToForm();
		panelToriginel.getChildren().add(toriginel);
		
		// J'initialise à 2 chaque sliders.
		Sdiso.setValue(2);
		sHeterogeneite.setValue(2);
		sStabiliteEtat.setValue(2);
		sStabilitePosition.setValue(2);
		STauxMurissement.setValue(3);
		
		
	}
	
	public void add_blobImmaginaire(Immaginaire b){
		tideal.add_blob(b.getBlob());
	}
	
	public void add_blobMigrant(Migrant b){
		tideal.add_blob(b.getBlob());
		treel.add_blob(b.getBlob());
	}
	
	public void add_blobHibernant(Migrant b){
		toriginel.add_blob(b.getBlob(), false);
	}
	
	public void remove_blobImmaginaire(Immaginaire b){
		tideal.remove_blob(b.getBlob());
	}
	
	public void remove_blobMigrant(Migrant b){
		tideal.remove_blob(b.getBlob());
		treel.remove_blob(b.getBlob());
	}
	
	
	public void remove_blobHibernant(BlobAgent b){
		toriginel.remove_blob(b.getBlob());

	}
	
	public void move_blobImmaginaire(Immaginaire b){
		tideal.move_blob(b.getBlob());
	}
	
	public void move_blobMigrant(Migrant b){
		tideal.move_blob(b.getBlob());
		treel.move_blob(b.getBlob());
	}
	
	public void move_blobHibernant(Migrant b){
		toriginel.move_blob(b.getBlob(), b.isRiped());
	}
	
	public int getIsolement(){
		return(Sdiso.valueProperty().intValue());
	}
	
	public int getHeterogenite(){
		return(sHeterogeneite.valueProperty().intValue());
	}
	
	public int getStabiliteHeterogeneite(){
		return(sStabiliteEtat.valueProperty().intValue());
	}
	
	public int getStabilitePosition(){
		return(sStabilitePosition.valueProperty().intValue());
	}
	
	public int getDistanceRealite(){
		return( distanceRealite.getValue().intValue());
	}
	
	public double getTauxMurissement(){
		return(tauxMur.getValue());
	}


	public AmasThread gettAmas() {
		return tAmas;
	}


	public void settAmas(AmasThread tAmas) {
		this.tAmas = tAmas;
	}
	
	
	
	/* ***************************************************************************** *
	 *  ******** 		METHODES DE POSITION_THREAD			************************ *
	 *	**************************************************************************** */
	
	public void sortirBlob(Migrant b){
		Blob tmp = b.getBlob();
		double[] coo = new double[2];
		coo[0] = Math.random() * 25;
		boolean isOk = false;
		while(!isOk){
			coo[1] = Math.random() * 25;
			if ((coo[0] - 12.5)*(coo[0] - 12.5) + (coo[1] - 12.5) * (coo[1] - 12.5) <= 12.5 * 12.5)
				isOk = true;
		}
		
		tmp.setCoordonnee(coo);
		b.setBlob(tmp);
		tAmas.t0_to_tr(b);
		blobHibernants.remove(b);
		blobActifs.add(b);	
	}
	
	
	public void rentrerBlob(Migrant b){
		tAmas.tr_to_t0(b);
		blobHibernants.add(b);
		blobActifs.remove(b);
	}
	
	public void moveBlob(Migrant b, double[] coo){
		tAmas.move_blob(b, coo);	
	}
	
	
	
	
	
	
	
	

}

/*
il suffit de construire une BufferedImage (format d'image standard de Java) et de la passer ÃƒÂƒÃ‚Â  un ImagePlus ou ImageProcessor (format ImageJ).

BufferedImage monimage = new BufferedImage(width, height, BufferedImage.LeTypeVoulu) ;

//Puis en fonction du type de l'image
return new BinaryProcessor(new ByteProcessor((java.awt.Image)source)) ;
return new ByteProcessor(source) ;
return new ShortProcessor(source) ;
*/