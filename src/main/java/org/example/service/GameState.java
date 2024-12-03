package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class GameState {
    private List<String> players = new ArrayList<>();
    private List<String> words;             // Список слов для игры
    private int team1Score;                  // Очки для команды 1
    private int team2Score;                  // Очки для команды 2
    private boolean gameOver;                // Статус завершения игры
    private String currentTheme;             // Текущая тема игры
    private long startTime;                  // Время начала игры
    private int round;                       // Текущий раунд
    private String team1Name;                // Имя команды 1
    private String team2Name;                // Имя команды 2
    private String currentTeam;              // Текущая команда (для поочередных ходов)
    private static final int MAX_ROUNDS = 6; // Максимальное количество раундов
    private static final long TIME_LIMIT = 60000; // Время для угадывания (1 минута)

    public GameState() {
        this.words = new ArrayList<>();
        this.team1Score = 0;
        this.team2Score = 0;
        this.gameOver = false;
        this.round = 1;
        this.currentTeam = "Team 1"; // Начинает команда 1
        this.currentTheme = null;
        initializeWords();
        this.startTime = System.currentTimeMillis();
    }

    // Метод для выбора команды
    public void selectTeam(String team1Name, String team2Name) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
    }

    // Метод для выбора темы
    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();  // Загружаем слова для выбранной темы
    }

    // Метод для инициализации списка слов в зависимости от выбранной темы
    public void initializeWords() {
        words.clear();

        try {
            if (currentTheme == null) {
                return; // Если тема не выбрана, не инициализируем слова
            }

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
            Collections.shuffle(words);  // Перемешиваем слова
        } catch (IOException e) {
            e.printStackTrace();
            words.add("Ошибка при загрузке слов.");
        }
    }

    // Загружает слова из файла
    private List<String> loadWordsFromFile(String filePath) throws IOException {
        List<String> loadedWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedWords.add(line.trim());
            }
        }
        return loadedWords;
    }

    // Метод для получения текущей команды
    public String getCurrentTeam() {
        return currentTeam;
    }

    // Метод для переключения команды (после каждого хода)
    public void switchTeam() {
        currentTeam = currentTeam.equals("Team 1") ? "Team 2" : "Team 1";
    }

    // Метод для получения текущего слова для угадывания
    public String nextWord(boolean isStartOfRound) {
        if (words.isEmpty()) {
            return "Слова закончились!";
        }

        if (isStartOfRound) {
            round++;  // Переход к следующему раунду
        }

        String word = words.remove(0);
        return word;
    }

    public List<String> getWords() {
        return words;
    }

    // Метод для перезапуска игры
    public void restartGame() {
        // Перезапускаем список слов и сбрасываем игровые параметры
        this.words = new ArrayList<>(Arrays.asList("apple", "banana", "cherry", "date"));  // Пример
        this.round = 0;  // Сбросить раунд
        this.gameOver = false;  // Игра не закончена
    }

    // Метод для пропуска слова
    public String skipWord() {
        if (words.isEmpty()) {
            return "Слова закончились!";
        }

        String word = words.remove(0);  // Пропускаем слово и убираем его из списка
        return word;
    }

    // Метод для начала игры
    public String startGame() {
        if (currentTheme != null && team1Name != null && team2Name != null) {
            this.startTime = System.currentTimeMillis();
            this.round = 1;
            this.gameOver = false;
        }
        return null;
    }


    // Метод для проверки, завершена ли игра
    public boolean isGameOver() {
        return gameOver;
    }

    // Метод для получения очков команды
    public int getTeamScore(String teamName) {
        if (teamName.equals(team1Name)) {
            return team1Score;
        } else if (teamName.equals(team2Name)) {
            return team2Score;
        }
        return 0;
    }

    // Метод для обновления очков
    public void updateScore(int score) {
        if (currentTeam.equals("Team 1")) {
            team1Score += score;
        } else {
            team2Score += score;
        }
    }

    // Метод для завершения игры
    public void endGame() {
        this.gameOver = true;
    }

    // Метод для получения времени, прошедшего с начала игры
    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    // Получение имени команды 1
    public String getTeam1Name() {
        return team1Name;
    }

    // Получение имени команды 2
    public String getTeam2Name() {
        return team2Name;
    }

    // Получение текущей темы
    public String getCurrentTheme() {
        return currentTheme;
    }

    // Получение текущего раунда
    public int getRound() {
        return round;
    }

    // Проверка на наличие завершенной игры
    public boolean isGameFinished() {
        return round > MAX_ROUNDS || gameOver;
    }

    // Метод для возвращения информации об игре
    public String getGameInfo() {
        return String.format("Тема: %s, Раунд: %d, Команда 1: %s, Команда 2: %s", currentTheme, round, team1Name, team2Name);
    }

    public List<String> getPlayers() {
        return players;
    }

}
