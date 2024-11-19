package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<String> words;
    private int currentWordIndex;
    private int score;
    private int round;
    private boolean gameOver;
    private boolean awaitingConfirmation;
    private long chatId;

    private static final int MAX_ROUNDS = 3;
    private static final int ROUND_TIME = 30000;  // 30 секунд

    private String currentTheme;

    public GameState(long chatId) {
        this.chatId = chatId;
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        this.awaitingConfirmation = false;
        this.currentTheme = "Технологии";  // Начинаем с этой темы по умолчанию
        initializeWords();
    }

    // Инициализация слов для выбранной темы
    private void initializeWords() {
        words.clear();
        switch (currentTheme) {
            case "Технологии":
                words.add("Программирование");
                words.add("Алгоритм");
                words.add("Синтаксис");
                words.add("Компилятор");
                break;
            case "Животные":
                words.add("Собака");
                words.add("Кошка");
                words.add("Птица");
                words.add("Лев");
                break;
            case "Еда":
                words.add("Яблоко");
                words.add("Пицца");
                words.add("Хлеб");
                words.add("Молоко");
                break;
        }
    }

    // Выбор темы
    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();
    }

    // Начать игру
    public String startGame() {
        if (currentWordIndex < words.size()) {
            return words.get(currentWordIndex);
        } else {
            return "Все слова закончились!";
        }
    }

    // Следующее слово
    public String nextWord(boolean isCorrect) {
        if (isCorrect) {
            score++;
        }
        currentWordIndex++;
        if (currentWordIndex >= words.size()) {
            gameOver = true;
            return "Игра завершена! Ваши очки: " + score;
        }
        return words.get(currentWordIndex);
    }

    // Пропуск слова
    public String skipWord() {
        currentWordIndex++;
        if (currentWordIndex >= words.size()) {
            return "Все слова закончились!";
        }
        return words.get(currentWordIndex);
    }

    // Подтверждение продолжения игры
    public void confirmContinue() {
        awaitingConfirmation = false;
        round++;
        if (round > MAX_ROUNDS) {
            gameOver = true;
        }
    }

    // Сброс игры
    public void resetGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        this.awaitingConfirmation = false;
        initializeWords();
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
