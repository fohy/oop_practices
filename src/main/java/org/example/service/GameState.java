package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<String> words;
    private int currentWordIndex;
    private int score;
    private int round;
    private boolean gameOver;
    private String currentTheme;



    public GameState() {
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        this.currentTheme = "Технологии";
        initializeWords();
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
                words.add("Яблоко");
                words.add("Пицца");
                words.add("Хлеб");
                words.add("Молоко");
                break;
            default:
                words.add("Ошибка");
                break;
        }
    }

    public String startGame() {
        if (currentWordIndex < words.size()) {
            return words.get(currentWordIndex);
        } else {
            return "Все слова закончились!";
        }
    }

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

    public String skipWord() {
        currentWordIndex++;  // Переходим к следующему слову
        if (currentWordIndex >= words.size()) {
            return "Все слова закончились!";
        }
        return words.get(currentWordIndex);
    }

    public void resetGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        initializeWords();
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getRound() {
        return round;
    }
}
