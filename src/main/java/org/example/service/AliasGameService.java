package org.example.service;

import org.example.service.GameState;

import java.util.HashMap;
import java.util.Map;

public class AliasGameService {

    // Карта для хранения состояний игры по chatId
    private final Map<Long, GameState> gameStates;

    public AliasGameService() {
        this.gameStates = new HashMap<>();
    }

    // Начать новую игру для пользователя
    public void startNewGame(Long chatId) {
        this.gameStates.put(chatId, new GameState(chatId));
    }

    // Получить текущее состояние игры для пользователя
    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    // Старт игры, для пользователя
    public String startGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        return gameState.startGame();
    }

    // Получить следующее слово для пользователя
    public String nextWord(Long chatId, boolean isCorrect) {
        GameState gameState = gameStates.get(chatId);
        return gameState.nextWord(isCorrect);
    }

    // Пропустить слово для пользователя
    public String skipWord(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        return gameState.skipWord();
    }

    // Подтвердить продолжение игры
    public void confirmContinue(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        gameState.confirmContinue();
    }

    // Выбрать тему для пользователя
    public void selectTheme(Long chatId, String theme) {
        GameState gameState = gameStates.get(chatId);
        gameState.selectTheme(theme);
    }

    // Сбросить игру для пользователя
    public void resetGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        gameState.resetGame();
    }

    // Проверить, завершена ли игра для пользователя
    public boolean isGameOver(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        return gameState.isGameOver();
    }
}
