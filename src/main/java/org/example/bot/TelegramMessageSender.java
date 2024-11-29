package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramMessageSender implements IMessageSender {
    private final TelegramLongPollingBot bot;

    public TelegramMessageSender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(String chatId, String message, ReplyKeyboardMarkup replyMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        // Если replyMarkup не null, устанавливаем его в сообщение
        if (replyMarkup != null) {
            sendMessage.setReplyMarkup(replyMarkup);
        }

        try {
            // Отправляем сообщение
            bot.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
