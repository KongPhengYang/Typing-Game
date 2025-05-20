/**
 * Program Name: GameTypeScreenController.java
 * Program Description: This program controls the Game Type Screen
 * @author - Samuel Lillge, Bibesh Pyakurel, Ryan Wichman, Kong Yang
 * @version 1
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class GameTypeScreenController {

    @FXML
    private Button easyButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button exitButton;

    /**
     * Method Name: onEasyButtonClicked
     * Method Description: This method starts a game of easy mode.
     * @param event
     * @throws Exception
     */
    @FXML
    private void onEasyButtonClicked(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypingScreen.fxml"));
        Parent root = loader.load();
        TypingScreenController ctrl = loader.getController();
        ctrl.setDifficulty("easy");
        TypingGame.enterNewScene(new Scene(root));
    }

    /**
     * Method Name: onHardButtonClicked
     * Method Description: This method starts a game of hard mode.
     * @param event
     * @throws Exception
     */
    @FXML
    private void onHardButtonClicked(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypingScreen.fxml"));
        Parent root = loader.load();
        TypingScreenController ctrl = loader.getController();
        ctrl.setDifficulty("hard");
        TypingGame.enterNewScene(new Scene(root));
    }

    /**
     * Method Name: onExitButtonClicked
     * Method Description: This method returns the user back to the title screen.
     * @param event
     * @throws Exception
     */
    @FXML
    private void onExitButtonClicked(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TitleScreen.fxml"));
        TypingGame.enterNewScene(new Scene(root));
    }
}
