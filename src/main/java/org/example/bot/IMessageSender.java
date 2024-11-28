package org.example.bot;

public interface IMessageSender {
    void sendMessage(String chatId, String message, String replyMarkup);
}
