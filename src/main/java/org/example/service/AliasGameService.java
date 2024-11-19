package org.example.service;

import org.example.service.GameState;

import java.util.HashMap;
import java.util.Map;

public class AliasGameService {

    private final Map<Long, GameState> gameStates;

    public AliasGameService() {
        this.gameStates = new HashMap<>();
    }

    // Начало новой игры для пользователя
    public void startNewGame(Long chatId) {
        this.gameStates.put(chatId, new GameState());
    }

    // Выбор темы для игры
    public void selectTheme(Long chatId, String theme) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            gameState.selectTheme(theme);
        }
    }

    // Старт игры для текущего пользователя
    public String startGame(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.startGame();
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    // Переход к следующему слову
    public String nextWord(Long chatId, boolean isCorrect) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.nextWord(isCorrect);
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    // Пропуск текущего слова
    public String skipWord(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.skipWord();
        }
        return "Ошибка: Игра не была инициализирована.";
    }

    // Завершение игры и сброс состояния
    public void resetGame(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            gameState.resetGame();
        }
    }

    // Получение состояния игры для пользователя
    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    // Получение счета игрока
    public int getScore(Long chatId) {
        GameState gameState = getGameState(chatId);
        if (gameState != null) {
            return gameState.getScore();
        }
        return 0;
    }
}
