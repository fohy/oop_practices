package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<String> words;  // Список слов для игры
    private int currentWordIndex;  // Индекс текущего слова
    private int score;  // Количество очков
    private boolean gameOver;  // Флаг завершения игры
    private String currentTheme;  // Тема текущей игры

    private static final int MAX_ROUNDS = 3;  // Максимальное количество раундов

    public GameState() {
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        this.currentTheme = "Технологии";  // По умолчанию начинаем с темы "Технологии"
        initializeWords();  // Инициализируем слова для текущей темы
    }

    // Метод для выбора темы
    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();  // Переинициализируем список слов для выбранной темы
    }

    // Получение текущей темы
    public String getCurrentTheme() {
        return currentTheme;
    }

    // Метод для инициализации списка слов в зависимости от выбранной темы
    private void initializeWords() {
        words.clear();  // Очистка списка слов
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

    // Начало новой игры
    public String startGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        return words.get(currentWordIndex);  // Возвращаем первое слово
    }

    // Метод для получения следующего слова
    public String nextWord(boolean isCorrect) {
        if (gameOver) return "Игра завершена!";

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
        if (gameOver) return "Игра завершена!";
        currentWordIndex++;
        if (currentWordIndex >= words.size()) {
            gameOver = true;
            return "Игра завершена! Ваши очки: " + score;
        }
        return words.get(currentWordIndex);
    }

    // Получение текущего счета
    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // Сброс игры
    public void resetGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.gameOver = false;
        initializeWords();  // Переинициализируем слова
    }
    public void endGame() {
        this.gameOver = true;  // Завершаем игру
        this.currentWordIndex = words.size();  // Можно установить текущий индекс в конец списка слов
    }
}
