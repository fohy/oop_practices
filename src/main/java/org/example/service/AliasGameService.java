package org.example.service;

import java.util.HashMap;
import java.util.Map;

public class AliasGameService {

    private Map<Long, GameState> gameStates = new HashMap<>();  // Хранение состояний игр по chatId

    // Метод для получения состояния игры по chatId
    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    // Метод для начала новой игры
    public void startNewGame(Long chatId) {
        gameStates.put(chatId, new GameState());  // Инициализируем новую игру для chatId
    }

    // Метод для завершения игры
    public void endGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState != null) {
            gameState.endGame();  // Завершаем игру в GameState
        }
    }

    // Метод для старта игры
    public String startGame(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null) {
            startNewGame(chatId);  // Если игра не начата, начинаем новую
            gameState = gameStates.get(chatId);
        }
        return gameState.startGame();
    }

    // Метод для получения следующего слова
    public String nextWord(Long chatId, boolean isCorrect) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null || gameState.isGameOver()) {
            return "";  // Если игры нет или она завершена
        }
        return gameState.nextWord(isCorrect);
    }

    // Метод для пропуска слова
    public String skipWord(Long chatId) {
        GameState gameState = gameStates.get(chatId);
        if (gameState == null || gameState.isGameOver()) {
            return "";  // Если игры нет или она завершена
        }
        return gameState.skipWord();
    }

    // Метод для выбора темы
    public void selectTheme(Long chatId, String theme) {
        GameState gameState = gameStates.get(chatId);
        if (gameState != null) {
            gameState.selectTheme(theme);  // Обновляем тему в состоянии игры
        }
    }
}
