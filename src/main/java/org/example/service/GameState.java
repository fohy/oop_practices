package org.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class GameState {
    private int currentRound = 1;
    private List<String> players = new ArrayList<>();
    private List<String> words;
    private int team1Score;
    private int team2Score;
    private boolean gameOver;
    private String currentTheme;
    private long roundStartTime;
    private int round;
    private String team1Name;
    private String team2Name;
    private String currentTeam;
    private static final int MAX_ROUNDS = 6;
    private static final long ROUND_TIME_LIMIT = 30000;
    private Boolean isTeam1Turn;


    public GameState() {
        this.words = new ArrayList<>();
        this.team1Score = 0;
        this.team2Score = 0;
        this.gameOver = false;
        this.round = 1;
        this.currentTeam = "Team 1";
        this.currentTheme = null;
        initializeWords();
        this.isTeam1Turn = true;
    }

    public void selectTeam(String team1Name, String team2Name) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
    }

    public void selectTheme(String theme) {
        this.currentTheme = theme;
        initializeWords();
    }

    public void initializeWords() {
        words.clear();

        try {
            if (currentTheme == null) {
                return;
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
                loadedWords.add(line.trim());
            }
        }
        return loadedWords;
    }

    public String getCurrentTeam() {
        return currentTeam;
    }

    public String nextWord(boolean isStartOfRound) {
        if (words.isEmpty()) {
            return "Слова закончились!";
        }

        if (isStartOfRound) {
            round++;
        }

        String word = words.remove(0);
        return word;
    }

    public List<String> getWords() {
        return words;
    }

    public void restartGame() {
        this.words = new ArrayList<>(Arrays.asList("apple", "banana", "cherry", "date"));
        this.round = 0;
        this.gameOver = false;
    }

    public String skipWord() {
        if (words.isEmpty()) {
            return "Слова закончились!";
        }

        String word = words.remove(0);
        return word;
    }

    public String startGame() {
        if (currentTheme != null && team1Name != null && team2Name != null) {
            this.roundStartTime = System.currentTimeMillis();
            this.round = 1;
            this.gameOver = false;
        }
        return null;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTeamScore(String teamName) {
        if (teamName.equals(team1Name)) {
            return team1Score;
        } else if (teamName.equals(team2Name)) {
            return team2Score;
        }
        return 0;
    }

    public void updateScore(int score) {
        if (currentTeam.equals("Team 1")) {
            team1Score += score;
        } else {
            team2Score += score;
        }
    }

    public void endGame() {
        this.gameOver = true;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - roundStartTime;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public int getRound() {
        return round;
    }

    public boolean isGameFinished() {
        return round > MAX_ROUNDS || gameOver;
    }

    public String getGameInfo() {
        return String.format("Тема: %s, Раунд: %d, Команда 1: %s, Команда 2: %s", currentTheme, round, team1Name, team2Name);
    }

    public List<String> getPlayers() {
        return players;
    }
    public void nextRound() {
        currentRound++;
        switchTeam();
    }
    public void switchTeam() {
        this.isTeam1Turn = !this.isTeam1Turn;
        currentTeam = isTeam1Turn ? "Team 1" : "Team 2";
    }
    public String trackTime() {
        long elapsedTime = System.currentTimeMillis() - roundStartTime;
        long remainingTime = ROUND_TIME_LIMIT - elapsedTime;

        if (remainingTime <= 0) {
            switchTeam(); // Переключаем команду, когда время истекло
            roundStartTime = System.currentTimeMillis(); // Сбрасываем время
            return "Время истекло! Следующий раунд.";
        }

        long secondsLeft = remainingTime / 1000;
        return "Осталось времени: " + secondsLeft + " секунд";
    }

}