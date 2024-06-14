package com.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class TestSession {
    private Stage stage;
    private WordDatabase wordDatabase;
    private List<Word> words;
    private List<TestCard> testCards;

    public TestSession(Stage stage, WordDatabase wordDatabase, List<Word> words) {
        this.stage = stage;
        this.wordDatabase = wordDatabase;
        this.words = new ArrayList<>(words);
        this.testCards = new ArrayList<>();
    }

    public void show() {
        stage.setTitle("Тест");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        Collections.shuffle(words);
        Set<String> usedWords = new HashSet<>();

        for (Word word : words) {
            if (!usedWords.contains(word.getWord())) {
                TestCard testCard = new TestCard(word);
                testCards.add(testCard);
                vbox.getChildren().add(testCard.getCard());
                usedWords.add(word.getWord());
            }
        }

        Button checkButton = new Button("Перевірити відповіді");
        checkButton.setOnAction(e -> checkAnswers());

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> new MainMenu(stage, wordDatabase).show());

        vbox.getChildren().addAll(checkButton, backButton);

        Scene scene = new Scene(scrollPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void checkAnswers() {
        int correctAnswers = 0;
        for (TestCard testCard : testCards) {
            if (testCard.isCorrect()) {
                correctAnswers++;
                testCard.getWord().decrementDifficulty();
            } else {
                testCard.getWord().incrementDifficulty();
            }
            wordDatabase.updateWord(testCard.getWord());
        }
        showResult(correctAnswers, testCards.size());
    }

    private void showResult(int correctAnswers, int totalQuestions) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText(null);
        alert.setContentText("Ви відповили правильно " + correctAnswers + " з " + totalQuestions + " питань");
        alert.showAndWait();

        new StudySession(stage, wordDatabase, words.get(0).getLevel()).show();
    }

    private class TestCard {
        private Word word;
        private ToggleGroup toggleGroup;
        private VBox card;

        public TestCard(Word word) {
            this.word = word;
            this.card = createTestCard();
        }

        public Word getWord() {
            return word;
        }

        public VBox getCard() {
            return card;
        }

        public boolean isCorrect() {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            return selectedRadioButton != null && selectedRadioButton.getText().equals(word.getTranslation());
        }

        private VBox createTestCard() {
            VBox card = new VBox(5);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

            Label wordLabel = new Label(word.getWord());
            card.getChildren().add(wordLabel);

            toggleGroup = new ToggleGroup();

            List<String> translations = new ArrayList<>();
            translations.add(word.getTranslation());

            List<String> allTranslations = wordDatabase.getWords().stream()
                    .map(Word::getTranslation)
                    .distinct()
                    .collect(Collectors.toList());

            Collections.shuffle(allTranslations);

            for (String translation : allTranslations) {
                if (translations.size() < 4 && !translations.contains(translation)) {
                    translations.add(translation);
                }
            }

            Collections.shuffle(translations);

            for (String translation : translations) {
                RadioButton radioButton = new RadioButton(translation);
                radioButton.setToggleGroup(toggleGroup);
                card.getChildren().add(radioButton);
            }

            return card;
        }
    }
}
