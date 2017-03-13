package application.view.controllers;

import java.io.File;
import application.Main;
import application.logic.Parcer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SaveSceneViewController {
	@FXML
	Button saveButton, pathButton, cancelButton;
	@FXML
	TextField passField,confirmField;
	@FXML
	TextField pathField;
	@FXML
	Label infoLabel;
	
	@FXML
	public void initialize(){
		DBViewController.lines.remove(Main.LNULL); //убираем пустую отладочную линию, чтобы не копились
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file .npdb or create new");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Database file", "*.npdb")); //выбираем разрешение. НЕ ЗАБУДЬ ЗВЁЗДОЧКУ ПИДР
		
		pathButton.setOnAction( (e) -> {
			String path = ( fileChooser.showSaveDialog(Main.functionalStage) ).getPath();
	        pathField.setText(path);
		});
		
		saveButton.setOnAction( (e) -> {
			if (! Parcer.passwordOk(passField.getText())){
				infoLabel.setText("password must conatin only latin, numbers and underline");
				return;
			}
			
			if (passField.getText().length()<6){
				infoLabel.setText("password must be 6 or more letters");
				return;
			}
			if ( ! passField.getText().equals( confirmField.getText() )  ) {
				infoLabel.setText("password and confirm isnt equals");
				return;
			}
			if (pathField.getText().length()<3){
				infoLabel.setText("Enter the path to save your database");
				return;
			}
			
			if (  !Parcer.pathOk( (pathField.getText()) )  ) {
				infoLabel.setText("Incorrect path: Input some .npdb file name!\n");
				return;
			}
			String answer=Parcer.save(DBViewController.lines, pathField.getText(),passField.getText());
			infoLabel.setText(answer);
		});
		
		
		cancelButton.setOnAction( (e)->{
			Main.functionalStage.close();
		});
		
	}
}
