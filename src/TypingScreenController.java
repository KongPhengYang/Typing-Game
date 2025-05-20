/**
 * Program Name: TypingScreenController.java
 * Program Description: Controls the Typing Screen, with in‐class persistent
 * background & input‐box color settings adjusted via the Settings button.
 * @author - Samuel Lillge, Bibesh Pyakurel, Ryan Wichman, Kong Yang
 * @version 1
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TypingScreenController {
    private static Color bgColor = Color.WHITE;
    private static Color inputBgColor = Color.web("#fafafa");
    private static Color textColor = Color.web("#2c3e50");

    @FXML private BorderPane rootPane;
    @FXML private Text paragraphText;
    @FXML private TextArea inputArea;
    @FXML private Label resultLabel;
    @FXML private Button tryAgainButton;
    @FXML private Button mainMenuButton;
    @FXML private Button settingsButton;

    private final List<String> paragraphs = new ArrayList<>();
    private String currentParagraph;
    private long startTime;
    private boolean typingStarted = false;
    private boolean submitted = false;

    @FXML
    public void initialize() {
        // Apply whatever colors are currently saved
        applyColorScheme();

        // Setup listener for timing & Enter submission
        setupEnterKeyListener();
    }

    public void setDifficulty(String difficulty) {
        String path = difficulty.equalsIgnoreCase("easy") ? "src/easy.txt" : "src/hard.txt";
        loadSentencesFromFile(path);
        generateNewParagraph();
        applyColorScheme();  // re-apply after new paragraph
    }

    private void loadSentencesFromFile(String filePath) {
        paragraphs.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) paragraphs.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNewParagraph() {
        if (paragraphs.isEmpty()) return;
        currentParagraph = paragraphs.get(new Random().nextInt(paragraphs.size()));
        paragraphText.setText(currentParagraph);
        inputArea.clear();
        resultLabel.setText("");
        typingStarted = false;
        submitted     = false;
        applyInputBoxStyle(); // ensure box style matches current inputBgColor/textColor
    }

    private void setupEnterKeyListener() {
        inputArea.setOnKeyPressed(evt -> {
            if (!typingStarted && evt.getCode().isLetterKey()) {
                startTime = System.currentTimeMillis();
                typingStarted = true;
            }
            if (evt.getCode() == KeyCode.ENTER) {
                evt.consume();
                checkInput();
            }
        });
    }

    @FXML
    public void checkInput() {
        if (submitted) return;
        submitted = true;
        inputArea.setEditable(false);

        String userInput = inputArea.getText().trim();
        long endTime = System.currentTimeMillis();

        double minutes = (endTime - startTime) / 60000.0;
        int typed = userInput.length();
        int words = typed / 5;
        int errs = countErrors(userInput, currentParagraph);

        double gross = words / minutes;
        double net = (words - errs) / minutes;
        double accuracy = (typed > 0) ? ((typed - errs) / (double)typed * 100) : 0.0;

        appendScoreToFile(gross, net, accuracy);

        String status = userInput.equals(currentParagraph) ? "Correct!" : "Incorrect!";
        resultLabel.setText(String.format(
            "%s\nGross WPM: %.2f\nNet WPM:   %.2f\nAccuracy: %.2f%%",
            status, gross, net, accuracy
        ));
    }

    private int countErrors(String typed, String original) {
        int errs = 0, length = Math.min(typed.length(), original.length());
        for (int i = 0; i < length; i++)
            if (typed.charAt(i) != original.charAt(i)) errs++;
        return errs + Math.abs(typed.length() - original.length());
    }

    private void appendScoreToFile(double gross, double net, double acc) {
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = String.format("%s,%.2f,%.2f,%.2f%%%n", timestamp, gross, net, acc);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onTryAgainClicked() {
        generateNewParagraph();
        inputArea.setEditable(true);
    }

    @FXML
    private void onMainMenuClicked() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TitleScreen.fxml"));
        Scene scene = new Scene(root);
        TypingGame.enterNewScene(scene);
    }
    @FXML
    private void onSettingsClicked() {
        ColorPicker picker = new ColorPicker(bgColor);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choose Background Color");
        ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(picker);
        dialog.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ok) {
            Color base = picker.getValue();
            bgColor = base;
            inputBgColor = base.interpolate(Color.WHITE, 0.3);
            textColor = (base.getBrightness() < 0.5) ? Color.WHITE : Color.BLACK;
            applyColorScheme();
        }
    }

    private void applyColorScheme() {
        // background
        rootPane.setStyle("-fx-background-color: " + toHex(bgColor) + ";");
        // paragraph text
        paragraphText.setFill(textColor);
        // input box styling
        applyInputBoxStyle();
        // result label text color
        resultLabel.setTextFill(textColor);
    }

    private void applyInputBoxStyle() {
        inputArea.setStyle(String.join(";", 
            "-fx-control-inner-background: " + toHex(inputBgColor),
            "-fx-text-fill: " + toHex(textColor),
            "-fx-border-color: #cccccc",
            "-fx-border-radius: 8",
            "-fx-background-radius: 8",
            "-fx-font-size: 14px"
        ));
        inputArea.setEffect(new DropShadow(6, 0, 2, Color.gray(0.5)));
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X",
            (int)(c.getRed()   * 255),
            (int)(c.getGreen() * 255),
            (int)(c.getBlue()  * 255)
        );
    }
}
