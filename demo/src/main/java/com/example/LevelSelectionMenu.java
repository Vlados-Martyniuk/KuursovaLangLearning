package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelSelectionMenu {
    private Stage stage;
    private WordDatabase wordDatabase;
    private String action;

    public LevelSelectionMenu(Stage stage, WordDatabase wordDatabase, String action) {
        this.stage = stage;
        this.wordDatabase = wordDatabase;
        this.action = action;
    }

    public void show() {
        stage.setTitle("Вибір рівня");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label instructionLabel = new Label("Виберіть рівень слів");
        Button levelA1Button = new Button("A1");
        Button levelA2Button = new Button("A2");
        Button levelB1Button = new Button("B1");
        Button levelB2Button = new Button("B2");
        Button levelC1Button = new Button("C1");
        Button levelC2Button = new Button("C2");
        Button backButton = new Button("Назад");

        levelA1Button.setOnAction(e -> handleLevelSelection("A1"));
        levelA2Button.setOnAction(e -> handleLevelSelection("A2"));
        levelB1Button.setOnAction(e -> handleLevelSelection("B1"));
        levelB2Button.setOnAction(e -> handleLevelSelection("B2"));
        levelC1Button.setOnAction(e -> handleLevelSelection("C1"));
        levelC2Button.setOnAction(e -> handleLevelSelection("C2"));
        backButton.setOnAction(e -> new MainMenu(stage, wordDatabase).show());

        vbox.getChildren().addAll(instructionLabel, levelA1Button, levelA2Button, levelB1Button, levelB2Button, levelC1Button, levelC2Button, backButton);

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void handleLevelSelection(String level) {
        if (action.equals("study")) {
            new StudySession(stage, wordDatabase, level).show();
        } else if (action.equals("view")) {
            new ViewTranslations(stage, wordDatabase, level).show();
        }
    }
}
