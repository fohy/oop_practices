package org.example.bot;

public interface MessageSender {
    void sendMessage(String chatId, String message, String replyMarkup);
}
