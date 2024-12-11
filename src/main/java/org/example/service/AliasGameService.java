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
    private final Map<Integer, Lobby> lobbies = new HashMap<>();

    // Создание нового лобби
    public int createLobby(Long creatorChatId) {
        int lobbyId = (int) (Math.random() * 10000);  // Простой случайный ID
        Lobby lobby = new Lobby(lobbyId, creatorChatId);
        lobbies.put(lobbyId, lobby);
        return lobbyId;
    }

    // Получение лобби по ID
    public Lobby getLobbyById(int lobbyId) {
        return lobbies.get(lobbyId);
    }

    // Добавление участника в лобби
    public boolean joinLobby(int lobbyId, Long chatId) {
        Lobby lobby = getLobbyById(lobbyId);
        if (lobby != null && !lobby.isFull()) {
            return lobby.addParticipant(chatId);
        }
        return false;
    }

    // Поиск лобби по участнику
    public Lobby getLobbyByParticipant(Long chatId) {
        for (Lobby lobby : lobbies.values()) {
            if (lobby.getParticipants().contains(chatId)) {
                return lobby;
            }
        }
        return null;
    }

    // Выход из лобби
    public boolean exitLobby(int lobbyId, Long chatId) {
        Lobby lobby = getLobbyById(lobbyId);
        if (lobby != null && lobby.getParticipants().contains(chatId)) {
            // Убираем участника из лобби
            lobby.getParticipants().remove(chatId);

            // Если лобби пустое, удаляем его
            if (lobby.getParticipants().isEmpty()) {
                lobbies.remove(lobbyId);
            }

            return true;
        }
        return false;
    }

    private final AtomicInteger lobbyIdCounter = new AtomicInteger(1);


    public GameState getGameState(Long chatId) {
        return gameStates.get(chatId);
    }

    public void startNewGame(Long chatId) {
        gameStates.put(chatId, new GameState());
    }



    public int getLobbyIdByChatId(Long chatId) {
        for (Map.Entry<Integer, Lobby> entry : lobbies.entrySet()) {
            if (entry.getValue().getParticipants().contains(chatId)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public boolean startGameEarly(int lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }

        List<Long> participants = new ArrayList<>(lobby.getParticipants());
        if (participants.size() < 1) {
            return false;
        }

        for (Long chatId : participants) {
            GameState gameState = gameStates.get(chatId);
            if (gameState == null) {
                gameState = new GameState();
                gameStates.put(chatId, gameState);
            }


            if (gameState.getWords().isEmpty()) {
                gameState.restartGame();
            }


            gameState.startGame();
        }

        return true;
    }
    public List<Long> getLobbyPlayers(int lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            return new ArrayList<>(lobby.getParticipants());
        }
        return new ArrayList<>();
    }

    private void addToLobby(int lobbyId, Long chatId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.addParticipant(chatId);
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
