package application;
	
import amak.AmasThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/ControlPanelProto2.fxml"));
			Parent root = loader.load();
			
			Scene scene = new Scene(root,1300,700);
     
			/*
			Controller runnerActivityController = loader.getController();
			 AmasThread tAmas = new AmasThread(runnerActivityController);
			 runnerActivityController.settAmas(tAmas);
			 tAmas.start();
			 */
			

			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


	
}
