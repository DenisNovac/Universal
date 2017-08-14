/*
 * Denis Novac
 * 2017
 * 
 * Following program is designed for easy fxml-files load.
 * 
 */

package simplefxml;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class - is the main idea of program.
 * It takes path to fxml-template and set it up on stage.
 * 
 * @version 1.0 14 oct 2017
 * @author Denis Novac
 */

public class Loader {
	
	private AnchorPane root;
	private Boolean isResizable=false;
	private FXMLLoader loader;
	private Scene scene;
	private Stage stage;
	private String fxmlpath;
	
	public Loader (Stage stage, String fxmlpath){
		this.stage=stage; // takes stage link to input template on it
		this.fxmlpath=fxmlpath; // path to fxml file
	}

	// install template on stage
	public void setUp() {
		loader = new FXMLLoader(); 
		loader.setLocation(Loader.class.getResource(fxmlpath));
		root = new AnchorPane();
		
		try {
			root = loader.load();
		} catch (IOException e) {
			System.out.println("Loading layout error!");
		}
		
		scene = new Scene(root);
		// paths to your own stylesheets
		//scene.getStylesheets().add("application/view/arra.css");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.setResizable(isResizable);
		stage.show();
	}
	
	public void setResizable(Boolean isResizable) {
		this.isResizable=isResizable;
	}
}
