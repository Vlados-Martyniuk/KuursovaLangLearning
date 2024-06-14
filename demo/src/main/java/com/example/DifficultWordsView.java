package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class DifficultWordsView {
    private Stage stage;
    private WordDatabase wordDatabase;

    public DifficultWordsView(Stage stage, WordDatabase wordDatabase) {
        this.stage = stage;
        this.wordDatabase = wordDatabase;
    }

    public void show() {
        stage.setTitle("Робота над помилками");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        List<Word> difficultWords = wordDatabase.getDifficultWords();

        if (difficultWords.isEmpty()) {
            showAlert("Вітаю!", "Ви відповіди на всі тести правильно");
            return;
        }

        for (Word word : difficultWords) {
            vbox.getChildren().add(new Label("Слово: " + word.getWord() + " - Переклад: " + word.getTranslation() ));
        }

        Button startReviewButton = new Button("Почати тест");
        startReviewButton.setOnAction(e -> {
            stage.close();
            new TestSession(stage, wordDatabase, difficultWords).show();
        });

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> new MainMenu(stage, wordDatabase).show());

        vbox.getChildren().addAll(startReviewButton, backButton);

        Scene scene = new Scene(scrollPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

      private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
