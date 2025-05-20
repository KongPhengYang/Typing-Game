import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TypingGame extends Application {
	private static Stage stage;
	
	/**
	 * Method Name: start
	 * Method Description: This method starts the program.
	 * @param mainStage - the stage
	 * @author - Samuel Lillge, Bibesh Pyakurel, Ryan Wichman, Kong Yang
	 * @throws Exception
	 */
	@Override
	public void start(Stage mainStage) throws Exception {
		//Load and use TitleScreen.fxml
		Parent fxmlFile = FXMLLoader.load(getClass().getResource("TitleScreen.fxml"));
    	Scene startScene = new Scene(fxmlFile);
    	//Title the stage
    	mainStage.setTitle("Typing Game");
    	
    	//Use the starting scene
    	mainStage.setScene(startScene);
    	
    	//Show stage
    	mainStage.show();
    	
    	//Set stage to first stage
    	stage = mainStage;
	}
	
	/**
	 * Method Name: main
	 * Method Description: This method launches/starts the application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Method Name: enterNewScene
	 * Method Description: This method changes the scene to the inputed screen.
	 * @param scene - the scene
	 */
	public static void enterNewScene(Scene scene) {
		stage.setScene(scene);
	}
}