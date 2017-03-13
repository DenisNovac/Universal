package application.view.controllers;

import java.io.File;

import application.Main;
import application.logic.Parcer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class LoginViewController{
	@FXML
	private Button pathButton, openButton, newButton;
	@FXML
	private Label infoLabel;
	@FXML
	private TextField pathField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private FileChooser fileChooser;
	
	@FXML
	public void initialize(){
		fileChooser = new FileChooser(); //объект, получающий файл из проводника
		fileChooser.setTitle("Open Resource File");//заголовок окошко выбора файла
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Database file", "*.npdb")); //выбираем разрешение. НЕ ЗАБУДЬ ЗВЁЗДОЧКУ ПИДР
		
		pathButton.setOnAction((e)->{
			fileChooser.setInitialDirectory(new File(".")); //открывает папку, откуда запущена программа
			File file = fileChooser.showOpenDialog(Main.primaryStage);
			String path = file.getPath();
			if (Parcer.pathOk(path)) pathField.setText(path); //автоматически вставляет его в строчку пути
			else infoLabel.setText("Incorrect file: Input some .npdb file!");
		});
		
		openButton.setOnAction((e)->{
			openDB();
		});
		
		passwordField.setOnAction((e)->{
			openDB();
		});
		
		newButton.setOnAction( (e)->{
			DBViewController.lines=FXCollections.observableArrayList();
			Main.dbViewLoader.setUp();
			Main.primaryStage.close();
		});
		
	}
	
	private void openDB(){
		String error=""; //хотим показывать пользователю все его ошибки сразу
		if (pathField.getText().length()<1){
			error+="You need to input path to your DB!\n";
		}else if (!Parcer.pathOk(pathField.getText())){
			error+="Incorrect file: Input some .npdb file!\n";
		}
		if ((passwordField.getLength())<6){
			error+="You need to input the password! (6 or more letters)\n";
		}
		if (error.length()>1) {
			infoLabel.setText(error);
			return;
		}
		
		else {//ошибок нет
			DBViewController.lines=Parcer.openDB(pathField.getText(),passwordField.getText());
			
			if (DBViewController.lines==null) {
				infoLabel.setText("Not correct password");
				return;
			}
			
			Main.dbViewLoader.setUp();
			Main.primaryStage.close();
		}
		
	}
	
	
	 
	
}
