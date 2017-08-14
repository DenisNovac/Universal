package simplefxml.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.*;

/**
 * Controller for test view page. It is specified in fxml file.
 * FXML annotations sets the fx:id from fxml template.
 * initialize() is the main method of javafx controllers
 * @author denull
 *
 */
public class TestViewController {
	@FXML
	Label testLabel = new Label(); 
	@FXML
	Button testButton = new Button();
	
	@FXML
	public void initialize() {
		//testButton.setOnAction(new ButtonListener());
		
		testButton.setOnAction((e) -> {
			testLabel.setText("The button was pressed");
		});
	}
	
	// you can use lamda expression instead of private class
	private class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			testLabel.setText("The button was pressed");
		}	
	} // end of button listener private class
	
	
}
