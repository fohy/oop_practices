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


    public int createLobby(Long creatorChatId) {
        int newLobbyId = lobbyIdCounter.getAndIncrement();
        Lobby newLobby = new Lobby(newLobbyId, creatorChatId);
        lobbies.put(newLobbyId, newLobby);
        return newLobbyId;
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



    public boolean joinLobby(int lobbyId, Long participantChatId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            return lobby.addParticipant(participantChatId);
        }
        return false;
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
