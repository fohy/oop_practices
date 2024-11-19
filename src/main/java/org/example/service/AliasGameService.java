package org.example.service;

import java.util.HashMap;
import java.util.Map;

public class AliasGameService {

    private Map<Long, GameState> gameStates = new HashMap<>();

    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    public void startNewGame(Long chatId) {
        gameStates.put(chatId, new GameState());
    }

    public void endGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState != null) {
            gameState.endGame();
        }
    }

    public String startGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null) {
            startNewGame(chatId);
            gameState = gameStates.get(chatId);
        }
        return gameState.startGame();
    }

    public String nextWord(Long chatId, boolean isCorrect) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null || gameState.isGameOver()) {
            return "";
        }
        return gameState.nextWord(isCorrect);
    }

    public String skipWord(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null || gameState.isGameOver()) {
            return "";
        }
        return gameState.skipWord();
    }

    public void selectTheme(Long chatId, String theme) {
        GameState gameState = gameStates.get(chatId);
        if (gameState != null) {
            gameState.selectTheme(theme);
        }
    }
}
