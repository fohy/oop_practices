package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {
    private List<String> words;  // Список слов, загруженных из файла
    private int score;  // Очки
    private boolean gameOver;  // Флаг завершения игры
    private String currentTheme;  // Текущая тема
    private long startTime;  // Время начала игры
    private int round;  // Номер текущего раунда
    private static final int MAX_ROUNDS = 3;  // Максимальное количество раундов
    private static final long TIME_LIMIT = 60000;  // 1 минута в миллисекундах

    public GameState() {
        this.words = new ArrayList<>();
        this.score = 0;
        this.gameOver = false;
        this.currentTheme = "Технологии";  // По умолчанию
        this.round = 1;  // Начинаем с первого раунда
        initializeWords();
        this.startTime = System.currentTimeMillis();  // Засекаем время начала игры
    }

    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();  // Инициализируем слова для выбранной темы
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    private void initializeWords() {
        words.clear();  // Очищаем список слов перед загрузкой новых

        try {
            switch (currentTheme) {
                case "Технологии":
                    words.addAll(loadWordsFromFile("src/main/java/org/example/resources/tech.txt"));
                    break;
                case "Животные":
                    words.addAll(loadWordsFromFile("src/main/java/org/example/resources/animals_words.txt"));
                    break;
                case "Еда":
                    words.addAll(loadWordsFromFile("src/main/java/org/example/resources/food_words.txt"));
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестная тема: " + currentTheme);
            }

            // Перемешиваем список слов, чтобы они выпадали случайным образом
            Collections.shuffle(words);

        } catch (IOException e) {
            e.printStackTrace();
            words.add("Ошибка при загрузке слов.");
        }
    }

    private List<String> loadWordsFromFile(String filePath) throws IOException {
        List<String> loadedWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedWords.add(line.trim());  // Чтение строки и добавление в список
            }
        }
        return loadedWords;
    }

    public String startGame() {
        this.score = 0;
        this.gameOver = false;
        this.round = 1;  // Начинаем с первого раунда
        this.startTime = System.currentTimeMillis();  // Обновляем время для нового раунда
        return getNextWord();  // Возвращаем первое случайное слово
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
            score++;  // Увеличиваем счет за правильный ответ
        }

        if (round < MAX_ROUNDS) {
            return getNextWord();  // Переходим к следующему случайному слову
        } else {
            gameOver = true;  // Игра завершена после достижения максимального количества раундов
            return "Игра завершена! Ваши очки: " + score;
        }
    }

    public String skipWord() {
        if (gameOver) {
            return "Игра завершена! Ваши очки: " + score;
        }

        return getNextWord();  // Переходим к следующему случайному слову
    }

    private String getNextWord() {
        if (words.isEmpty()) {
            return "Нет доступных слов!";
        }

        // Возвращаем следующее случайное слово из списка
        String nextWord = words.get(0);
        words.remove(0);  // Убираем использованное слово из списка

        if (words.isEmpty()) {
            startNewRound();  // Если все слова использованы, начинаем новый раунд
            return "Раунд " + round + " завершен! Нажмите 'Следующее', чтобы начать следующий раунд.";
        }

        return nextWord;  // Возвращаем слово
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        this.score = 0;
        this.gameOver = false;
        initializeWords();  // Перезагружаем слова
    }

    public void endGame() {
        this.gameOver = true;
    }

    public void startNewRound() {
        if (round < MAX_ROUNDS) {
            round++;
            this.startTime = System.currentTimeMillis();  // Обновляем время для нового раунда
            initializeWords();  // Перезагружаем слова для следующего раунда
        } else {
            this.gameOver = true;  // Заканчиваем игру после 3 раундов
        }
    }

    public int getRound() {
        return round;
    }
}
