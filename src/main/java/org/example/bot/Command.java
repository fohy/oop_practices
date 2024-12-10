package org.example.bot;

public interface Command {
    void execute(String chatId, String command);
}
