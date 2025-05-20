/**
 * Program Name: TitleScreenController.java
 * Program Description: This program controls how the title screen operates.
 * @author - Samuel Lillge, Bibesh Pyakurel, Ryan Wichman, Kong Yang
 * @version 1
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TitleScreenController {
    @FXML private Button startGameButton;
    @FXML private Button viewScoreboardButton;
    @FXML
    private void onStartGameButtonClicked(ActionEvent event) throws Exception {
        Parent fxmlFile = FXMLLoader.load(
            getClass().getResource("GameTypeScreen.fxml")
        );
        Scene scene = new Scene(fxmlFile);
        TypingGame.enterNewScene(scene);
    }
    @FXML
    private void onViewScoreboardClicked() {
        File file = new File("scores.txt");
        if (!file.exists()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "No scores recorded yet!");
            alert.setTitle("Scoreboard");
            alert.setHeaderText(null);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextArea ta = new TextArea(content.toString());
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setPrefRowCount(15);
        ta.setPrefColumnCount(40);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scoreboard");
        alert.setHeaderText("Previous Runs (timestamp, grossWPM, netWPM, accuracy)");
        alert.getDialogPane().setContent(ta);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
