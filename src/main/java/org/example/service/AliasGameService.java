package org.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AliasGameService {

    private Map<Long, GameState> gameStates = new HashMap<>();

    private Map<Integer, Lobby> lobbies = new ConcurrentHashMap<>();

    private final AtomicInteger lobbyIdCounter = new AtomicInteger(1);


    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    public void startNewGame(Long chatId) {
        gameStates.put(chatId, new GameState());
    }

    // Создание нового лобби
    public int createLobby(Long creatorChatId) {
        int newLobbyId = lobbyIdCounter.getAndIncrement(); // Генерируем новый уникальный идентификатор лобби
        Lobby newLobby = new Lobby(newLobbyId, creatorChatId); // Создаем новое лобби
        lobbies.put(newLobbyId, newLobby); // Добавляем его в коллекцию лобби
        return newLobbyId; // Возвращаем номер созданного лобби
    }

    public int getLobbyIdByChatId(Long chatId) {
        for (Map.Entry<Integer, Lobby> entry : lobbies.entrySet()) {
            if (entry.getValue().getParticipants().contains(chatId)) {
                return entry.getKey(); // Возвращаем lobbyId
            }
        }
        return -1; // Если лобби не найдено для chatId
    }

    public boolean startGameEarly(int lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);  // Получаем лобби по ID
        if (lobby == null) {
            return false;  // Лобби не найдено
        }

        List<Long> participants = new ArrayList<>(lobby.getParticipants());  // Получаем участников лобби
        if (participants.size() < 1) {  // Убедитесь, что в лобби есть хотя бы один участник
            return false;
        }

        for (Long chatId : participants) {
            GameState gameState = gameStates.get(chatId);
            if (gameState == null) {
                gameState = new GameState();
                gameStates.put(chatId, gameState);
            }

            // Перезапускаем игру (инициализация слов при старте игры досрочно)
            if (gameState.getWords().isEmpty()) {
                gameState.restartGame();  // Загружаем новые слова для игры
            }

            // Инициализируем игру для каждого участника
            gameState.startGame();  // Запуск игры
        }

        return true;  // Игра успешно началась
    }


    // Присоединение к лобби
    public boolean joinLobby(int lobbyId, Long participantChatId) {
        Lobby lobby = lobbies.get(lobbyId); // Получаем лобби по ID
        if (lobby != null) {
            return lobby.addParticipant(participantChatId); // Добавляем участника в лобби
        }
        return false; // Лобби с таким ID не найдено
    }

    public List<Long> getLobbyPlayers(int lobbyId) {
        Lobby lobby = lobbies.get(lobbyId); // Извлекаем лобби
        if (lobby != null) {
            return new ArrayList<>(lobby.getParticipants()); // Возвращаем список участников
        }
        return new ArrayList<>(); // Если лобби не найдено, возвращаем пустой список
    }

    private void addToLobby(int lobbyId, Long chatId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.addParticipant(chatId); // Используем метод `addParticipant` из класса `Lobby`
        } else {
            throw new IllegalArgumentException("Лобби с ID " + lobbyId + " не существует.");
        }
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
