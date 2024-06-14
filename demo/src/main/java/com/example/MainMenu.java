package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private Stage primaryStage;
    private WordDatabase wordDatabase;

    public MainMenu(Stage primaryStage, WordDatabase wordDatabase) {
        this.primaryStage = primaryStage;
        this.wordDatabase = wordDatabase;
    }

    public void show() {
        primaryStage.setTitle("Вивчення мови");

        Button studyNewWordsButton = new Button("Вивчення нових слів");
        studyNewWordsButton.setOnAction(e -> new LevelSelectionMenu(primaryStage, wordDatabase, "study").show());

        Button viewTranslationsButton = new Button("Словник");
        viewTranslationsButton.setOnAction(e -> new LevelSelectionMenu(primaryStage, wordDatabase, "view").show());

        Button viewDifficultWordsButton = new Button("Робота над помилками");
        viewDifficultWordsButton.setOnAction(e -> new DifficultWordsView(primaryStage, wordDatabase).show());


        VBox vbox = new VBox(10, studyNewWordsButton, viewTranslationsButton, viewDifficultWordsButton);
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
