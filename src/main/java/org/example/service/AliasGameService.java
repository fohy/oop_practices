package org.example.service;

import org.example.service.GameState;

import java.util.HashMap;
import java.util.Map;

public class AliasGameService {

    private final Map<Long, GameState> gameStates;

    public AliasGameService() {
        this.gameStates = new HashMap<>();
    }

    public void startNewGame(Long chatId) {
        this.gameStates.put(chatId, new GameState());
    }

    public void selectTheme(Long chatId, String theme) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            gameState.selectTheme(theme);
        }
    }

    public String startGame(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.startGame();
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    public String nextWord(Long chatId, boolean isCorrect) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.nextWord(isCorrect);
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    public String skipWord(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.skipWord();
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    public void resetGame(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            gameState.resetGame();
        }
    }

    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    public int getScore(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.getScore();
        }
        return 0;
    }
}
