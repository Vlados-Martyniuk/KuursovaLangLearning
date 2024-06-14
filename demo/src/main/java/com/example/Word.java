package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Word {
    private String word;
    private String translation;
    private String level;
    private int difficultyLevel;

    public Word(String word, String translation, String level, int difficultyLevel) {
        this.word = word;
        this.translation = translation;
        this.level = level;
        this.difficultyLevel = difficultyLevel;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getLevel() {
        return level;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void incrementDifficulty() {
        this.difficultyLevel++;
        updateDifficultyInDatabase();
    }

    public void decrementDifficulty() {
        if (this.difficultyLevel > 0) {
            this.difficultyLevel--;
            updateDifficultyInDatabase();
        }
    }

    private void updateDifficultyInDatabase() {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase database = mongoClient.getDatabase("language_learning");
            MongoCollection<Document> collection = database.getCollection("words");
            collection.updateOne(
                    new Document("word", this.word),
                    new Document("$set", new Document("difficultyLevel", this.difficultyLevel))
            );
        }
    }
}
