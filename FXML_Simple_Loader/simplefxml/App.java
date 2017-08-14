package simplefxml;

import javafx.stage.Stage;
import javafx.application.Application;
/**
 * Start class of test app.
 * @author denull
 *
 */
public class App extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Loader testPageLoader = new Loader(primaryStage, "view/test_button_fxml.fxml");
		testPageLoader.setResizable(false);
		testPageLoader.setUp();
		
	}
}
