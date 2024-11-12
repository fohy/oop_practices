package org.example;

import java.util.Collections;

public class GameSession {
    private QuestionManager questionManager;
    private int score;
    private boolean gameStarted;

    public GameSession(QuestionManager questionManager) {
        this.questionManager = questionManager;
        this.score = 0;
        this.gameStarted = false;
    }

    // Начало игры
    public void startGame() {
        gameStarted = true;
        score = 0; // сбрасываем баллы
        questionManager.reset(); // сбрасываем вопросы
    }

    // Завершаем игру
    public void endGame() {
        gameStarted = false;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public Question getNextQuestion() {
        return questionManager.getNextQuestion();
    }

    public Question getCurrentQuestion() {
        return questionManager.getCurrentQuestion();
    }

    public void reset() {
        questionManager.reset();
        score = 0;
    }

    public int getTotalQuestions() {
        return questionManager.getTotalQuestions();
    }
}
