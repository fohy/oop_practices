package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<String> words;
    private int currentWordIndex;
    private int score;
    private boolean gameOver;
    private String currentTheme;
    private long startTime; // Время начала игры
    private int round; // Номер текущего раунда
    private static final int MAX_ROUNDS = 3; // Максимальное количество раундов
    private static final long TIME_LIMIT = 60000; // 1 минута в миллисекундах

    public GameState() {
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        this.currentTheme = "Технологии";
        this.round = 1;  // Начинаем с первого раунда
        initializeWords();
        this.startTime = System.currentTimeMillis(); // Засекаем время начала игры
    }

    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    private void initializeWords() {
        words.clear();
        switch (currentTheme) {
            case "Технологии":
                words.add("Программирование");
                words.add("Алгоритм");
                words.add("Дебаг");
                words.add("Синтаксис");
                words.add("Компилятор");
                break;
            case "Животные":
                words.add("Кошка");
                words.add("Птица");
                words.add("Лев");
                break;
            case "Еда":
                words.add("Пицца");
                words.add("Бургер");
                words.add("Паста");
                break;
        }
    }

    public String startGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        this.round = 1;  // Начинаем с первого раунда
        this.startTime = System.currentTimeMillis(); // Обновляем время старта игры
        return words.get(currentWordIndex);
    }

    public String nextWord(boolean isCorrect) {
        if (gameOver) {
            return "Игра завершена! Ваши очки: " + score;
        }

        // Проверка времени
        if (System.currentTimeMillis() - startTime > TIME_LIMIT) {
            gameOver = true;
            return "Время вышло! Ваши очки: " + score;
        }

        if (isCorrect) {
            score++;
        }

        currentWordIndex++;
        if (currentWordIndex >= words.size()) {
            // Переход к следующему раунду
            startNewRound();
            return "Раунд " + round + " завершен! Нажмите 'Следующее', чтобы начать следующий раунд.";
        }

        return words.get(currentWordIndex);
    }

    public String skipWord() {
        if (gameOver) {
            return "Игра завершена! Ваши очки: " + score;
        }
        currentWordIndex++;
        if (currentWordIndex >= words.size()) {
            // Переход к следующему раунду
            startNewRound();
            return "Раунд " + round + " завершен! Нажмите 'Следующее', чтобы начать следующий раунд.";
        }

        return words.get(currentWordIndex);
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        initializeWords();
    }

    public void endGame() {
        this.gameOver = true;
        this.currentWordIndex = words.size();
    }

    public void startNewRound() {
        if (round < MAX_ROUNDS) {
            round++;
            this.currentWordIndex = 0;  // Начинаем новый раунд с первого слова
            this.startTime = System.currentTimeMillis(); // Обновляем время для нового раунда
            this.gameOver = false;
        } else {
            this.gameOver = true;  // Заканчиваем игру после 3 раундов
        }
    }

    public int getRound() {
        return round;
    }
}
