package org.example.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Lobby {
    private final int lobbyId;
    private final Long creatorChatId;
    private final Set<Long> participants = new HashSet<>();

    public Lobby(int lobbyId, Long creatorChatId) {
        this.lobbyId = lobbyId;
        this.creatorChatId = creatorChatId;
        this.participants.add(creatorChatId);
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public boolean addParticipant(Long chatId) {
        return participants.add(chatId);
    }

    public boolean isFull() {
        return participants.size() >= 2;
    }

    public Set<Long> getParticipants() {
        return Collections.unmodifiableSet(participants);
    }
}
