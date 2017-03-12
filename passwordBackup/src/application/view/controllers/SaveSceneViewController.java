package application.view.controllers;

import java.io.File;
import application.Main;
import application.logic.Parcer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

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
		DirectoryChooser dirChooser = new DirectoryChooser(); //объект, получающий путь из проводника
		dirChooser.setTitle("Select directory for result");//заголовок окошка выбора дирректории
		
		pathButton.setOnAction( (e) -> {
			dirChooser.setInitialDirectory(new File("."));
	        String path = ( dirChooser.showDialog(Main.functionalStage) ).getPath();
	        pathField.setText(path+"\\mydb.npdb");
	        infoLabel.setText("Don't forget to check db name in path: mydb.npdb");
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
			String answer=Parcer.save(DBViewController.lines, pathField.getText());
			infoLabel.setText(answer);
		});
		
		
		cancelButton.setOnAction( (e)->{
			Main.functionalStage.close();
		});
		
	}
}
