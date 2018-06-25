package application;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {

            Stage menu = new Stage();
            
            Button b_test = new Button("Test");
            Button b_exp = new Button("Expérience");
            
            primaryStage.setTitle("Comme un blob");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
			FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/ControlPanelProto2.fxml"));
			Parent root;
			root = loader.load();
			Scene scene = new Scene(root,1300,700);

            b_test.setOnAction((event) -> {
    			menu.close();
    			primaryStage.setScene(scene);
    			primaryStage.show();
            });
            
            b_exp.setOnAction((event) -> {
    			menu.close();
    			primaryStage.setScene(scene);
    			primaryStage.show();
    			Controller control = loader.getController();
    			control.setexperience(true);
    			control.initTO();
    			control.initTI();
    		});
            
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(b_test, b_exp);
            
            menu.setTitle("Comme un blob - Menu");
			menu.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
            menu.setScene(new Scene(vbox, 200, 200));
            menu.show();
            
			
     
			/*
			Controller runnerActivityController = loader.getController();
			 AmasThread tAmas = new AmasThread(runnerActivityController);
			 runnerActivityController.settAmas(tAmas);
			 tAmas.start();
			 */
			


			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


	
}
