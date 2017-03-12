package application.view.controllers;

import application.Loader;
import application.Main;
import application.logic.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DBViewController{
	static protected ObservableList<Line> lines;
	@FXML
	private MenuItem menuSave, menuClose, menuAbout;
	@FXML
	private TableView<Line> mainTable;
	@FXML
	private TableColumn<Line,String> tableName, tablePassword,tableDesc, numberColumn;
	@FXML
	private Button addButton, removeButton, editButton;
	protected Loader addSceneLoader, saveSceneLoader; //загрузчики сцен
	
	@FXML
	public void initialize(){
		
		saveSceneLoader = new Loader(Main.functionalStage,"view/SaveSceneView.fxml",false);
		addSceneLoader = new Loader(Main.functionalStage,"view/AddSceneView.fxml",false);
		
		
		
		
        // устанавливаем тип и значение которое должно хранится в колонке
        tableName.setCellValueFactory(new PropertyValueFactory<Line,String>("name"));
        tablePassword.setCellValueFactory(new PropertyValueFactory<Line,String>("pass"));
        tableDesc.setCellValueFactory(new PropertyValueFactory<Line,String>("desc"));
        mainTable.setItems(lines);
        
        addButton.setOnAction((e)->{
        	addSceneLoader.setUp();
        });
        
        mainTable.setOnMouseClicked((e)->{ //открывает по двойному клику окошко edit
        	if (e.getClickCount()>1){ //считаем количество кликов
	        	editEvent();
        	}
        });
        
        removeButton.setOnAction((e)->{
        	Line l = mainTable.getSelectionModel().getSelectedItem();
        	lines.remove(l);
        });
        
        editButton.setOnAction( (e) -> {
        	editEvent();
        });
        
        menuClose.setOnAction( (e) -> {
        	Main.loginViewLoader.setUp();
        	Main.dbStage.close();
        });
        
        menuSave.setOnAction( (e) -> {
        	saveSceneLoader.setUp();
        });
        
	}//end of initialize()
	
	private void editEvent(){ //метод для изменения наполнения строчки
		Line l = mainTable.getSelectionModel().getSelectedItem();
		//этот if нужен для избежание ошибки, при которой пользователь пытается изменить
		//строку LNULL, нужную лишь для отладки листа
		if (l.equals(Main.LNULL)) {
			lines.remove(Main.LNULL);
			addSceneLoader.setUp();
		}else {
	    	AddSceneViewController.setLine(l);
	    	addSceneLoader.setUp();
		}
	}//end of editEvent()
}
