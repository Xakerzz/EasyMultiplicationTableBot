package com.example.EasyMultiplicationTableBot;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final DataStorage instance = new DataStorage();
    private final Map<Long, Integer> correctAnswers = new HashMap<>();

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return instance;
    }

    public void setCorrectAnswer(long chatId, int correctAnswer) {
        correctAnswers.put(chatId, correctAnswer);
    }

    public Integer getCorrectAnswer(long chatId) {
        return correctAnswers.get(chatId);
    }

    public void removeCorrectAnswer(long chatId) {
        correctAnswers.remove(chatId);
    }
}
