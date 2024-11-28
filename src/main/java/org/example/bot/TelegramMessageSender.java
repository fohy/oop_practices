package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelegramMessageSender implements IMessageSender {
    private final TelegramLongPollingBot bot;

    public TelegramMessageSender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(String chatId, String message, String replyMarkupJson) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        if (replyMarkupJson != null) {
            try {
                // Преобразуем строку JSON в объект ReplyKeyboardMarkup
                ReplyKeyboardMarkup replyMarkup = new ObjectMapper().readValue(replyMarkupJson, ReplyKeyboardMarkup.class);
                sendMessage.setReplyMarkup(replyMarkup);
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage.setReplyMarkup(new ReplyKeyboardMarkup()); // В случае ошибки используем пустую клавиатуру
            }
        }

        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
