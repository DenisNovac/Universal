package application.view.controllers;
import application.Main;
import application.logic.Line;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
//эта же форма используется для изменения уже созданных записей
public class AddSceneViewController{
	@FXML
	private Button saveButton, cancelButton;
	@FXML
	private TextField nameField, passwordField,descriptionField;

	private static Line inChanging; //переменная, содержащая линию, которую мы меняем
	
	@FXML
	public void initialize(){
		
		DBViewController.lines.remove(Main.LNULL); //убираем пустую отладочную линию, чтобы не копились
		
		if (inChanging!=null){ //если у нас есть линия на замену - извлекаем из неё данные
			nameField.setText(inChanging.getName());
			passwordField.setText(inChanging.getPass());
			descriptionField.setText(inChanging.getDesc());
		}
		
		saveButton.setOnAction( (e)->{
			if (inChanging!=null){ //если есть линия на замену - вставляем изменения в неё
				inChanging.setProp(nameField.getText(), passwordField.getText(), descriptionField.getText());
				DBViewController.lines.add(Main.LNULL); //добавляем пустую линию для обновления ObservableList
				inChanging=null;//зануляем линию
			} else { //если нет линии на замену - пишем новую и сразу добавляем
				DBViewController.lines.add(
					new Line(/**/nameField.getText(), passwordField.getText(), descriptionField.getText()/**/) 
				);
			}
			Main.functionalStage.close();
			
		});
		
		cancelButton.setOnAction( (e)->{ //простая отмена всего
			inChanging=null;
			Main.functionalStage.close();
		});
	}
	
	
	protected static void setLine(Line l){ //сюда передаём линию, которую желаем изменить
		inChanging=l;
	}
}
