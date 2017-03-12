package application;
	
import application.logic.Line;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage, dbStage, functionalStage;//все фреймы
	public static Loader loginViewLoader,dbViewLoader; //загрузчики сцен
	public static Line LNULL; //константная пустая линия, нужна для обновления ObservableList
	
	
	@Override
	public void start(Stage pS) {
		LNULL = new Line();
		//объявляем все фреймы, чтобы потом ссылаться на них из разных частей приложения
		primaryStage=pS;
		primaryStage.setTitle("Novac Password");
		dbStage=new Stage();
		dbStage.setTitle("Novac Password Database");
		functionalStage=new Stage();
		functionalStage.setTitle("Novac Password Function");
		
		functionalStage.initModality(Modality.WINDOW_MODAL);
		functionalStage.initOwner(dbStage);
		
		//показываем окошко входа
		loginViewLoader = new Loader(primaryStage, "view/LoginView.fxml", false);
		dbViewLoader = new Loader(dbStage, "view/DBView.fxml",true);
		loginViewLoader.setUp();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
