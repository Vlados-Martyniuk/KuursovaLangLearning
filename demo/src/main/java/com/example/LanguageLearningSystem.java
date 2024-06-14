package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class LanguageLearningSystem extends Application {
    private WordDatabase wordDatabase;

    @Override
    public void start(Stage primaryStage) {
        wordDatabase = new WordDatabase();
        new MainMenu(primaryStage, wordDatabase).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
