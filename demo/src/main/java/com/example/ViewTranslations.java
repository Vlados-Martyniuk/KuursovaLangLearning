package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ViewTranslations {
    private Stage stage;
    private WordDatabase wordDatabase;
    private String level;

    public ViewTranslations(Stage stage, WordDatabase wordDatabase, String level) {
        this.stage = stage;
        this.wordDatabase = wordDatabase;
        this.level = level;
    }

    public void show() {
        stage.setTitle("Словник - Рівень " + level);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        List<Word> words = wordDatabase.getWordsByLevel(level);
        for (Word word : words) {
            vbox.getChildren().add(new Label("Слово: " + word.getWord() + " - Переклад: " + word.getTranslation()));
        }

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> new LevelSelectionMenu(stage, wordDatabase, "view").show());

        vbox.getChildren().add(backButton);

        Scene scene = new Scene(scrollPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}
