package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface IMessageSender {
    void sendMessage(String chatId, String message, ReplyKeyboardMarkup replyMarkup);
}
