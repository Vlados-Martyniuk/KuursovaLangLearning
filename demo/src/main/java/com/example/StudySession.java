package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class StudySession {
    private Stage stage;
    private WordDatabase wordDatabase;
    private String level;
    private List<Word> wordsToStudy;

    public StudySession(Stage stage, WordDatabase wordDatabase, String level) {
        this.stage = stage;
        this.wordDatabase = wordDatabase;
        this.level = level;
        updateWordsToStudy();
    }

    private void updateWordsToStudy() {
        List<Word> allWords = wordDatabase.getWordsByLevel(level);
        wordsToStudy = allWords.subList(0, Math.min(15, allWords.size()));
    }

    public void show() {
        stage.setTitle("Вивчення нових слів - Рівень " + level);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        for (Word word : wordsToStudy) {
            vbox.getChildren().add(new Label("Слово: " + word.getWord() + " - Переклад: " + word.getTranslation()));
        }

        Button startTestButton = new Button("Розпочати тест");
        startTestButton.setOnAction(e -> {
            wordDatabase.markAsStudied(wordsToStudy); 
            new TestSession(stage, wordDatabase, wordsToStudy).show();
        });

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> new LevelSelectionMenu(stage, wordDatabase, "study").show());

        vbox.getChildren().addAll(startTestButton, backButton);

        Scene scene = new Scene(scrollPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}
