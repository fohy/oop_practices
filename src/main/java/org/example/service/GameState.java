package org.example.service;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<String> words;  // Список слов для игры
    private int currentWordIndex;  // Индекс текущего слова
    private int score;  // Количество очков
    private int round;  // Номер раунда
    private boolean gameOver;  // Флаг завершения игры
    private String currentTheme;  // Тема текущей игры

    private static final int MAX_ROUNDS = 3;  // Максимальное количество раундов

    // Конструктор по умолчанию
    public GameState() {
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
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
                words.add("Яблоко");
                words.add("Пицца");
                words.add("Хлеб");
                words.add("Молоко");
                break;
            default:
                words.add("Ошибка");  // В случае, если тема не выбрана
                break;
        }
    }

    // Старт игры, возвращает первое слово из списка
    public String startGame() {
        if (currentWordIndex < words.size()) {
            return words.get(currentWordIndex);
        } else {
            return "Все слова закончились!";
        }
    }

    // Переход к следующему слову и начисление очков за правильное решение
    public String nextWord(boolean isCorrect) {
        if (isCorrect) {
            score++;  // Увеличиваем счет за правильный ответ
        }
        currentWordIndex++;  // Переходим к следующему слову

        // Если все слова пройдены, завершаем игру
        if (currentWordIndex >= words.size()) {
            gameOver = true;
            return "Игра завершена! Ваши очки: " + score;
        }
        return words.get(currentWordIndex);
    }

    // Пропуск слова
    public String skipWord() {
        currentWordIndex++;  // Переходим к следующему слову
        if (currentWordIndex >= words.size()) {
            return "Все слова закончились!";
        }
        return words.get(currentWordIndex);
    }

    // Завершение игры, сбрасывает состояние игры
    public void resetGame() {
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        initializeWords();  // Переинициализируем слова
    }

    // Получение текущего счета
    public int getScore() {
        return score;
    }

    // Получение флага завершения игры
    public boolean isGameOver() {
        return gameOver;
    }

    // Получение текущего раунда
    public int getRound() {
        return round;
    }
}
