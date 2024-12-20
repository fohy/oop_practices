package org.example.bot;

import org.example.service.AliasGameService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AliasBot extends TelegramLongPollingBot {
    private final TelegramCommandHandler commandHandler;

    public AliasBot() {
        IMessageSender messageSender = new TelegramMessageSender(this);
        this.commandHandler = new TelegramCommandHandler(new AliasGameService(), messageSender);
    }

    @Override
    public String getBotUsername() {
        return "StudyProject_Bot";
    }

    @Override
    public String getBotToken() {
        return "7756968278:AAG76WowoPUeqdBAbHZSBt_K-I4wHmYYUiA";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String userMessage = update.getMessage().getText();
        String chatId = String.valueOf(update.getMessage().getChatId());
        commandHandler.handleCommand(userMessage, chatId);
        System.out.println(chatId + " написал(а): " + userMessage);
    }
}