package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordDatabase {
    private MongoCollection<Document> collection;
    private List<String> studiedWords = new ArrayList<>();

    public WordDatabase() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("language_learning");
        collection = database.getCollection("words");
        loadStudiedWords();
    }

    private void loadStudiedWords() {
        // Load studied words from the database
        for (Document doc : collection.find(new Document("studied", true))) {
            studiedWords.add(doc.getString("word"));
        }
    }

    public List<Word> getWordsByLevel(String level) {
        List<Word> words = new ArrayList<>();
        for (Document doc : collection.find(new Document("level", level))) {
            if (!studiedWords.contains(doc.getString("word"))) {
                words.add(new Word(
                        doc.getString("word"),
                        doc.getString("translation"),
                        doc.getString("level"),
                        doc.getInteger("difficultyLevel")
                ));
            }
        }
        return words;
    }

    public List<Word> getDifficultWords() {
        List<Word> words = new ArrayList<>();
        for (Document doc : collection.find(new Document("difficultyLevel", new Document("$gt", 0)))) {
            words.add(new Word(
                    doc.getString("word"),
                    doc.getString("translation"),
                    doc.getString("level"),
                    doc.getInteger("difficultyLevel")
            ));
        }
        return words.stream().sorted((w1, w2) -> Integer.compare(w2.getDifficultyLevel(), w1.getDifficultyLevel())).collect(Collectors.toList());
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (Document doc : collection.find()) {
            words.add(new Word(
                    doc.getString("word"),
                    doc.getString("translation"),
                    doc.getString("level"),
                    doc.getInteger("difficultyLevel")
            ));
        }
        return words;
    }

    public void updateWord(Word word) {
        collection.updateOne(
                new Document("word", word.getWord()),
                new Document("$set", new Document("difficultyLevel", word.getDifficultyLevel()))
        );
    }

    public void markAsStudied(List<Word> words) {
        for (Word word : words) {
            collection.updateOne(
                    new Document("word", word.getWord()),
                    new Document("$set", new Document("studied", true))
            );
            studiedWords.add(word.getWord());
        }
    }
}
